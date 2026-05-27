package kitchen;

import entity.Beverage;
import entity.OrderItem;
import java.util.ArrayDeque;
import java.util.Deque;

// Stack minuman: Last-Ordered-First-Served (LIFO)
public class BeverageKitchenStack {

    private Deque<OrderItem> beverageStack;

    public BeverageKitchenStack() {
        this.beverageStack = new ArrayDeque<>();
    }

    public void push(OrderItem item) {
        if (item != null && item.getMenu() instanceof Beverage) {
            beverageStack.push(item);
        }
    }

    public OrderItem pop() { return beverageStack.poll(); }
    public OrderItem peek() { return beverageStack.peek(); }
    public boolean isEmpty() { return beverageStack.isEmpty(); }
    public int size() { return beverageStack.size(); }

    public void displayProcessOrder() {
        if (beverageStack.isEmpty()) {
            System.out.println("   (Tidak ada pesanan minuman)");
            return;
        }

        Deque<OrderItem> temp = new ArrayDeque<>(beverageStack);

        System.out.println("\n   [ TIM DAPUR - MINUMAN ] (Urutan Proses: Terakhir Dipesan = Pertama Diproses)");
        System.out.printf("   %-5s %-38s %-12s %-8s%n", "No.", "Nama Minuman", "Harga", "Qty");
        System.out.println("   " + "-".repeat(66));
        int no = 1;
        while (!temp.isEmpty()) {
            OrderItem item = temp.poll();
            String harga = String.format("Rp%,.0f", item.getPrice());
            System.out.printf("   %-5d %-38s %-12s %-8d%n",
                    no++, item.getName(), harga, item.getQuantity());
        }
    }
}
