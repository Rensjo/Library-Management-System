import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.JTableHeader;
import java.io.File;
import javax.imageio.ImageIO;

public class SearchBooksPanel extends JPanel {
    private Image backgroundImage;

    public SearchBooksPanel() {
        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\joren\\Desktop\\ccs0070\\java vscode\\FinalsProject\\27_11_2024 5_50_25 pm.png"));
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }

        setLayout(new BorderLayout());
        setBackground(new Color(245, 222, 179)); // Background color (will be overridden by image)

        // Search Bar Panel
        JPanel searchPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f)); // 60% opacity
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        searchPanel.setBackground(new Color(255, 255, 255, 0)); // Black background with transparency
        searchPanel.setOpaque(false); // Ensure transparency is applied
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField searchField = createTextField();
        JButton searchButton = createButton("Search");

        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(searchField, gbc);
        gbc.gridx = 1;
        searchPanel.add(searchButton, gbc);

        add(searchPanel, BorderLayout.NORTH);

        // Create table model and table
        String[] columnNames = {"Book Title", "Author", "ISBN", "Available Copies"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setForeground(Color.WHITE); // Set text color to white
                c.setBackground(new Color(255, 255, 255, 30)); // Transparent background
                return c;
            }
        };

        // Table Header Transparency
        JTableHeader header = table.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(20, 20, 20)); // Semi-transparent header
        header.setForeground(Color.WHITE); // Header text color
        header.setFont(new Font("Arial", Font.BOLD, 14));

        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setOpaque(false); // Make table body background transparent

        // ScrollPane Transparency
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        // Wrap the scroll pane in a transparent panel and center it with margins
        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setOpaque(false); // Transparent panel
        GridBagConstraints tableGbc = new GridBagConstraints();
        tableGbc.insets = new Insets(0, 35, 35, 35); // Margins around the table
        tableGbc.fill = GridBagConstraints.BOTH;
        tableGbc.weightx = 1.0;
        tableGbc.weighty = 1.0;
        tablePanel.add(scrollPane, tableGbc);

        add(tablePanel, BorderLayout.CENTER);

        // Button ActionListener for Searching Books
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            tableModel.setRowCount(0); // Clear previous results

            try (Connection conn = Database.connect()) {
                String sql = "SELECT title, author, isbn, available_copies FROM books WHERE title LIKE ? OR author LIKE ? OR isbn LIKE ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchText + "%");
                pstmt.setString(2, "%" + searchText + "%");
                pstmt.setString(3, "%" + searchText + "%");

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String isbn = rs.getString("isbn");
                    int copies = rs.getInt("available_copies");
                    tableModel.addRow(new Object[]{title, author, isbn, copies});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error searching books: " + ex.getMessage());
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

    private JTextField createTextField() {
        JTextField textField = new JTextField(30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2)); // Border color
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
