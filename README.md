# Kiosk Ordering System

A Java-based restaurant self-service kiosk application with GUI for customer ordering and manager operations.

## Features

### Customer Mode
- Browse menu by categories (Set Meals, Drinks, Add-Ons)
- Choose Dine-in or Takeaway
- Customize orders (Set/Ala Carte, drink selection, Hot/Cold)
- Add items with quantity
- Multiple payment methods (Cash, Card, E-Wallet)
- Print receipt with SST calculation (6%)

### Manager Mode
- Display complete menu with prices and availability
- Add new menu items
- Delete existing items
- Update prices (Set & Ala Carte)
- Toggle item availability
- Set percentage discounts (0-100%)

### Sales Reports
- Daily sales summary
- Total customers and revenue
- Cost breakdown (45% food cost, 25% operating)
- Net profit calculation
- Top 5 selling items

## System Structure

```
├── RestaurantGUI.java    # Main window
├── KioskGUI.java         # Customer interface
├── ManagerGUI.java       # Manager interface
├── MenuManager.java      # Menu operations
├── SalesSummary.java     # Sales tracking
├── Order.java            # Order management
├── MenuItem.java         # Base item class
└── Category classes      # SetGepuk, Drinks, AddOn, etc.
```

## Quick Start

1. Compile: `javac *.java`
2. Run: `java RestaurantGUI`

## Technologies
- Java Swing GUI
- Object-Oriented Programming
- ArrayLists for data management

## Sample Menu
- Set Gepuk (RM14.50-20.50)
- Set Bakso (RM11.50-16.50)
- Drinks (RM2.00-4.00)
- Add-Ons (RM1.00-5.00)
