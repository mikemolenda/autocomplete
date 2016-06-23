/* 
 * ViewItem
 * 
 * Servlet that displays the item from the user's search. User can add the item 
 * to their cart, or return to search.
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
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewItem extends HttpServlet {

    /**
     * Handles HTTP GET requests
     * @param   request     servlet request
     * @param   response    servlet response
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {

        //Get session object from request
        HttpSession session = request.getSession();

        // Get item data
        String itemId = request.getParameter("itemId"); 
        Item item = Inventory.getItem(itemId);
        String name = item.getName();
        String category = item.getCategory();
        String longName = name + " " + category;
        String price = item.getPrice();
        String imgSrc = item.getImgSrc();
        String description = generateDescription(name, category);

        // Respond to user with HTML page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
      
        // Write header of HTML response with title of item
        out.println(Utilities.makeHeader(name + " " + category));

        // Display page
        out.println(
              "<div class=\"col-sm-6\">\n"
            + "  <img src=\"" + imgSrc + "\" class=\"img-responsive\" " 
                + "style=\"width:100%\" alt=\"" + longName + "\">\n"
            + "</div>\n"
            + "<div class=\"col-sm-6\">\n"
            + "  <div class=\"page-header text-center\">\n"
            + "    <h2>" + longName + "</h2>\n"
            + "  </div>\n"
            + description
            + "  <hr>\n"
            + "  <form action=\"ViewCart\">\n"
            + "    <div class=\"form-group\">\n"
            + "      <input type=\"hidden\" name=\"itemId\" " 
                + "value=\"" + itemId + "\">\n"
            + "      <input type=\"submit\" " 
                + "class=\"btn btn-success btn-block\" " 
                + "value=\"" + price + " Add to Cart\">\n"
            + "    </div>\n"
            + "  </form>\n"
            + "  <a href=\"Shop\" class=\"btn btn-info btn-block\">"
                + "Continue Shopping</a>\n"
            + "</div>");

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
     * Generates item description, based on category
     */
    private String generateDescription(String name, String category) {
        String description = "No description available";

        if (category.equals("Tablet")) {
            description = (
                  "  <p>With the " + name + " tablet, you can covertly check " 
                    + "Facebook, but look like you're doing important work. " 
                    + "Also works great as a cutting board.</p>\n"
                + "  <h4>Features:</h4>\n"
                + "  <ul>\n"
                + "    <li>64GB Memory</li>\n"
                + "    <li>Stylus</li>\n"
                + "    <li>Super-reflective screen</li>\n"
                + "  </ul>\n");
        }

        if (category.equals("Phone")) {
            description = (
                  "  <p>The " + name + " is the best phone for annoying people " 
                    + "by having loud conversations on the bus or train.</p>\n"
                + "  <h4>Features:</h4>\n"
                + "  <ul>\n"
                + "    <li>16GB Memory</li>\n"
                + "    <li>Buttons</li>\n"
                + "    <li>Angry Birds</li>\n"
                + "  </ul>\n");
        }

        if (category.equals("HDTV")) {
            description = (
                  "  <p>There is no better way to spend hours on end sitting " 
                    + "silently with your loved ones than with a " + name 
                    + " HDTV.</p>\n"
                + "  <h4>Features:</h4>\n"
                + "  <ul>\n"
                + "    <li>40-inch full HD display</li>\n"
                + "    <li>Color</li>\n"
                + "    <li>UHF/VHF</li>\n"
                + "  </ul>\n");
        }

        if (category.equals("Laptop")) {
            description = (
                  "  <p>The revolutionary " + name + " laptop is like a " 
                    + "computer, but you can take it with you wherever you go." 
                    + "</p>\n"
                + "  <h4>Features:</h4>\n"
                + "  <ul>\n"
                + "    <li>Intel i7 processor</li>\n"
                + "    <li>Windows 10</li>\n"
                + "    <li>QWERTY keyboard</li>\n"
                + "  </ul>\n");
        }

        return description;
    }
}