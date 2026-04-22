package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class NoteNode {

    // ==================== Static ID Counter ====================
    private static int idCounter = 0;

    // ==================== Note Data Fields ====================
    private int id;
    private String title;
    private String subject;
    private String content;
    private int priority;           // 1 (Highest) to 5 (Lowest)
    private LocalDate revisionDate;
    private LocalDateTime createdAt;
    private List<Integer> linkedNoteIds;

    // ==================== DLL Pointers ====================
    public NoteNode prev;   // Pointer to previous node
    public NoteNode next;   // Pointer to next node

    // ==================== Constructor ====================


    public NoteNode(String title, String subject, String content, int priority, LocalDate revisionDate) {
        this.id = ++idCounter;
        this.title = title;
        this.subject = subject;
        this.content = content;
        this.priority = priority;
        this.revisionDate = revisionDate;
        this.createdAt = LocalDateTime.now();
        this.linkedNoteIds = new ArrayList<>();
        this.prev = null;
        this.next = null;
    }

    // ==================== Getters ====================

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDate getRevisionDate() {
        return revisionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Integer> getLinkedNoteIds() {
        return linkedNoteIds;
    }

    // ==================== Setters ====================

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPriority(int priority) {
        if (priority >= 1 && priority <= 5) {
            this.priority = priority;
        } else {
            System.out.println("Priority must be between 1 and 5.");
        }
    }

    public void setRevisionDate(LocalDate revisionDate) {
        this.revisionDate = revisionDate;
    }

    // ==================== Linked Notes Management ====================


    public void addLinkedNoteId(int noteId) {
        if (!linkedNoteIds.contains(noteId)) {
            linkedNoteIds.add(noteId);
        }
    }


    public void removeLinkedNoteId(int noteId) {
        linkedNoteIds.remove(Integer.valueOf(noteId));
    }

    // ==================== Priority Label ====================


    public String getPriorityLabel() {
        switch (priority) {
            case 1: return "Critical";
            case 2: return "High";
            case 3: return "Medium";
            case 4: return "Low";
            case 5: return "Optional";
            default: return "Unknown";
        }
    }

    // ==================== Display ====================


    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        StringBuilder sb = new StringBuilder();
        sb.append("┌─────────────────────────────────────────────┐\n");
        sb.append(String.format("│ ID: %-40d│%n", id));
        sb.append(String.format("│ Title: %-37s│%n", title));
        sb.append(String.format("│ Subject: %-35s│%n", subject));
        sb.append(String.format("│ Priority: %-34s│%n", priority + " (" + getPriorityLabel() + ")"));
        sb.append(String.format("│ Revision Date: %-29s│%n", revisionDate.format(dateFormat)));
        sb.append(String.format("│ Created At: %-32s│%n", createdAt.format(dateTimeFormat)));
        sb.append("│─────────────────────────────────────────────│\n");
        sb.append("│ Content:                                    │\n");

        // Word-wrap content to fit in the box
        String[] words = content.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            if (line.length() + word.length() + 1 > 43) {
                sb.append(String.format("│   %-42s│%n", line.toString()));
                line = new StringBuilder();
            }
            if (line.length() > 0) line.append(" ");
            line.append(word);
        }
        if (line.length() > 0) {
            sb.append(String.format("│   %-42s│%n", line.toString()));
        }

        if (!linkedNoteIds.isEmpty()) {
            sb.append("│─────────────────────────────────────────────│\n");
            sb.append(String.format("│ Linked Notes: %-30s│%n", linkedNoteIds.toString()));
        }

        sb.append("└─────────────────────────────────────────────┘");
        return sb.toString();
    }


    public String toShortString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("│ %-4d│ %-20s│ %-15s│ %-10s│ %-12s│",
                id,
                truncate(title, 20),
                truncate(subject, 15),
                priority + "-" + getPriorityLabel(),
                revisionDate.format(dateFormat));
    }


    private String truncate(String str, int maxLen) {
        if (str.length() <= maxLen) return str;
        return str.substring(0, maxLen - 3) + "...";
    }


    public static void resetIdCounter() {
        idCounter = 0;
    }
}

