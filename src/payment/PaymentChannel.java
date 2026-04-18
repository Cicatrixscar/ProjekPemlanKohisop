package payment;

public interface PaymentChannel {
    double getDiscount();
    double getAdminFee();
    boolean validateBalance(double amount);
    String getPaymentName();
}
