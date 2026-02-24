import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.text.DecimalFormat;

public class MenuManager implements MenuOperations {
    private ArrayList<MenuItem> menuList;
    private DecimalFormat df = new DecimalFormat("0.00");

    public MenuManager() {
        menuList = new ArrayList<MenuItem>();
        // Initialize menu items
        // Set Gepuk
        menuList.add(new SetGepuk("Set Gepuk A", 14.5, 12.5, true));
        menuList.add(new SetGepuk("Set Gepuk B", 15.5, 13.5, true));
        menuList.add(new SetGepuk("Set Gepuk C", 18.5, 16.5, true));
        menuList.add(new SetGepuk("Set Gepuk D", 20.5, 18.5, true));

        // Set Bakso
        menuList.add(new SetBakso("Set Bakso Biasa", 11.5, 9.5, true));
        menuList.add(new SetBakso("Set Bakso Daging", 16.5, 14.5, true));
        menuList.add(new SetBakso("Set Bakso Ayam", 13.5, 11.5, true));

        // Set Penyet
        menuList.add(new SetPenyet("Set Penyet Ayam", 16.5, 14.5, true));
        menuList.add(new SetPenyet("Set Penyet Keli", 14.5, 12.5, true));
        menuList.add(new SetPenyet("Set Penyet Talapia", 15.5, 13.5, true));

        // Set Bakar
        menuList.add(new SetBakar("Set Bakar Ayam", 16.5, 14.5, true));
        menuList.add(new SetBakar("Set Bakar Keli", 14.5, 12.5, true));
        menuList.add(new SetBakar("Set Bakar Daging", 16.5, 14.5, true));

        // Drinks
        menuList.add(new Drinks("Nescafe", 2.0, true));
        menuList.add(new Drinks("Sirap Limau", 2.0, true));
        menuList.add(new Drinks("Milo", 4.0, true));
        menuList.add(new Drinks("Sirap Bandung", 4.0, true));

        // Add-Ons
        menuList.add(new AddOn("Extra Sambal", 1.0, true));
        menuList.add(new AddOn("Extra Rice", 2.0, true));
        menuList.add(new AddOn("Fried Chicken", 5.0, true));
        menuList.add(new AddOn("Tempe", 1.0, true));
        menuList.add(new AddOn("Tauhu", 1.0, true));
        menuList.add(new AddOn("Pedal", 1.0, true));
        menuList.add(new AddOn("Paru", 1.0, true));
    }

    public ArrayList<MenuItem> getMenuList() {
        return menuList;
    }

