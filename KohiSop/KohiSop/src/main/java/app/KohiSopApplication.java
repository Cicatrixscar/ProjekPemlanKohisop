package app;

import currency.*;
import entity.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import kitchen.KitchenManager;
import payment.*;
import repository.MemberRepository;
import repository.MenuRepository;
import utility.InputValidator;

public class KohiSopApplication {
    private MenuRepository menuRepository;
    private MemberRepository memberRepository;
    private KitchenManager kitchenManager;
    private Order currentOrder;
    private List<PaymentChannel> paymentMethods;
    private List<Currency> currencies;
    private Scanner scanner;
    private Member currentMember;

    // Construct
    public KohiSopApplication() {
        this.menuRepository = new MenuRepository();
        this.memberRepository = new MemberRepository();
        this.kitchenManager = new KitchenManager();
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
        currencies.add(new IDR());
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
        System.out.println(" - Masukkan kode menu (contoh: B1 atau M1)");
        System.out.println(" - Ketik 'S' untuk melewati/skip pemilihan item");
        System.out.println(" - Ketik 'CC' untuk menyelesaikan pesanan");
        System.out.println(" - Tekan ENTER untuk jumlah default (1)");
        System.out.println("==================================================");

        processMembership();
        processMenuInput();

        if (currentOrder.isEmpty()) {
            System.out.println("\nPesanan kosong. Terima kasih telah berkunjung.");
            return;
        }

        if (currentMember != null && currentMember.isTaxFree()) {
            applyTaxFreeToOrder();
        }

        currentOrder.displayOrderSummary();
        processPaymentSelection();
        processCurrencySelection();
        generateFinalInvoice();

        kitchenManager.addCustomerOrder(currentOrder);
        System.out.printf("%n[INFO] Pelanggan ke-%d telah terlayani.%n", kitchenManager.getCustomerCount());

        if (kitchenManager.isReadyToProcess()) {
            kitchenManager.displayKitchenProcess();
            kitchenManager.resetBatch();
        }
    }

    // Membership
    private void processMembership() {
        System.out.println("\n--- Membership ---");
        System.out.println("1. Saya sudah menjadi member (masukkan kode member)");
        System.out.println("2. Daftar sebagai member baru");
        System.out.println("3. Lanjut tanpa member");
        System.out.print("Pilihan (1/2/3) : ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1": processExistingMember(); break;
            case "2": processNewMember(); break;
            default:
                System.out.println("[INFO] Melanjutkan sebagai tamu (tanpa member).");
                break;
        }
    }

    private void processExistingMember() {
        System.out.print("Masukkan kode member Anda (6 karakter) : ");
        String code = scanner.nextLine().trim().toUpperCase();
        Member found = memberRepository.findByCode(code);
        if (found != null) {
            currentMember = found;
            System.out.printf("[+] Selamat datang kembali, %s! Poin Anda: %d%n",
                    found.getName(), found.getPoints());
            if (found.isTaxFree()) {
                System.out.println("[*] Kode member Anda mengandung 'A' -> BEBAS PAJAK & POIN GANDA!");
            }
        } else {
            System.out.println("[!] Kode member tidak ditemukan. Melanjutkan sebagai tamu.");
        }
    }

    private void processNewMember() {
        System.out.print("Masukkan nama Anda untuk pendaftaran member : ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("[!] Nama tidak boleh kosong. Melanjutkan sebagai tamu.");
            return;
        }
        currentMember = memberRepository.addMember(name);
        System.out.println("[+] Pendaftaran berhasil!");
        System.out.printf("    Kode Member Anda: %s%n", currentMember.getMemberCode());
        System.out.printf("    Nama            : %s%n", currentMember.getName());
        System.out.println("    Simpan kode member Anda untuk transaksi berikutnya.");
        if (currentMember.isTaxFree()) {
            System.out.println("[*] Kode member Anda mengandung 'A' -> BEBAS PAJAK & POIN GANDA!");
        }
    }

