package manager;

import util.ConsoleHelper;
import model.NoteNode;
import datastructure.DoublyLinkedList;

import java.time.LocalDate;
import java.util.Scanner;

public class StudyManager {

    String newTitle = ConsoleHelper.readStringOptional(scanner, "Title", note.getTitle());
    String newSubject = ConsoleHelper.readStringOptional(scanner, "Subject", note.getSubject());
    String newContent = ConsoleHelper.readStringOptional(scanner, "Content", note.getContent());
    int newPriority = ConsoleHelper.readIntOptional(scanner, "Priority", note.getPriority(), 1, 5);
    LocalDate newDate = ConsoleHelper.readDateOptional(scanner, "Revision Date", note.getRevisionDate());

//Himesha part navigation

    public void navigateNotes(Scanner scanner) {
        ConsoleHelper.printHeader("🔄 NOTE NAVIGATION");

        // Check if list is empty
        if (noteList.isEmpty()) {
            ConsoleHelper.printWarning("No notes to navigate. The list is empty.");
            ConsoleHelper.printInfo("Use option 1 to add a new note first.");
            return;
        }

        // Initialize cursor at head if not set, or if current note was deleted
        if (currentNote == null || noteList.getById(currentNote.getId()) == null) {
            currentNote = noteList.getHead();
        }

        boolean navigating = true;

        while (navigating) {
            // Show current position
            int position = getPosition(currentNote);
            int total = noteList.getSize();

            System.out.println();
            ConsoleHelper.printDivider();
            ConsoleHelper.printInfo("Current Position: [Note " + position + " of " + total + "]");
            ConsoleHelper.printDivider();

            // Show current note summary
            System.out.println(currentNote.toShortString());

            // Show navigation indicators
            String prevIndicator = (currentNote.prev != null)
                    ? "◄ " + currentNote.prev.getTitle()
                    : "◄ (Beginning of list)";
            String nextIndicator = (currentNote.next != null)
                    ? currentNote.next.getTitle() + " ►"
                    : "(End of list) ►";

            System.out.println();
            System.out.println("  " + ConsoleHelper.YELLOW + prevIndicator + ConsoleHelper.RESET
                    + "  ◄──  " + ConsoleHelper.BOLD + ConsoleHelper.CYAN + "[CURRENT]" + ConsoleHelper.RESET
                    + "  ──►  " + ConsoleHelper.YELLOW + nextIndicator + ConsoleHelper.RESET);

            // Show navigation sub-menu
            ConsoleHelper.printNavigationMenu();
            String choice = ConsoleHelper.readNavigationChoice(scanner);

            switch (choice) {
                case "N":
                    moveForward();
                    break;
                case "P":
                    moveBackward();
                    break;
                case "V":
                    viewCurrentNote();
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;
                case "B":
                    navigating = false;
                    ConsoleHelper.printInfo("Returning to main menu...");
                    break;
                default:
                    ConsoleHelper.printError("Invalid choice. Use N, P, V, or B.");
                    break;
            }
        }
    }

}
