/* 
 * ViewCart
 * 
 * Servlet that displays the user's shopping cart.
 * Facilitates update and deletion of items.
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
import java.util.Map.*;
import java.math.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewCart extends HttpServlet {

    /**
     * Handles HTTP GET requests
     * @param   request     servlet request
     * @param   response    servlet response
     */
    @Override
    public void doGet(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Get session object from request
        HttpSession session = request.getSession();

        // Declare Cart object, will initialize from session
        Cart cart;

        // Get Inventory in order to reference item data by ID
        Map<String, Item> items = Inventory.getItems();

        // Perform read/write to session synchronously
        synchronized (session) {

            // Get shopping cart from session
            cart = (Cart)session.getAttribute("cart");

            // If no cart, create one
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            // Get itemId from request
            String itemId = request.getParameter("itemId");

            if (itemId != null) {
                // If valid itemID, user requested add item or update quantity
                // Get quantity from request
                String quantityString = request.getParameter("quantity");

                if (quantityString == null) {
                    // If no quantity parameter, user added new item
                    cart.addItem(itemId);

                } else {
                    // If quantity parameter, user requested to update quantity
                    // Convert quantityString to int
                    int quantity;
                    try {
                        quantity = Integer.parseInt(quantityString);
                    } catch(NumberFormatException nfe) {
                        // In case exception occurs, just reset quantity to 1
                        quantity = 1;
                    }

                    cart.setQuantity(itemId, quantity);

                } 
            } // If no itemId, user just requested to view cart, so move on
        } // End synchronized

        // Respond to user with HTML page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Write header of HTML response with title "Cart"
        out.println(Utilities.makeHeader("Cart"));

        // Write list of items
        synchronized (session) {
            // Get contents from cart
            ArrayList<Order> cartContents = cart.getContents();
            BigDecimal orderTotal = new BigDecimal(0);

            if (cartContents.size() == 0) {
                // If cart empty, inform user
                out.println(Utilities.apologize("empty"));
            } else {
                // Otherwise display cart contents
                // Write cart header
                out.println(
                      "  <div class=\"panel panel-primary\">\n"
                    + "    <div class=\"panel-heading text-center\">\n" 
                    + "      <h4>YOUR CART</h4>\n" 
                    + "    </div>\n"
                    + "    <div class=\"panel-body\">\n"
                    + "      <div class=\"table-responsive\">\n"
                    + "        <table class=\"table table-hover\">\n"
                    + "          <thead>\n"
                    + "            <tr>\n"
                    + "              <th>&nbsp;</th><!--image-->\n"
                    + "              <th>Item</th>\n"
                    + "              <th>Unit Cost</th>\n"
                    + "              <th>Quantity</th>\n"
                    + "              <th>Total Cost</th>\n"
                    + "            </tr>\n"
                    + "          </thead>\n"
                    + "          <tbody>");

                // Write table row for each item in cart
                for (Order cartItem : cartContents) {
                    // Get item info
                    String cartItemId = cartItem.getItemId();
                    int cartItemQty = cartItem.getQuantity();
                    Item item = items.get(cartItemId);
                    String name = item.getName();
                    String category = item.getCategory();
                    String price = item.getPrice();
                    String imgSrc = item.getImgSrc();
                    BigDecimal itemTotal = 
                            calculateItemTotal(price, cartItemQty);

                    // Add item total to grand total
                    orderTotal = orderTotal.add(itemTotal);
                    session.setAttribute("orderTotal", orderTotal);

                    // Print rows
                    out.println(
                          "            <tr>\n"
                        + "              <td><img src=\"" + imgSrc + "\" " 
                            + "class=\"img-responsive\" style=\"width:64px\" "
                            + "alt=\"" + name + "\"></td>\n"
                        + "              <td>" + name + " " + category 
                            + "</td>\n"
                        + "              <td>" + price + "</td>\n"
                        + "              <td>\n"
                        + "                <form class=\"form-inline\">\n"
                        + "                  <input type=\"hidden\" "
                            + "name=\"itemId\" value=\"" + cartItemId 
                            + "\">\n"
                        + "                  <div class="
                            + "\"form-group form-group-sm\">\n"
                        + "                    <input type=\"number\" " 
                            + "min=\"0\" max=\"999\" class=\"form-control\" " 
                            + "name=\"quantity\" value=\"" 
                            + cartItemQty + "\">\n"
                        + "                    <input type=\"submit\" " 
                            + "class=\"btn btn-info btn-sm\" "
                            + "value=\"Update\">\n"
                        + "                  </div>\n"
                        + "                </form>\n"
                        + "              </td>\n"
                        + "              <td>" + itemTotal + "</td>\n"
                        + "            </tr>");

                }

                // Write cart footer
                out.println(
                      "          </tbody>\n"
                    + "        </table>\n"
                    + "      </div>\n"
                    + "    </div>\n"
                    + "    <div class=\"panel-footer text-right\">"
                    + "      <h4>Total: $" 
                        + session.getAttribute("orderTotal") + "</h4>\n"
                    + "      <a href=\"Shop\" " 
                        + "class=\"btn btn-info\">" 
                        + "Continue shopping</a>\n"    
                    + "    <a href=\"Checkout\" " 
                        + "class=\"btn btn-success\">" 
                        + "Proceed to checkout</a>\n"
                    + "    </div>"
                    + "  </div>");
            }
        }

        //Write HTML page footer
        out.println(Utilities.makeFooter());

        // Close output stream
        out.close();
    }

    /**
     * Handles HTTP POST requests
     * @param   request     servlet request
     * @param   response    servlet response
     */
    @Override
    public void doPost(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Calculates total cost for the quantity of items ordered
     */
    public BigDecimal calculateItemTotal(String price, int qty) {
        BigDecimal itemPrice = new BigDecimal(price);
        BigDecimal itemQty = new BigDecimal(qty);
        return (itemPrice.multiply(itemQty));
    }
}