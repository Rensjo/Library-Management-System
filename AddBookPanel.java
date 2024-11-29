import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddBookPanel extends JPanel {
    private Image backgroundImage;

    public AddBookPanel() {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\joren\\Desktop\\ccs0070\\java vscode\\FinalsProject\\27_11_2024 5_50_25 pm.png"));
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }

        setLayout(new GridBagLayout());
        setBackground(new Color(220, 174, 150)); // #DCAE96

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6); // Margins between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Labels and Input Fields
        JLabel titleLabel = createLabel("Book Title:");
        JTextField titleField = createTextField();

        JLabel authorLabel = createLabel("Author:");
        JTextField authorField = createTextField();

        JLabel isbnLabel = createLabel("ISBN:");
        JTextField isbnField = createTextField();

        JLabel copiesLabel = createLabel("Number of Copies:");
        JTextField copiesField = createTextField();

        JButton addButton = createButton("Add Book");

        // Add components to the panel
        add(titleLabel, gbc);
        gbc.gridy++;
        add(titleField, gbc);
        gbc.gridy++;
        add(authorLabel, gbc);
        gbc.gridy++;
        add(authorField, gbc);
        gbc.gridy++;
        add(isbnLabel, gbc);
        gbc.gridy++;
        add(isbnField, gbc);
        gbc.gridy++;
        add(copiesLabel, gbc);
        gbc.gridy++;
        add(copiesField, gbc);
        gbc.gridy++;
        add(addButton, gbc);

        // Button ActionListener for Adding Book
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String copies = copiesField.getText();

            if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || copies.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            try {
                int numCopies = Integer.parseInt(copies);

                // Connect to SQLite and insert the book
                try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {
                    String sql = "INSERT INTO books (title, author, isbn, available_copies) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, title);
                    pstmt.setString(2, author);
                    pstmt.setString(3, isbn);
                    pstmt.setInt(4, numCopies);

                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Book added successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for copies!");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image to cover the entire panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(30);
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        return textField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(134, 75, 45)); // #864B2D
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(107, 65, 37), 2));
        return button;
    }
}
