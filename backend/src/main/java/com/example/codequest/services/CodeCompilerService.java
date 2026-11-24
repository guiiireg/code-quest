package com.example.codequest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

@Service
public class CodeCompilerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeCompilerService.class);
    private static final Pattern PUBLIC_CLASS = Pattern.compile("\\bpublic\\s+class\\s+([a-zA-Z_][a-zA-Z0-9_]*)\\b");
    private static final int MAX_OUTPUT_BYTES = 16 * 1024;
    private static final Duration COMPILER_TIMEOUT = Duration.ofSeconds(5);
    private static final Duration RUNTIME_TIMEOUT = Duration.ofSeconds(1);

    public record CompilationResult(boolean success, String output) {}

    public CompilationResult compile(String sourceCode) {
        var declaration = PUBLIC_CLASS.matcher(sourceCode);
        if (!declaration.find()) {
            return new CompilationResult(false, "Compilation error: Could not find a valid public class declaration.");
        }

        String className = declaration.group(1);
        Path tempDir = null;
        try {
            tempDir = Files.createTempDirectory("codequest-sandbox-");
            Path javaFile = Files.writeString(tempDir.resolve(className + ".java"), sourceCode);
            String runtime = findContainerRuntime(tempDir);

            CompilationResult result = runtime == null
                    ? run(List.of(Path.of(System.getProperty("java.home"), "bin", "javac").toString(),
                            "-J-Xmx64m", "-J-XX:ActiveProcessorCount=1", "-proc:none",
                            javaFile.getFileName().toString()), tempDir, COMPILER_TIMEOUT)
                    : compileInContainer(runtime, tempDir, className);

            if (!result.success()) {
                return result;
            }
            String output = result.output().isBlank() ? "" : "\n" + result.output();
            return new CompilationResult(true, "Compilation successful." + output);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            return new CompilationResult(false, "Evaluation was interrupted.");
        } catch (IOException exception) {
            LOGGER.warn("Could not start Java evaluation", exception);
            return new CompilationResult(false, "Java evaluation is temporarily unavailable.");
        } finally {
            deleteWorkspace(tempDir);
        }
    }

    private String findContainerRuntime(Path directory) throws InterruptedException {
        for (String runtime : List.of("podman", "docker")) {
            try {
                if (run(List.of(runtime, "info", "--format", "{{.ServerVersion}}"), directory, RUNTIME_TIMEOUT).success()) {
                    return runtime;
                }
            } catch (IOException ignored) {
                // The executable is unavailable on this host.
            }
        }
        return null;
    }

    private CompilationResult compileInContainer(String runtime, Path directory, String className)
            throws IOException, InterruptedException {
        String containerName = "codequest-" + UUID.randomUUID();
        try {
            return run(List.of(runtime, "run", "--rm", "--name", containerName,
                    "--net=none", "--memory=128m", "--cpus=0.5", "--pids-limit=64",
                    "-v", directory.toAbsolutePath() + ":/workspace:Z", "-w", "/workspace",
                    "docker.io/library/eclipse-temurin:21-alpine",
                    "sh", "-c", "javac -proc:none \"$1.java\" && exec java \"$1\"", "sh", className),
                    directory, COMPILER_TIMEOUT);
        } finally {
            try {
                run(List.of(runtime, "rm", "-f", containerName), directory, RUNTIME_TIMEOUT);
            } catch (IOException exception) {
                LOGGER.warn("Could not clean up the evaluation container", exception);
            }
        }
    }

    private CompilationResult run(List<String> command, Path directory, Duration timeout)
            throws IOException, InterruptedException {
        Process process = new ProcessBuilder(command)
                .directory(directory.toFile())
                .redirectErrorStream(true)
                .start();
        long deadline = System.nanoTime() + timeout.toNanos();
        FutureTask<byte[]> output = new FutureTask<>(() -> {
            try (var stream = process.getInputStream()) {
                return stream.readNBytes(MAX_OUTPUT_BYTES + 1);
            }
        });
        Thread.startVirtualThread(output);

        try {
            process.getOutputStream().close();
            if (!process.waitFor(timeout.toNanos(), TimeUnit.NANOSECONDS)) {
                return new CompilationResult(false, "Evaluation timed out.");
            }
            byte[] bytes = output.get(Math.max(0, deadline - System.nanoTime()), TimeUnit.NANOSECONDS);
            if (bytes.length > MAX_OUTPUT_BYTES) {
                return new CompilationResult(false, "Evaluation output exceeded the 16 KiB limit.");
            }
            return new CompilationResult(process.exitValue() == 0, new String(bytes, StandardCharsets.UTF_8).strip());
        } catch (TimeoutException exception) {
            return new CompilationResult(false, "Evaluation timed out.");
        } catch (ExecutionException exception) {
            throw new IOException("Could not read Java evaluation output", exception.getCause());
        } finally {
            process.destroyForcibly();
            output.cancel(true);
        }
    }

    private void deleteWorkspace(Path directory) {
        if (directory == null) {
            return;
        }
        try (var paths = Files.walk(directory)) {
            for (Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists(path);
            }
        } catch (IOException exception) {
            LOGGER.warn("Could not clean up the evaluation workspace", exception);
        }
    }
}
