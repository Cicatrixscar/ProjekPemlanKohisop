import java.util.*;

public class MenuRepository {
    private Map<String, Beverage> beverages;
    private Map<String, Food> foods;

    public MenuRepository() {
        beverages = new HashMap<>();
        foods = new HashMap<>();
        initializeBeverages();
        initializeFoods();
    }

    private void initializeBeverages() {
        beverages.put("B1", new Beverage("B1", "Americano", 20000));
        beverages.put("B2", new Beverage("B2", "Latte", 25000));
        beverages.put("B3", new Beverage("B3", "Cappuccino", 25000));
    }

    private void initializeFoods() {
        foods.put("F1", new Food("F1", "Croissant", 15000));
        foods.put("F2", new Food("F2", "Sandwich", 30000));
        foods.put("F3", new Food("F3", "Cake", 28000));
    }

    public Beverage getBeverageByCode(String code) {
        return beverages.get(code.toUpperCase());
    }

    public Food getFoodByCode(String code) {
        return foods.get(code.toUpperCase());
    }

    public Menu getMenuByCode(String code) {
        code = code.toUpperCase();
        if (beverages.containsKey(code)) {
            return beverages.get(code);
        } else if (foods.containsKey(code)) {
            return foods.get(code);
        }
        return null;
    }

    public List<Beverage> getAllBeverages() {
        return new ArrayList<>(beverages.values());
    }

    public List<Food> getAllFoods() {
        return new ArrayList<>(foods.values());
    }

    public boolean isValidBeverageCode(String code) {
        return beverages.containsKey(code.toUpperCase());
    }

    public boolean isValidFoodCode(String code) {
        return foods.containsKey(code.toUpperCase());
    }

    public boolean isValidMenuCode(String code) {
        return isValidBeverageCode(code) || isValidFoodCode(code);
    }

    public void displayBeverageMenu() {
        System.out.println("\n=== BEVERAGES ===");
        for (Beverage b : beverages.values()) {
            System.out.println(b.getCode() + " - " + b.getName() + " : " + b.getPrice());
        }
    }

    public void displayFoodMenu() {
        System.out.println("\n=== FOODS ===");
        for (Food f : foods.values()) {
            System.out.println(f.getCode() + " - " + f.getName() + " : " + f.getPrice());
        }
    }

    public void displayAllMenu() {
        displayBeverageMenu();
        displayFoodMenu();
    }
}