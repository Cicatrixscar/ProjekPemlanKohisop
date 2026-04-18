package currency;

public class IDR implements Currency {
    
    private static final String CODE = "IDR";
    private static final String SYMBOL = "Rp";
    private static final double EXCHANGE_RATE = 1.0;
    
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
        return amountIDR;
    }

    @Override
    public String getCurrencySymbol() {
        return SYMBOL;
    }

    @Override
    public String formatCurrency(double amount) {
        return String.format("%s %,.2f", SYMBOL, amount);
    }
}