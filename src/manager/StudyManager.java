package manager;

import model.NoteNode;
import datastructure.DoublyLinkedList;
import util.ConsoleHelper;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * StudyNoteManager - Application-level manager wrapping the Doubly Linked List.
 *
 * Provides CRUD operations for study notes:
 *   - addNote():       Collect input from user, create NoteNode, insert into DLL
 *   - editNote():      Find a note by ID, update its fields in-place
 *   - deleteNote():    Remove a note from the DLL by ID
 *   - viewAllNotes():  Display all notes in a formatted table
 *
 * This class acts as the bridge between the console UI (ConsoleHelper)
 * and the core data structure (DoublyLinkedList).
 */
public class StudyNoteManager {

    // ==================== Fields ====================
    private DoublyLinkedList noteList;
    private NoteNode currentNote;   // Cursor pointer for navigation

    // ==================== Constructor ====================

    /**
     * Creates a new StudyNoteManager with an empty Doubly Linked List.
     */
    public StudyNoteManager() {
        this.noteList = new DoublyLinkedList();
        this.currentNote = null;
    }

    // ==================== Getter ====================

    /**
     * Returns the underlying DoublyLinkedList.
     */
    public DoublyLinkedList getNoteList() {
        return noteList;
    }

    // ==================== CRUD OPERATIONS ====================

    /**
     * ADD NOTE — Prompts the user for all fields and inserts a new note at the end of the DLL.
     *
     * @param scanner The Scanner instance for reading user input
     */
    public void addNote(Scanner scanner) {
        ConsoleHelper.printHeader("📝 ADD NEW NOTE");

        // Collect input from user
        String title = ConsoleHelper.readString(scanner, "Enter title");
        String subject = ConsoleHelper.readString(scanner, "Enter subject");
        String content = ConsoleHelper.readString(scanner, "Enter content");

        // Show priority guide
        System.out.println();
        ConsoleHelper.printInfo("Priority Levels:");
        System.out.println("      1 = 🔴 Critical");
        System.out.println("      2 = 🟠 High");
        System.out.println("      3 = 🟡 Medium");
        System.out.println("      4 = 🟢 Low");
        System.out.println("      5 = 🔵 Optional");
        int priority = ConsoleHelper.readInt(scanner, "Enter priority", 1, 5);

        LocalDate revisionDate = ConsoleHelper.readDate(scanner, "Enter revision date");

        // Create the new NoteNode and insert into the DLL
        NoteNode newNote = new NoteNode(title, subject, content, priority, revisionDate);
        noteList.insertAtEnd(newNote);

        // Confirm success
        System.out.println();
        ConsoleHelper.printDivider();
        ConsoleHelper.printSuccess("Note added successfully! (ID: " + newNote.getId() + ")");
        ConsoleHelper.printDivider();
    }

    /**
     * EDIT NOTE — Prompts the user for a note ID, then allows updating each field.
     * Leave a field blank to keep the current value.
     *
     * @param scanner The Scanner instance for reading user input
     */
    public void editNote(Scanner scanner) {
        ConsoleHelper.printHeader("✏️  EDIT NOTE");

        // Check if list is empty
        if (noteList.isEmpty()) {
            ConsoleHelper.printWarning("No notes to edit. The list is empty.");
            return;
        }

        // Show all notes first so user can pick one
        viewAllNotes();

        // Get the note ID to edit
        int id = ConsoleHelper.readInt(scanner, "Enter the ID of the note to edit");
        NoteNode note = noteList.getById(id);

        if (note == null) {
            ConsoleHelper.printError("Note with ID " + id + " not found.");
            return;
        }

        // Show current note details
        ConsoleHelper.printSubHeader("Current Note Details");
        System.out.println(note.toString());

        ConsoleHelper.printInfo("Press Enter to keep the current value for any field.");
        System.out.println();

        // Edit each field (optional — press Enter to skip)
        String newTitle = ConsoleHelper.readStringOptional(scanner, "Title", note.getTitle());
        String newSubject = ConsoleHelper.readStringOptional(scanner, "Subject", note.getSubject());
        String newContent = ConsoleHelper.readStringOptional(scanner, "Content", note.getContent());
        int newPriority = ConsoleHelper.readIntOptional(scanner, "Priority", note.getPriority(), 1, 5);
        LocalDate newDate = ConsoleHelper.readDateOptional(scanner, "Revision Date", note.getRevisionDate());

        // Apply changes
        note.setTitle(newTitle);
        note.setSubject(newSubject);
        note.setContent(newContent);
        note.setPriority(newPriority);
        note.setRevisionDate(newDate);

        // Confirm success
        System.out.println();
        ConsoleHelper.printDivider();
        ConsoleHelper.printSuccess("Note ID " + id + " updated successfully!");
        ConsoleHelper.printDivider();

        // Show updated note
        ConsoleHelper.printSubHeader("Updated Note");
        System.out.println(note.toString());
    }

