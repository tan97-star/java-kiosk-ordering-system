import java.util.ArrayList;

public class Order {
    private static int orderCounter = 1;
    private int orderNumber;
    private int customerNumber;
    private boolean isDineIn;
    private ArrayList<OrderItem> items;
    
    public Order(boolean isDineIn, int customerNumber) {
        this.orderNumber = orderCounter++;
        this.customerNumber = customerNumber;
        this.isDineIn = isDineIn;
        this.items = new ArrayList<OrderItem>();
    }
    
    public void addItem(OrderItem item) {
        items.add(item);
    }
    
    public ArrayList<OrderItem> getItems() {
        return items;
    }
    
    public boolean isDineIn() {
        return isDineIn;
    }
    
    public int getOrderNumber() {
        return orderNumber;
    }
    
    public int getCustomerNumber() {
        return customerNumber;
    }
    
    public double calculateSubtotal() {
        double subtotal = 0;
        for (OrderItem item : items) {
            subtotal += item.getTotalPrice();
        }
        return subtotal;
    }
    
    public double calculateSST() {
        return calculateSubtotal() * 0.06;
    }
    
    public double calculateTotal() {
        return calculateSubtotal() + calculateSST();
    }
}