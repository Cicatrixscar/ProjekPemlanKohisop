package kitchen;

import entity.Food;
import entity.OrderItem;
import java.util.Comparator;
import java.util.PriorityQueue;

// Priority Queue makanan: harga tertinggi diproses pertama
public class FoodKitchenQueue {

    private PriorityQueue<OrderItem> foodQueue;

    public FoodKitchenQueue() {
        this.foodQueue = new PriorityQueue<>(
            Comparator.comparingDouble((OrderItem item) -> item.getPrice()).reversed()
        );
    }

    public void enqueue(OrderItem item) {
        if (item != null && item.getMenu() instanceof Food) {
            foodQueue.add(item);
        }
    }

    public OrderItem dequeue() { return foodQueue.poll(); }
    public OrderItem peek() { return foodQueue.peek(); }
    public boolean isEmpty() { return foodQueue.isEmpty(); }
    public int size() { return foodQueue.size(); }

    public void displayProcessOrder() {
        if (foodQueue.isEmpty()) {
            System.out.println("   (Tidak ada pesanan makanan)");
            return;
        }

        PriorityQueue<OrderItem> temp = new PriorityQueue<>(
            Comparator.comparingDouble((OrderItem item) -> item.getPrice()).reversed()
        );
        temp.addAll(foodQueue);

        System.out.println("\n   [ TIM DAPUR - MAKANAN ] (Urutan Proses by Harga)");
        System.out.printf("   %-5s %-35s %-12s %-8s%n", "No.", "Nama Makanan", "Harga", "Qty");
        System.out.println("   " + "-".repeat(63));
        int no = 1;
        while (!temp.isEmpty()) {
            OrderItem item = temp.poll();
            String harga = String.format("Rp%,.0f", item.getPrice());
            System.out.printf("   %-5d %-35s %-12s %-8d%n",
                    no++, item.getName(), harga, item.getQuantity());
        }
    }
}