    /**
     * DELETE NOTE — Prompts the user for a note ID and removes it from the DLL.
     * Handles edge cases: cursor repositioning and linked note cleanup.
     *
     * @param scanner The Scanner instance for reading user input
     */
    public void deleteNote(Scanner scanner) {
        ConsoleHelper.printHeader("🗑️  DELETE NOTE");

        // Check if list is empty
        if (noteList.isEmpty()) {
            ConsoleHelper.printWarning("No notes to delete. The list is empty.");
            return;
        }

        // Show all notes first
        viewAllNotes();

        // Get the note ID to delete
        int id = ConsoleHelper.readInt(scanner, "Enter the ID of the note to delete");
        NoteNode note = noteList.getById(id);

        if (note == null) {
            ConsoleHelper.printError("Note with ID " + id + " not found.");
            return;
        }

        // Show the note to be deleted
        ConsoleHelper.printSubHeader("Note to Delete");
        System.out.println(note.toString());

        // Ask for confirmation
        if (ConsoleHelper.confirm(scanner, "Are you sure you want to delete this note?")) {

            // Edge case: If deleting the cursor note, move cursor before deletion
            if (currentNote != null && currentNote.getId() == id) {
                if (currentNote.next != null) {
                    currentNote = currentNote.next;
                } else if (currentNote.prev != null) {
                    currentNote = currentNote.prev;
                } else {
                    currentNote = null; // List will be empty after deletion
                }
            }

            // Clean up linked note references — remove this ID from other notes' linkedNoteIds
            for (int linkedId : note.getLinkedNoteIds()) {
                NoteNode linkedNote = noteList.getById(linkedId);
                if (linkedNote != null) {
                    linkedNote.removeLinkedNoteId(id);
                }
            }

            boolean deleted = noteList.deleteById(id);
            System.out.println();
            if (deleted) {
                ConsoleHelper.printDivider();
                ConsoleHelper.printSuccess("Note ID " + id + " deleted successfully!");
                ConsoleHelper.printDivider();
            } else {
                ConsoleHelper.printError("Failed to delete the note.");
            }
        } else {
            ConsoleHelper.printInfo("Delete cancelled.");
        }
    }

    /**
     * VIEW ALL NOTES — Displays all notes in the DLL as a formatted table.
     */
    public void viewAllNotes() {
        ConsoleHelper.printHeader("📋 ALL NOTES");

        if (noteList.isEmpty()) {
            ConsoleHelper.printWarning("No notes found. The list is empty.");
            ConsoleHelper.printInfo("Use option 1 to add a new note.");
            return;
        }

        noteList.displayAll();
    }

    /**
     * VIEW SINGLE NOTE — Prompts for an ID and displays the full detail view.
     *
     * @param scanner The Scanner instance for reading user input
     */
    public void viewNoteById(Scanner scanner) {
        if (noteList.isEmpty()) {
            ConsoleHelper.printWarning("No notes to view. The list is empty.");
            return;
        }

        int id = ConsoleHelper.readInt(scanner, "Enter the note ID to view");
        NoteNode note = noteList.getById(id);

        if (note == null) {
            ConsoleHelper.printError("Note with ID " + id + " not found.");
            return;
        }

        ConsoleHelper.printSubHeader("Note Details (ID: " + id + ")");
        System.out.println(note.toString());
    }

// ==================== NAVIGATION (Phase 3) ====================
