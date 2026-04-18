package app;

import currency.*;
import entity.*;
import payment.*;
import repository.MenuRepository;
import utility.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KohiSopApplication {
    private MenuRepository menuRepository;
    private Order currentOrder;
    private List<PaymentChannel> paymentMethods;
    private List<Currency> currencies;
    private Scanner scanner;

    // Construct
    public KohiSopApplication() {
        this.menuRepository = new MenuRepository();
        this.currentOrder = new Order();
        this.paymentMethods = new ArrayList<>();
        this.currencies = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        
        initializePaymentMethods();
        initializeCurrencies();
    }

    // Inisialisasi
    private void initializePaymentMethods() {
        paymentMethods.add(new Cash());
        paymentMethods.add(new QRIS(1000000)); 
        paymentMethods.add(new eMoney(1000000));
        paymentMethods.add(new CreditCard("User", "00000000000"));
    }

    private void initializeCurrencies() {
        currencies.add(new USD());
        currencies.add(new JPY());
        currencies.add(new MYR());
        currencies.add(new EUR());
    }

    // Sout
    public void start() {
        System.out.println("==================================================");
        System.out.println("            Selamat Datang di KohiSop             ");
        System.out.println("==================================================");
        System.out.println("PANDUAN :");
        System.out.println(" - Masukkan kode menu (contoh: B1 atau F1)");
        System.out.println(" - Ketik 'S' untuk melewati/skip item");
        System.out.println(" - Ketik 'CC' untuk menyelesaikan pesanan");
        System.out.println(" - Tekan ENTER untuk jumlah default (1)");
        System.out.println("==================================================");
        
        processMenuInput();
        
        if (currentOrder.isEmpty()) {
            System.out.println("\nPesanan kosong. Terima kasih telah berkunjung.");
            return;
        }

        currentOrder.displayOrderSummary();
        
        processPaymentSelection();
        processCurrencySelection();
        
        generateFinalInvoice();
    }

    private void processMenuInput() {
        while (true) {
            menuRepository.displayAllMenu();
            System.out.print("\nMasukkan kode menu : ");
            String input = scanner.nextLine();

            if (InputValidator.isCancelInput(input)) {
                break;
            }

            if (InputValidator.isSkipInput(input)) {
                continue;
            }

            if (!InputValidator.isValidMenuCode(input, menuRepository)) {
                System.out.println("[!] Kode menu tidak ditemukan.");
                continue;
            }

            Menu selectedMenu = menuRepository.getMenuByCode(input);
            
            System.out.print("Jumlah pesanan : ");
            String qtyInput = scanner.nextLine();
            int qty = InputValidator.parseQuantity(qtyInput);

            if (!InputValidator.isValidQuantity(selectedMenu, qty)) {
                System.out.println("[!] Jumlah tidak valid atau melebihi batas !");
                continue;
            }

            currentOrder.addOrderItem(selectedMenu, qty);
            System.out.println("[+] Berhasil menambahkan " + selectedMenu.getName());
        }
    }

    private void processPaymentSelection() {
        while (true) {
            System.out.println("\n--- Pilih Metode ---");
            for (int i = 0; i < paymentMethods.size(); i++) {
                System.out.println((i + 1) + ". " + paymentMethods.get(i).getPaymentName());
            }
            System.out.print("Pilihan (1-" + paymentMethods.size() + ") : ");
            String choice = scanner.nextLine();
            
            if (InputValidator.isValidPaymentMethod(choice)) {
                int index = Integer.parseInt(choice.trim()) - 1;
                if (index >= 0 && index < paymentMethods.size()) {
                    currentOrder.setPaymentChannel(paymentMethods.get(index));
                    break;
                }
            }
            System.out.println("[!] Metode pembayaran tidak valid.");
        }
    }

    private void processCurrencySelection() {
        while (true) {
            System.out.println("\n--- Pilih Mata Uang ---");
            for (Currency c : currencies) {
                System.out.println("- " + c.getCode());
            }
            System.out.print("Masukkan kode mata uang : ");
            String choice = scanner.nextLine();

            if (InputValidator.isValidCurrency(choice)) {
                for (Currency c : currencies) {
                    if (c.getCode().equalsIgnoreCase(choice.trim())) {
                        currentOrder.setCurrency(c);
                        return;
                    }
                }
            }
            System.out.println("[!] Mata uang tidak valid.");
        }
    }

    private void generateFinalInvoice() {
        Receipt receipt = new Receipt();
        for (OrderItem item : currentOrder.getItems()) {
            receipt.addItem(item);
        }
        
        Invoice invoice = new Invoice(receipt, currentOrder.getPaymentChannel(), currentOrder.getCurrency());
        
        if (currentOrder.getPaymentChannel() instanceof CreditCard) {
            System.out.print("\nMasukkan tenor cicilan (bulan, 1-24) : ");
            try {
                int term = Integer.parseInt(scanner.nextLine());
                if (InputValidator.isValidInstallmentTerm(term)) {
                    invoice.setInstallmentTerm(term);
                }
            } catch (Exception e) {}
        }
        
        invoice.printInvoice();
    }

    // Main
    public static void main(String[] args) {
        KohiSopApplication app = new KohiSopApplication();
        app.start();
    }
}