/* 
 * Cart
 * 
 * Methods to access and modify the array of the user's ordered items. 
 */

import java.util.*;
import java.math.*;

public class Cart {

    private ArrayList<Order> contents; // Dynamic array of orders

    public Cart() {
        contents = new ArrayList<Order>();
    }

    public ArrayList<Order> getContents() {
        return(contents);
    }

    /**
     * Adds a single item to the cart.
     * @param   itemId      the unique ID of the item to be added
     */
    public synchronized void addItem(String itemId) {
        
        Order order;

        // Check whether item is already in cart
        for (Order itemOrder : contents) {
            order = itemOrder;
            if (order.getItemId().equals(itemId)) {
                // If item already in cart, ignore request
                // This prevents user from increment qty by hitting refresh
                return;
            }
        }
        // If item not in cart, create an entry for it
        order = new Order(itemId);
        contents.add(order);
    }

    /**
     * Sets the quantity of the specified item to the specified value.
     * Removes the order if the quantity is zero or less.
     * @param   itemId      the unique ID of the item to be updated
     * @param   quantity    the quantity to set
     */
    public synchronized void setQuantity(String itemId, int quantity) {
        
        Order order;

        // Check that the specified item is in the cart
        for (Order itemOrder : contents) {
            order = itemOrder;
            if (order.getItemId().equals(itemId)) {
                if (quantity > 0) {
                    // If item in cart and quntity greater than zero, set its quantity
                    order.setQuantity(quantity);
                } else {
                    // If quantity zero or less, remove order from cart
                    contents.remove(order);
                }
                return;
            }
        }
        
        // If item not in cart, add it (if qty more than zero)
        if (quantity > 0) {
            addItem(itemId);
        } else {
            // If qty zero, ignore request
            // Otherwise user can add an item with qty 0
            return;
        }
    }

    /**
     * Empties the contents of the cart
     */
    public synchronized void emptyContents() {
        contents.clear();
    }
}
