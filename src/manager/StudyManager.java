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

    private void moveForward() {
        if (currentNote.next != null) {
            currentNote = currentNote.next;
            ConsoleHelper.printSuccess("Moved forward to: " + currentNote.getTitle());
        } else {
            ConsoleHelper.printWarning("Already at the END of the list. Cannot move forward.");
        }
    }

    private void moveBackward() {
        if (currentNote.prev != null) {
            currentNote = currentNote.prev;
            ConsoleHelper.printSuccess("Moved backward to: " + currentNote.getTitle());
        } else {
            ConsoleHelper.printWarning("Already at the BEGINNING of the list. Cannot move backward.");
        }
    }


    private void viewCurrentNote() {
        ConsoleHelper.printSubHeader("Note Details (ID: " + currentNote.getId() + ")");
        System.out.println(currentNote.toString());
    }


    private int getPosition(NoteNode node) {
        NoteNode current = noteList.getHead();
        int pos = 1;
        while (current != null) {
            if (current == node) return pos;
            current = current.next;
            pos++;
        }
        return -1; // Should not happen
    }


    public void searchNotes(Scanner scanner) {
        ConsoleHelper.printHeader("🔍 SEARCH NOTES");

        if (noteList.isEmpty()) {
            ConsoleHelper.printWarning("No notes to search. The list is empty.");
            return;
        }

        ConsoleHelper.printSearchMenu();
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                String keyword = ConsoleHelper.readString(scanner, "Enter title keyword");
                noteList.searchByTitle(keyword);
                break;
            case "2":
                String subject = ConsoleHelper.readString(scanner, "Enter subject name");
                noteList.searchBySubject(subject);
                break;
            default:
                ConsoleHelper.printError("Invalid choice.");
                break;
        }
    }

    public void sortNotes(Scanner scanner) {
        ConsoleHelper.printHeader("📊 SORT NOTES");

        if (noteList.isEmpty()) {
            ConsoleHelper.printWarning("No notes to sort. The list is empty.");
            return;
        }

        ConsoleHelper.printSortMenu();
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                noteList.sortByPriority();
                ConsoleHelper.printSuccess("Notes sorted by Priority (1-Critical first).");
                System.out.println();
                noteList.displayAll();
                break;
            case "2":
                noteList.sortByRevisionDate();
                ConsoleHelper.printSuccess("Notes sorted by Revision Date (earliest first).");
                System.out.println();
                noteList.displayAll();
                break;
            default:
                ConsoleHelper.printError("Invalid choice.");
                break;
        }
    }


    public void studyPlanMenu(Scanner scanner) {
        ConsoleHelper.printHeader("📊 PRIORITY-BASED STUDY PLAN");

        if (noteList.isEmpty()) {
            ConsoleHelper.printWarning("No notes available. The list is empty.");
            return;
        }

        ConsoleHelper.printStudyPlanMenu();
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                generateStudyPlan();
                break;
            case "2":
                int level = ConsoleHelper.readInt(scanner, "Enter priority level", 1, 5);
                filterByPriority(level);
                break;
            case "3":
                showUpcomingRevisions();
                break;
            default:
                ConsoleHelper.printError("Invalid choice.");
                break;
        }
    }

    private void generateStudyPlan() {
        // Sort by priority first (modifies DLL in-place)
        noteList.sortByPriority();

        ConsoleHelper.printSubHeader("Your Study Plan (Ordered by Priority)");

        String[] priorityIcons = {"", "🔴", "🟠", "🟡", "🟢", "🔵"};
        String[] priorityColors = {"",
                ConsoleHelper.RED, ConsoleHelper.YELLOW, ConsoleHelper.YELLOW,
                ConsoleHelper.GREEN, ConsoleHelper.CYAN};

        NoteNode current = noteList.getHead();
        int step = 1;

        while (current != null) {
            int p = current.getPriority();
            String icon = priorityIcons[p];
            String color = priorityColors[p];

            System.out.printf("  %s%d. %s [%s %s] — %s (Due: %s)%s%n",
                    color, step, icon, current.getPriorityLabel(),
                    "P" + p, current.getTitle(),
                    current.getRevisionDate().toString(),
                    ConsoleHelper.RESET);

            current = current.next;
            step++;
        }

        System.out.println();
        ConsoleHelper.printInfo("Total notes in plan: " + noteList.getSize());
    }

    private void filterByPriority(int level) {
        String[] labels = {"", "Critical", "High", "Medium", "Low", "Optional"};
        ConsoleHelper.printSubHeader("Notes with Priority " + level + " (" + labels[level] + ")");

        NoteNode current = noteList.getHead();
        boolean found = false;

        while (current != null) {
            if (current.getPriority() == level) {
                System.out.println(current.toShortString());
                found = true;
            }
            current = current.next;
        }

        if (!found) {
            ConsoleHelper.printWarning("No notes found with priority " + level + ".");
        }
    }

    private void showUpcomingRevisions() {
        // Sort by revision date first (modifies DLL in-place)
        noteList.sortByRevisionDate();

        ConsoleHelper.printSubHeader("Upcoming Revisions (Earliest First)");

        LocalDate today = LocalDate.now();
        NoteNode current = noteList.getHead();

        while (current != null) {
            boolean overdue = current.getRevisionDate().isBefore(today);
            String color = overdue ? ConsoleHelper.RED : ConsoleHelper.GREEN;
            String status = overdue ? " ⚠️ OVERDUE" : "";

            System.out.printf("  %s%-25s | Due: %s%s%s%n",
                    color,
                    current.getTitle(),
                    current.getRevisionDate().toString(),
                    status,
                    ConsoleHelper.RESET);

            current = current.next;
        }

        System.out.println();
        ConsoleHelper.printInfo("Today's date: " + today.toString());
    }

    public void linkNotes(Scanner scanner) {
        ConsoleHelper.printHeader("🔗 LINK NOTES");

        if (noteList.getSize() < 2) {
            ConsoleHelper.printWarning("Need at least 2 notes to create a link.");
            return;
        }

        // Show all notes so user can pick
        viewAllNotes();

        int id1 = ConsoleHelper.readInt(scanner, "Enter first note ID");
        int id2 = ConsoleHelper.readInt(scanner, "Enter second note ID");

        if (id1 == id2) {
            ConsoleHelper.printError("Cannot link a note to itself.");
            return;
        }

        NoteNode note1 = noteList.getById(id1);
        NoteNode note2 = noteList.getById(id2);

        if (note1 == null) {
            ConsoleHelper.printError("Note with ID " + id1 + " not found.");
            return;
        }
        if (note2 == null) {
            ConsoleHelper.printError("Note with ID " + id2 + " not found.");
            return;
        }

        if (note1.getLinkedNoteIds().contains(id2)) {
            ConsoleHelper.printWarning("These notes are already linked.");
            return;
        }

        // Create bidirectional link
        note1.addLinkedNoteId(id2);
        note2.addLinkedNoteId(id1);

        System.out.println();
        ConsoleHelper.printDivider();
        ConsoleHelper.printSuccess("Linked: \"" + note1.getTitle() + "\" (ID:" + id1
                + ") ↔ \"" + note2.getTitle() + "\" (ID:" + id2 + ")");
        ConsoleHelper.printDivider();
    }

    public void viewLinkedNotes(Scanner scanner) {
        ConsoleHelper.printHeader("🔗 VIEW LINKED NOTES");

        if (noteList.isEmpty()) {
            ConsoleHelper.printWarning("No notes available. The list is empty.");
            return;
        }

        int id = ConsoleHelper.readInt(scanner, "Enter note ID to view its links");
        NoteNode note = noteList.getById(id);

        if (note == null) {
            ConsoleHelper.printError("Note with ID " + id + " not found.");
            return;
        }

        ConsoleHelper.printSubHeader("Links for: \"" + note.getTitle() + "\" (ID: " + id + ")");

        if (note.getLinkedNoteIds().isEmpty()) {
            ConsoleHelper.printWarning("This note has no linked notes.");
            ConsoleHelper.printInfo("Use option 8 (Link Notes) to create links.");
            return;
        }

        System.out.println("  Linked Note IDs: " + note.getLinkedNoteIds());
        System.out.println();

        // Display each linked note
        for (int linkedId : note.getLinkedNoteIds()) {
            NoteNode linked = noteList.getById(linkedId);
            if (linked != null) {
                System.out.println(linked.toShortString());
            } else {
                ConsoleHelper.printWarning("Linked note ID " + linkedId + " no longer exists.");
            }
        }

        // Offer to navigate to a linked note
        System.out.println();
        if (ConsoleHelper.confirm(scanner, "Jump cursor to a linked note?")) {
            int jumpId = ConsoleHelper.readInt(scanner, "Enter linked note ID to jump to");
            NoteNode jumpNote = noteList.getById(jumpId);
            if (jumpNote != null && note.getLinkedNoteIds().contains(jumpId)) {
                currentNote = jumpNote;
                ConsoleHelper.printSuccess("Cursor moved to: \"" + jumpNote.getTitle() + "\" (ID: " + jumpId + ")");
            } else {
                ConsoleHelper.printError("Invalid linked note ID.");
            }
        }
    }







}
