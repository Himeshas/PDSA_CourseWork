package manager;

public class StudyManager {

    String newTitle = ConsoleHelper.readStringOptional(scanner, "Title", note.getTitle());
    String newSubject = ConsoleHelper.readStringOptional(scanner, "Subject", note.getSubject());
    String newContent = ConsoleHelper.readStringOptional(scanner, "Content", note.getContent());
    int newPriority = ConsoleHelper.readIntOptional(scanner, "Priority", note.getPriority(), 1, 5);
    LocalDate newDate = ConsoleHelper.readDateOptional(scanner, "Revision Date", note.getRevisionDate());
}
