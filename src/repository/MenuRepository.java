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
        // Minuman
        menus.add(new Beverage("A1", "Caffe Latte", 46));
        menus.add(new Beverage("A2", "Cappuccino", 46));
        menus.add(new Beverage("E1", "Caffe Americano", 37));
        menus.add(new Beverage("E2", "Caffe Mocha", 55));
        menus.add(new Beverage("E3", "Caramel Macchiato", 59));
        menus.add(new Beverage("E4", "Asian Dolce Latte", 55));
        menus.add(new Beverage("E5", "Double Shots Iced Shaken Espresso", 50));
        menus.add(new Beverage("B1", "Freshly Brewed Coffee", 23));
        menus.add(new Beverage("B2", "Vanilla Sweet Cream Cold Brew", 50));
        menus.add(new Beverage("B3", "Cold Brew", 44));

        // Makanan
        menus.add(new Food("M1", "Petemania Pizza", 112));
        menus.add(new Food("M2", "Mie Rebus Super Mario", 35));
        menus.add(new Food("M3", "Ayam Bakar Goreng Rebus Spesial", 72));
        menus.add(new Food("M4", "Soto Kambing Iga Guling", 124));
        menus.add(new Food("S1", "Singkong Bakar A La Carte", 37));
        menus.add(new Food("S2", "Ubi Cilembu Bakar Arang", 58));
        menus.add(new Food("S3", "Tempe Mendoan", 18));
        menus.add(new Food("S4", "Tahu Bakso Extra Telur", 28));
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

    public List<Menu> getAllMenus() { return menus; }

    public boolean isValidMenuCode(String code) {
        return getMenuByCode(code) != null;
    }

    public void displayAllMenu() {
        System.out.println("\n[ DAFTAR MENU KOHISOP ]");
        System.out.println("--- Minuman ---");
        for (Menu menu : menus) {
            if (menu instanceof Beverage) {
                System.out.printf("%s - %-35s : Rp %.0f\n", menu.getCodeMenu(), menu.getName(), menu.getPrice());
            }
        }
        System.out.println("\n--- Makanan ---");
        for (Menu menu : menus) {
            if (menu instanceof Food) {
                System.out.printf("%s - %-35s : Rp %.0f\n", menu.getCodeMenu(), menu.getName(), menu.getPrice());
            }
        }
    }
}