//2252709 Xuanhe Yang
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FoodDataLoader {
    public List<FoodItem> loadFoodItems(String htmlFilePath) throws Exception {
        List<FoodItem> foodItems = new ArrayList<>();

        // Load the HTML file
        Document doc = Jsoup.parse(new File(htmlFilePath), "UTF-8");

        // Select elements based on the item class names
        Elements items = doc.select(".pizza, .french-fries, .fried-chicken");

        for (Element item : items) {
            try {
                String type = item.className();
                String name = item.select("h2").text();
                String weightText = item.select("p:contains(Weight)").text().replaceAll("[^\\d.]", "");
                String priceText = item.select(".price").text().replace("$", "");
                String features = item.select(".features").text();
                String caloriesText = item.select("p:contains(Calories)").text().replaceAll("[^\\d]", "");
                String imageUrl = item.select("img").attr("src");

                // Ensure essential fields are not empty before parsing
                if (name.isEmpty() || weightText.isEmpty() || priceText.isEmpty() || caloriesText.isEmpty()) {
                    System.out.println("Skipping item due to missing required fields.");
                    continue; // Skip this item if any required field is empty
                }

                double weight = Double.parseDouble(weightText);
                double price = Double.parseDouble(priceText);
                int calories = Integer.parseInt(caloriesText);

                // Handle each type by creating corresponding FoodItem subclasses
                switch (type) {
                    case "pizza":
                        String radiusText = item.select("p:contains(Radius)").text().replaceAll("[^\\d.]", "");
                        if (radiusText.isEmpty()) {
                            System.out.println("Skipping pizza item due to missing radius.");
                            continue;
                        }
                        double radius = Double.parseDouble(radiusText);
                        foodItems.add(new Pizza(name, weight, price, features, calories, imageUrl, radius));
                        break;
                    case "fried-chicken":
                        String spiciness = item.select("p:contains(Spiciness)").text();
                        if (!spiciness.contains(": ")) {
                            System.out.println("Skipping fried chicken item due to missing spiciness level.");
                            continue;
                        }
                        spiciness = spiciness.split(": ")[1]; // Get spiciness after ": "
                        foodItems.add(new FriedChicken(name, weight, price, features, calories, imageUrl, spiciness));
                        break;
                    // For French Fries - thickness is now a string
                    case "french-fries":
                        String thickness = item.select("p:contains(Thickness)").text().split(": ")[1];
                        foodItems.add(new FrenchFries(name, weight, price, features, calories, imageUrl, thickness));
                        break;

                    default:
                        System.out.println("Unknown food type: " + type);
                }
            } catch (Exception e) {
                System.out.println("Failed to parse food item: " + e.getMessage());
            }
        }

        return foodItems;
    }
}
