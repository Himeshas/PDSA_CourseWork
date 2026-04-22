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

    public static LocalDate readDateOptional(Scanner scanner, String prompt, LocalDate currentValue) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.print(YELLOW + "  " + prompt + " [" + currentValue.format(formatter) + "]: " + RESET);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return currentValue;
        }
        try {
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            printError("Invalid date format. Keeping current value: " + currentValue.format(formatter));
            return currentValue;
        }
    }

    public static int readIntOptional(Scanner scanner, String prompt, int currentValue, int min, int max) {
        System.out.print(YELLOW + "  " + prompt + " [" + currentValue + "] (" + min + "-" + max + "): " + RESET);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return currentValue;
        }
        try {
            int value = Integer.parseInt(input);
            if (value >= min && value <= max) {
                return value;
            }
            printError("Value must be between " + min + " and " + max + ". Keeping current value.");
            return currentValue;
        } catch (NumberFormatException e) {
            printError("Invalid number. Keeping current value: " + currentValue);
            return currentValue;
        }
    }

    public static void printMainMenu() {
        System.out.println();
        System.out.println(CYAN + "╔══════════════════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + BOLD + WHITE + "       📚 SMART STUDY NOTE MANAGER               " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + WHITE + "       Powered by Doubly Linked List             " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠══════════════════════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  1." + RESET + "  Add New Note                              " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  2." + RESET + "  Edit Note                                 " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  3." + RESET + "  Delete Note                               " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  4." + RESET + "  View All Notes                            " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  5." + RESET + "  Search Notes                              " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  6." + RESET + "  Navigate Notes (Undo/Redo)                " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  7." + RESET + "  Priority-Based Study Plan                 " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  8." + RESET + "  Link Notes                                " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  9." + RESET + "  View Linked Notes                         " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN  + "  10." + RESET + " Sort Notes                                " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + "                                                  " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + RED    + "  11." + RESET + " Exit                                      " + CYAN + "║" + RESET);
        System.out.println(CYAN + "╚══════════════════════════════════════════════════╝" + RESET);
    }


    public static int readMenuChoice(Scanner scanner) {
        System.out.print(BOLD + "\n  👉 Enter your choice: " + RESET);
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;  // Invalid choice
        }
    }

    public static boolean confirm(Scanner scanner, String prompt) {
        System.out.print(YELLOW + "  " + prompt + " (y/n): " + RESET);
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }

    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print(CYAN + "\n  Press Enter to continue..." + RESET);
        scanner.nextLine();
    }


    public static void printNavigationMenu() {
        System.out.println();
        System.out.println(CYAN + "╔═══════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + BOLD + WHITE + "      NOTE NAVIGATION              " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠═══════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  [N]" + RESET + " Next Note (Forward)       " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  [P]" + RESET + " Previous Note (Backward)  " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  [V]" + RESET + " View Current Note         " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + RED   + "  [B]" + RESET + " Back to Main Menu         " + CYAN + "║" + RESET);
        System.out.println(CYAN + "╚═══════════════════════════════════╝" + RESET);
    }

    public static String readNavigationChoice(Scanner scanner) {
        System.out.print(BOLD + "\n  👉 Enter choice (N/P/V/B): " + RESET);
        return scanner.nextLine().trim().toUpperCase();
    }

    public static void printSearchMenu() {
        System.out.println(CYAN + "╔═══════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + BOLD + WHITE + "      SEARCH BY                    " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠═══════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  1." + RESET + " Search by Title            " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  2." + RESET + " Search by Subject          " + CYAN + "║" + RESET);
        System.out.println(CYAN + "╚═══════════════════════════════════╝" + RESET);
        System.out.print(BOLD + "\n  👉 Enter choice (1/2): " + RESET);
    }

    public static void printSortMenu() {
        System.out.println(CYAN + "╔═══════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + BOLD + WHITE + "      SORT BY                      " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠═══════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  1." + RESET + " Sort by Priority           " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  2." + RESET + " Sort by Revision Date      " + CYAN + "║" + RESET);
        System.out.println(CYAN + "╚═══════════════════════════════════╝" + RESET);
        System.out.print(BOLD + "\n  👉 Enter choice (1/2): " + RESET);
    }

    public static void printStudyPlanMenu() {
        System.out.println(CYAN + "╔═══════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║" + BOLD + WHITE + "      STUDY PLAN OPTIONS            " + RESET + CYAN + "║" + RESET);
        System.out.println(CYAN + "╠═══════════════════════════════════╣" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  1." + RESET + " Generate Study Plan        " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  2." + RESET + " Filter by Priority         " + CYAN + "║" + RESET);
        System.out.println(CYAN + "║" + RESET + GREEN + "  3." + RESET + " Upcoming Revisions         " + CYAN + "║" + RESET);
        System.out.println(CYAN + "╚═══════════════════════════════════╝" + RESET);
        System.out.print(BOLD + "\n  👉 Enter choice (1/2/3): " + RESET);
    }







}
