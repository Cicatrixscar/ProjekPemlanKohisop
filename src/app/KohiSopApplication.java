import java.util.*;

public class KohiSopApplication {
    private MenuRepository menuRepository;
    private Order currentOrder;
    private Map<Integer, PaymentChannel> paymentMethods;
    private Map<String, Currency> currencies;
    private Scanner scanner;

    private static final String CANCEL_CODE = "CC";
    private static final String DEFAULT_WELCOME_MESSAGE = "Welcome to KohiSop!";

    public KohiSopApplication() {
        menuRepository = new MenuRepository();
        currentOrder = new Order();
        paymentMethods = initializePaymentMethods();
        currencies = initializeCurrencies();
        scanner = new Scanner(System.in);
    }

    private Map<Integer, PaymentChannel> initializePaymentMethods() {
        Map<Integer, PaymentChannel> map = new HashMap<>();
        map.put(1, new PaymentChannel("Cash"));
        map.put(2, new PaymentChannel("QRIS"));
        map.put(3, new PaymentChannel("Credit Card"));
        return map;
    }

    private Map<String, Currency> initializeCurrencies() {
        Map<String, Currency> map = new HashMap<>();
        map.put("IDR", new Currency("IDR", 1));
        map.put("USD", new Currency("USD", 15000));
        return map;
    }

    private void displayWelcome() {
        System.out.println(DEFAULT_WELCOME_MESSAGE);
    }

    private void displayMenuOptions() {
        menuRepository.displayAllMenu();
        System.out.println("Masukkan kode menu (CC untuk selesai)");
    }

    private void displayPaymentOptions() {
        System.out.println("\n=== PAYMENT METHODS ===");
        for (Map.Entry<Integer, PaymentChannel> entry : paymentMethods.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName());
        }
    }

    private void displayCurrencyOptions() {
        System.out.println("\n=== CURRENCY ===");
        for (String key : currencies.keySet()) {
            System.out.println(key);
        }
    }

    private String getMenuCodeInput() {
        System.out.print("Input kode: ");
        return scanner.nextLine();
    }

    private int getQuantityInput() {
        System.out.print("Jumlah: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private PaymentChannel selectPaymentMethod() {
        displayPaymentOptions();
        System.out.print("Pilih metode: ");
        int choice = Integer.parseInt(scanner.nextLine());
        return paymentMethods.get(choice);
    }

    private Currency selectCurrency() {
        displayCurrencyOptions();
        System.out.print("Pilih currency: ");
        String code = scanner.nextLine().toUpperCase();
        return currencies.get(code);
    }

    private void processMenuInput() {
        while (true) {
            displayMenuOptions();
            String code = getMenuCodeInput();

            if (code.equalsIgnoreCase(CANCEL_CODE)) {
                break;
            }

            if (!menuRepository.isValidMenuCode(code)) {
                System.out.println("Kode tidak valid!");
                continue;
            }

            Menu menu = menuRepository.getMenuByCode(code);
            int qty = getQuantityInput();

            currentOrder.addItem(menu, qty);
        }
    }

    private void displayCurrentOrderSummary() {
        System.out.println("\n=== ORDER SUMMARY ===");
        currentOrder.displayOrder();
    }

    private void processBill() {
        PaymentChannel payment = selectPaymentMethod();
        Currency currency = selectCurrency();

        double total = currentOrder.calculateTotal();
        double converted = total / currency.getRate();

        System.out.println("\n=== BILL ===");
        System.out.println("Total: " + total + " IDR");
        System.out.println("Converted: " + converted + " " + currency.getCode());
        System.out.println("Payment: " + payment.getName());
    }

    public void start() {
        displayWelcome();
        processMenuInput();
        displayCurrentOrderSummary();
        processBill();
    }

    public static void main(String[] args) {
        KohiSopApplication app = new KohiSopApplication();
        app.start();
    }
}