package entity;

public class OrderItem {
    private Menu menu;
    private int quantity;

    //Construct
    public OrderItem(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    //Get
    public Menu getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCode() {
        return menu.getCodeMenu();
    }

    public String getName() {
        return menu.getName();
    }

    public double getPrice() {
        return menu.getPrice();
    }

    public String getCategory() {
        return menu.getCategory();
    }

    //Set
    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }
    }

    //Calculate
    public double getSubtotal() {
        return menu.getSubtotal(quantity);
    }

    public double getTaxAmount() {
        return menu.calculateTax(quantity);
    }

    public double getTotal() {
        return getSubtotal() + getTaxAmount();
    }

    @Override
    public String toString() {
        return String.format("%s x%d - Rp %.2f (Tax : Rp %.2f) = Rp %.2f", getCode(), quantity, getSubtotal(), getTaxAmount(), getTotal());
    }
}
