import java.util.Scanner;

public interface MenuOperations {
    void displayMenu();
    void addMenuItem(Scanner input);
    void deleteMenuItem(Scanner input);
    void updateMenuItem(Scanner input);
    void updateAvailability(Scanner input);
    void updateDiscount(Scanner input);
}