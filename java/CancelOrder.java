/* 
 * CancelOrder
 * 
 * Servlet that allows customer to cancel an order by providing the 
 * confirmation number they received after checkout. 
 */

import java.io.*;
import java.util.*;
import java.util.Map.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CancelOrder extends HttpServlet {

    /**
     * Handles HTTP GET requests.
     * User arrives at page by clicking cancel order link.
     * @param   request     servlet request
     * @param   response    servlet response
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {

        // Respond to user with HTML page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
      
        // Write header of HTML response with title "Cancel Order"
        out.println(Utilities.makeHeader("Cancel Order"));

        // Display cancellation form
        out.println(
              "  <form action=\"CancelOrder\" method=\"post\">\n"
            + "    <div class=\"panel panel-warning text-center\">\n"
            + "      <div class=\"panel-heading\">\n"
            + "        <h4>ORDER CANCELLATION</h4>\n"
            + "      </div>\n"
            + "      <div class=\"panel-body\">\n"
            + "        <p>\n"
            + "          Enter confirmation number of order to cancel.<br>\n"
            + "          <strong>This action cannot be undone!</strong><br>\n"
            + "        </p>\n"
            + "        <div class=\"form-group\">\n"
            + "            <input type=\"text\" class=\"form-control\" " 
                + "name=\"orderNo\" placeholder=\"Confirmation #\" required>\n"
            + "        </div>\n"
            + "        <button type=\"Submit\" class=\"btn btn-danger\">" 
                + "Permanently Cancel Order</button>\n"
            + "      </div>\n"
            + "    </div>\n"
            + "  </form>");

        //Write HTML page footer
        out.println(Utilities.makeFooter());

        // Close output stream
        out.close();
    }

    /**
     * Handles HTTP POST requests.
     * User arrives at page by submitting order cancellation.
     * @param   request     servlet request
     * @param   response    servlet response
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {

        //Get session object from request
        HttpSession session = request.getSession();
        String orderNo = request.getParameter("orderNo").trim();

        // Check status before remove
        boolean before = Fulfillment.verifyOrder(orderNo);

        // Process remove
        Fulfillment.removeOrder(orderNo);

        // Check status after remove
        boolean after = Fulfillment.verifyOrder(orderNo);

        // If before true and after false, remove was successful
        boolean success;

        if (before && !after) {
            success = true;
        } else {
            success = false;
        }

        // Respond to user with HTML page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
      
        // Write header of HTML response with title "Cancel Order"
        out.println(Utilities.makeHeader("Cancel Order"));

        // Display confirmation or error
        out.println(
              "  <div class=\"panel panel-warning text-center\">\n"
            + "      <div class=\"panel-heading\">\n"
            + "        <h4>ORDER " + (success ? "" : "NOT ") + "CANCELLED</h4>\n"
            + "      </div>\n"
            + "      <div class=\"panel-body\">\n"
            + "        <p>\n"
            + "          Your order <strong># " + orderNo + "</strong> " 
                + (success ? "was cancelled." : "could not be cancelled. " 
                    + "Please <a href=\"CancelOrder\">try again</a>, " 
                    + "or contact customer service.") + "\n"
            + "        </p>\n"
            + "        <a href=\"Shop\" class=\"btn btn-default\">" 
                + "Continue shopping?</a>\n"
            + "      </div>\n"
            + "  </div>");

        //Write HTML page footer
        out.println(Utilities.makeFooter());

        // Close output stream
        out.close();
    }
}
