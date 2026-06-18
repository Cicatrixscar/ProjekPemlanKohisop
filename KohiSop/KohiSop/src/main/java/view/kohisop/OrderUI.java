/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.kohisop;

/**
 *
 * @author Lenovo
 */
public class OrderUI extends javax.swing.JFrame {
    
    private repository.MenuRepository menuRepository = new repository.MenuRepository();
    private entity.Order currentOrder = new entity.Order();
    private kitchen.KitchenManager kitchenManager = new kitchen.KitchenManager();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(OrderUI.class.getName());

    /**
     * Creates new form OrderUI
     */
public OrderUI() {
    initComponents();
    inisialisasiDanTampilkanMenu();
    setSize(1200, 750);
    setLocationRelativeTo(null);
    setTitle("KohiSop - Café Ordering System");
    perbaikiTampilan();
}
private void inisialisasiDanTampilkanMenu() {
        ScrollFoodPanel.removeAll();
        ScrollBeveragePanel.removeAll();

        for (entity.Menu item : menuRepository.getAllMenus()) {
            FoodCard card = new FoodCard();
            card.setMenuData(item);
            
            if (item instanceof entity.Food) {
                ScrollFoodPanel.add(card);
            } else if (item instanceof entity.Beverage) {
                ScrollBeveragePanel.add(card);
            }
        }

        ScrollFoodPanel.revalidate();
        ScrollFoodPanel.repaint();
        ScrollBeveragePanel.revalidate();
        ScrollBeveragePanel.repaint();
    }

private void perbaikiTampilan() {

    // === 1. HEADER / BRANDING ===
    javax.swing.JPanel headerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
    headerPanel.setBackground(new java.awt.Color(44, 28, 14));
    headerPanel.setPreferredSize(new java.awt.Dimension(0, 60));

    javax.swing.JLabel titleLabel = new javax.swing.JLabel("  KohiSop");
    titleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 26));
    titleLabel.setForeground(new java.awt.Color(255, 220, 150));
    titleLabel.setIcon(loadIcon("/image/icons/coffee-cup.png", 32, 32));
    titleLabel.setIconTextGap(10);
    headerPanel.add(titleLabel, java.awt.BorderLayout.WEST);

    javax.swing.JLabel subLabel = new javax.swing.JLabel("Café Ordering System  ");
    subLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
    subLabel.setForeground(new java.awt.Color(200, 180, 150));
    headerPanel.add(subLabel, java.awt.BorderLayout.EAST);

    getContentPane().add(headerPanel, java.awt.BorderLayout.PAGE_START);

    // === 2. NAVBAR KIRI ===
    NavBarPanel.setBackground(new java.awt.Color(62, 39, 20));
    NavBarPanel.setPreferredSize(new java.awt.Dimension(120, 0));
    NavBarPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 8, 20, 8));

    styleNavButton(FoodButton, "Food", "/image/icons/restaurant.png");
    styleNavButton(BeverageButton, "Beverage", "/image/icons/soda.png");

    // === 3. PANEL ORDER KANAN ===
    buatPanelOrderBaru();
}

