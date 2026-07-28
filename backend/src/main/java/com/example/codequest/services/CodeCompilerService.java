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
 * Service gérant la compilation et l'exécution dynamique sécurisée du code Java soumis par les utilisateurs.
 */
@Service
public class CodeCompilerService {

    /**
     * Record représentant le résultat d'une compilation et exécution.
     * 
     * @param success Indique si la compilation et l'exécution ont réussi
     * @param output Les messages de diagnostic ou logs de compilation/exécution
     */
    public record CompilationResult(boolean success, String output) {}

    /**
     * Compile et exécute de manière isolée (Sandbox Container) le code source Java.
     * 
     * @param sourceCode Le code source Java à compiler
     * @return Le résultat de la compilation contenant le statut et la sortie
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
     * Détermine si podman ou docker est disponible sur la machine.
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
     * Extrait le nom de la classe publique depuis le code source.
     * 
     * @param sourceCode Le code source Java
     * @return Le nom de la classe, ou null si non trouvé
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
     * Classe interne représentant un fichier source Java en mémoire.
     */
    private static class JavaSourceFromString extends SimpleJavaFileObject {
        /**
         * Le code source Java sous forme de chaîne de caractères.
         */
        final String code;

        /**
         * Constructeur pour initialiser le fichier source.
         * 
         * @param name Le nom de la classe
         * @param code Le code source
         */
        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
                  JavaFileObject.Kind.SOURCE);
            this.code = code;
        }

        /**
         * Renvoie le contenu du code source.
         * 
         * @param ignoreEncodingErrors Si vrai, ignore les erreurs d'encodage
         * @return Le contenu sous forme de séquence de caractères
         */
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}
