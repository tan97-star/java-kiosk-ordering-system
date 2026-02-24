public abstract class MenuItem {
    protected String name;
    protected double setPrice;
    protected Double alaCartePrice;
    protected boolean available;
    protected double discount;
    protected String category;

    public MenuItem(String name, double setPrice, Double alaCartePrice, boolean available, String category) 
    {
        this.name = name;
        this.setPrice = setPrice;
        this.alaCartePrice = alaCartePrice;
        this.available = available;
        this.category = category;
        this.discount = 0.0;
    }
    
    //setter 
    public String getName(){ 
      return name; 
    }
    public double getSetPrice() { 
      return setPrice * (1 - discount); 
    }
    public Double getAlaCartePrice() { 
      return alaCartePrice == null ? null : alaCartePrice * (1 - discount); 
    }
    public boolean isAvailable() { 
      return available; 
    }
    public double getDiscount() { 
      return discount;
    }
    public String getCategory() { 
      return category; 
    }
    
    //getter
    public void setSetPrice(double price) { 
      this.setPrice = price; 
    }
    public void setAlaCartePrice(double price) { 
      this.alaCartePrice = price; 
    }
    public void setAvailable(boolean available) { 
      this.available = available; 
    }
    public void setDiscount(double discount) { 
      this.discount = discount; 
    }

    public String toString() {
        String ala = (alaCartePrice == null) ? "-" : String.format("RM %.2f", getAlaCartePrice());
        return String.format("%-20s RM %-9.2f %-15s %-12s",
            name, getSetPrice(), ala, (available ? "Yes" : "No"));
    }
}