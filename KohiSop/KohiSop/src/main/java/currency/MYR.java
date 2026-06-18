package currency;

public class MYR implements Currency {
    
    private static final String CODE = "MYR";
    private static final String SYMBOL = "RM";
    private static final double EXCHANGE_RATE = 4000.0;
    
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