    @Override
    public void displayMenu() {
        StringBuilder sb = new StringBuilder();
        String[] categories = {"Set Gepuk", "Set Bakso", "Set Penyet", "Set Bakar", "Drinks", "Add-On"};
        for (String category : categories) {
            sb.append("\n==== ").append(category.toUpperCase()).append(" ====\n");
            sb.append(String.format("%-4s %-20s %-12s %-15s %-12s %-12s\n",
                    "No", "Name", "Set", "Ala Carte", "Available", "Discount"));
            int count = 1;
            for (MenuItem item : menuList) {
                if (item.getCategory().equals(category)) {
                    String ala = (item.getAlaCartePrice() == null) ? "-" : String.format("RM %.2f", item.getAlaCartePrice());
                    String discount = df.format(item.getDiscount() * 100) + "%";
                    sb.append(String.format("%-4d %-20s RM %-9.2f %-15s %-12s %-12s\n",
                            count++, item.getName(), item.getSetPrice(), ala,
                            (item.isAvailable() ? "Yes" : "No"), discount));
                }
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12)); // 
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));
        JOptionPane.showMessageDialog(null, scrollPane, "Menu", JOptionPane.INFORMATION_MESSAGE);
    }

    private int selectCategory(Scanner input) {
        Object[] options = {"Set Gepuk", "Set Bakso", "Set Penyet", "Set Bakar", "Drinks", "Add-On"};
        return JOptionPane.showOptionDialog(null,
                "Select category:",
                "Category Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    public ArrayList<MenuItem> getItemsByCategory(int catIndex) {
        String[] categories = {"Set Gepuk", "Set Bakso", "Set Penyet", "Set Bakar", "Drinks", "Add-On"};
        ArrayList<MenuItem> result = new ArrayList<MenuItem>();
        for (MenuItem item : menuList) {
            if (item.getCategory().equals(categories[catIndex])) {
                result.add(item);
            }
        }
        return result;
    }

    @Override
    public void addMenuItem(Scanner input) {
        int cat = selectCategory(input);
        if (cat == -1) return;

        try {
            String name = JOptionPane.showInputDialog("Item name:");
            if (name == null || name.trim().isEmpty()) return;

            String setPriceStr = JOptionPane.showInputDialog("Set price:");
            if (setPriceStr == null) return;
            double setP = Double.parseDouble(setPriceStr);

            Double ala = null;
            if (cat < 4) { // only set meals
                String alaPriceStr = JOptionPane.showInputDialog("Ala Carte price:");
                if (alaPriceStr != null) {
                    ala = Double.parseDouble(alaPriceStr);
                }
            }

            MenuItem m = null;
            switch (cat) {
                case 0: m = new SetGepuk(name, setP, ala, true); break;
                case 1: m = new SetBakso(name, setP, ala, true); break;
                case 2: m = new SetPenyet(name, setP, ala, true); break;
                case 3: m = new SetBakar(name, setP, ala, true); break;
                case 4: m = new Drinks(name, setP, true); break;
                case 5: m = new AddOn(name, setP, true); break;
            }
            if (m != null) {
                menuList.add(m);
                JOptionPane.showMessageDialog(null, "Item added successfully.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteMenuItem(Scanner input) {
        int cat = selectCategory(input);
        if (cat == -1) return;
        ArrayList<MenuItem> items = getItemsByCategory(cat);
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items in this category.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[] itemOptions = new Object[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemOptions[i] = (i + 1) + ". " + items.get(i).toString();
        }

        String selected = (String) JOptionPane.showInputDialog(null,
                "Select item to delete:",
                "Delete Item",
                JOptionPane.QUESTION_MESSAGE,
                null,
                itemOptions,
                itemOptions[0]);

        if (selected != null) {
            int selectedIndex = -1;
            for (int i = 0; i < itemOptions.length; i++) {
                if (itemOptions[i].equals(selected)) {
                    selectedIndex = i;
                    break;
                }
            }

            if (selectedIndex >= 0) {
                menuList.remove(items.get(selectedIndex));
                JOptionPane.showMessageDialog(null, "Item deleted successfully.");
            }
        }
    }

    @Override
    public void updateMenuItem(Scanner input) {
        int cat = selectCategory(input);
        if (cat == -1) return;
        ArrayList<MenuItem> items = getItemsByCategory(cat);

        Object[] itemOptions = new Object[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemOptions[i] = (i + 1) + ". " + items.get(i).toString();
        }

        String selected = (String) JOptionPane.showInputDialog(null,
                "Select item to update:",
                "Update Item",
                JOptionPane.QUESTION_MESSAGE,
                null,
                itemOptions,
                itemOptions[0]);

        if (selected != null) {
            int selectedIndex = -1;
            for (int i = 0; i < itemOptions.length; i++) {
                if (itemOptions[i].equals(selected)) {
                    selectedIndex = i;
                    break;
                }
            }

            if (selectedIndex >= 0) {
                MenuItem item = items.get(selectedIndex);
                try {
                    String setPriceStr = JOptionPane.showInputDialog("New set price:", item.getSetPrice());
                    if (setPriceStr != null) {
                        item.setSetPrice(Double.parseDouble(setPriceStr));
                    }

                    if (item.getAlaCartePrice() != null) {
                        String alaPriceStr = JOptionPane.showInputDialog("New ala carte price:", item.getAlaCartePrice());
                        if (alaPriceStr != null) {
                            item.setAlaCartePrice(Double.parseDouble(alaPriceStr));
                        }
                    }

                    JOptionPane.showMessageDialog(null, "Prices updated successfully.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void updateDiscount(Scanner input) {
        int cat = selectCategory(input);
        if (cat == -1) return;
        ArrayList<MenuItem> items = getItemsByCategory(cat);

        Object[] itemOptions = new Object[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemOptions[i] = (i + 1) + ". " + items.get(i).toString();
        }

        String selected = (String) JOptionPane.showInputDialog(null,
                "Select item to discount:",
                "Set Discount",
                JOptionPane.QUESTION_MESSAGE,
                null,
                itemOptions,
                itemOptions[0]);

        if (selected != null) {
            int selectedIndex = -1;
            for (int i = 0; i < itemOptions.length; i++) {
                if (itemOptions[i].equals(selected)) {
                    selectedIndex = i;
                    break;
                }
            }

            if (selectedIndex >= 0) {
                try {
                    String discountStr = JOptionPane.showInputDialog(
                            "Discount percentage (0-100):",
                            df.format(items.get(selectedIndex).getDiscount() * 100));
                    if (discountStr != null) {
                        double discountPercent = Double.parseDouble(discountStr);
                        if (discountPercent >= 0 && discountPercent <= 100) {
                            items.get(selectedIndex).setDiscount(discountPercent / 100);
                            JOptionPane.showMessageDialog(null,
                                    "Discount updated to " + df.format(discountPercent) + "%");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Discount must be between 0 and 100%.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid number format.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void updateAvailability(Scanner input) {
        int cat = selectCategory(input);
        if (cat == -1) return;
        ArrayList<MenuItem> items = getItemsByCategory(cat);

        Object[] itemOptions = new Object[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemOptions[i] = (i + 1) + ". " + items.get(i).toString();
        }

        String selected = (String) JOptionPane.showInputDialog(null,
                "Select item to update availability:",
                "Update Availability",
                JOptionPane.QUESTION_MESSAGE,
                null,
                itemOptions,
                itemOptions[0]);

        if (selected != null) {
            int selectedIndex = -1;
            for (int i = 0; i < itemOptions.length; i++) {
                if (itemOptions[i].equals(selected)) {
                    selectedIndex = i;
                    break;
                }
            }

            if (selectedIndex >= 0) {
                int choice = JOptionPane.showConfirmDialog(null,
                        "Is this item available?",
                        "Availability",
                        JOptionPane.YES_NO_OPTION);

                items.get(selectedIndex).setAvailable(choice == JOptionPane.YES_OPTION);
                JOptionPane.showMessageDialog(null, "Availability updated successfully.");
            }
        }
    }
}