private void buatPanelOrderBaru() {

    DisplayPanel.removeAll();
    DisplayPanel.setLayout(new java.awt.BorderLayout());
    DisplayPanel.setPreferredSize(new java.awt.Dimension(260, 0));
    DisplayPanel.setBackground(new java.awt.Color(250, 245, 240));
    DisplayPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(200, 180, 160)));

    // --- HEADER "My Order" ---
    javax.swing.JPanel headerOrder = new javax.swing.JPanel();
    headerOrder.setLayout(new javax.swing.BoxLayout(headerOrder, javax.swing.BoxLayout.Y_AXIS));
    headerOrder.setBackground(new java.awt.Color(44, 28, 14));
    headerOrder.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

    javax.swing.JLabel lblMyOrder = new javax.swing.JLabel("My Order");
    lblMyOrder.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
    lblMyOrder.setForeground(new java.awt.Color(255, 220, 150));
    lblMyOrder.setIcon(loadIconWhite("/image/icons/bill.png", 22, 22));
    lblMyOrder.setIconTextGap(10);

    headerOrder.add(lblMyOrder);
    headerOrder.add(javax.swing.Box.createVerticalStrut(15));

    // --- INPUT KODE + TOMBOL + ---
    javax.swing.JPanel inputPanel = new javax.swing.JPanel(new java.awt.BorderLayout(8, 0));
    inputPanel.setOpaque(false);

    OrderField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    OrderField.setBorder(javax.swing.BorderFactory.createCompoundBorder(
        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 140, 100), 1),
        javax.swing.BorderFactory.createEmptyBorder(8, 10, 8, 10)
    ));

    jButton3.setText("+");
    jButton3.setBackground(new java.awt.Color(180, 120, 60));
    jButton3.setForeground(java.awt.Color.WHITE);
    jButton3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));
    jButton3.setFocusPainted(false);
    jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 16, 8, 16));
    jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    inputPanel.add(OrderField, java.awt.BorderLayout.CENTER);
    inputPanel.add(jButton3, java.awt.BorderLayout.EAST);

    headerOrder.add(inputPanel);

    DisplayPanel.add(headerOrder, java.awt.BorderLayout.PAGE_START);

    // --- LIST PESANAN (tengah, scrollable) ---
    ListOrderPanel.removeAll();
    ListOrderPanel.setLayout(new javax.swing.BoxLayout(ListOrderPanel, javax.swing.BoxLayout.Y_AXIS));
    ListOrderPanel.setBackground(java.awt.Color.WHITE);
    ListOrderPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

    javax.swing.JScrollPane scrollOrder = new javax.swing.JScrollPane(ListOrderPanel);
    scrollOrder.setBorder(null);
    scrollOrder.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    DisplayPanel.add(scrollOrder, java.awt.BorderLayout.CENTER);

    // --- FOOTER CHECKOUT ---
    javax.swing.JPanel footerPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
    footerPanel.setBackground(new java.awt.Color(250, 245, 240));
    footerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

    jButton2.setText("Checkout");
    jButton2.setIcon(loadIconWhite("/image/icons/bill.png", 18, 18));
    jButton2.setIconTextGap(8);
    jButton2.setBackground(new java.awt.Color(44, 28, 14));
    jButton2.setForeground(java.awt.Color.WHITE);
    jButton2.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
    jButton2.setFocusPainted(false);
    jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 0, 14, 0));
    jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

    footerPanel.add(jButton2, java.awt.BorderLayout.CENTER);

    DisplayPanel.add(footerPanel, java.awt.BorderLayout.PAGE_END);

    DisplayPanel.revalidate();
    DisplayPanel.repaint();
}

private javax.swing.ImageIcon loadIcon(String path, int width, int height) {
    try {
        java.net.URL url = getClass().getResource(path);
        System.out.println("CEK ICON: " + path + " -> " + url);
        if (url == null) return null;
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(url);
        java.awt.Image scaled = icon.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new javax.swing.ImageIcon(scaled);
    } catch (Exception e) {
        return null;
    }
}

private void styleNavButton(javax.swing.JButton btn, String label, String iconPath) {
    btn.setText(label);
    btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
    btn.setForeground(java.awt.Color.WHITE);
    btn.setBackground(new java.awt.Color(90, 58, 30));
    btn.setFocusPainted(false);
    btn.setBorderPainted(false);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btn.setPreferredSize(new java.awt.Dimension(104, 60));
    btn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    btn.setIcon(loadIconWhite(iconPath, 24, 24));
    btn.setIconTextGap(6);

    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent e) {
            btn.setBackground(new java.awt.Color(180, 120, 60));
        }
        public void mouseExited(java.awt.event.MouseEvent e) {
            btn.setBackground(new java.awt.Color(90, 58, 30));
        }
    });
}

    private void tambahPesanan(String kode) {
        entity.Menu menu = menuRepository.getMenuByCode(kode);
        if (menu == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Kode menu tidak ditemukan!");
            return;
        }

        if (!currentOrder.isAlreadyOrdered(kode) && currentOrder.isMaxTypesReached(menu)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Batas maksimal 5 jenis menu tercapai untuk kategori ini!");
            return;
        }

        int currentQty = 1;
        for (entity.OrderItem oi : currentOrder.getItems()) {
            if (oi.getCode().equalsIgnoreCase(kode)) {
                currentQty = oi.getQuantity() + 1;
            }
        }
        
        currentOrder.addOrderItem(menu, currentQty);
        updateKeranjangUI();
    }

    private void updateKeranjangUI() {
        ListOrderPanel.removeAll();
        for (entity.OrderItem item : currentOrder.getItems()) {
            javax.swing.JLabel itemLabel = new javax.swing.JLabel(
                item.getCode() + " - " + item.getName() + " - " + String.format("Rp %,.0f", item.getTotal()) + " (x" + item.getQuantity() + ")"
            );
            ListOrderPanel.add(itemLabel);
        }
        ListOrderPanel.revalidate();
        ListOrderPanel.repaint();
    }
    private void prosesOrder() {

    String kode = OrderField.getText().trim();

    if (!kode.isEmpty()) {

        tambahPesanan(kode);

        OrderField.setText("");
    }
}

