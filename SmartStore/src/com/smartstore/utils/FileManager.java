package com.smartstore.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileManager — handles all file reading and writing for SmartStore.
 *
 * Demonstrates:
 *   - File I/O using BufferedReader and BufferedWriter
 *   - try-with-resources (auto-closes streams)
 *   - IOException exception handling
 */
public class FileManager {

    /**
     * Reads all lines from a file and returns them as a List.
     * Returns an empty list if the file doesn't exist yet.
     *
     * @param filePath relative path to the file
     * @return list of non-empty lines
     */
    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("  [Info] No existing file at: " + filePath + " — starting fresh.");
            return lines;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.out.println("  [Error] Could not read file: " + filePath);
            System.out.println("          Reason: " + e.getMessage());
        }

        return lines;
    }

    /**
     * Writes a list of strings to a file, one per line.
     * Overwrites the file if it already exists.
     *
     * @param filePath relative path to the file
     * @param lines    data to write
     */
    public static void writeLines(String filePath, List<String> lines) {
        ensureDirectoryExists(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("  [Error] Could not write to file: " + filePath);
            System.out.println("          Reason: " + e.getMessage());
        }
    }

    /**
     * Writes a single string block to a file (used for receipts).
     *
     * @param filePath relative path to the file
     * @param content  full text content to write
     */
    public static void writeFile(String filePath, String content) {
        ensureDirectoryExists(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("  [Error] Could not write receipt: " + filePath);
            System.out.println("          Reason: " + e.getMessage());
        }
    }

    /**
     * Creates parent directories if they don't exist.
     * Prevents FileNotFoundException when writing to a new subdirectory.
     */
    private static void ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }
}
