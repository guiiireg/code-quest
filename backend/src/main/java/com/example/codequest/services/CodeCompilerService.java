package com.example.codequest.services;

import org.springframework.stereotype.Service;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import javax.tools.StandardJavaFileManager;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Comparator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Service managing dynamic and secure compilation and execution of user-submitted Java code.
 */
@Service
public class CodeCompilerService {

    /**
     * Record representing compilation and execution result.
     * 
     * @param success Indicates whether compilation and execution succeeded
     * @param output Diagnostic messages or execution output logs
     */
    public record CompilationResult(boolean success, String output) {}

    /**
     * Compiles and executes Java source code in an isolated container sandbox.
     * 
     * @param sourceCode The Java source code to compile
     * @return The compilation result containing execution status and output logs
     */
    public CompilationResult compile(String sourceCode) {
        String className = extractClassName(sourceCode);
        if (className == null || !className.matches("^[a-zA-Z0-9_]+$")) {
            return new CompilationResult(false, "Compilation error: Could not find a valid public class declaration.");
        }

        Path tempDir = null;
        try {
            tempDir = Files.createTempDirectory("codequest-sandbox-");
            Path javaFile = tempDir.resolve(className + ".java");
            Files.writeString(javaFile, sourceCode, StandardCharsets.UTF_8);

            String containerRuntime = getContainerRuntime();
            if (containerRuntime == null) {
                return compileFallbackInProcess(className, sourceCode, tempDir);
            }

            ProcessBuilder pb = new ProcessBuilder(
                containerRuntime, "run", "--rm",
                "--net=none",
                "--memory=128m",
                "--cpus=0.5",
                "--pids-limit=64",
                "-v", tempDir.toAbsolutePath().toString() + ":/workspace:Z",
                "-w", "/workspace",
                "docker.io/library/eclipse-temurin:21-alpine",
                "sh", "-c", "javac " + className + ".java && java " + className
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }

            boolean finished = process.waitFor(5, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                return new CompilationResult(false, "Execution timed out (limit: 5 seconds).");
            }

            int exitCode = process.exitValue();
            if (exitCode == 0) {
                return new CompilationResult(true, "Compilation successful.\n" + output.toString().trim());
            } else {
                return new CompilationResult(false, output.toString().trim());
            }
        } catch (Exception e) {
            return new CompilationResult(false, "Internal compiler error: " + e.getMessage());
        } finally {
            if (tempDir != null && Files.exists(tempDir)) {
                try {
                    Files.walk(tempDir)
                         .sorted(Comparator.reverseOrder())
                         .map(Path::toFile)
                         .forEach(File::delete);
                } catch (Exception ignored) {}
            }
        }
    }

    /**
     * Determines whether podman or docker is available on the host machine.
     */
    private String getContainerRuntime() {
        if (isExecutableAvailable("podman")) {
            return "podman";
        } else if (isExecutableAvailable("docker")) {
            return "docker";
        }
        return null;
    }

    private boolean isExecutableAvailable(String command) {
        try {
            Process p = new ProcessBuilder(command, "--version").start();
            boolean finished = p.waitFor(2, TimeUnit.SECONDS);
            return finished && p.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private CompilationResult compileFallbackInProcess(String className, String sourceCode, Path tempDir) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            return new CompilationResult(false, "Compilation error: System JavaCompiler not found.");
        }

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaSourceFromString file = new JavaSourceFromString(className, sourceCode);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);

        try {
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
            Iterable<String> options = Arrays.asList("-d", tempDir.toAbsolutePath().toString());

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);
            boolean success = task.call();

            StringBuilder output = new StringBuilder();
            if (!success) {
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    output.append("Line ").append(diagnostic.getLineNumber())
                          .append(": ").append(diagnostic.getMessage(null)).append("\n");
                }
            } else {
                output.append("Compilation successful.\n");
            }

            fileManager.close();
            return new CompilationResult(success, output.toString().trim());
        } catch (Exception e) {
            return new CompilationResult(false, "Internal compiler error: " + e.getMessage());
        }
    }

    /**
     * Extracts public class name from Java source code.
     * 
     * @param sourceCode Java source code
     * @return The extracted class name, or null if not found
     */
    private String extractClassName(String sourceCode) {
        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)");
        Matcher matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Inner class representing an in-memory Java source file.
     */
    private static class JavaSourceFromString extends SimpleJavaFileObject {
        /**
         * Java source code string.
         */
        final String code;

        /**
         * Constructor to initialize the in-memory source file.
         * 
         * @param name Class name
         * @param code Source code
         */
        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
                  JavaFileObject.Kind.SOURCE);
            this.code = code;
        }

        /**
         * Returns the source code character content.
         * 
         * @param ignoreEncodingErrors If true, ignore encoding errors
         * @return The source code character sequence
         */
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}

