//2252709 Xuanhe Yang
import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<FoodItem> orderedItems = new ArrayList<>();
    private double totalPrice;
    private int totalCalories;

    public void addItem(FoodItem item) {
        orderedItems.add(item);
        totalPrice += item.getPrice();
        totalCalories += item.getCalories();
    }

    public List<FoodItem> getOrderedItems() {
        return orderedItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void applyDiscount(String discountType) {
        if (discountType.equals("50_OFF_100") && totalPrice > 100) {
            totalPrice -= 50;
        } else if (discountType.equals("50_PERCENT_MARGHERITA")) {
            for (FoodItem item : orderedItems) {
                if (item instanceof Pizza && item.getName().equalsIgnoreCase("Margherita")) {
                    totalPrice -= item.getPrice() * 0.5;
                }
            }
        }
    }

    public void displayOrderSummary() {
        System.out.println("Order Summary:");
        for (FoodItem item : orderedItems) {
            System.out.println(item);
        }
        System.out.println("Total Calories: " + totalCalories);
        System.out.println("Total Price: $" + totalPrice);
    }
}