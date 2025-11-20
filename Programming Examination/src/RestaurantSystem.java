//2252709 Xuanhe Yang
import java.util.List;
import java.util.Scanner;

public class RestaurantSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FoodDataLoader loader = new FoodDataLoader();
        List<FoodItem> foodItems;

        try {
            // 1. Load food data from the food.html file
            foodItems = loader.loadFoodItems("C:\\Users\\33426\\Desktop\\Programming Examination\\src\\food.html");
        } catch (Exception e) {
            System.out.println("Failed to load food data: " + e.getMessage());
            return;
        }

        // Initialize the CLI menu for displaying, sorting, and managing orders
        RestaurantCLI cli = new RestaurantCLI(foodItems);
        Order currentOrder = new Order();
        boolean running = true;

        while (running) {
            System.out.println("\n=== Welcome to Restaurant System ===");
            System.out.println("1. Display Food List");
            System.out.println("2. Add Food to Order");
            System.out.println("3. Apply Discount");
            System.out.println("4. Show Order Summary");
            System.out.println("5. Generate Order PDF Report");
            System.out.println("6. Exit System");
            System.out.print("Please enter an option (1-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear newline

            switch (choice) {
                case 1:
                    // Display food list
                    System.out.print("Enter food type (Pizza, FriedChicken, FrenchFries): ");
                    String type = scanner.nextLine();
                    System.out.print("Choose sorting method (calories or price): ");
                    String sortBy = scanner.nextLine();
                    cli.displayFoodItems(type, sortBy);
                    break;

                case 2:
                    // Add food to the order
                    System.out.print("Enter the name of the food to order: ");
                    String foodName = scanner.nextLine();
                    FoodItem itemToAdd = findFoodByName(foodItems, foodName);
                    if (itemToAdd != null) {
                        currentOrder.addItem(itemToAdd);
                        System.out.println(foodName + " has been added to the order.");
                    } else {
                        System.out.println("Food item not found, please try again.");
                    }
                    break;

                case 3:
                    // Apply discount to the order
                    System.out.println("Select discount plan:");
                    System.out.println("1. $50 off for orders over $100");
                    System.out.println("2. 50% off Margherita Pizza");
                    System.out.print("Enter discount option (1 or 2): ");
                    int discountChoice = scanner.nextInt();
                    String discountType = (discountChoice == 1) ? "50_OFF_100" : "50_PERCENT_MARGHERITA";
                    currentOrder.applyDiscount(discountType);
                    System.out.println("Discount applied.");
                    break;

                case 4:
                    // Show order summary
                    currentOrder.displayOrderSummary();
                    break;

                case 5:
                    // Generate order PDF report with a specified PDF library
                    System.out.print("Choose PDF library (pdfbox/itext): ");
                    String libraryChoice = scanner.nextLine();
                    PDFGenerator pdfGenerator;
                    try {
                        pdfGenerator = PDFGeneratorFactory.getPDFGenerator(libraryChoice);
                        pdfGenerator.generatePDF(currentOrder, "C:\\Users\\33426\\Desktop\\OrderSummary.pdf");
                        System.out.println("Order PDF report has been generated using " + libraryChoice + "!");
                    } catch (Exception e) {
                        System.out.println("Failed to generate PDF report: " + e.getMessage());
                    }
                    break;

                case 6:
                    // Exit the system
                    running = false;
                    System.out.println("Thank you for using the Restaurant System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }

        scanner.close();
    }

    // Helper method to find a food item by name
    private static FoodItem findFoodByName(List<FoodItem> foodItems, String name) {
        for (FoodItem item : foodItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}
