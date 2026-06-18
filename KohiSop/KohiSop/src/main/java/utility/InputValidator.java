package utility;

import entity.Beverage;
import entity.Food;
import entity.Menu;
import payment.PaymentChannel;
import repository.MenuRepository;

public class InputValidator {
    private static final String SKIP_INPUT = "S";
    private static final String SKIP_INPUT_LOWER = "s";
    private static final String CANCEL_INPUT = "CC";
    private static final String CANCEL_INPUT_LOWER = "cc";
    private static final int MAX_BEVERAGE_QUANTITY = 3;
    private static final int MAX_FOOD_QUANTITY = 2;
    private static final int MIN_QUANTITY = 0;

    // Construct
    private InputValidator() {
    }

    // Validation
    public static boolean isValidMenuCode(String code, MenuRepository repo) {
        return repo.isValidMenuCode(code);
    }

    public static boolean isValidQuantity(Menu menu, int quantity) {
        if (quantity <= MIN_QUANTITY) {
            return false;
        }

        if (menu instanceof Beverage) {
            return quantity <= MAX_BEVERAGE_QUANTITY;
        } else if (menu instanceof Food) {
            return quantity <= MAX_FOOD_QUANTITY;
        }

        return false;
    }

    public static int parseQuantity(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 1;
        }
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static boolean isValidPaymentMethod(String choice) {
        try {
            int choiceNum = Integer.parseInt(choice.trim());
            return choiceNum >= 1 && choiceNum <= 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidCurrency(String choice) {
        if (choice == null) {
            return false;
        }
        String code = choice.trim().toUpperCase();
        return code.equals("IDR") || code.equals("USD") || code.equals("JPY") || 
               code.equals("MYR") || code.equals("EUR");
    }

    public static boolean isSkipInput(String input) {
        if (input == null) {
            return false;
        }
        return input.trim().equals(SKIP_INPUT) || input.trim().equals(SKIP_INPUT_LOWER);
    }

    public static boolean isCancelInput(String input) {
        if (input == null) {
            return false;
        }
        return input.trim().equals(CANCEL_INPUT) || input.trim().equals(CANCEL_INPUT_LOWER);
    }

    public static boolean isValidBalance(PaymentChannel payment, double amount) {
        return payment.validateBalance(amount);
    }

    public static boolean isValidCreditCard(String cardNumber) {
        if (cardNumber == null) {
            return false;
        }
        
        String cleanCardNumber = cardNumber.replaceAll("\\s", "");
        
        if (!cleanCardNumber.matches("\\d+")) {
            return false;
        }
        
        return cleanCardNumber.length() >= 13 && cleanCardNumber.length() <= 19;
    }

    public static boolean isValidInstallmentTerm(int term) {
        return term >= 1 && term <= 24;
    }
}