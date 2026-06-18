package entity;

import java.util.*;
import currency.Currency;
import payment.PaymentChannel;

public class Order {

    // Menggunakan java.util.LinkedList sesuai ketentuan tugas
    private LinkedList<OrderItem> items;
    private PaymentChannel selectedPayment;
    private Currency selectedCurrency;

    // Batas maksimal jenis item per kategori (sesuai PDF: 5 jenis per kategori)
    private static final int MAX_BEVERAGE_TYPES = 5;
    private static final int MAX_FOOD_TYPES = 5;

    //Construct
    public Order() {
        this.items = new LinkedList<>();
        this.selectedPayment = null;
        this.selectedCurrency = null;
    }

    //Order
    public void addOrderItem(Menu menu, int quantity) {
        // Cek apakah menu sudah ada dalam pesanan, jika iya update quantity
        for (OrderItem item : items) {
            if (item.getCode().equalsIgnoreCase(menu.getCodeMenu())) {
                item.setQuantity(quantity);
                return;
            }
        }
        items.addLast(new OrderItem(menu, quantity));
    }

    /**
     * Mengecek apakah jenis menu ini sudah mencapai batas maksimum.
     * Batas: 5 jenis makanan berbeda dan 5 jenis minuman berbeda per pesanan.
     */
    public boolean isMaxTypesReached(Menu menu) {
        if (menu instanceof Beverage) {
            return getBeverageTypes() >= MAX_BEVERAGE_TYPES;
        } else if (menu instanceof Food) {
            return getFoodTypes() >= MAX_FOOD_TYPES;
        }
        return false;
    }

    /**
     * Mengecek apakah menu tertentu sudah ada dalam daftar pesanan.
     */
    public boolean isAlreadyOrdered(String menuCode) {
        for (OrderItem item : items) {
            if (item.getCode().equalsIgnoreCase(menuCode)) {
                return true;
            }
        }
        return false;
    }

    public int getBeverageTypes() {
        int count = 0;
        for (OrderItem item : items) {
            if (item.getMenu() instanceof Beverage) count++;
        }
        return count;
    }

    public int getFoodTypes() {
        int count = 0;
        for (OrderItem item : items) {
            if (item.getMenu() instanceof Food) count++;
        }
        return count;
    }

    public LinkedList<OrderItem> getItems() {
        return items;
    }

    public void removeOrderItem(String codeMenu) {
        Iterator<OrderItem> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getCode().equals(codeMenu)) {
                it.remove();
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

    /**
     * Menampilkan tabel pesanan real-time saat pengguna sedang memilih item.
     * Format sesuai PDF: tabel dengan kolom Kode, Nama, Harga, Kuantitas.
     */
    public void displayOrderTable() {
        if (items.isEmpty()) {
            System.out.println("   (Belum ada pesanan)");
            return;
        }

        List<OrderItem> beverages = getBeverageItems();
        List<OrderItem> foods = getFoodItems();

        // Tampilkan makanan dulu (sesuai PDF)
        if (!foods.isEmpty()) {
            System.out.printf("   %-6s %-35s %-12s %-10s%n", "Kode", "Makanan", "Harga", "Kuantitas");
            System.out.println("   " + "-".repeat(67));
            for (OrderItem item : foods) {
                String harga = String.format("Rp%,.0f", item.getPrice());
                System.out.printf("   %-6s %-35s %-12s %-10d%n",
                        item.getCode(), item.getName(), harga, item.getQuantity());
            }
        }

        // Tampilkan minuman
        if (!beverages.isEmpty()) {
            System.out.printf("   %-6s %-35s %-12s %-10s%n", "Kode", "Minuman", "Harga", "Kuantitas");
            System.out.println("   " + "-".repeat(67));
            for (OrderItem item : beverages) {
                String harga = String.format("Rp%,.0f", item.getPrice());
                System.out.printf("   %-6s %-35s %-12s %-10d%n",
                        item.getCode(), item.getName(), harga, item.getQuantity());
            }
        }
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
                System.out.printf("   %d. %s (%s) x%d%n", no, item.getName(), item.getCode(), item.getQuantity());
                System.out.printf("      Harga: Rp %.2f | Pajak: Rp %.2f | Subtotal: Rp %.2f%n",
                        item.getSubtotal(), item.getTaxAmount(), item.getTotal());
                no++;
            }
        }

        List<OrderItem> foods = getFoodItems();
        if (!foods.isEmpty()) {
            System.out.println("\n--- Makanan ---");
            int no = 1;
            for (OrderItem item : foods) {
                System.out.printf("   %d. %s (%s) x%d%n", no, item.getName(), item.getCode(), item.getQuantity());
                System.out.printf("      Harga: Rp %.2f | Pajak: Rp %.2f | Subtotal: Rp %.2f%n",
                        item.getSubtotal(), item.getTaxAmount(), item.getTotal());
                no++;
            }
        }

        System.out.println("\n--------------------------------------------------");
        System.out.printf("TOTAL PESANAN: Rp %.2f%n", getTotalAfterTax());
        System.out.println("==================================================\n");
    }

}
