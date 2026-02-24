import java.util.ArrayList;
import javax.swing.JOptionPane; 

public class SalesSummary {
    private ArrayList<Order> dailyOrders;
    private double totalRevenue;
    private double totalCost;
    private double totalTax;
    private double totalProfit;
    private static final double TAX_RATE = 0.06;
    private final double foodCostRate = 0.45;
    private final double operatingCostRate = 0.25;
    private int totalCustomers;
    
    public SalesSummary() {
        dailyOrders = new ArrayList<Order>(); 
        totalRevenue = 0.0;
        totalCost = 0.0;
        totalTax = 0.0;
        totalProfit = 0.0;
        totalCustomers = 0;
    }
    
    public void addOrder(Order order) {
        dailyOrders.add(order);
        double orderRevenue = order.calculateTotal();
        totalRevenue += orderRevenue;
        
        // Calculate tax using the TAX_RATE constant
        double orderTax = order.calculateSubtotal() * TAX_RATE;
        totalTax += orderTax;
        
        // Calculate cost components
        double orderFoodCost = order.calculateSubtotal() * foodCostRate;
        double orderOperatingCost = order.calculateSubtotal() * operatingCostRate;
        double orderCost = orderFoodCost + orderOperatingCost;
        totalCost += orderCost;
        
        // Calculate profit
        double orderProfit = orderRevenue - orderCost - orderTax;
        totalProfit += orderProfit;
        
        totalCustomers++;
    }
    
    public void displayDailySales() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n==== DAILY SALES SUMMARY ====\n");
        sb.append(String.format("Total Customers: %d\n", totalCustomers));
        sb.append(String.format("Total Orders: %d\n", dailyOrders.size()));
        sb.append("---------------------------------\n");
        sb.append(String.format("Total Revenue: RM%.2f\n", totalRevenue));
        sb.append(String.format("Total Food Cost (45%%): RM%.2f\n", totalRevenue * foodCostRate));
        sb.append(String.format("Operating Cost (25%%): RM%.2f\n", totalRevenue * operatingCostRate));
        sb.append(String.format("Total Cost: RM%.2f\n", totalCost));
        sb.append(String.format("Total Tax (6%% SST): RM%.2f\n", totalTax));
        sb.append("---------------------------------\n");
        sb.append(String.format("NET PROFIT: RM%.2f\n", totalProfit));
        sb.append(String.format("Profit Margin: %.1f%%\n", (totalProfit/totalRevenue)*100));
        
        // Show popular items
        displayPopularItems(sb);
        
        JOptionPane.showMessageDialog(null, sb.toString(), "Sales Summary", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void displayPopularItems(StringBuilder sb) {
        ArrayList<String> itemNames = new ArrayList<String>();
        ArrayList<Integer> itemCounts = new ArrayList<Integer>();
        ArrayList<Double> itemRevenues = new ArrayList<Double>();

        for (Order order : dailyOrders) {
            for (OrderItem item : order.getItems()) {
                String itemName = item.getItem().getName();
                boolean found = false;
                
                for (int i = 0; i < itemNames.size(); i++) {
                    if (itemNames.get(i).equals(itemName)) {
                        itemCounts.set(i, itemCounts.get(i) + item.getQuantity());
                        itemRevenues.set(i, itemRevenues.get(i) + item.getTotalPrice());
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    itemNames.add(itemName);
                    itemCounts.add(item.getQuantity());
                    itemRevenues.add(item.getTotalPrice());
                }
            }
        }

        // Sort by popularity
        for (int i = 0; i < itemCounts.size(); i++) {
            for (int j = i + 1; j < itemCounts.size(); j++) {
                if (itemCounts.get(j) > itemCounts.get(i)) {
                    // Swap counts
                    int tempCount = itemCounts.get(i);
                    itemCounts.set(i, itemCounts.get(j));
                    itemCounts.set(j, tempCount);
                    
                    // Swap names
                    String tempName = itemNames.get(i);
                    itemNames.set(i, itemNames.get(j));
                    itemNames.set(j, tempName);
                    
                    // Swap revenues
                    double tempRev = itemRevenues.get(i);
                    itemRevenues.set(i, itemRevenues.get(j));
                    itemRevenues.set(j, tempRev);
                }
            }
        }

        if (!itemNames.isEmpty()) {
            sb.append("\n==== TOP SELLING ITEMS ====\n");
            sb.append(String.format("%-20s %-10s %-15s\n", "Item", "Quantity", "Revenue"));
            int displayCount = Math.min(5, itemNames.size());
            for (int i = 0; i < displayCount; i++) {
                sb.append(String.format("%-20s %-10d RM%-10.2f\n", 
                    itemNames.get(i), itemCounts.get(i), itemRevenues.get(i)));
            }
        }
    }
}