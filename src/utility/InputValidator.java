package utility;

import entity.Menu;

public class InputValidator {
    private static final String SKIP_INPUT = "S";
    private static final String SKIP_INPUT_LOWER = "s";
    private static final String CANCEL_INPUT = "CC";
    private static final String CANCEL_INPUT_LOWER = "cc";
    private static final int MAX_BEVERAGE_QUANTITY = 3;
    private static final int MAX_FOOD_QUANTITY = 2;
    private static final int MIN_QUANTITY = 0;

    private InputValidator() {
        throw new AssertionError("Tidak Bisa Menginstansiasi InputValidator!");
    }

    public static boolean isValidMenuCode(String code, MenuRepository repo) {
        return repo.isValidMenuCode(code);
    }

    public static boolean isValidQuantity(Menu menu, int quantity) {
        if (quantity <= MIN_QUANTITY) {
            return false;
        }

        if (menu.getCategory().equals("Beverage")) {
            return quantity <= MAX_BEVERAGE_QUANTITY;
        } else if (menu.getCategory().equals("Food")) {
            return quantity <= MAX_FOOD_QUANTITY;
        }
        return false;
    }

    public static int parseQuantity(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(input.trim());
    }
}
