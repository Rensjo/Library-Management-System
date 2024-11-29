import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

class Database {
    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:library.db";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return conn;
    }

    public static void initializeDatabase() {
        try (Connection conn = connect()) {
            // SQL statements for table creation
            String booksTable = "CREATE TABLE IF NOT EXISTS books ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "title TEXT NOT NULL,"
                    + "author TEXT NOT NULL,"
                    + "isbn TEXT UNIQUE NOT NULL,"
                    + "available_copies INTEGER NOT NULL"
                    + ");";

            String studentsTable = "CREATE TABLE IF NOT EXISTS students ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL,"
                    + "reg_no TEXT UNIQUE NOT NULL"
                    + ");";

            String borrowedBooksTable = "CREATE TABLE IF NOT EXISTS borrowed_books ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "student_reg_no TEXT NOT NULL,"
                    + "book_id INTEGER NOT NULL,"
                    + "FOREIGN KEY (student_reg_no) REFERENCES students(reg_no),"
                    + "FOREIGN KEY (book_id) REFERENCES books(id)"
                    + ");";

            // Execute table creation queries
            conn.createStatement().execute(booksTable);
            conn.createStatement().execute(studentsTable);
            conn.createStatement().execute(borrowedBooksTable);

            System.out.println("Database tables initialized successfully.");
        } catch (Exception e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    // Add the sample data insertion method here
    public static void addSampleData() {
        String sampleBooks = """
            INSERT INTO books (title, author, isbn, available_copies) VALUES
            ('One Piece', 'Eiichiro Oda', '9781234567897', 15),
            ('Boruto: Blue Vortex', 'Masashi Kishimoto', '9782345678901', 10),
            ('Unordinary', 'uru-chan', '9783456789012', 12),
            ('Omniscient Reader’s Viewpoint', 'Sing Shong', '9784567890123', 8),
            ('Tales of Demons and Gods', 'Mad Snail', '9785678901234', 5),
            ('Tower of God', 'SIU', '9786789012345', 10),
            ('Spy x Family', 'Tatsuya Endo', '9787890123456', 14),
            ('Atomic Habits', 'James Clear', '9788901234567', 18),
            ('The Subtle Art of Not Giving a F*ck', 'Mark Manson', '9789012345678', 8),
            ('The Alchemist', 'Paulo Coelho', '9780123456789', 10),
            ('To Kill a Mockingbird', 'Harper Lee', '9780987654321', 9),
            ('1984', 'George Orwell', '9789876543210', 11),
            ('Harry Potter and the Philosopher’s Stone', 'J.K. Rowling', '9788765432109', 7),
            ('The Great Gatsby', 'F. Scott Fitzgerald', '9787654321098', 13),
            ('Pride and Prejudice', 'Jane Austen', '9786543210987', 10);
        """;

        String sampleStudents = """
            INSERT INTO students (name, reg_no) VALUES
            ('Jorens Ivan Rance', '2023-12215'),
            ('Sudais Amerol', '2024-12345'),
            ('Kyle David Paraiso', '2024-23456'),
            ('Angel Francel Hermogenes', '2024-34567'),
            ('John Joseph Topacio', '2024-45678'),
            ('Maria Clara Santos', '2024-56789'),
            ('Jose Rizal de Leon', '2024-67890'),
            ('Andres Bonifacio Cruz', '2024-78901'),
            ('Gabriela Silang Navarro', '2024-89012'),
            ('Francisco Balagtas Reyes', '2024-90123'),
            ('Lapu-Lapu Fernandez', '2024-01234'),
            ('Emilio Aguinaldo Perez', '2024-22345'),
            ('Apolinario Mabini Garcia', '2024-33456'),
            ('Antonio Luna Rivera', '2024-44567'),
            ('Melchora Aquino Bautista', '2024-66789');
        """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sampleBooks);
            stmt.executeUpdate(sampleStudents);
            System.out.println("Sample data added successfully.");
        } catch (Exception e) {
            System.err.println("Error adding sample data: " + e.getMessage());
        }
    }
}
