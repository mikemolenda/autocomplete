/* 
 * Fulfillment
 * 
 * Holds completed order data. Not persistent.
 * 
 * Northwestern University
 * CIS 419 Web Application Development, Winter 2016
 * Week 10 Assignment #5
 *
 * March 13, 2016
 *
 * Mike Molenda
 * michaelmolenda2014@u.northwestern.edu 
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