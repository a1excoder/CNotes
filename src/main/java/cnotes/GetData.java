package cnotes;

import cnotes.model.Note;

import java.util.Date;
import java.util.Scanner;

public class GetData {

    private static String buffer;
    private static DataOperations dataOperations;

    public GetData() {
        dataOperations = new DataOperations();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            buffer = scanner.nextLine();

            if (!procession()) break;
        }
        dataOperations.closeSessionFactory();
    }

    private static boolean procession() {

        switch (buffer) {
            case "quit":
                return false;
            case "new":
                dataOperations.newNote(createNote());
                return true;
            case "all":
                checkAllNotes(dataOperations);
                return true;
            case "delete":
                deleteNote(dataOperations);
                return true;
            case "read":
                readNote(dataOperations);
                return true;
            case "help":
                System.out.println("""
                        Enter commands without '<>'
                        create note - <new>
                        check all notes id - <all>
                        read note - <read>
                        delete note - <delete>
                        quit - <quit>""");
                return true;
            default:
                System.out.printf("%s command not found\n", buffer);
                return true;
        }

    }

    private static Note createNote() {
        Note note = new Note();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter title: ");
        note.setTitle(scanner.nextLine());

        System.out.print("Enter query: ");
        note.setQuery(scanner.nextLine());

        note.setDate(new Date());

        return note;
    }

    private static void checkAllNotes(DataOperations dataOperations) {
        for (Note note : dataOperations.getNotes()) {
            System.out.printf("%s - %d\n", note.getTitle(), note.getId());
        }
    }

    private static void deleteNote(DataOperations dataOperations) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter note id: ");

        int id = scanner.nextInt();
        if (!dataOperations.deleteNote(id)) {
            System.out.printf("Note with %d id not found\n", id);
        } else {
            System.out.println("Note deleted");
        }

    }

    private static void readNote(DataOperations dataOperations) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter note id: ");
        int id = scanner.nextInt();
        Note note = dataOperations.getNote(id);

        try {
            System.out.printf("%d - %s - %s\n%s\n", note.getId(), note.getTitle(), note.getDate(), note.getQuery());
        } catch (NullPointerException ignored) {
            System.out.printf("Note with %d id not found\n", id);
        }
    }

}
