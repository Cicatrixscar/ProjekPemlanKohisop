package payment;

public class CreditCard implements PaymentChannel {
    private static final double DISCOUNT_RATE = 0.00;
    private static final double ADMIN_FEE = 5000.0;
    private String paymentName;
    private String cardHolderName;
    private String cardNumber;
    private int installmentTerm;
    private double balance;

    public CreditCard(String cardHolderName, String cardNumber) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.installmentTerm = 0;
        this.balance = 0.0;
        this.paymentName = "CreditCard";
    }

    @Override
    public double getDiscount() {
        return DISCOUNT_RATE;
    }

    @Override
    public double getAdminFee() {
        return ADMIN_FEE;
    }

    @Override
    public boolean validateBalance(double amount) {
        return balance >= amount;
    }

    @Override
    public String getPaymentName() {
        return paymentName;
    }

    public int getInstallmentTerm() {
        return installmentTerm;
    }

    public void setInstallmentTerm(int term) {
        this.installmentTerm = term;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
