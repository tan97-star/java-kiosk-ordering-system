import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class ManagerGUI extends JFrame {
    private MenuManager menuManager;
    private Scanner inputScanner;

    public ManagerGUI(final MenuManager menuManager) {
        this.menuManager = menuManager;
        this.inputScanner = new Scanner(System.in);

        setTitle("Manager Menu");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));
        setLayout(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(new Color(240, 240, 240));

        buttonPanel.add(createStyledButton("Display Menu", new Color(70, 130, 180), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuManager.displayMenu();
            }
        }));

        buttonPanel.add(createStyledButton("Add Menu Item", new Color(60, 179, 113), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuManager.addMenuItem(inputScanner);
            }
        }));

        buttonPanel.add(createStyledButton("Delete Menu Item", new Color(205, 92, 92), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuManager.deleteMenuItem(inputScanner);
            }
        }));

        buttonPanel.add(createStyledButton("Update Prices", new Color(255, 165, 0), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuManager.updateMenuItem(inputScanner);
            }
        }));

        buttonPanel.add(createStyledButton("Update Availability", new Color(147, 112, 219), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuManager.updateAvailability(inputScanner);
            }
        }));

        buttonPanel.add(createStyledButton("Set Discount", new Color(233, 150, 122), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuManager.updateDiscount(inputScanner);
            }
        }));

        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, final Color bgColor, ActionListener listener) {
        final JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }
}
