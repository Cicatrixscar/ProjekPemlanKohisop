package currency;

public interface Currency {
    
    public String getCode();

    public double getExchangeRate();

    public double convertFromIDR(double amountIDR);

    public String getCurrencySymbol();

    public String formatCurrency(double amount);
}
