/* 
 * Fulfillment
 * 
 * Holds completed order data. Not persistent.
 */

import java.io.*;
import java.util.*;

public class Fulfillment {

    private static HashMap<String, PlacedOrder> placedOrders = 
        new HashMap<String, PlacedOrder>();

    public static void addOrder(String orderId, PlacedOrder po) {
        placedOrders.put(orderId, po);
    }

    public static void removeOrder(String orderId) {
        placedOrders.remove(orderId);
    }

    public static boolean verifyOrder(String orderId) {
        if (placedOrders.get(orderId) != null) {
            return true;
        }
            return false;
    }
}
