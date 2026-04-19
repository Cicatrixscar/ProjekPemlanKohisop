package entity;

public class Beverage extends Menu {
    
    public Beverage(String codeMenu, String name, double price) {
        super(codeMenu, name, price);
    }

    @Override
    public double calculateTax(int quantity) {
        double subtotal = getSubtotal(quantity);
        double taxRate;

        if (price < 50000) {
            taxRate = 0.0;
        } else if (price >= 50000 && price <= 55000) {
            taxRate = 0.08;
        } else {
            taxRate = 0.11;
        }
        
        return subtotal * taxRate;
    }

    @Override
    public String getCategory() {
        return "Beverage";
    }
}