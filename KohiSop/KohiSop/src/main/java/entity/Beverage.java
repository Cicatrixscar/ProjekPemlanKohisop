package entity;

public class Beverage extends Menu {

    private boolean taxFree = false;

    public Beverage(String codeMenu, String name, double price, String imagePath) {
        super(codeMenu, name, price, imagePath);
    }

    public void setTaxFree(boolean taxFree) { this.taxFree = taxFree; }
    public boolean isTaxFree() { return taxFree; }

    @Override
    public double calculateTax(int quantity) {
        if (taxFree) return 0.0;

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