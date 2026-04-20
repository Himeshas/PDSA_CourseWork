import manager.StudyNoteManager;
import util.ConsoleHelper;



import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        StudyNoteManager manager = new StudyNoteManager();

        // Welcome screen
        ConsoleHelper.printHeader("📚 SMART STUDY NOTE MANAGER");
        ConsoleHelper.printInfo("Powered by Doubly Linked List (DLL)");

        // Ask if user wants to load sample data
        if (ConsoleHelper.confirm(scanner, "Load sample notes for demo?")) {
            manager.loadSampleData();
        }

        // ==================== Main Menu Loop ====================
        boolean running = true;

        while (running) {
            ConsoleHelper.printMainMenu();
            int choice = ConsoleHelper.readMenuChoice(scanner);

            switch (choice) {
                case 1:
                    // Add New Note
                    manager.addNote(scanner);
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 2:
                    // Edit Note
                    manager.editNote(scanner);
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 3:
                    // Delete Note
                    manager.deleteNote(scanner);
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 4:
                    // View All Notes
                    manager.viewAllNotes();
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 5:
                    // Search Notes
                    manager.searchNotes(scanner);
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 6:
                    // Navigate Notes (Undo/Redo)
                    manager.navigateNotes(scanner);
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 7:
                    // Priority-Based Study Plan
                    manager.studyPlanMenu(scanner);
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 8:
                    // Link Notes
                    manager.linkNotes(scanner);
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 9:
                    // View Linked Notes
                    manager.viewLinkedNotes(scanner);
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 10:
                    // Sort Notes
                    manager.sortNotes(scanner);
                    ConsoleHelper.pressEnterToContinue(scanner);
                    break;

                case 11:
                    // Exit
                    if (ConsoleHelper.confirm(scanner, "Are you sure you want to exit?")) {
                        running = false;
                        System.out.println();
                        ConsoleHelper.printDivider();
                        ConsoleHelper.printSuccess("Thank you for using Smart Study Note Manager!");
                        ConsoleHelper.printInfo("Goodbye! 👋");
                        ConsoleHelper.printDivider();
                    }
                    break;

                default:
                    ConsoleHelper.printError("Invalid choice. Please select 1-11.");
                    break;
            }
        }

        scanner.close();
    }
}
