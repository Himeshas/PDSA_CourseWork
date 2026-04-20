


package datastructure;

import model.NoteNode;

public class DoublyLinkedList {

    public class DoublyLinkedList {

        // ==================== DLL Core Pointers ====================
        private NoteNode head;  // Pointer to the first node
        private NoteNode tail;  // Pointer to the last node
        private int size;       // Number of nodes in the list

        // ==================== Constructor ====================

        /**
         * Initializes an empty Doubly Linked List.
         */
        public DoublyLinkedList() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        // ==================== Getters ====================

        public NoteNode getHead() {
            return head;
        }

        public NoteNode getTail() {
            return tail;
        }

        public int getSize() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // ==================== INSERT OPERATIONS ====================

        /**
         * Insert a new node at the END of the list.
         * Time Complexity: O(1)
         *
         * @param newNode The NoteNode to insert
         */
        public void insertAtEnd(NoteNode newNode) {
            if (head == null) {
                // List is empty — new node becomes both head and tail
                head = newNode;
                tail = newNode;
                newNode.prev = null;
                newNode.next = null;
            } else {
                // Attach new node after the current tail
                tail.next = newNode;
                newNode.prev = tail;
                newNode.next = null;
                tail = newNode;
            }
            size++;
        }

        /**
         * Insert a new node at the BEGINNING of the list.
         * Time Complexity: O(1)
         *
         * @param newNode The NoteNode to insert
         */
        public void insertAtBeginning(NoteNode newNode) {
            if (head == null) {
                // List is empty
                head = newNode;
                tail = newNode;
                newNode.prev = null;
                newNode.next = null;
            } else {
                // Attach new node before the current head
                newNode.next = head;
                newNode.prev = null;
                head.prev = newNode;
                head = newNode;
            }
            size++;
        }

        /**
         * Insert a new node at a specific POSITION (0-indexed).
         * Time Complexity: O(n)
         *
         * @param newNode  The NoteNode to insert
         * @param position The 0-indexed position to insert at
         */
        public void insertAtPosition(NoteNode newNode, int position) {
            // Validate position
            if (position < 0 || position > size) {
                System.out.println("Invalid position! Must be between 0 and " + size);
                return;
            }

            if (position == 0) {
                insertAtBeginning(newNode);
                return;
            }

            if (position == size) {
                insertAtEnd(newNode);
                return;
            }

            // Traverse to the node currently at the target position
            NoteNode current = head;
            for (int i = 0; i < position; i++) {
                current = current.next;
            }

            // Insert newNode BEFORE current
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }

        /**
         * Insert a new node maintaining PRIORITY ORDER (ascending: 1 first, 5 last).
         * Time Complexity: O(n)
         *
         * @param newNode The NoteNode to insert in priority order
         */
        public void insertByPriority(NoteNode newNode) {
            if (head == null) {
                // List is empty
                head = newNode;
                tail = newNode;
                newNode.prev = null;
                newNode.next = null;
                size++;
                return;
            }

            // Find the correct position based on priority
            NoteNode current = head;
            while (current != null && current.getPriority() <= newNode.getPriority()) {
                current = current.next;
            }

            if (current == null) {
                // Insert at end — all existing nodes have equal or higher priority
                insertAtEnd(newNode);
            } else if (current == head) {
                // Insert at beginning — new node has the highest priority
                insertAtBeginning(newNode);
            } else {
                // Insert before current
                newNode.prev = current.prev;
                newNode.next = current;
                current.prev.next = newNode;
                current.prev = newNode;
                size++;
            }
        }

        // ==================== DELETE OPERATIONS ====================

