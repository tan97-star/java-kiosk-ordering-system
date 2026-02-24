public class OrderItem {
    private MenuItem item;
    private double price;
    private int quantity;
    private String drinkChoice;
    private String temperature;
    
    public OrderItem(MenuItem item, double price, int quantity, 
                    String drinkChoice, String temperature) {
        this.item = item;
        this.price = price;
        this.quantity = quantity;
        this.drinkChoice = drinkChoice;
        this.temperature = temperature;
    }
    
    public MenuItem getItem() { return item; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getDrinkChoice() { return drinkChoice; }
    public String getTemperature() { return temperature; }
    
    public double getTotalPrice() {
        return price * quantity;
    }
}