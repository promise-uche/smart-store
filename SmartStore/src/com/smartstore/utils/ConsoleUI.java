package com.smartstore.utils;

import java.util.Scanner;

/**
 * ConsoleUI — handles all user input and formatted console output.
 *
 * Centralising I/O here means StoreService stays focused on
 * business logic and is easier to test.
 *
 * Demonstrates: exception handling on invalid input (try-catch),
 * input validation loops.
 */
public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);

    // ── Display helpers ──────────────────────────────────────

    public void print(String text) {
        System.out.println(text);
    }

    public void printDivider() {
        System.out.println("  " + "─".repeat(62));
    }

    public void printHeader(String title) {
        System.out.println("\n" + "═".repeat(66));
        System.out.printf("  %s%n", title);
        System.out.println("═".repeat(66));
    }

    public void printSuccess(String message) {
        System.out.println("\n  ✔  " + message + "\n");
    }

    public void printError(String message) {
        System.out.println("\n  ✘  ERROR: " + message + "\n");
    }

    public void showWelcomeBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║            SMARTSTORE v1.0                      ║");
        System.out.println("  ║      Java Capstone Project — ICT Academy        ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void showMainMenu() {
        System.out.println("  ┌─────────────────────────┐");
        System.out.println("  │       MAIN MENU         │");
        System.out.println("  ├─────────────────────────┤");
        System.out.println("  │  [1]  Browse products   │");
        System.out.println("  │  [2]  Search products   │");
        System.out.println("  │  [3]  View cart         │");
        System.out.println("  │  [4]  Checkout          │");
        System.out.println("  │  [5]  Admin panel       │");
        System.out.println("  │  [0]  Exit              │");
        System.out.println("  └─────────────────────────┘");
    }

    // ── Input methods ────────────────────────────────────────

    /**
     * Prompts the user and returns a trimmed String.
     * Loops until a non-empty value is entered.
     */
    public String getStringInput(String prompt) {
        System.out.print("  " + prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Prompts for an integer. Repeats if input is not a valid integer.
     */
    public int getIntInput(String prompt) {
        while (true) {
            System.out.print("  " + prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("  ✘  Please enter a whole number (e.g. 1, 2, 3).\n");
            }
        }
    }

    /**
     * Prompts for a double. Repeats if input is not a valid number.
     * Also rejects negative values.
     */
    public double getDoubleInput(String prompt) {
        while (true) {
            System.out.print("  " + prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println("  ✘  Value cannot be negative.\n");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("  ✘  Please enter a valid number (e.g. 4500.00).\n");
            }
        }
    }
}