        /**
         * Delete a node by its unique ID.
         * Time Complexity: O(n)
         *
         * @param id The ID of the note to delete
         * @return true if the node was found and deleted, false otherwise
         */
        public boolean deleteById(int id) {
            if (head == null) {
                System.out.println("List is empty. Nothing to delete.");
                return false;
            }

            NoteNode current = head;

            // Traverse to find the node with the matching ID
            while (current != null) {
                if (current.getId() == id) {
                    // Found the node — now unlink it

                    if (current == head && current == tail) {
                        // Only one node in the list
                        head = null;
                        tail = null;
                    } else if (current == head) {
                        // Deleting the head
                        head = head.next;
                        head.prev = null;
                    } else if (current == tail) {
                        // Deleting the tail
                        tail = tail.prev;
                        tail.next = null;
                    } else {
                        // Deleting a middle node
                        current.prev.next = current.next;
                        current.next.prev = current.prev;
                    }

                    // Clean up pointers of the deleted node
                    current.prev = null;
                    current.next = null;
                    size--;
                    return true;
                }
                current = current.next;
            }

            System.out.println("Note with ID " + id + " not found.");
            return false;
        }

        // ==================== SEARCH OPERATIONS ====================

        /**
         * Search for nodes by title (case-insensitive, partial match).
         * Time Complexity: O(n)
         *
         * @param keyword The search keyword to match against note titles
         */
        public void searchByTitle(String keyword) {
            if (head == null) {
                System.out.println("List is empty. No notes to search.");
                return;
            }

            NoteNode current = head;
            boolean found = false;
            String lowerKeyword = keyword.toLowerCase();

            System.out.println("\n--- Search Results for \"" + keyword + "\" ---\n");

            while (current != null) {
                if (current.getTitle().toLowerCase().contains(lowerKeyword)) {
                    System.out.println(current.toShortString());
                    found = true;
                }
                current = current.next;
            }

            if (!found) {
                System.out.println("No notes found matching \"" + keyword + "\".");
            }
        }

        /**
         * Search for nodes by subject (case-insensitive, exact match).
         * Time Complexity: O(n)
         *
         * @param subject The subject to filter by
         */
        public void searchBySubject(String subject) {
            if (head == null) {
                System.out.println("List is empty. No notes to search.");
                return;
            }

            NoteNode current = head;
            boolean found = false;
            String lowerSubject = subject.toLowerCase();

            System.out.println("\n--- Notes in Subject: " + subject + " ---\n");

            while (current != null) {
                if (current.getSubject().toLowerCase().contains(lowerSubject)) {
                    System.out.println(current.toShortString());
                    found = true;
                }
                current = current.next;
            }

            if (!found) {
                System.out.println("No notes found for subject \"" + subject + "\".");
            }
        }

        // ==================== GET OPERATIONS ====================

        /**
         * Retrieve a node by its unique ID.
         * Time Complexity: O(n)
         *
         * @param id The ID of the note to retrieve
         * @return The NoteNode if found, null otherwise
         */
        public NoteNode getById(int id) {
            NoteNode current = head;
            while (current != null) {
                if (current.getId() == id) {
                    return current;
                }
                current = current.next;
            }
            return null;
        }

        // ==================== TRAVERSAL OPERATIONS ====================

        /**
         * Traverse the list from HEAD to TAIL (forward direction).
         * Time Complexity: O(n)
         * Prints each note's short summary.
         */
        public void traverseForward() {
            if (head == null) {
                System.out.println("List is empty. No notes to display.");
                return;
            }

            System.out.println("\n═══════════════════════════════════════════════════════════════════════════════");
            System.out.println("  TRAVERSAL: HEAD ──────────────────────────────────────────────────► TAIL");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════");
            printTableHeader();

            NoteNode current = head;
            int position = 1;
            while (current != null) {
                System.out.println(current.toShortString());
                current = current.next;
                position++;
            }

            printTableFooter();
            System.out.println("  Total Notes: " + size);
        }

