/* 
 * Utilities
 * 
 * Provides utility methods for common tasks like printing HTML elements
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

public class Utilities {

    /**
     * Returns a page header for HTML response with a custom title.
     * Includes both HTML head element, and the website banner.
     * @param   title   the title of the current page
     */
    public static String makeHeader(String title) {

        // Bootstrap front-end framework used for styling page
        String bootstrapCSS = "http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/" 
            + "css/bootstrap.min.css";  // URL for Bootstrap CSS
        String bootstrapJS = "http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/" 
            + "js/bootstrap.min.js";    // URL for Bootstrap JavaScript
        String jQuery = "https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/" 
            + "jquery.min.js";          // URL for jQuery

        // Include autocomplete JavaScript if Shop page
        String autocompleteJS = (title.equals("Shop") ? 
            "  <script type=\"text/javascript\" src=\"js/autocomplete.js\"></script>" : "");
        String onload = 
                (title.equals("Shop") ? " onload=\"init()\"" : "");

        return (
              "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "  <title>BestDeal: " + title + "</title>\n"
            + "  <meta charset=\"utf-8\">\n"
            + "  <meta name=\"viewport\" "
                + "content=\"width=device-width, initial-scale=1\">\n"
            + "  <link rel=\"stylesheet\" href=\"" + bootstrapCSS + "\">\n"
            + "  <script src=\"" + jQuery + "\"></script>\n"
            + "  <script src=\"" + bootstrapJS + "\"></script>\n"
            + "  <link rel=\"stylesheet\" type=\"text/css\" " 
                + "href=\"css/style.css\">\n"
            + autocompleteJS +"\n"
            + "</head>\n"
            + "<body" + onload + ">\n"
            + "<div class=\"jumbotron\">\n"
            + "  <div class=\"container text-center\">\n"
            + "    <h1>BestDeal</h1>\n"
            + "    <p>\"A real website selling real merchandise\"</p>\n"
            + "  </div>\n"
            + "</div>\n"
            + "<nav class=\"navbar navbar-inverse\">\n"
            + "  <div class=\"container-fluid\">\n"
            + "    <div class=\"navbar-header\">\n"
            + "      <button type=\"button\" class=\"navbar-toggle\" "
                + "data-toggle=\"collapse\" data-target=\"#myNavbar\">\n"
            + "        <span class=\"icon-bar\"></span>\n"
            + "        <span class=\"icon-bar\"></span>\n"
            + "        <span class=\"icon-bar\"></span>\n"
            + "      </button>\n"
            + "    </div>\n"
            + "    <div class=\"collapse navbar-collapse\" id=\"myNavbar\">\n"
            + "      <ul class=\"nav navbar-nav\">\n"
            + "        <li><a href=\"Shop\">Shop</a></li>\n"
            + "        <li><a href=\"ViewCart\"><span "
                + "class=\"glyphicon glyphicon-shopping-cart\"></span>"
                + " View Cart</a></li>\n"
            + "      </ul>\n"
            + "    </div>\n"
            + "  </div>\n"
            + "</nav>\n"
            + "<div class=\"container\">");
    }

    /**
     * Returns a page footer for HTML response with a custom title.
     * Provides closing tags for container div, body, and html elements.
     */
    public static String makeFooter() {
        return ("</div><!--end container-->\n</body>\n</html>");
    }

    /**
     * Generates apology message if something goes wrong
     * @param   condition   String literal specifying the error condition
     */
    public static String apologize(String condition) {

        // Specific conditions return specialized apologies
        if (condition.equals("empty")) {             
            return (
                  "  <div class=\"panel panel-danger text-center\">\n"
                + "    <div class=\"panel-heading\">\n"
                + "      <h4>NO ITEMS IN CART</h4>\n"
                + "    </div>\n"
                + "    <div class=\"panel-body\">\n"
                + "      <p>Your cart is currently empty.</p>\n"
                + "      <a href=\"Shop\" class=\"btn btn-danger\">" 
                    + "Continue shopping?</a>\n"
                + "    </div>\n"
                + "  </div>");
        }

        // Any other conditions simply return a generic apology
        return (
              "  <div class=\"panel panel-danger text-center\">\n"
            + "    <div class=\"panel-heading\">\n"
            + "      <h4>OOPS...</h4>\n"
            + "    </div>\n"
            + "    <div class=\"panel-body\">\n"
            + "      <p>We're sorry, something appears to have gone wrong." 
                + "<br>\n"
            + "      <em>No charges have been applied to your card.</em></p>\n"
            + "      <a href=\"Shop\" class=\"btn btn-default\">" 
                + "Click here to start over</a>\n"
            + "    </div>\n"
            + "  </div>");
    }
}