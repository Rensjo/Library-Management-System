import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;

class RegisterStudentPanel extends JPanel {
    private Image backgroundImage;

    public RegisterStudentPanel() {
        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\joren\\Desktop\\ccs0070\\java vscode\\FinalsProject\\27_11_2024 5_50_25 pm.png"));
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255)); // Background color (will be overridden by the image)

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margins
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Labels and Text Fields
        JLabel nameLabel = createLabel("Student Name:");
        JTextField nameField = createTextField();

        JLabel regNoLabel = createLabel("Registration Number:");
        JTextField regNoField = createTextField();

        JButton registerButton = createButton("Register Student");

        // Add components to panel
        add(nameLabel, gbc);
        gbc.gridx = 1;
        add(nameField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(regNoLabel, gbc);
        gbc.gridx = 1;
        add(regNoField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Button spans both columns
        add(registerButton, gbc);

        // Button ActionListener for Registering Student
        registerButton.addActionListener(e -> {
            String name = nameField.getText();
            String regNo = regNoField.getText();

            if (name.isEmpty() || regNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            // Database insertion
            try (Connection conn = Database.connect()) {
                String sql = "INSERT INTO students(name, reg_no) VALUES(?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, regNo);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Student registered successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error registering student: " + ex.getMessage());
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
