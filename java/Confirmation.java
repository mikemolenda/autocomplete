/* 
 * Confirmation
 * 
 * Servlet that provides user with a confirmation page for their order
 */

import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.math.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Confirmation extends HttpServlet {

    /**
     * Handles HTTP POST requests
     * @param   request     servlet request
     * @param   response    servlet response
     */
    @Override
    public void doPost(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get session object from request
        HttpSession session = request.getSession();

        // Put request parameters into hashmap
        Enumeration<String> requestParamNames = request.getParameterNames();
        Map<String, String> responseParams = new HashMap<String, String>();

        while (requestParamNames.hasMoreElements()) {
            String key = requestParamNames.nextElement();
            responseParams.put(key, request.getParameter(key));
        }

        // Get date values from session
        Calendar orderDate = null;
        Calendar deliveryDate = null;
        Calendar cancelDeadline = null;

        synchronized(session) {

            orderDate = (Calendar)session.getAttribute("orderDate");
            deliveryDate = (Calendar)session.getAttribute("orderDate");
            cancelDeadline = (Calendar)session.getAttribute("orderDate");

        } // end synchronized

        // Set date values in session 
        // Only set dates once, otherwise delivery date changes on refresh
        if (orderDate == null || deliveryDate == null || 
            cancelDeadline == null) {

            // Set order date to current date
            orderDate = new GregorianCalendar();
            
            // Set the delivery date to two weeks after the order date
            deliveryDate = new GregorianCalendar();
            deliveryDate.add(Calendar.DATE, 14);
            
            // Set cancellation deadline to 5 business days before delivery
            // Holidays don't count in this scenario
            cancelDeadline = new GregorianCalendar();
            cancelDeadline.add(Calendar.DATE, 14);
            int businessDays = 0;
           
            // Subtract at least one day 5 times
            for(int i = 0; i <= 5; i++) {
                // Always subtract at least one day
                businessDays -= 1;

                // If date is now Sun, subtract 2 additional days 
                if (cancelDeadline.get(Calendar.DAY_OF_WEEK) 
                        - businessDays == 1) {
                    businessDays -= 2;
                    continue;
                }
                // If date is now Sat, subtract 1 additional day
                if (cancelDeadline.get(Calendar.DAY_OF_WEEK) 
                        - businessDays == 7) {
                    businessDays -= 1;
                    continue;
                }
            }

            cancelDeadline.add(Calendar.DATE, businessDays);

            synchronized (session) {

                session.setAttribute("orderDate", orderDate);
                session.setAttribute("deliveryDate", deliveryDate);
                session.setAttribute("cancelDeadline", cancelDeadline);

            } // end synchronized

        } 

            // Convert dates to strings
            SimpleDateFormat sdf = new SimpleDateFormat("EEEEE, MMMMM d, yyyy");
            
            sdf.setCalendar(orderDate);
            String orderDateString = sdf.format(orderDate.getTime());

            sdf.setCalendar(deliveryDate);
            String deliveryDateString = sdf.format(deliveryDate.getTime());

            sdf.setCalendar(cancelDeadline);
            String cancelDeadlineString = sdf.format(cancelDeadline.getTime());

            // Put values in hashmap so we only need to synchronize once
            responseParams.put("orderDateString", orderDateString);
            responseParams.put("deliveryDateString", deliveryDateString);
            responseParams.put("cancelDeadlineString", cancelDeadlineString);
        

        // Get Inventory in order to reference item data by ID
        Map<String, Item> items = Inventory.getItems();

        // Get order data
        Integer hash = new Integer(Math.abs(responseParams.hashCode()));
        String confNo = hash.toString();
        Cart cart;
        synchronized(session) {
            cart = (Cart)session.getAttribute("cart");
        }
        String name = responseParams.get("name");
        String address = responseParams.get("address");

        // Store order in hashmap
        Fulfillment.addOrder(confNo, new PlacedOrder(confNo, cart, name, 
            address, orderDate, cancelDeadline));

        // Get list of items ordered
        ArrayList<String> itemNames = new ArrayList<String>();

        for (int i = 0; i < responseParams.size(); i++) {
            if (responseParams.containsKey("item" + i) 
                    && responseParams.get("item" + i) != null) {
                String itemId = responseParams.get("item" + i);
                Item item = items.get(itemId);
                String itemName = item.getName() + " " + item.getCategory();
                itemNames.add(itemName);
            }
        }

        // Respond to user with HTML page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Write page header with title "Order Confirmation"
        out.println(Utilities.makeHeader("Order Confirmation"));

        // Write header of order confirmation
        out.println(
              "  <div class=\"panel panel-success text-center\">\n"
            + "    <div class=\"panel-heading\">\n"
            + "      <h4>THANK YOU!</h4>\n"
            + "    </div>\n"
            + "    <div class=\"panel-body\">\n"
            + "      <h4>Your confirmation number:</h4>");

        // Use hash code of order submission as confirmation number
        out.println("      " + confNo);

        // Write items ordered
        out.println("      <h4>Items ordered:</h4>");

        for (String itemName : itemNames) {
            out.println("      " + itemName + "<br>");
        }

        // Continue writing confirmation
        out.println(
              "      <h4>Payment method</h4>\n"
            + "      $" + responseParams.get("orderTotal") + " charged to " 
                + responseParams.get("cardType") + " (not really)\n"
            + "      <h4>Ship to:</h4>\n"
            + "      " + name + "<br>\n"
            + "      " + address
            + "      <h4>Estimated delivery date:</h4>\n" 
            + "      " + responseParams.get("deliveryDateString")
            + "      <hr>\n"+ "      <p>\n" 
            + "        A copy of this information has been sent to "
                + responseParams.get("email") + " (not really).<br>\n"
            + "        You may cancel this order at any time before " 
                + responseParams.get("cancelDeadlineString")
                + " (five business days before the scheduled delivery date) "
                + "by visiting the <a href=\"CancelOrder\">"
                + "<strong>Order Cancellation Page</strong></a>\n" 
            + "      </p>");

        // Write confirmation footer
        out.println(
              "    </div>\n"
            + "    <div class=\"panel-footer\">\n"
            + "      <a href=\"Shop\" class=\"btn btn-primary\">" 
                + "Shop some more?</a>\n"
            + "    </div>\n"
            + "  </div>");

        //Write HTML page footer
        out.println(Utilities.makeFooter());

        // Close output stream
        out.close();

        // Reset session attributes
        synchronized(session) {  

            session.invalidate();

        } // end synchronized
    }

    /**
     * Handles HTTP GET requests
     * @param   request     servlet request
     * @param   response    servlet response
     */
    @Override
    public void doGet(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Respond to user with error message
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
