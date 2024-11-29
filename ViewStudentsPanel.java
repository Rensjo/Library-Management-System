import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.io.File;
import javax.imageio.ImageIO;

public class ViewStudentsPanel extends JPanel {
    private Image backgroundImage;

    public ViewStudentsPanel() {
        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\joren\\Desktop\\ccs0070\\java vscode\\FinalsProject\\27_11_2024 5_50_25 pm.png"));
        } catch (Exception e) {
            System.out.println("Error loading background image: " + e.getMessage());
        }

        setLayout(new GridBagLayout()); // Center components in the panel
        setBackground(new Color(255, 255, 255, 30)); // Background color (overridden by image)

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(40, 40, 40, 40); // Margins around the table
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // Create table model and table
        String[] columnNames = {"Student Name", "Registration Number"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setForeground(Color.WHITE); // White text color
                c.setBackground(new Color(255, 255, 255, 30)); // Fully transparent background
                return c;
            }
        };

        // Table Header Styling
        JTableHeader header = table.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(20, 20, 20)); // Semi-transparent dark
        header.setForeground(Color.WHITE); // White text
        header.setFont(new Font("Arial", Font.BOLD, 14));

        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setOpaque(false);

        // ScrollPane Transparency
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        // Wrap the scroll pane in a transparent panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false); // Transparent panel
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add table panel to the center with margins
        add(tablePanel, gbc);

        // Fetch data from the database
        try (Connection conn = Database.connect()) {
            String sql = "SELECT name, reg_no FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                String regNo = rs.getString("reg_no");
                tableModel.addRow(new Object[]{name, regNo});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading students: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image to cover the entire panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
