package entity;

import java.util.*;
import currency.Currency;
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
        List<OrderItem> foods = new ArrayList<>();
        for (OrderItem item : items) {
            if (item.getMenu() instanceof Food) {
                foods.add(item);
            }
        }
        return foods;
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

    public void setCurrency(Currency currency) {
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
            throw new IllegalStateException("Mata Uang Belum Dipilih!");
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void displayOrderSummary() {
        if (isEmpty()) {
            System.out.println("\n Pesanan Anda Masih Kosong !");
            return;
        }

        System.out.println("\n==================================================");
        System.out.println("============= Ringkasan Pesanan Anda =============");
        System.out.println("==================================================");
        
        List<OrderItem> beverages = getBeverageItems();
        if (!beverages.isEmpty()) {
            System.out.println("\n--- Minuman ---");
            int no = 1;
            for (OrderItem item : beverages) {
                System.out.printf("   %d. %s (%s) x%d\n", no, item.getName(), item.getCode(), item.getQuantity());
                System.out.printf("      Harga: Rp %.2f | Pajak: Rp %.2f | Subtotal: Rp %.2f\n", item.getSubtotal(),
                        item.getTaxAmount(), item.getTotal());
                no++;
            }
        }
        
        List<OrderItem> foods = getFoodItems();
        if (!foods.isEmpty()) {
            System.out.println("\n--- Makanan ---");
            int no = 1;
            for (OrderItem item : foods) {
                System.out.printf("   %d. %s (%s) x%d\n", no, item.getName(), item.getCode(), item.getQuantity());
                System.out.printf("      Harga: Rp %.2f | Pajak: Rp %.2f | Subtotal: Rp %.2f\n", item.getSubtotal(),
                        item.getTaxAmount(), item.getTotal());
                no++;
            }
        }
        
        System.out.println("\n--------------------------------------------------");
        System.out.printf("TOTAL PESANAN: Rp %.2f\n", getTotalAfterTax());
        System.out.println("==================================================\n");
    }

}