    // Input menu
    private void processMenuInput() {
        currentOrder = new Order();
        while (true) {
            menuRepository.displayAllMenu();

            System.out.printf("%n[Info] Makanan: %d/5 jenis | Minuman: %d/5 jenis%n",
                    currentOrder.getFoodTypes(), currentOrder.getBeverageTypes());

            if (!currentOrder.isEmpty()) {
                System.out.println("\n[ PESANAN ANDA SAAT INI ]");
                currentOrder.displayOrderTable();
            }

            System.out.print("\nMasukkan kode menu : ");
            String input = scanner.nextLine();

            if (InputValidator.isCancelInput(input)) break;
            if (InputValidator.isSkipInput(input)) continue;

            if (!InputValidator.isValidMenuCode(input, menuRepository)) {
                System.out.println("[!] Kode menu tidak ditemukan.");
                continue;
            }

            Menu selectedMenu = menuRepository.getMenuByCode(input);

            if (!currentOrder.isAlreadyOrdered(selectedMenu.getCodeMenu())
                    && currentOrder.isMaxTypesReached(selectedMenu)) {
                String cat = (selectedMenu instanceof Beverage) ? "minuman" : "makanan";
                System.out.printf("[!] Batas maksimum 5 jenis %s sudah tercapai.%n", cat);
                continue;
            }

            System.out.print("Jumlah pesanan (ENTER=1, 0/S=batalkan item ini) : ");
            String qtyInput = scanner.nextLine();

            if (qtyInput.trim().equalsIgnoreCase("S") || qtyInput.trim().equals("0")) {
                System.out.println("[-] Item dilewati.");
                continue;
            }

            int qty = InputValidator.parseQuantity(qtyInput);

            if (!InputValidator.isValidQuantity(selectedMenu, qty)) {
                System.out.println("[!] Jumlah tidak valid atau melebihi batas !");
                continue;
            }

            currentOrder.addOrderItem(selectedMenu, qty);
            System.out.println("[+] Berhasil menambahkan " + selectedMenu.getName());
        }
    }

    // Bebas pajak
    private void applyTaxFreeToOrder() {
        for (OrderItem item : currentOrder.getItems()) {
            item.getMenu().setTaxFree(true);
        }
        System.out.println("[*] Pajak dibebaskan untuk semua item (kode member mengandung 'A').");
    }

    // Pembayaran
    private void processPaymentSelection() {
        while (true) {
            System.out.println("\n--- Pilih Metode Pembayaran ---");
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

    // Invoice
    private void generateFinalInvoice() {
        Receipt receipt = new Receipt();
        for (OrderItem item : currentOrder.getItems()) {
            receipt.addItem(item);
        }

        Invoice invoice = new Invoice(receipt, currentOrder.getPaymentChannel(), currentOrder.getCurrency());

        if (currentMember != null) {
            double totalIDR = invoice.getFinalTotalInIDR();
            int pointsEarned = currentMember.calculateEarnedPoints(totalIDR);
            double pointsDeduction = 0.0;
            int usedPoints = 0;

            boolean isIDR = currentOrder.getCurrency().getCode().equalsIgnoreCase("IDR");
            if (isIDR && currentMember.getPoints() > 0) {
                System.out.printf("%nAnda memiliki %d poin (= Rp %,.0f).%n",
                        currentMember.getPoints(), (double) currentMember.getPoints() * 2);
                System.out.print("Gunakan poin untuk membayar? (Y/N) : ");
                String usePoints = scanner.nextLine().trim();
                if (usePoints.equalsIgnoreCase("Y")) {
                    double maxDeduction = currentMember.getPoints() * 2.0;
                    if (maxDeduction >= totalIDR) {
                        pointsDeduction = totalIDR;
                        usedPoints = (int) Math.ceil(totalIDR / 2.0);
                    } else {
                        pointsDeduction = maxDeduction;
                        usedPoints = currentMember.getPoints();
                    }
                    System.out.printf("[+] Menggunakan %d poin = Rp %,.0f sebagai potongan.%n",
                            usedPoints, pointsDeduction);
                }
            }

            int pointsBefore = currentMember.getPoints();
            if (usedPoints > 0) currentMember.deductPoints(usedPoints);
            currentMember.addPoints(pointsEarned);

            invoice.setMemberInfo(
                    currentMember.getMemberCode(),
                    currentMember.getName(),
                    pointsBefore,
                    pointsEarned,
                    pointsDeduction,
                    currentMember.isTaxFree()
            );
        }

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

        while (true) {
            app.currentMember = null;
            app.start();

            System.out.print("\nLanjutkan pelanggan berikutnya? (Y/N) : ");
            String cont = app.scanner.nextLine().trim();
            if (!cont.equalsIgnoreCase("Y")) break;
            System.out.println();
        }

        System.out.println("\nTerima kasih telah menggunakan KohiSop. Sampai jumpa!");
        app.scanner.close();
    }
}