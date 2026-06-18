package entity;

public abstract class Menu {

    protected String codeMenu;
    protected String name;
    protected double price;
    protected String imagePath;

    public Menu(String codeMenu, String name, double price, String imagePath) {
        this.codeMenu = codeMenu;
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getCodeMenu() {
        return codeMenu;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setCodeMenu(String codeMenu) {
        this.codeMenu = codeMenu;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract double calculateTax(int quantity);

    public abstract String getCategory();

    public abstract void setTaxFree(boolean taxFree);

    public abstract boolean isTaxFree();

    public double getSubtotal(int quantity) {
        return price * quantity;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Rp %.2f)", codeMenu, name, price);
    }
}