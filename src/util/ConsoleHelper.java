package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleHelper {

    public static final String RESET   = "\u001B[0m";
    public static final String RED     = "\u001B[31m";
    public static final String GREEN   = "\u001B[32m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String BLUE    = "\u001B[34m";
    public static final String CYAN    = "\u001B[36m";
    public static final String WHITE   = "\u001B[37m";
    public static final String BOLD    = "\u001B[1m";

    public static void printHeader(String title) {
        int boxWidth = 50;
        String border = "═".repeat(boxWidth);

        System.out.println();
        System.out.println(CYAN + "╔" + border + "╗" + RESET);
        System.out.printf(CYAN + "║" + BOLD + " %-" + (boxWidth - 1) + "s" + RESET + CYAN + "║%n" + RESET, title);
        System.out.println(CYAN + "╚" + border + "╝" + RESET);
        System.out.println();
    }

    public static void printSubHeader(String title) {
        System.out.println();
        System.out.println(CYAN + "── " + BOLD + title + RESET + CYAN + " ──" + RESET);
        System.out.println();
    }

    public static void printSuccess(String message) {
        System.out.println(GREEN + "  ✅ " + message + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + "  ❌ " + message + RESET);
    }

    public static void printWarning(String message) {
        System.out.println(YELLOW + "  ⚠️  " + message + RESET);
    }

    public static void printInfo(String message) {
        System.out.println(CYAN + "  ℹ️  " + message + RESET);
    }

    public static void printDivider() {
        System.out.println(CYAN + "──────────────────────────────────────────────────" + RESET);
    }



    public static String readString(Scanner scanner, String prompt) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print(YELLOW + "  " + prompt + ": " + RESET);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                printError("Input cannot be empty. Please try again.");
            }
        }
        return input;
    }

    public static String readStringOptional(Scanner scanner, String prompt, String currentValue) {
        System.out.print(YELLOW + "  " + prompt + " [" + currentValue + "]: " + RESET);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? currentValue : input;
    }

    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(YELLOW + "  " + prompt + " (" + min + "-" + max + "): " + RESET);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                printError("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                printError("Invalid number. Please enter a valid integer.");
            }
        }
    }

    public static LocalDate readDate(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(YELLOW + "  " + prompt + " (yyyy-MM-dd): " + RESET);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                printError("Invalid date format. Please use yyyy-MM-dd (e.g., 2026-05-15).");
            }
        }
    }







}
