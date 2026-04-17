package utility;

import java.util.List;

public class PriceCalculator {
    private static final double MIN_DISCOUNT = 0.0;
    private static final double MAX_DISCOUNT = 1.0;

    // Construct
    private PriceCalculator() {
    }

    // Calculate
    public static double calculateSubtotal(List<OrderItem> items) {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public static double calculateTotalTax(List<OrderItem> items) {
        double totalTax = 0;
        for (OrderItem item : items) {
            totalTax += item.getTaxAmount();
        }
        return totalTax;
    }

    public static double applyDiscount(double total, double discountPercent) {
        if (discountPercent < MIN_DISCOUNT || discountPercent > MAX_DISCOUNT) {
            throw new IllegalArgumentException("Diskon harus berada di antara 0.0 dan 1.0");
        }
        return total - (total * discountPercent);
    }

    public static double addAdminFee(double total, double fee) {
        if (fee < 0) {
            throw new IllegalArgumentException("Biaya admin tidak boleh negatif");
        }
        return total + fee;
    }

    public static double convertCurrency(double amountIDR, String currency) {
        if (currency == null) {
            return amountIDR;
        }
        
        switch (currency.toUpperCase()) {
            case "USD": return amountIDR / 17.0;
            case "JPY": return amountIDR / 0.1;
            case "MYR": return amountIDR / 4.0;
            case "EUR": return amountIDR / 20.0;
            default: return amountIDR;
        }
    }

    public static double calculateFinalBill(Order order) {
        if (order.isEmpty()) {
            throw new IllegalStateException("Tidak dapat menghitung tagihan untuk pesanan kosong !");
        }
        if (order.getPaymentChannel() == null) {
            throw new IllegalStateException("Metode pembayaran belum dipilih !");
        }
        if (order.getCurrency() == null) {
            throw new IllegalStateException("Mata uang belum dipilih !");
        }

        double subtotal = order.getTotalBeforeTax();
        double tax = order.getTotalTax();
        double totalWithTax = subtotal + tax;

        double discount = order.getPaymentChannel().getDiscount();
        double totalAfterDiscount = applyDiscount(totalWithTax, discount);

        double adminFee = order.getPaymentChannel().getAdminFee();
        double totalWithFee = addAdminFee(totalAfterDiscount, adminFee);

        return convertCurrency(totalWithFee, order.getCurrency().getCode());
    }
}