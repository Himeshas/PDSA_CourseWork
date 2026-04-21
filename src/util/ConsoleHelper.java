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

}
