//2252709 Xuanhe Yang
import java.util.*;

public class RestaurantCLI {
    private List<FoodItem> foodItems;

    public RestaurantCLI(List<FoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public void displayFoodItems(String type, String sortBy) {
        List<FoodItem> filteredItems = new ArrayList<>();
        for (FoodItem item : foodItems) {
            if (item.getType().equalsIgnoreCase(type)) {
                filteredItems.add(item);
            }
        }

        if ("calories".equalsIgnoreCase(sortBy)) {
            filteredItems.sort(Comparator.comparingInt(FoodItem::getCalories));
        } else if ("price".equalsIgnoreCase(sortBy)) {
            filteredItems.sort(Comparator.comparingDouble(FoodItem::getPrice));
        }

        filteredItems.forEach(System.out::println);
    }
}