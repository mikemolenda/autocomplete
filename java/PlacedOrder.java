/* 
 * PlacedOrder
 * 
 * Represents an order successfully placed by a customer.
 */

import java.io.*;
import java.util.*;

public class PlacedOrder implements java.io.Serializable {

    private String orderId;
    private Cart submittedOrders;
    private String customerName;
    private String customerAddress;
    private Calendar orderDate;
    private Calendar cancelDeadline;

    public PlacedOrder() {
        setOrderId(null);
        setSubmittedOrders(null);
        setCustomerName(null);
        setCustomerAddress(null);
        setOrderDate(null);
        setCancelDeadline(null);
    }

    public PlacedOrder(String id, Cart cart, String name, String address, 
            Calendar date, Calendar deadline) {
        setOrderId(id);
        setSubmittedOrders(cart);
        setCustomerName(name);
        setCustomerAddress(address);
        setOrderDate(date);
        setCancelDeadline(deadline);
    }

    public synchronized void setOrderId(String id) {
        orderId = id;
    }
    
    public synchronized String getOrderId() {
        return (orderId);
    }

    public synchronized Cart getSubmittedOrders() {
        return (submittedOrders);
    }

    public synchronized void setSubmittedOrders(Cart cart) {
        submittedOrders = cart;
    }

    public synchronized String getCustomerName() {
        return (customerName);
    }

    public synchronized void setCustomerName(String name) {
        customerName = name;
    }

    public synchronized String getCustomerAddress() {
        return (customerAddress);
    }

    public synchronized void setCustomerAddress(String address) {
        customerAddress = address;
    }

    public synchronized Calendar getOrderDate() {
        return (orderDate);
    }

    public synchronized void setOrderDate(Calendar date) {
        orderDate = date;
    }

    public synchronized Calendar getCancelDeadline() {
        return (cancelDeadline);
    }

    public synchronized void setCancelDeadline(Calendar deadline) {
        cancelDeadline = deadline;
    }
}
