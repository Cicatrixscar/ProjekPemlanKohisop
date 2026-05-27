# Catatan Perubahan Kode - KohiSop II

## MenuRepository.java
- `ArrayList<Menu>` diganti `LinkedList<Menu>`
- Ditambah method `addMenuSorted(Menu)`: insertion sort berdasarkan harga, dengan Makanan selalu di depan Minuman dalam LinkedList (sesuai PDF: makanan dulu, kemudian minuman)
- `displayAllMenu()`: loop LinkedList dua kali – iterasi pertama cetak Food, iterasi kedua cetak Beverage

## Order.java
- `ArrayList<OrderItem>` diganti `LinkedList<OrderItem>`
- Ditambah `MAX_BEVERAGE_TYPES = 5` dan `MAX_FOOD_TYPES = 5` (sesuai PDF: maks 5 jenis berbeda per kategori)
- Ditambah `isMaxTypesReached(Menu)`: cek apakah batas jenis sudah penuh
- Ditambah `isAlreadyOrdered(String)`: cek apakah menu sudah ada di pesanan (agar update qty, bukan dianggap jenis baru)
- Ditambah `getBeverageTypes()` dan `getFoodTypes()`
- Ditambah `displayOrderTable()`: tampilkan tabel pesanan real-time saat input menu (sesuai PDF: tabel Kode, Nama, Harga, Kuantitas)
- `removeOrderItem()` menggunakan `Iterator` (best practice untuk remove dari LinkedList saat iterasi)

## Menu.java
- Ditambah dua abstract method: `setTaxFree(boolean)` dan `isTaxFree()` agar bisa dipanggil secara polimorfis dari `KohiSopApplication`

## Beverage.java
- Ditambah field `taxFree` (default false)
- `calculateTax()` mengembalikan 0.0 jika `taxFree == true` (sesuai PDF: bebas pajak jika kode member mengandung "A")

## Food.java
- Sama seperti Beverage.java

## Invoice.java
- Ditambah field: `memberCode`, `memberName`, `pointsBefore`, `pointsEarned`, `pointsAfter`, `pointsDeduction`, `hasMember`, `isTaxFree`
- Ditambah `setMemberInfo(...)`: dipanggil setelah membership diproses, men-trigger `calculateTotals()` ulang
- `calculateTotals()`: ditambah pengurangan `pointsDeduction` sebelum hitung final
- `printInvoice()`: ditambah blok INFO MEMBER dan keterangan bebas pajak pada baris pajak tiap item

## Member.java (baru)
- `memberCode`: 6 karakter dari set {A-F, 0-9}, di-generate acak
- `isTaxFree()`: true jika `memberCode.contains("A")`
- `isDoublePoints()`: true jika `memberCode.contains("A")`
- `calculateEarnedPoints(double)`: 1 poin per 10 IDR, digandakan jika double points

## MemberRepository.java (baru)
- `LinkedList<Member>` sebagai database member
- `addMember(String)`: buat Member baru, addLast ke LinkedList
- `findByCode(String)`: cari linear di LinkedList

## FoodKitchenQueue.java (baru)
- `PriorityQueue<OrderItem>` dengan Comparator descending by harga
- Makanan harga tertinggi = diproses pertama (sesuai PDF)

## BeverageKitchenStack.java (baru)
- `ArrayDeque<OrderItem>` dipakai sebagai Stack (LIFO)
- `push()` → `beverageStack.push()`, `pop()` → `beverageStack.poll()`
- Minuman yang terakhir di-push = pertama keluar (Last-Ordered-First-Served)

## KitchenManager.java (baru)
- Menyimpan pesanan dari tiap pelanggan di `LinkedList<Order>`
- `addCustomerOrder(Order)`: push semua minuman ke Stack, enqueue semua makanan ke PriorityQueue
- `isReadyToProcess()`: true jika sudah 3 pelanggan
- `resetBatch()`: kosongkan semua setelah batch diproses

## KohiSopApplication.java
- Ditambah field: `memberRepository`, `kitchenManager`, `currentMember`
- `start()` jadi satu sesi (loop di `main()` sekarang yang mengulang per pelanggan)
- `processMembership()`: pilih login/daftar/tamu
- `processExistingMember()`: cari member by kode
- `processNewMember()`: daftar member baru
- `applyTaxFreeToOrder()`: set semua item.getMenu().setTaxFree(true)
- `generateFinalInvoice()`: hitung poin earned, proses penggunaan poin (hanya IDR), panggil setMemberInfo
- Setelah invoice: `kitchenManager.addCustomerOrder()`, tampilkan dapur jika sudah 3 pelanggan
