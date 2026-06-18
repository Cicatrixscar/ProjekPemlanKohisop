package repository;

import entity.Beverage;
import entity.Food;
import entity.Menu;
import java.util.LinkedList;

public class MenuRepository {

    private LinkedList<Menu> menus;

    public MenuRepository() {
        this.menus = new LinkedList<>();
        initializeMenus();
    }

    public void initializeMenus() {
        // Minuman
        addMenuSorted(new Beverage("A1", "Caffe Latte", 46000, "/image/Beverage/Cafe_Latte.jpg"));
        addMenuSorted(new Beverage("A2", "Cappuccino", 46000, "/image/Beverage/Cappucino.jpg"));
        addMenuSorted(new Beverage("E1", "Caffe Americano", 37000, "/image/Beverage/Caffe_Americano.jpg"));
        addMenuSorted(new Beverage("E2", "Caffe Mocha", 55000, "/image/Beverage/Caffe_Mocha.jpg"));
        addMenuSorted(new Beverage("E3", "Caramel Macchiato", 59000, "/image/Beverage/Caramel_Macchiato.jpg"));
        addMenuSorted(new Beverage("E4", "Asian Dolce Latte", 55000, "/image/Beverage/Asian_Dolce_Latte.jpg"));
        addMenuSorted(new Beverage("E5", "Double Shots Iced Shaken Espresso", 50000, "/image/Beverage/Double_Shot_Iced_Shaken_Espresso.jpg"));
        addMenuSorted(new Beverage("B1", "Freshly Brewed Coffee", 23000, "/image/Beverage/Freshly_Brewed_Coffee.jpg"));
        addMenuSorted(new Beverage("B2", "Vanilla Sweet Cream Cold Brew", 50000, "/image/Beverage/Vanilla_Sweet_Cream_Cold_Brew.jpg"));
        addMenuSorted(new Beverage("B3", "Cold Brew", 44000, "/image/Beverage/Cold_Brew.jpg"));

        // Makanan
        addMenuSorted(new Food("M1", "Petemania Pizza", 112000, "/image/Food/Petemania_Pizza.jpg"));
        addMenuSorted(new Food("M2", "Mie Rebus Super Mario", 35000, "/image/Food/Mie_Rebus_Medan.jpg"));
        addMenuSorted(new Food("M3", "Ayam Bakar Goreng Rebus Spesial", 72000, "/image/Food/Ayam_bakar_goreng_rebus_spesial.jpg"));
        addMenuSorted(new Food("M4", "Soto Kambing Iga Guling", 124000, "/image/Food/Soto_kambing_iga_guling.jpg"));
        addMenuSorted(new Food("S1", "Singkong Bakar A La Carte", 37000, "/image/Food/Singkong_bakar.jpg"));
        addMenuSorted(new Food("S2", "Ubi Cilembu Bakar Arang", 58000, "/image/Food/Ubi_lembu_bakar.jpg"));
        addMenuSorted(new Food("S3", "Tempe Mendoan", 18000, "/image/Food/Tempe_Mendoan.jpg"));
        addMenuSorted(new Food("S4", "Tahu Bakso Extra Telur", 28000, "/image/Food/Tahu_bakso_isi_telur.jpg"));
    }

    // Insertion sort by harga; Food selalu sebelum Beverage
    private void addMenuSorted(Menu newMenu) {
        if (menus.isEmpty()) {
            menus.add(newMenu);
            return;
        }

        int insertIndex = 0;
        for (int i = 0; i < menus.size(); i++) {
            Menu existing = menus.get(i);
            boolean existingIsFood = (existing instanceof Food);
            boolean newIsFood = (newMenu instanceof Food);

            if (existingIsFood && !newIsFood) {
                insertIndex = i + 1;
                continue;
            }
            if (!existingIsFood && newIsFood) {
                break;
            }
            if (existing.getPrice() <= newMenu.getPrice()) {
                insertIndex = i + 1;
            } else {
                break;
            }
        }
        menus.add(insertIndex, newMenu);
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

    public LinkedList<Menu> getAllMenus() {
        return menus;
    }

    public boolean isValidMenuCode(String code) {
        return getMenuByCode(code) != null;
    }

    // Tampilkan menu: Makanan dulu (sorted by harga), lalu Minuman (sorted by harga)
    public void displayAllMenu() {
        System.out.println("\n[ DAFTAR MENU KOHISOP ]");
        System.out.printf("%-6s %-38s %s%n", "Kode", "Menu Makanan", "Harga (Rp)");
        System.out.println("-".repeat(58));
        for (Menu menu : menus) {
            if (menu instanceof Food) {
                System.out.printf("%-6s %-38s %,.0f%n",
                        menu.getCodeMenu(), menu.getName(), menu.getPrice());
            }
        }

        System.out.println();
        System.out.printf("%-6s %-38s %s%n", "Kode", "Menu Minuman", "Harga (Rp)");
        System.out.println("-".repeat(58));
        for (Menu menu : menus) {
            if (menu instanceof Beverage) {
                System.out.printf("%-6s %-38s %,.0f%n",
                        menu.getCodeMenu(), menu.getName(), menu.getPrice());
            }
        }
    }
}