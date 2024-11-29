import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RemoveMemberPanel extends JPanel {
    private Image backgroundImage;

    public RemoveMemberPanel() {
        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\joren\\Desktop\\ccs0070\\java vscode\\FinalsProject\\27_11_2024 5_50_25 pm.png"));
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }

        setLayout(new GridBagLayout());
        setBackground(new Color(245, 222, 179)); // Background color (will be overridden by image)

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margins
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Labels and Input Fields
        JLabel regNoLabel = createLabel("Enter Registration Number:");
        JTextField regNoField = createTextField();

        JButton removeButton = createButton("Remove Member");

        // Add components to panel
        add(regNoLabel, gbc);
        gbc.gridy++;
        add(regNoField, gbc);
        gbc.gridy++;
        gbc.gridwidth = 2; // Button spans two columns
        add(removeButton, gbc);

        // Button ActionListener for Removing Member
        removeButton.addActionListener(e -> {
            String regNo = regNoField.getText();

            if (regNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Registration Number is required!");
                return;
            }

            try (Connection conn = Database.connect()) {
                String sql = "DELETE FROM students WHERE reg_no = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, regNo);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Member removed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Member not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
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
