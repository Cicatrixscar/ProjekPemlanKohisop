package entity;

import currency.Currency;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import payment.PaymentChannel;

public class Invoice {
    private Receipt receipt;
    private PaymentChannel paymentMethod;
    private Currency currency;
    private double discountAmount;
    private double adminFeeAmount;
    private double totalBeforeDiscount;
    private double totalAfterDiscount;
    private double finalTotalInIDR;
    private double finalTotalInCurrency;
    private LocalDateTime transactionTime;
    private int installmentTerm;
    private double installmentAmountPerMonth;

    // Construct
    public Invoice(Receipt receipt, PaymentChannel paymentMethod, Currency currency) {
        this.receipt = receipt;
        this.paymentMethod = paymentMethod;
        this.currency = currency;
        this.transactionTime = LocalDateTime.now();
        this.installmentTerm = 0;
        this.installmentAmountPerMonth = 0.0;
        calculateTotals();
    }

    // Calculate
    private void calculateTotals() {
        totalBeforeDiscount = receipt.getTotalWithTax();
        discountAmount = totalBeforeDiscount * paymentMethod.getDiscount();
        totalAfterDiscount = totalBeforeDiscount - discountAmount;
        adminFeeAmount = paymentMethod.getAdminFee();
        finalTotalInIDR = totalAfterDiscount + adminFeeAmount;
        finalTotalInCurrency = currency.convertFromIDR(finalTotalInIDR);
    }

    // Get
    public Receipt getReceipt() { return receipt; }
    public PaymentChannel getPaymentMethod() { return paymentMethod; }
    public Currency getCurrency() { return currency; }
    public String getPaymentMethodName() { return paymentMethod.getPaymentName(); }
    public String getCurrencyCode() { return currency.getCode(); }
    public double getSubtotalBeforeDiscount() { return totalBeforeDiscount; }
    public double getDiscountAmount() { return discountAmount; }
    public double getDiscountPercent() { return paymentMethod.getDiscount() * 100; }
    public double getAdminFeeAmount() { return adminFeeAmount; }
    public double getTotalAfterDiscount() { return totalAfterDiscount; }
    public double getFinalTotalInIDR() { return finalTotalInIDR; }
    public double getFinalTotalInCurrency() { return finalTotalInCurrency; }
    public LocalDateTime getTransactionTime() { return transactionTime; }
    public int getInstallmentTerm() { return installmentTerm; }
    public double getInstallmentAmountPerMonth() { return installmentAmountPerMonth; }

    // Set
    public void setInstallmentTerm(int term) {
        if (term > 0) {
            this.installmentTerm = term;
            this.installmentAmountPerMonth = finalTotalInIDR / term;
        }
    }

    public void setInstallmentAmountPerMonth(double amount) {
        this.installmentAmountPerMonth = amount;
    }

    // Print
    public void printInvoice() {
        System.out.println("\n========================================================");
        System.out.println("                      Invoice Anda                      ");
        System.out.println("                        KOHISOP                        ");
        System.out.println("========================================================");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("\nWaktu Transaksi: " + transactionTime.format(formatter));
        System.out.println("\n" + "-".repeat(56));
        System.out.println("DETAIL PESANAN :");
        System.out.println("-".repeat(56));
        
        int itemNumber = 1;

        boolean headerBeveragePrinted = false;
        for (OrderItem item : receipt.getItems()) {
            if (item.getMenu() instanceof Beverage) {
                if (!headerBeveragePrinted) {
                    System.out.println("\n[ Minuman ]");
                    headerBeveragePrinted = true;
                }
                System.out.printf("   %d. %-30s x%d\n", itemNumber, item.getName(), item.getQuantity());
                System.out.printf("      - Harga: Rp %.2f\n", item.getSubtotal());
                System.out.printf("      - Pajak: Rp %.2f\n", item.getTaxAmount());
                itemNumber++;
            }
        }
        
        boolean headerFoodPrinted = false;
        for (OrderItem item : receipt.getItems()) {
            if (item.getMenu() instanceof Food) {
                if (!headerFoodPrinted) {
                    System.out.println("\n[ Makanan ]");
                    headerFoodPrinted = true;
                }
                System.out.printf("   %d. %-30s x%d\n", itemNumber, item.getName(), item.getQuantity());
                System.out.printf("      - Harga: Rp %.2f\n", item.getSubtotal());
                System.out.printf("      - Pajak: Rp %.2f\n", item.getTaxAmount());
                itemNumber++;
            }
        }
        
        System.out.println("\n" + "-".repeat(56));
        System.out.println("RINGKASAN PEMBAYARAN :");
        System.out.println("-".repeat(56));
        System.out.printf("Subtotal (dengan pajak):   Rp %,.2f\n", totalBeforeDiscount);
        
        if (discountAmount > 0) {
            System.out.printf("Diskon (%s, %.0f%%):        -Rp %,.2f\n", 
                    getPaymentMethodName(), getDiscountPercent(), discountAmount);
        }
        
        System.out.printf("Setelah Diskon:            Rp %,.2f\n", totalAfterDiscount);
        
        if (adminFeeAmount > 0) {
            System.out.printf("Biaya Admin (%s):        +Rp %,.2f\n",
                    getPaymentMethodName(), adminFeeAmount);
        }
        
        System.out.println("-".repeat(56));
        System.out.printf("METODE PEMBAYARAN : %s\n", getPaymentMethodName());
        System.out.printf("MATA UANG : %s (Rate: 1 %s = Rp %.0f)\n",
                currency.getCode(), currency.getCode(), currency.getExchangeRate());
        
        System.out.println("-".repeat(56));
        System.out.printf("TOTAL PEMBAYARAN (IDR) : Rp %,.2f\n", finalTotalInIDR);
        System.out.printf("TOTAL PEMBAYARAN (%s) : %s\n",
                currency.getCode(), currency.formatCurrency(finalTotalInCurrency));
        
        if (installmentTerm > 0) {
            System.out.println("\n" + "-".repeat(56));
            System.out.println("RENCANA CICILAN (KARTU KREDIT) :");
            System.out.printf("   - Tenor : %d bulan\n", installmentTerm);
            System.out.printf("   - Cicilan Per Bulan : Rp %,.2f\n", installmentAmountPerMonth);
            System.out.println("   - Catatan : Pastikan kartu kredit Anda aktif");
        }
        
        System.out.println("\n========================================================");
        System.out.println("       Terima kasih telah berbelanja di KohiSop!        ");
        System.out.println("========================================================\n");
    }
}