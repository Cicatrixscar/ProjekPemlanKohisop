package repository;

import entity.Beverage;
import entity.Food;
import entity.Menu;
import java.util.ArrayList;
import java.util.List;

public class MenuRepository {
    
    private List<Menu> menus;

    public MenuRepository() {
        this.menus = new ArrayList<>();
        initializeMenus();
    }

    public void initializeMenus() {
        // Makanan
        menus.add(new Food("F1", "Croissant", 15000));
        menus.add(new Food("F2", "Sandwich", 30000));
        menus.add(new Food("F3", "Cake", 28000));
        
        // Minuman
        menus.add(new Beverage("B1", "Americano", 20000));
        menus.add(new Beverage("B2", "Latte", 25000));
        menus.add(new Beverage("B3", "Cappuccino", 25000));
    }

    public Menu getMenuByCode(String code) {
        if (code == null) return null;
        
        for (Menu menu : menus) {
            if (menu.getCodeMenu().equalsIgnoreCase(code)) {
                return menu;
            }
        }
        return null;
    }

    public List<Menu> getAllMenus() {
        return menus;
    }

    public boolean isValidMenuCode(String code) {
        return getMenuByCode(code) != null;
    }

    public void displayAllMenu() {
        System.out.println("\n[ DAFTAR MENU KOHISOP ]");
        
        System.out.println("--- Minuman ---");
        for (Menu menu : menus) {
            if (menu instanceof Beverage) {
                System.out.printf("%s - %s : Rp %.2f\n", menu.getCodeMenu(), menu.getName(), menu.getPrice());
            }
        }
        
        System.out.println("\n--- Makanan ---");
        for (Menu menu : menus) {
            if (menu instanceof Food) {
                System.out.printf("%s - %s : Rp %.2f\n", menu.getCodeMenu(), menu.getName(), menu.getPrice());
            }
        }
    }
}