package payment;

public class Cash implements PaymentChannel {
    private String paymentName;

    public Cash() {
        this.paymentName = "Cash";
    }

    @Override
    public double getDiscount() {
        return 0.0;
    }

    @Override
    public double getAdminFee() {
        return 0.0;
    }

    @Override
    public boolean validateBalance(double amount) {
        return amount >= 0;
    }

    @Override
    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String name) {
        this.paymentName = name;
    }
}
