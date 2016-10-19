/* 
 * AutoComplete
 * 
 * Responds with inventory data for search autocompletion.
 *
 * "complete" & "lookup" actions borrowed from CIS419 source code (ajax-my-app)
 */

import java.io.*;
import java.util.*;
import java.util.Map.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AutoComplete extends HttpServlet {

    private Map<String, Item> items = Inventory.getItems();

    /**
     * Handles HTTP GET requests
     * @param   request     servlet request
     * @param   response    servlet response
     */
    @Override
    public void doGet(
            HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String action = request.getParameter("action");  // action to perform
        String targetId = request.getParameter("id");  // value of completeField

        StringBuffer sb = new StringBuffer();

        //TODO If action or targetId null, redirect to error

        // Format completeField value for ease of matching
        if (targetId != null) {
            targetId = targetId.trim().toLowerCase();
        }

        boolean itemsAdded = false;  // flag whether an item was added to table

        // complete action populates completeTable data
        if (action.equals("complete")) {

            if (!targetId.equals("")) {
                // If completeField is not empty, check against inventory
                for (Entry<String, Item> entry : items.entrySet()) {
                    Item item = entry.getValue();
                    String name = item.getName();
                    String cat = item.getCategory();
                    String longName = name + " " + cat;

                    // Check against: name, category, name + category
                    if (name.toLowerCase().startsWith(targetId) || 
                            cat.toLowerCase().startsWith(targetId) || 
                            longName.toLowerCase().startsWith(targetId)) {
                        // If match found, add data to XML response
                        sb.append("<item>");
                        sb.append("<id>" + entry.getKey() + "</id>");
                        sb.append("<name>" + name + "</name>");
                        sb.append("<category>" + cat + "</category>");
                        sb.append("</item>");
                        itemsAdded = true;
                    }
                }
            } 

            // If items were added, respond with XML, otherwise SC 204
            if (itemsAdded) {
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                PrintWriter out = response.getWriter();
                out.write("<items>" + sb.toString() + "</items>");
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } // end action complete

        // lookup action forwards request to appropriate item page
        if (action.equals("lookup")) {

            if (targetId != null) {
                
                String key = targetId.trim();
                String url = "ViewItem?itemId=" + key;

                if (items.containsKey(key)) { 
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }
        } // end action lookup

    } // end doGet
}