private javax.swing.ImageIcon loadIconWhite(String path, int width, int height) {
    try {
        java.net.URL url = getClass().getResource(path);
        if (url == null) return null;
        
        java.awt.image.BufferedImage original = javax.imageio.ImageIO.read(url);
        java.awt.image.BufferedImage white = new java.awt.image.BufferedImage(
            original.getWidth(), original.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB
        );
        
        for (int x = 0; x < original.getWidth(); x++) {
            for (int y = 0; y < original.getHeight(); y++) {
                int argb = original.getRGB(x, y);
                int alpha = (argb >> 24) & 0xff;
                if (alpha > 0) {
                    // Ganti warna jadi putih, alpha tetap
                    white.setRGB(x, y, (alpha << 24) | 0xFFFFFF);
                }
            }
        }
        
        java.awt.Image scaled = white.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        return new javax.swing.ImageIcon(scaled);
    } catch (Exception e) {
        return null;
    }
}
    // ---> BATAS METHOD BARU <---
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NavBarPanel = new javax.swing.JPanel();
        FoodButton = new javax.swing.JButton();
        BeverageButton = new javax.swing.JButton();
        DisplayPanel = new javax.swing.JPanel();
        PanelOrderHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        OrderField = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        PanelOrderFooter = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ListOrderPanel = new javax.swing.JPanel();
        Home = new javax.swing.JPanel();
        FoodPanel = new javax.swing.JPanel();
        FoodScroll = new javax.swing.JScrollPane();
        ScrollFoodPanel = new javax.swing.JPanel();
        BeveragePanel = new javax.swing.JPanel();
        BeverageScroll = new javax.swing.JScrollPane();
        ScrollBeveragePanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        NavBarPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        NavBarPanel.setLayout(new java.awt.GridLayout(0, 1));

        FoodButton.setText("Food");
        FoodButton.addActionListener(this::FoodButtonActionPerformed);
        NavBarPanel.add(FoodButton);

        BeverageButton.setText("Beverage");
        BeverageButton.addActionListener(this::BeverageButtonActionPerformed);
        NavBarPanel.add(BeverageButton);

        getContentPane().add(NavBarPanel, java.awt.BorderLayout.LINE_START);

        DisplayPanel.setPreferredSize(new java.awt.Dimension(150, 100));
        DisplayPanel.setLayout(new java.awt.BorderLayout());

        PanelOrderHeader.setPreferredSize(new java.awt.Dimension(150, 90));

        jLabel1.setText("My Order:");

        OrderField.addActionListener(this::OrderFieldActionPerformed);

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setText("+");
        jButton3.addActionListener(this::plus);

        javax.swing.GroupLayout PanelOrderHeaderLayout = new javax.swing.GroupLayout(PanelOrderHeader);
        PanelOrderHeader.setLayout(PanelOrderHeaderLayout);
        PanelOrderHeaderLayout.setHorizontalGroup(
            PanelOrderHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOrderHeaderLayout.createSequentialGroup()
                .addComponent(OrderField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(PanelOrderHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelOrderHeaderLayout.setVerticalGroup(
            PanelOrderHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelOrderHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addGroup(PanelOrderHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(OrderField, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                .addContainerGap())
        );

        DisplayPanel.add(PanelOrderHeader, java.awt.BorderLayout.PAGE_START);

        jButton2.setText("Checkout");
        jButton2.setName("CheckOut"); // NOI18N
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout PanelOrderFooterLayout = new javax.swing.GroupLayout(PanelOrderFooter);
        PanelOrderFooter.setLayout(PanelOrderFooterLayout);
        PanelOrderFooterLayout.setHorizontalGroup(
            PanelOrderFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        PanelOrderFooterLayout.setVerticalGroup(
            PanelOrderFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelOrderFooterLayout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(jButton2))
        );

        DisplayPanel.add(PanelOrderFooter, java.awt.BorderLayout.PAGE_END);

        ListOrderPanel.setLayout(new javax.swing.BoxLayout(ListOrderPanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(ListOrderPanel);

        DisplayPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(DisplayPanel, java.awt.BorderLayout.LINE_END);

        Home.setLayout(new java.awt.CardLayout());

        FoodScroll.setBorder(null);
        FoodScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        FoodScroll.setHorizontalScrollBar(null);

        ScrollFoodPanel.setName("ScrollPanel"); // NOI18N
        ScrollFoodPanel.setLayout(new java.awt.GridLayout(0, 3, 15, 15));
        FoodScroll.setViewportView(ScrollFoodPanel);

        javax.swing.GroupLayout FoodPanelLayout = new javax.swing.GroupLayout(FoodPanel);
        FoodPanel.setLayout(FoodPanelLayout);
        FoodPanelLayout.setHorizontalGroup(
            FoodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FoodScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
        );
        FoodPanelLayout.setVerticalGroup(
            FoodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(FoodScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
        );

        Home.add(FoodPanel, "FoodCard");

        ScrollBeveragePanel.setLayout(new java.awt.GridLayout(0, 4, 15, 15));
        BeverageScroll.setViewportView(ScrollBeveragePanel);

        javax.swing.GroupLayout BeveragePanelLayout = new javax.swing.GroupLayout(BeveragePanel);
        BeveragePanel.setLayout(BeveragePanelLayout);
        BeveragePanelLayout.setHorizontalGroup(
            BeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BeverageScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1440, Short.MAX_VALUE)
        );
        BeveragePanelLayout.setVerticalGroup(
            BeveragePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BeverageScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
        );

        Home.add(BeveragePanel, "BeverageCard");

        getContentPane().add(Home, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
   
               

    private void BeverageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BeverageButtonActionPerformed
        java.awt.CardLayout layout = (java.awt.CardLayout) Home.getLayout();
        layout.show(Home, "BeverageCard");
    }//GEN-LAST:event_BeverageButtonActionPerformed

    private void FoodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FoodButtonActionPerformed
        java.awt.CardLayout layout = (java.awt.CardLayout) Home.getLayout();
        layout.show(Home, "FoodCard");
    }//GEN-LAST:event_FoodButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (currentOrder.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Keranjang masih kosong! Tambahkan pesanan terlebih dahulu.", "Keranjang Kosong", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Default pembayaran cash, mata uang IDR untuk GUI
        currentOrder.setPaymentChannel(new payment.Cash());
        currentOrder.setCurrency(new currency.IDR());

        entity.Receipt receipt = new entity.Receipt();
        receipt.addItems(currentOrder.getItems());
        entity.Invoice invoice = new entity.Invoice(receipt, currentOrder.getPaymentChannel(), currentOrder.getCurrency());

        StringBuilder ringkasan = new StringBuilder();
        ringkasan.append("===== INVOICE ANDA =====\n\n");
        for (entity.OrderItem item : currentOrder.getItems()) {
            ringkasan.append(item.getCode()).append(" - ").append(item.getName())
                     .append(" (x").append(item.getQuantity()).append(")  →  ")
                     .append(String.format("Rp %,.0f", item.getTotal())).append("\n");
        }
        ringkasan.append("\n─────────────────────────\n");
        ringkasan.append("SUBTOTAL: ").append(String.format("Rp %,.0f", invoice.getSubtotalBeforeDiscount())).append("\n");
        ringkasan.append("TOTAL BAYAR: ").append(String.format("Rp %,.0f", invoice.getFinalTotalInIDR()));

        javax.swing.JOptionPane.showMessageDialog(this, ringkasan.toString(), "Checkout Berhasil", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        kitchenManager.addCustomerOrder(currentOrder);

        if (kitchenManager.isReadyToProcess()) {
            StringBuilder dapurInfo = new StringBuilder();
            dapurInfo.append("=== TIM DAPUR MEMPROSES PESANAN ===\n\n");
            dapurInfo.append("Batch pesanan 3 pelanggan telah terkumpul.\n");
            dapurInfo.append("Tim dapur sedang memproses pesanan dengan antrian khusus.\n\n");
            dapurInfo.append("Silakan cek terminal/console untuk melihat hasil proses tim dapur secara detail.");
            
            javax.swing.JOptionPane.showMessageDialog(this, dapurInfo.toString(), "Info Tim Dapur", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            kitchenManager.displayKitchenProcess();
            kitchenManager.resetBatch();
        }

        currentOrder = new entity.Order();
        updateKeranjangUI();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void OrderFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderFieldActionPerformed
        // TODO add your handling code here:
        String kode = OrderField.getText().trim();
   
        if (!kode.isEmpty()) {
            tambahPesanan(kode);
            OrderField.setText("");
        }
    }//GEN-LAST:event_OrderFieldActionPerformed

    private void plus(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plus
        // TODO add your handling code here:
    String kode = OrderField.getText().trim();
    
    if (!kode.isEmpty()) {
        tambahPesanan(kode);
        OrderField.setText("");
    }
    }//GEN-LAST:event_plus

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new OrderUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BeverageButton;
    private javax.swing.JPanel BeveragePanel;
    private javax.swing.JScrollPane BeverageScroll;
    private javax.swing.JPanel DisplayPanel;
    private javax.swing.JButton FoodButton;
    private javax.swing.JPanel FoodPanel;
    private javax.swing.JScrollPane FoodScroll;
    private javax.swing.JPanel Home;
    private javax.swing.JPanel ListOrderPanel;
    private javax.swing.JPanel NavBarPanel;
    private javax.swing.JTextField OrderField;
    private javax.swing.JPanel PanelOrderFooter;
    private javax.swing.JPanel PanelOrderHeader;
    private javax.swing.JPanel ScrollBeveragePanel;
    private javax.swing.JPanel ScrollFoodPanel;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
