package entity;
import java.util.*;

public class Order {
    
    private List<OrderItem> items;
    private PaymentChannel selectedPayment;
    private Currency selectedCurrency;

    //Construct
    public Order() {
        this.items = new ArrayList<>();
        this.selectedPayment = null;
        this.selectedCurrency = null;
    }

    //Order
    public void addOrderItem(Menu menu, int quantity) {
        items.add(new OrderItem(menu, quantity));
    }

    public List<OrderItem> getItems() {
        return items;

    }

    public List<OrderItem> getBeverageItems() {
        List<OrderItem> beverages = new ArrayList<>();
        for (OrderItem item : items) {
            if (item.getMenu() instanceof Beverage) {
                beverages.add(item);
            }
        }
        return beverages;
    }

    public List<OrderItem> getFoodItems() {
        List<OrderItem> food = new ArrayList<>();
        for (OrderItem item : items) {
            if (item.getMenu() instanceof Food) {
                food.add(item);
            }
        }
        return food;
    }

    public void removeOrderItem(String codeMenu {
        
    })
}
