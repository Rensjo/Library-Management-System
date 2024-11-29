import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;

public class ReturnBookPanel extends JPanel {
    private Image backgroundImage;

    public ReturnBookPanel() {
        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\joren\\Desktop\\ccs0070\\java vscode\\FinalsProject\\27_11_2024 5_50_25 pm.png"));
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255)); // Background color (will be overridden by image)

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margins
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Labels and Input Fields
        JLabel regNoLabel = createLabel("Student Registration Number:");
        JTextField regNoField = createTextField();

        JLabel bookIdLabel = createLabel("Book ID:");
        JTextField bookIdField = createTextField();

        JButton returnButton = createButton("Return Book");

        // Add components to panel
        add(regNoLabel, gbc);
        gbc.gridx = 1;
        add(regNoField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(bookIdLabel, gbc);
        gbc.gridx = 1;
        add(bookIdField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Button spans both columns
        add(returnButton, gbc);

        // Button ActionListener for Returning Book
        returnButton.addActionListener(e -> {
            String regNo = regNoField.getText();
            String bookIdText = bookIdField.getText();

            if (regNo.isEmpty() || bookIdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            try {
                int bookId = Integer.parseInt(bookIdText);

                try (Connection conn = Database.connect()) {
                    // Delete from borrowed_books
                    String sql = "DELETE FROM borrowed_books WHERE student_reg_no = ? AND book_id = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, regNo);
                    pstmt.setInt(2, bookId);
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // Update book quantity
                        String updateSql = "UPDATE books SET available_copies = available_copies + 1 WHERE id = ?";
                        PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                        updateStmt.setInt(1, bookId);
                        updateStmt.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Book returned successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "No matching record found.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error returning book: " + ex.getMessage());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid Book ID!");
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
        label.setForeground(Color.WHITE); // Set font color to white
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(134, 75, 45)); // #864B2D
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(107, 65, 37), 2));
        return button;
    }
}
