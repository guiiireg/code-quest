package com.example.codequest;

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

@Service
public class CodeCompilerService {

    public record CompilationResult(boolean success, String output) {}

    public CompilationResult compile(String sourceCode) {
        String className = extractClassName(sourceCode);
        if (className == null) {
            return new CompilationResult(false, "Compilation error: Could not find a public class declaration.");
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            return new CompilationResult(false, "Compilation error: System JavaCompiler not found.");
        }

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaSourceFromString file = new JavaSourceFromString(className, sourceCode);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        
        try {
            Path tempDir = Files.createTempDirectory("java-compile");
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
            
            // Cleanup temp dir
            Files.walk(tempDir)
                 .sorted(Comparator.reverseOrder())
                 .map(Path::toFile)
                 .forEach(File::delete);
                 
            return new CompilationResult(success, output.toString().trim());
        } catch (Exception e) {
            return new CompilationResult(false, "Internal compiler error: " + e.getMessage());
        }
    }

    private String extractClassName(String sourceCode) {
        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)");
        Matcher matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
                  JavaFileObject.Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}
