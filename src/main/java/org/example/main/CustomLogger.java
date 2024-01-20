package org.example.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomLogger {

    private final String LOG_FILE_PATH = "logs.txt";

    public void info(String message) {
        log("INFO", message);
    }

    public void warning(String message) {
        log("WARNING", message);
    }

    public void error(String message) {
        log("ERROR", message);
    }

    private void log(String logLevel, String message) {
        String logEntry = buildLogEntry(logLevel, message);

        // Print to console
        System.out.println(logEntry);

        // Write to log file
        writeToFile(logEntry);
    }

    private String buildLogEntry(String logLevel, String message) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(new Date());

        return String.format("[%s] %s - %s - %s.%s(%d): %s",
                formattedDate, logLevel, Thread.currentThread().getName(), className, methodName, lineNumber, message);
    }

    private void writeToFile(String logEntry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your requirements
        }
    }
}
