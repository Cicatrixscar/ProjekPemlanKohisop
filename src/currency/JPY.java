package currency;

public class JPY implements Currency {
    
    private static final String CODE = "JPY";
    private static final String SYMBOL = "Y";
    private static final double EXCHANGE_RATE = 0.1;
    
    @Override
    public String getCode() {
        return CODE;
    }

    @Override
    public double getExchangeRate() {
        return EXCHANGE_RATE;
    }

    @Override
    public double convertFromIDR(double amountIDR) {
        return amountIDR / EXCHANGE_RATE;
    }

    @Override
    public String getCurrencySymbol() {
        return SYMBOL;
    }

    @Override
    public String formatCurrency(double amount) {
        return String.format("%s %.2f", SYMBOL, amount);
    }
}