        /**
         * Traverse the list from TAIL to HEAD (backward direction).
         * Time Complexity: O(n)
         * Prints each note's short summary in reverse order.
         */
        public void traverseBackward() {
            if (tail == null) {
                System.out.println("List is empty. No notes to display.");
                return;
            }

            System.out.println("\n═══════════════════════════════════════════════════════════════════════════════");
            System.out.println("  TRAVERSAL: TAIL ──────────────────────────────────────────────────► HEAD");
            System.out.println("═══════════════════════════════════════════════════════════════════════════════");
            printTableHeader();

            NoteNode current = tail;
            while (current != null) {
                System.out.println(current.toShortString());
                current = current.prev;
            }

            printTableFooter();
            System.out.println("  Total Notes: " + size);
        }

        // ==================== SORT OPERATIONS ====================

        /**
         * Sort the list by PRIORITY using Bubble Sort (ascending: 1 first).
         * Swaps DATA between nodes, not the nodes/pointers themselves.
         * Time Complexity: O(n²)
         */
        public void sortByPriority() {
            if (size <= 1) return;

            boolean swapped;
            do {
                swapped = false;
                NoteNode current = head;

                while (current.next != null) {
                    if (current.getPriority() > current.next.getPriority()) {
                        swapNodeData(current, current.next);
                        swapped = true;
                    }
                    current = current.next;
                }
            } while (swapped);
        }

        /**
         * Sort the list by REVISION DATE using Bubble Sort (earliest first).
         * Swaps DATA between nodes, not the nodes/pointers themselves.
         * Time Complexity: O(n²)
         */
        public void sortByRevisionDate() {
            if (size <= 1) return;

            boolean swapped;
            do {
                swapped = false;
                NoteNode current = head;

                while (current.next != null) {
                    if (current.getRevisionDate().isAfter(current.next.getRevisionDate())) {
                        swapNodeData(current, current.next);
                        swapped = true;
                    }
                    current = current.next;
                }
            } while (swapped);
        }

        /**
         * Swap the DATA between two nodes (not the pointers).
         * This approach is simpler than re-linking nodes.
         */
        private void swapNodeData(NoteNode a, NoteNode b) {
            // Swap title
            String tempTitle = a.getTitle();
            a.setTitle(b.getTitle());
            b.setTitle(tempTitle);

            // Swap subject
            String tempSubject = a.getSubject();
            a.setSubject(b.getSubject());
            b.setSubject(tempSubject);

            // Swap content
            String tempContent = a.getContent();
            a.setContent(b.getContent());
            b.setContent(tempContent);

            // Swap priority
            int tempPriority = a.getPriority();
            a.setPriority(b.getPriority());
            b.setPriority(tempPriority);

            // Swap revision date
            java.time.LocalDate tempDate = a.getRevisionDate();
            a.setRevisionDate(b.getRevisionDate());
            b.setRevisionDate(tempDate);

            // Swap linked note IDs
            java.util.List<Integer> tempLinked = new java.util.ArrayList<>(a.getLinkedNoteIds());
            a.getLinkedNoteIds().clear();
            a.getLinkedNoteIds().addAll(b.getLinkedNoteIds());
            b.getLinkedNoteIds().clear();
            b.getLinkedNoteIds().addAll(tempLinked);
        }

        // ==================== DISPLAY HELPERS ====================

        /**
         * Display all notes in a formatted table (forward traversal).
         */
        public void displayAll() {
            traverseForward();
        }

        /**
         * Print the table header row.
         */
        private void printTableHeader() {
            System.out.println("┌──────┬──────────────────────┬─────────────────┬────────────┬──────────────┐");
            System.out.printf("│ %-4s │ %-20s │ %-15s │ %-10s │ %-12s │%n",
                    "ID", "Title", "Subject", "Priority", "Revision");
            System.out.println("├──────┼──────────────────────┼─────────────────┼────────────┼──────────────┤");
        }

        /**
         * Print the table footer.
         */
        private void printTableFooter() {
            System.out.println("└──────┴──────────────────────┴─────────────────┴────────────┴──────────────┘");
        }
}
