//2252709 Xuanhe Yang
import java.util.*;

public abstract class FoodItem {
    protected String name;
    protected double weight;
    protected double price;
    protected String features;
    protected int calories;
    protected String imageUrl;

    public FoodItem(String name, double weight, double price, String features, int calories, String imageUrl) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.features = features;
        this.calories = calories;
        this.imageUrl = imageUrl;
    }

    public abstract String getType();

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    public String getFeatures() {
        return features;
    }

    public int getCalories() {
        return calories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f g) - $%.2f | %d cal | %s", name, weight, price, calories, features);
    }
}

class Pizza extends FoodItem {
    private double radius;

    public Pizza(String name, double weight, double price, String features, int calories, String imageUrl, double radius) {
        super(name, weight, price, features, calories, imageUrl);
        this.radius = radius;
    }

    @Override
    public String getType() {
        return "Pizza";
    }

    public double getRadius() {
        return radius;
    }
}

class FriedChicken extends FoodItem {
    private String spiciness;

    public FriedChicken(String name, double weight, double price, String features, int calories, String imageUrl, String spiciness) {
        super(name, weight, price, features, calories, imageUrl);
        this.spiciness = spiciness;
    }

    @Override
    public String getType() {
        return "FriedChicken";
    }

    public String getSpiciness() {
        return spiciness;
    }
}

class FrenchFries extends FoodItem {
    private String thickness; // Changed to String to accommodate values like "Regular", "Thick", etc.

    public FrenchFries(String name, double weight, double price, String features, int calories, String imageUrl, String thickness) {
        super(name, weight, price, features, calories, imageUrl);
        this.thickness = thickness;
    }

    @Override
    public String getType() {
        return "FrenchFries";
    }

    public String getThickness() {
        return thickness;
    }
}
