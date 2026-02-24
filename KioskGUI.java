import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class KioskGUI extends JFrame {
    private MenuManager menuManager;
    private SalesSummary salesSummary;
    private Order currentOrder;
    private static int customerCounter = 1;

    public KioskGUI(final MenuManager menuManager, final SalesSummary salesSummary) {
        this.menuManager = menuManager;
        this.salesSummary = salesSummary;

        setTitle("Kiosk Order System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize order
        boolean isDineIn = showDineInOption();
        currentOrder = new Order(isDineIn, customerCounter++);

        // Font & size standard
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        Dimension btnSize = new Dimension(300, 60);

        // Create category buttons
        JPanel categoryPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        final String[] categories = {"Set Gepuk", "Set Bakso", "Set Penyet", "Set Bakar", "Drinks", "Add-On"};

        for (int i = 0; i < categories.length; i++) {
            final int index = i;
            JButton btn = createStyledButton(categories[i], buttonFont, btnSize);
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showItemsForCategory(index);
                }
            });
            categoryPanel.add(btn);
        }

        // ? Finish Order Button
        JButton finishButton = createStyledButton("Finish Order", buttonFont, btnSize);
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                finishOrder();
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.add(finishButton);

        add(categoryPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ? Helper method: Buat butang style sama
    private JButton createStyledButton(String text, Font font, Dimension size) {
        final JButton button = new JButton(text);
        button.setFont(font);
        button.setPreferredSize(size);
        button.setBackground(new Color(139, 69, 19));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
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

    private boolean showDineInOption() {
        int option = JOptionPane.showOptionDialog(this,
                "Dine-in or Takeaway?",
                "Order Type",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Dine-in", "Takeaway"},
                "Dine-in");
        return option == 0;
    }

    private void showItemsForCategory(int categoryIndex) {
        ArrayList<MenuItem> items = menuManager.getItemsByCategory(categoryIndex);
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No available items in this category.");
            return;
        }

        String[] itemNames = new String[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemNames[i] = items.get(i).getName() + " - RM" + items.get(i).getSetPrice();
        }

        String selected = (String) JOptionPane.showInputDialog(this,
                "Select item:",
                "Menu Items",
                JOptionPane.PLAIN_MESSAGE,
                null,
                itemNames,
                itemNames[0]);

        if (selected != null) {
            int selectedIndex = -1;
            for (int i = 0; i < itemNames.length; i++) {
                if (itemNames[i].equals(selected)) {
                    selectedIndex = i;
                    break;
                }
            }

            if (selectedIndex >= 0) {
                MenuItem selectedItem = items.get(selectedIndex);
                addItemToOrder(selectedItem, categoryIndex);
            }
        }
    }

    private void addItemToOrder(final MenuItem item, final int categoryIndex) {
        if (categoryIndex < 4 && item.getAlaCartePrice() != null) {
            Object[] options = {"Set", "Ala Carte"};
            int choice = JOptionPane.showOptionDialog(this,
                    "Choose meal type:",
                    "Meal Type",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == 1) {
                Object[] drinkOptions = {"Teh O", "Sirap"};
                int drinkChoice = JOptionPane.showOptionDialog(this,
                        "Choose drink:",
                        "Drink Selection",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        drinkOptions,
                        drinkOptions[0]);

                Object[] tempOptions = {"Hot", "Cold"};
                int tempChoice = JOptionPane.showOptionDialog(this,
                        "Choose temperature:",
                        "Temperature",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        tempOptions,
                        tempOptions[0]);

                String qtyStr = JOptionPane.showInputDialog(this, "Quantity:", "1");
                int quantity = 1;
                try {
                    quantity = Integer.parseInt(qtyStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid quantity. Defaulting to 1.");
                }

                currentOrder.addItem(new OrderItem(
                        item,
                        item.getAlaCartePrice(),
                        quantity,
                        drinkOptions[drinkChoice].toString(),
                        tempOptions[tempChoice].toString()
                ));
                return;
            }
        }

        String qtyStr = JOptionPane.showInputDialog(this, "Quantity:", "1");
        int quantity = 1;
        try {
            quantity = Integer.parseInt(qtyStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Defaulting to 1.");
        }

        if (categoryIndex == 4) {
            Object[] tempOptions = {"Hot", "Cold"};
            int tempChoice = JOptionPane.showOptionDialog(this,
                    "Choose temperature:",
                    "Temperature",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tempOptions,
                    tempOptions[0]);

            currentOrder.addItem(new OrderItem(
                    item,
                    item.getSetPrice(),
                    quantity,
                    "",
                    tempOptions[tempChoice].toString()
            ));
        } else {
            currentOrder.addItem(new OrderItem(
                    item,
                    item.getSetPrice(),
                    quantity,
                    "",
                    ""
            ));
        }
    }

    private void finishOrder() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("==== RECEIPT ====\n");
        receipt.append("Customer #").append(currentOrder.getCustomerNumber()).append("\n");
        receipt.append("Type: ").append(currentOrder.isDineIn() ? "Dine-in" : "Takeaway").append("\n\n");

        for (OrderItem item : currentOrder.getItems()) {
            receipt.append(item.getQuantity()).append("x ").append(item.getItem().getName());
            if (!item.getTemperature().isEmpty()) {
                receipt.append(" (").append(item.getTemperature()).append(")");
            }
            receipt.append(" @ RM").append(item.getPrice())
                    .append(" = RM").append(item.getTotalPrice()).append("\n");
        }

        receipt.append("\nSubtotal: RM").append(String.format("%.2f", currentOrder.calculateSubtotal())).append("\n");
        receipt.append("SST (6%): RM").append(String.format("%.2f", currentOrder.calculateSST())).append("\n");
        receipt.append("TOTAL: RM").append(String.format("%.2f", currentOrder.calculateTotal())).append("\n");

        Object[] paymentOptions = {"Cash", "Credit Card", "Debit Card", "E-Wallet"};
        int paymentChoice = JOptionPane.showOptionDialog(this,
                "Select payment method:", "Payment", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, paymentOptions, paymentOptions[0]);

        receipt.append("\nPayment: ").append(paymentOptions[paymentChoice]).append("\n");
        receipt.append("\nThank you for your order!");

        JOptionPane.showMessageDialog(this, receipt.toString(), "Order Complete", JOptionPane.INFORMATION_MESSAGE);

        salesSummary.addOrder(currentOrder);
        dispose();
    }
}
