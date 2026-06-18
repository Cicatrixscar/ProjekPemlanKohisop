package kitchen;

import entity.Order;
import entity.OrderItem;
import java.util.LinkedList;

public class KitchenManager {

    private static final int CUSTOMERS_PER_BATCH = 3;

    private LinkedList<Order> customerOrders;
    private FoodKitchenQueue foodQueue;
    private BeverageKitchenStack beverageStack;

    public KitchenManager() {
        this.customerOrders = new LinkedList<>();
        this.foodQueue = new FoodKitchenQueue();
        this.beverageStack = new BeverageKitchenStack();
    }

    public void addCustomerOrder(Order order) {
        customerOrders.addLast(order);
        for (OrderItem item : order.getBeverageItems()) {
            beverageStack.push(item);
        }
        for (OrderItem item : order.getFoodItems()) {
            foodQueue.enqueue(item);
        }
    }

    public boolean isReadyToProcess() {
        return customerOrders.size() >= CUSTOMERS_PER_BATCH;
    }

    public int getCustomerCount() {
        return customerOrders.size();
    }

    public void displayKitchenProcess() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("               PROSES TIM DAPUR KOHISOP");
        System.out.printf("         (Setelah %d pelanggan terlayani)%n", customerOrders.size());
        System.out.println("=".repeat(70));

        System.out.println("\n--- ANTRIAN MAKANAN (Priority Queue - by Harga) ---");
        foodQueue.displayProcessOrder();

        System.out.println("\n--- ANTRIAN MINUMAN (Stack - Last Ordered First Served) ---");
        beverageStack.displayProcessOrder();

        System.out.println("\n" + "=".repeat(70));
    }

    public void resetBatch() {
        customerOrders.clear();
        this.foodQueue = new FoodKitchenQueue();
        this.beverageStack = new BeverageKitchenStack();
    }
}
