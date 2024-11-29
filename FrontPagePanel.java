import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class FrontPagePanel extends JPanel {
    private Image backgroundImage;

    public FrontPagePanel(JTabbedPane tabbedPane) {
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\joren\\Desktop\\ccs0070\\java vscode\\FinalsProject\\1339726.png"));
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }

        setLayout(new GridBagLayout()); // GridBagLayout for responsiveness
        GridBagConstraints gbc = new GridBagConstraints();

        // Button configuration
        gbc.insets = new Insets(10, 300, 10, 300); // Add padding around buttons
        gbc.fill = GridBagConstraints.BOTH; // Buttons resize horizontally and vertically
        gbc.weightx = 1.0; // Equal horizontal space distribution
        gbc.weighty = 0.1; // Smaller vertical weight for each button
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Each button takes up a full row

        // Create buttons for navigation
        JButton addBookButton = createButton("Add Books", tabbedPane, 1);
        JButton registerStudentButton = createButton("Register Students", tabbedPane, 2);
        JButton borrowBookButton = createButton("Borrow Book", tabbedPane, 3);
        JButton returnBookButton = createButton("Return Book", tabbedPane, 4);
        JButton viewBooksButton = createButton("View All Books", tabbedPane, 5);
        JButton viewStudentsButton = createButton("View Registered Students", tabbedPane, 6);
        JButton removeBookButton = createButton("Remove Book", tabbedPane, 8);
        JButton removeMemberButton = createButton("Remove Member", tabbedPane, 9);
        JButton searchBookButton = createButton("Search Books", tabbedPane, 7);

        // Add buttons to the panel with layout constraints
        add(addBookButton, gbc);
        add(registerStudentButton, gbc);
        add(borrowBookButton, gbc);
        add(returnBookButton, gbc);
        add(viewBooksButton, gbc);
        add(viewStudentsButton, gbc);
        add(removeBookButton, gbc);
        add(removeMemberButton, gbc);
        add(searchBookButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image to cover the entire panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();
        // Adjust button fonts dynamically
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int fontSize = Math.min(panelWidth, panelHeight) / 30; // Calculate font size dynamically

        for (Component component : getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setFont(new Font("Arial", Font.BOLD, fontSize));
            }
        }
    }

    private JButton createButton(String text, JTabbedPane tabbedPane, int tabIndex) {
        JButton button = new JButton(text);

        // Button styling
        button.setBackground(new Color(1, 50, 32)); // Moss green background
        button.setForeground(new Color(210, 180, 140)); // White text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(54, 38, 36), 2)); // Moss green border

        // Add click listener to navigate to the selected tab
        button.addActionListener(e -> tabbedPane.setSelectedIndex(tabIndex));

        return button;
    }
}
