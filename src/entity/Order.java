package entity;
import java.util.*;

import payment.PaymentChannel;

public class Order {
    
    private List<OrderItem> items;
    private PaymentChannel selectedPayment;
    private Currency selectedCurrency;

    //Construct
    public Order() {
        this.items = new ArrayList<>();
        this.selectedPayment = null;
        this.selectedCurrency = null;
    }

    //Order
    public void addOrderItem(Menu menu, int quantity) {
        items.add(new OrderItem(menu, quantity));
    }

    public List<OrderItem> getItems() {
        return items;

    }

    public void removeOrderItem(String codeMenu) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCode().equals(codeMenu)) {
                items.remove(i);
                break;
            }
        }
    }

    public List<OrderItem> getBeverageItems() {
        List<OrderItem> beverages = new ArrayList<>();
        for (OrderItem item : items) {
            if (item.getMenu() instanceof Beverage) {
                beverages.add(item);
            }
        }
        return beverages;
    }

    public List<OrderItem> getFoodItems() {
        List<OrderItem> food = new ArrayList<>();
        for (OrderItem item : items) {
            if (item.getMenu() instanceof Food) {
                food.add(item);
            }
        }
        return food;
    }

    //Get
    public int getItemCount() {
        return items.size();
    }
    
    public PaymentChannel getPaymentChannel() {
        return selectedPayment;
    }

    public Currency getCurrency() {
        return selectedCurrency;
    }

    //Set
    public void setPaymentChannel(PaymentChannel payment) {
        this.selectedPayment = payment;
    }

    public void selectedCurrency(Currency currency) {
        this.selectedCurrency = currency;
    }

    //Calculate
    public double getTotalBeverageBeforeTax() {
        double total = 0;
        for (OrderItem item : getBeverageItems()) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double getTotalFoodBeforeTax() {
        double total = 0;
        for (OrderItem item : getFoodItems()) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double getTotalBeforeTax() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public double getTotalBeverageTax() {
        double totalTax = 0;
        for (OrderItem item : getBeverageItems()) {
            totalTax += item.getTaxAmount();
        }
        return totalTax;
    }

    public double getTotalFoodTax() {
        double totalTax = 0;
        for (OrderItem item : getFoodItems()) {
            totalTax += item.getTaxAmount();
        }
        return totalTax;
    }

    public double getTotalTax() {
        double totalTax = 0;
        for (OrderItem item : items) {
            totalTax += item.getTaxAmount();
        }
        return totalTax;
    }

    public double getTotalAfterTax() {
        return getTotalBeforeTax() + getTotalTax();
    }

    //Validation
    private void validatePaymentAndCurrency() {
        if (selectedPayment == null) {
            throw new IllegalStateException("Metode Pembayaran Belum Dipilih !");
        }
        if (selectedCurrency == null) {
            throw new IllegalStateException("Currency Belum Dipilih!");
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void displayOrderSummary() {
        System.out.println("\n========== RINGKASAN ORDER ==========");
        
        List<OrderItem> beverages = getBeverageItems();
        if (!beverages.isEmpty()) {
            System.out.println("\n--- Minuman ---");
            for (OrderItem item : beverages) {
                System.out.println(item);
            }
        }
        
        List<OrderItem> foods = getFoodItems();
        if (!foods.isEmpty()) {
            System.out.println("\n--- Makanan ---");
            for (OrderItem item : foods) {
                System.out.println(item);
            }
        }
        
        System.out.println("\n--- TOTAL ---");
        System.out.printf("Total Minuman : Rp %.2f\n", getTotalBeverageBeforeTax());
        System.out.printf("Total Makanan : Rp %.2f\n", getTotalFoodBeforeTax());
        System.out.printf("Pajak Minuman : Rp %.2f\n", getTotalBeverageTax());
        System.out.printf("Pajak Makanan : Rp %.2f\n", getTotalFoodTax());
        System.out.printf("TOTAL : Rp %.2f\n", getTotalAfterTax());
        System.out.println("===============================================\n");
    }

}
