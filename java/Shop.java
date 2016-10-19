/* 
 * Shop
 * 
 * Servlet that displays inventory to user. User may add items to their cart.
 */

import java.io.*;
import java.util.*;
import java.util.Map.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Shop extends HttpServlet {

    /**
     * Handles HTTP GET requests
     * @param   request     servlet request
     * @param   response    servlet response
     */
    @Override
    public void doGet(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        //Get session object from request
        HttpSession session = request.getSession();

        // Respond to user with HTML page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
      
        // Write header of HTML response with title "Shop"
        out.println(Utilities.makeHeader("Shop"));

        // Search Form
        out.println(
              "  <div class=\"panel panel-info\">\n"
            + "    <div class=\"panel-heading text-center\">\n"
            + "      <h4>Search for a deal</h4>\n"
            + "    </div>\n"
            + "    <div class=\"panel-body\">\n");

        // Include search form
        String url = "includes/search.jsp";
        request.getRequestDispatcher(url).include(request, response);

        out.println("    </div>\n" + "  </div>");

        // Browse Panels
        out.println(
              "  <div class=\"page-header text-center\">\n"
            + "    <h1>Browse by category</h1>\n"
            + "  </div>");

        // Print panels for each category, item
        ArrayList<String> categories = Inventory.getCategories();

        for (String cat : categories) {
            // Get HashMap of all items in current category
            Map<String, Item> itemPanels = Inventory.getItemsByCategory(cat);

            // Print category header
            out.println(
                  "  <div class=\"col-sm-12 text-center\">\n"
                + "    <h2>" + cat + "s</h2>\n"
                + "  </div>");
            
            // Print panel for each item in category
            for (Entry<String, Item> entry : itemPanels.entrySet()) {
                printItemPanel(out, entry);
            }
        }

        // Write HTML page footer
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
     * Prints panel for item, including item details and "Add to Cart" form
     */
    public void printItemPanel(PrintWriter out, Entry<String, Item> entry) {

        // Get individual entry attributes
        String itemId = entry.getKey();
        String name = entry.getValue().getName();
        String category = entry.getValue().getCategory();
        String price = entry.getValue().getPrice();
        String imgSrc = entry.getValue().getImgSrc();

        out.println(
              "  <div class=\"col-sm-4\">\n"
            + "    <div class=\"panel panel-primary\">\n"
            + "      <div class=\"panel-heading\">" + name + "</div>\n"
            + "      <div class=\"panel-body\">\n"
            + "        <img src=\"" + imgSrc 
                + "\" class=\"img-responsive\" style=\"width:100%\" " 
                + "alt=\"" + name + " " + category + "\">\n"
            + "      </div>\n"
            + "      <div class=\"panel-footer\">\n"
            + "        <form action=\"ViewItem\">\n"
            + "          <div class=\"form-group\">\n"
            + "            <p class=\"form-control-static\">" 
                + name + " " + category + "</p>\n"
            + "            <input type=\"hidden\" name=\"itemId\" " 
                + "value=\"" + itemId + "\">\n"
            + "            <input type=\"submit\" " 
                + "class=\"btn btn-success btn-block\" value=\"" 
                + price + " View Item\">\n"
            + "          </div>\n"
            + "        </form>\n"
            + "      </div>\n"
            + "    </div>\n"
            + "  </div>");

    }
}
