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

    // Info membership
    private String memberCode;
    private String memberName;
    private int pointsBefore;
    private int pointsEarned;
    private int pointsAfter;
    private double pointsDeduction;
    private boolean hasMember;
    private boolean isTaxFree;

    // Construct
    public Invoice(Receipt receipt, PaymentChannel paymentMethod, Currency currency) {
        this.receipt = receipt;
        this.paymentMethod = paymentMethod;
        this.currency = currency;
        this.transactionTime = LocalDateTime.now();
        this.installmentTerm = 0;
        this.installmentAmountPerMonth = 0.0;
        this.hasMember = false;
        this.isTaxFree = false;
        this.pointsDeduction = 0.0;
        calculateTotals();
    }

    // Set
    public void setMemberInfo(String memberCode, String memberName, int pointsBefore,
                               int pointsEarned, double pointsDeductionIDR, boolean isTaxFree) {
        this.memberCode = memberCode;
        this.memberName = memberName;
        this.pointsBefore = pointsBefore;
        this.pointsEarned = pointsEarned;
        this.pointsDeduction = pointsDeductionIDR;
        this.isTaxFree = isTaxFree;
        this.hasMember = true;
        this.pointsAfter = pointsBefore + pointsEarned;
        calculateTotals();
    }

    // Calculate
    private void calculateTotals() {
        totalBeforeDiscount = receipt.getTotalWithTax();
        discountAmount = totalBeforeDiscount * paymentMethod.getDiscount();
        totalAfterDiscount = totalBeforeDiscount - discountAmount;
        adminFeeAmount = paymentMethod.getAdminFee();
        double afterPointsDeduction = totalAfterDiscount - pointsDeduction;
        if (afterPointsDeduction < 0) afterPointsDeduction = 0;
        finalTotalInIDR = afterPointsDeduction + adminFeeAmount;
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

        if (hasMember) {
            System.out.println("\n" + "-".repeat(60));
            System.out.println("INFO MEMBER :");
            System.out.printf("   Kode Member   : %s%n", memberCode);
            System.out.printf("   Nama Member   : %s%n", memberName);
            if (isTaxFree) {
                System.out.println("   Status        : BEBAS PAJAK & POIN GANDA (kode mengandung 'A')");
            }
            System.out.printf("   Poin Sebelum  : %d poin%n", pointsBefore);
            System.out.printf("   Poin Diperoleh: +%d poin%n", pointsEarned);
            System.out.printf("   Poin Sesudah  : %d poin%n", pointsAfter);
        }

        System.out.println("\n" + "-".repeat(60));
        System.out.println("DETAIL PESANAN :");
        System.out.println("-".repeat(60));

        int itemNumber = 1;

        boolean headerBeveragePrinted = false;
        for (OrderItem item : receipt.getItems()) {
            if (item.getMenu() instanceof Beverage) {
                if (!headerBeveragePrinted) {
                    System.out.println("\n[ Minuman ]");
                    headerBeveragePrinted = true;
                }
                System.out.printf("   %d. %-30s x%d%n", itemNumber, item.getName(), item.getQuantity());
                System.out.printf("      - Harga: Rp %.2f%n", item.getSubtotal());
                System.out.printf("      - Pajak: Rp %.2f%s%n", item.getTaxAmount(),
                        isTaxFree ? " (BEBAS PAJAK)" : "");
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
                System.out.printf("   %d. %-30s x%d%n", itemNumber, item.getName(), item.getQuantity());
                System.out.printf("      - Harga: Rp %.2f%n", item.getSubtotal());
                System.out.printf("      - Pajak: Rp %.2f%s%n", item.getTaxAmount(),
                        isTaxFree ? " (BEBAS PAJAK)" : "");
                itemNumber++;
            }
        }

        System.out.println("\n" + "-".repeat(60));
        System.out.println("RINGKASAN PEMBAYARAN :");
        System.out.println("-".repeat(60));
        System.out.printf("Subtotal (dengan pajak):   Rp %,.2f%n", totalBeforeDiscount);

        if (discountAmount > 0) {
            System.out.printf("Diskon (%s, %.0f%%):        -Rp %,.2f%n",
                    getPaymentMethodName(), getDiscountPercent(), discountAmount);
        }

        System.out.printf("Setelah Diskon:            Rp %,.2f%n", totalAfterDiscount);

        if (pointsDeduction > 0) {
            System.out.printf("Potongan Poin (%d poin):   -Rp %,.2f%n",
                    (int)(pointsDeduction / 2), pointsDeduction);
        }

        if (adminFeeAmount > 0) {
            System.out.printf("Biaya Admin (%s):        +Rp %,.2f%n",
                    getPaymentMethodName(), adminFeeAmount);
        }

        System.out.println("-".repeat(60));
        System.out.printf("METODE PEMBAYARAN : %s%n", getPaymentMethodName());
        System.out.printf("MATA UANG : %s (Rate: 1 %s = Rp %.0f)%n",
                currency.getCode(), currency.getCode(), currency.getExchangeRate());

        System.out.println("-".repeat(60));
        System.out.printf("TOTAL PEMBAYARAN (IDR) : Rp %,.2f%n", finalTotalInIDR);
        System.out.printf("TOTAL PEMBAYARAN (%s) : %s%n",
                currency.getCode(), currency.formatCurrency(finalTotalInCurrency));

        if (installmentTerm > 0) {
            System.out.println("\n" + "-".repeat(60));
            System.out.println("RENCANA CICILAN (KARTU KREDIT) :");
            System.out.printf("   - Tenor : %d bulan%n", installmentTerm);
            System.out.printf("   - Cicilan Per Bulan : Rp %,.2f%n", installmentAmountPerMonth);
            System.out.println("   - Catatan : Pastikan kartu kredit Anda aktif");
        }

        System.out.println("\n========================================================");
        System.out.println("       Terima kasih dan silakan datang kembali!         ");
        System.out.println("========================================================\n");
    }
}