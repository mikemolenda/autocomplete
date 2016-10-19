/* 
 * Checkout
 * 
 * Servlet that prompts user to enter info to complete checkout.
 */

import java.io.*;
import java.util.*;
import java.util.Map.*;
import java.math.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Checkout extends HttpServlet {

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

        // Respond to user with HTML page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
      
        // Write page header with title "Checkout"
        out.println(Utilities.makeHeader("Checkout"));

        // Read and write data to session synchronously
        synchronized(session) {
            // Get cart and order total attributes from session
            Cart cart = (Cart)session.getAttribute("cart");
            BigDecimal orderTotal = 
                (BigDecimal)session.getAttribute("orderTotal");

            if (cart == null || orderTotal == null || 
                    orderTotal.compareTo(BigDecimal.ZERO) < 0) {
                // Write an error message if cart or order total is invalid
                out.println(Utilities.apologize("generic"));
            } else {
                // Check cart contents
                ArrayList<Order> cartContents = cart.getContents();

                if (cartContents.size() == 0) {
                    // Write error message if cart is empty
                    out.println(Utilities.apologize("empty"));
                } else {
                    // Write user payment form
                    out.println(
                      "  <form action=\"Confirmation\" method=\"post\" "
                        + "class=\"form-horizontal\" role=\"form\">\n"
                    + "  <div class=\"panel panel-info\">\n"
                    + "    <div class=\"panel-heading text-center\">\n"
                    + "      <h4 class=\"text-center\">Checkout Total: $"
                        + orderTotal + "</h4>\n"
                    + "    </div>\n"
                    + "    <div class=\"panel-body\">\n"
                    + "      <h4>Payment and Delivery Information</h4>\n"
                    + "      <p>Please provide your personal information:</p>\n"
                    + "      <div class=\"form-group\">\n"
                    + "        <label class=\"control-label col-sm-3\" "
                        + "for=\"name\">Name:</label>\n"
                    + "        <div class=\"col-sm-7\">\n"
                    + "          <input type=\"text\" class=\"form-control\" "
                        + "name=\"name\" required>\n"
                    + "        </div>\n"
                    + "      </div>\n"
                    + "      <div class=\"form-group\">\n"
                    + "        <label class=\"control-label col-sm-3\" "
                        + "for=\"address\">Address:</label>\n"
                    + "        <div class=\"col-sm-7\">\n"
                    + "          <textarea class=\"form-control\" rows=\"5\" "
                        + "name=\"address\" required></textarea>\n"
                    + "        </div>\n"
                    + "      </div>\n"
                    + "      <div class=\"form-group\">\n"
                    + "        <label class=\"control-label col-sm-3\" "
                        + "for=\"email\">Email:</label>\n"
                    + "        <div class=\"col-sm-7\">\n"
                    + "          <input type=\"email\" class=\"form-control\" "
                        + "name=\"email\" required>\n"
                    + "        </div>\n"
                    + "      </div>\n"
                    + "      <div class=\"form-group\">\n"
                    + "        <label class=\"control-label col-sm-3\" "
                        + "for=\"cardType\">Credit Card Type:</label>\n"
                    + "        <div class=\"col-sm-7\">\n"
                    + "          <select class=\"form-control\" "
                        + "name=\"cardType\">\n"
                    + "            <option>Visa</option>\n"
                    + "            <option>Mastercard</option>\n"
                    + "            <option>AmEx</option>\n"
                    + "            <option>Discover</option>\n"
                    + "          </select>\n"
                    + "        </div>\n"
                    + "      </div>\n"
                    + "      <div class=\"form-group\">\n"
                    + "        <label class=\"control-label col-sm-3\" "
                        + "for=\"cardNo\">Card #</label>\n"
                    + "        <div class=\"col-sm-7\">\n"
                    + "          <input type=\"text\" class=\"form-control\" "
                        + "name=\"cardNo\" required>\n"
                    + "        </div>\n"
                    + "      </div>\n"
                    + "    </div>\n"
                    + "    <div class=\"panel-footer text-right\">\n"
                    + "      <h4>Place your order for <strong>$" + orderTotal 
                        + "</strong>?</h4>\n"
                    + "      <a href=\"ViewCart\" class=\"btn btn-danger\">"
                        + "Return to edit cart</a>\n"
                    + "      <input type=\"hidden\" name=\"orderTotal\" " 
                        + "value=\"" + orderTotal + "\">");

                // Write hidden field for each item in cart
                for(int i = 0; i < cartContents.size(); i++) {
                    out.println(
                          "      <input type=\"hidden\" name=\"item" + i + "\" "
                        + "value=\"" + cartContents.get(i).getItemId() + "\">");
                }

                // Write submit button and end of form
                out.println(
                      "      <button type=\"Submit\" class=\"btn btn-success\">"
                        + "Place order</button>\n"
                    + "    </div>\n"
                    + "  </div>\n"
                    + "  </form>");
                }
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
}
