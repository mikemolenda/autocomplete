/* 
 * Order
 * 
 * Associates the itemId and the quantity ordered for shopping cart.
 * Provides methods for accessing data, and for calculating totals.
 */

import java.math.*;

public class Order {

    private String itemId;
    private int quantity;

    public Order(String itemId) {
        setItemId(itemId);
        setQuantity(1);
    }

    public String getItemId() {
        return(itemId);
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return(quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Increments the quantity ordered by n
     */
    public void incrementQty(int n) {
        setQuantity(getQuantity() + n);
    }

    /**
     * Sets item quantity to zero, effectively cancelling the order
     */
    public void remove() {
        setQuantity(0);
    }  
}
