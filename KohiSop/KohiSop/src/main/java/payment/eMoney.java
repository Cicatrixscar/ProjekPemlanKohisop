package payment;

public class eMoney implements PaymentChannel {
    private static final double DISCOUNT_RATE = 0.07;
    private static final double ADMIN_FEE = 2000.0;
    private String paymentName;
    private double balance;

    public eMoney(double initialBalance) {
        this.balance = initialBalance;
        this.paymentName = "eMoney";
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

    public void setPaymentName(String name) {
        this.paymentName = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }
}
