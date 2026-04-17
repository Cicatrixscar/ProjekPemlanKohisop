package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Receipt {
    private List<OrderItem> items;
    private LocalDateTime transactionTime;

    //Construct
    public Receipt() {
        this.items = new ArrayList<>();
        this.transactionTime = LocalDateTime.now();
    }
    
    //Get
    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    //Add
    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void addItems(List<OrderItem> newItems) {
        items.addAll(newItems);
    }

    //Calculate
    public double getTotalWithTax() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getTotal();
        }
        return total;
    }

    //Print
    public void printReceiptDetail() {
        System.out.println("\n==============================================");
        System.out.println("============ Struk Pembelian Anda ============");
        System.out.println("==============================================");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Waktu Transaksi : " + transactionTime.format(formatter));
        System.out.println("----------------------------------------------");

        int itemNumber = 1;

        boolean hasBeverage = false;
        for (OrderItem item : items) {
            if (item.getMenu() instanceof Beverage) {
                if (!hasBeverage) {
                    System.out.println(("\n [ Minuman ]"));
                    hasBeverage = true;
                }
                System.out.printf("   %d. %-25s x%d\n", itemNumber, item.getName(), item.getQuantity());
                System.out.printf("      Harga @Rp %.2f x %d = Rp %.2f\n", item.getPrice(), item.getQuantity(),
                        item.getSubtotal());
                System.out.printf("      Pajak = Rp %.2f\n", item.getTaxAmount());
                System.out.printf("      Subtotal: Rp %.2f\n", item.getTotal());
                itemNumber++;
            }
        }
        
        boolean hasFood = false;
        for (OrderItem item : items) {
            if (item.getMenu() instanceof Food) {
                if (!hasFood) {
                    System.out.println(("\n [ Makanan ]"));
                    hasFood = true;
                }
                System.out.printf("   %d. %-25s x%d\n", itemNumber, item.getName(), item.getQuantity());
                System.out.printf("      Harga @Rp %.2f x %d = Rp %.2f\n", item.getPrice(), item.getQuantity(),
                        item.getSubtotal());
                System.out.printf("      Pajak = Rp %.2f\n", item.getTaxAmount());
                System.out.printf("      Subtotal: Rp %.2f\n", item.getTotal());
                itemNumber++;
            }
        }

        System.out.println("\n==============================================");
        System.out.printf("Total Harga (Termasuk Pajak): Rp %.2f\n", getTotalWithTax());
        System.out.println("==============================================\n");
    }
}
