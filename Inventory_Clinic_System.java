import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Class to represent a single medicine item
class Medicine {
    private String name;
    private int stockQuantity;
    private LocalDate deliveryDate;
    private LocalDate dueDate; // Added due date for stock

    // Constructor for the Medicine class
    public Medicine(String name, int stockQuantity, LocalDate deliveryDate, LocalDate dueDate) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.deliveryDate = deliveryDate;
        this.dueDate = dueDate;
    }

    // Method to get the name of the medicine
    public String getName() {
        return name;
    }

    // Method to get the stock quantity of the medicine
    public int getStockQuantity() {
        return stockQuantity;
    }

    // Method to get the delivery date of the medicine
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

     // Method to get the due date of the medicine
    public LocalDate getDueDate() {
        return dueDate;
    }

    // Method to reduce stock quantity when medicine is sold
    public void sellMedicine(int quantity) {
        if (quantity <= stockQuantity) {
            this.stockQuantity -= quantity;
            System.out.println(quantity + " units of " + name + " sold.");
        } else {
            System.out.println("Insufficient stock of " + name + ". Only " + stockQuantity + " units available.");
        }
    }

    // Method to add stock when medicine is delivered
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
        System.out.println(quantity + " units of " + name + " added to stock.");
    }

      // Override toString method for easy printing of medicine details
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Medicine: " + name + ", Stock: " + stockQuantity + ", Delivery Date: " + deliveryDate.format(formatter) + ", Due Date: " + dueDate.format(formatter);
    }
}

// Class to manage the inventory of medicines
public class InventoryManagement {
    private static List<Medicine> inventory = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Method to add a new medicine to the inventory
    public static void addMedicine() {
        System.out.println("Enter medicine name:");
        String name = scanner.nextLine();

        int stockQuantity = 0;
        boolean validQuantity = false;
        while (!validQuantity) {
            System.out.println("Enter stock quantity:");
            try {
                stockQuantity = Integer.parseInt(scanner.nextLine());
                if (stockQuantity >= 0) {
                    validQuantity = true;
                } else {
                    System.out.println("Stock quantity must be a non-negative number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for stock quantity.");
            }
        }
        LocalDate deliveryDate = getValidDate("Enter delivery date (yyyy-MM-dd): ");
        LocalDate dueDate = getValidDate("Enter due date (yyyy-MM-dd): ");


        // Check if medicine already exists
        for (Medicine medicine : inventory) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                System.out.println("Medicine already exists.  Use 'Add Stock' option to increase quantity.");
                return; // Exit the addMedicine method
            }
        }

        Medicine newMedicine = new Medicine(name, stockQuantity, deliveryDate, dueDate);
        inventory.add(newMedicine);
        System.out.println(name + " added to inventory.");
    }

    // Method to sell medicine from the inventory
    public static void sellMedicine() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty. No medicines to sell.");
            return;
        }
        System.out.println("Enter medicine name to sell:");
        String name = scanner.nextLine();

        for (Medicine medicine : inventory) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                int quantity = 0;
                boolean validQuantity = false;
                while (!validQuantity) {
                    System.out.println("Enter quantity to sell:");
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity > 0) {
                            validQuantity = true;
                        } else {
                            System.out.println("Quantity must be a positive number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number for quantity.");
                    }
                }
                medicine.sellMedicine(quantity);
                return; // Exit the sellMedicine method after selling
            }
        }
        System.out.println("Medicine " + name + " not found in inventory.");
    }

    // Method to add stock to an existing medicine
    public static void addStock() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty. No medicines to add stock to.");
            return;
        }

        System.out.println("Enter medicine name to add stock:");
        String name = scanner.nextLine();

        for (Medicine medicine : inventory) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                int quantity = 0;
                boolean validQuantity = false;
                while (!validQuantity) {
                    System.out.println("Enter quantity to add:");
                    try {
                        quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity > 0) {
                            validQuantity = true;
                        } else {
                            System.out.println("Quantity must be a positive number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number for quantity.");
                    }
                }
                medicine.addStock(quantity);
                return; // Exit the addStock method
            }
        }
        System.out.println("Medicine " + name + " not found in inventory.");
    }

    // Method to display all medicines in the inventory
    public static void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        System.out.println("Current Inventory:");
        for (Medicine medicine : inventory) {
            System.out.println(medicine); // Uses the toString method of Medicine class
        }
    }

    // Method to display medicines that are due (before a given date)
     public static void displayDueMedicines() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        LocalDate checkDate = getValidDate("Enter the date to check for due medicines (yyyy-MM-dd): ");
        boolean found = false; //flag to check if any medicines are due
        System.out.println("Medicines due before " + checkDate.format(dateFormatter) + ":");
        for (Medicine medicine : inventory) {
            if (medicine.getDueDate().isBefore(checkDate)) {
                System.out.println(medicine);
                found = true;
            }
        }
        if(!found){
            System.out.println("No medicines are due before the specified date.");
        }
    }
    // Helper method to get a valid date from user input
    private static LocalDate getValidDate(String prompt) {
        LocalDate date = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.println(prompt);
            String dateString = scanner.nextLine();
            try {
                date = LocalDate.parse(dateString, dateFormatter);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
        return date;
    }

    // Main method - entry point of the program
    public static void main(String[] args) {
        String choice;
        do {
            System.out.println("\nClinic Inventory Management System");
            System.out.println("1. Add Medicine");
            System.out.println("2. Sell Medicine");
            System.out.println("3. Add Stock to Medicine");
            System.out.println("4. Display Inventory");
            System.out.println("5. Display Due Medicines"); //added option 5
            System.out.println("0. Exit");
            System.out.println("Enter your choice:");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addMedicine();
                    break;
                case "2":
                    sellMedicine();
                    break;
                case "3":
                    addStock();
                    break;
                case "4":
                    displayInventory();
                    break;
                case "5":
                    displayDueMedicines();
                    break;
                case "0":
                    System.out.println("Exiting System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equals("0"));
        scanner.close(); // Close the scanner to prevent resource leaks
    }
}

