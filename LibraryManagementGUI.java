import javax.swing.*;
import java.awt.*;

public class LibraryManagementGUI {
    public static void main(String[] args) {
        Database.initializeDatabase();
        Database.addSampleData();

        // Create the main frame
        JFrame frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.add("Home", new FrontPagePanel(tabbedPane));
        tabbedPane.add("Add Book", new AddBookPanel());
        tabbedPane.add("Register Student", new RegisterStudentPanel());
        tabbedPane.add("Borrow Book", new BorrowBookPanel());
        tabbedPane.add("Return Book", new ReturnBookPanel());
        tabbedPane.add("View All Books", new ViewBooksPanel());
        tabbedPane.add("View Registered Students", new ViewStudentsPanel());
        tabbedPane.add("Search Books", new SearchBooksPanel());
        tabbedPane.add("Remove Book", new RemoveBookPanel());
        tabbedPane.add("Remove Member", new RemoveMemberPanel());

        // Add tabs to frame
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }
}
