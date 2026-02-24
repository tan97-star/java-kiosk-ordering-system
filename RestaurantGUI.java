import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestaurantGUI extends JFrame {
    private MenuManager menuManager;
    private SalesSummary salesSummary;

    public RestaurantGUI() {
        menuManager = new MenuManager();
        salesSummary = new SalesSummary();
        
        setTitle("AYAM GEPUK MANJA MANAGEMENT SYSTEM");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 220));
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(139, 69, 19));
        headerPanel.setPreferredSize(new Dimension(100, 100));
        
        JLabel titleLabel = new JLabel("AYAM GEPUK MANJA MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setForeground(new Color(255, 215, 0));
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(245, 245, 220));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
        
        Font buttonFont = new Font("Arial", Font.BOLD, 24);
        
        // Manager Button
        JButton managerButton = createCustomButton("MANAGER MENU", new Dimension(350, 70), buttonFont);
        managerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ManagerGUI(menuManager).setVisible(true);
            }
        });
        
        // Kiosk Button
        JButton kioskButton = createCustomButton("KIOSK ORDER", new Dimension(300, 60), buttonFont);
        kioskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new KioskGUI(menuManager, salesSummary).setVisible(true);
            }
        });
        
        // Sales Button
        JButton salesButton = createCustomButton("SALES REPORT", new Dimension(300, 60), buttonFont);
        salesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salesSummary.displayDailySales();
            }
        });
        
        // Exit Button
        JButton exitButton = createCustomButton("EXIT SYSTEM", new Dimension(300, 60), buttonFont);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(managerButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(kioskButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(salesButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(exitButton);
        
        add(menuPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(152, 251, 152));
        footerPanel.setPreferredSize(new Dimension(100, 50));
        JLabel footerLabel = new JLabel("© 2025 Ayam Gepuk Manja");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footerLabel.setForeground(Color.BLACK);
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JButton createCustomButton(String text, Dimension size, Font font) {
        final JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
        button.setFont(font);
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(139, 69, 19));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Fix untuk align tengah dalam BoxLayout.Y_AXIS
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Efek hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(160, 82, 45));
                button.setForeground(new Color(255, 215, 0));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(139, 69, 19));
                button.setForeground(Color.BLACK);
            }
        });
        
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                RestaurantGUI gui = new RestaurantGUI();
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
            }
        });
    }
}
