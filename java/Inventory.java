/*
 * Inventory
 * 
 * Stores and retrieves items by id in a HashMap.
 * Supersedes the role that a database would perform in a real-world 
 * application, for the sake of this exercise.
 */

import java.util.*;
import java.util.Map.*;

public class Inventory {

    // HashMap in which data is to be stored
    private static Map<String, Item> items = new HashMap<String, Item>() {{
        put("ph001", new Item("Droid MAXX",
                                    "Phone",
                                    "99.99",
                                    "img/droid-maxx.jpg"));
        put("ph002", new Item("Moto X",
                                    "Phone",
                                    "129.99",
                                    "img/moto-x.jpg"));
        put("ph003", new Item("iPhone 5S",
                                    "Phone",
                                    "199.99",
                                    "img/iphone-5s.jpg"));
        put("ph004", new Item("iPhone 5C",
                                    "Phone",
                                    "149.99",
                                    "img/iphone-5c.jpg"));
        put("ph005", new Item("Galaxy S3",
                                    "Phone",
                                    "149.99",
                                    "img/galaxy-s3.jpg"));
        put("ph006", new Item("Galaxy S4",
                                    "Phone",
                                    "189.99",
                                    "img/galaxy-s4.jpg"));
        put("tb001", new Item("Kindle",
                                    "Tablet",
                                    "49.99",
                                    "img/kindle.jpg"));
        put("tb002", new Item("Nexus",
                                    "Tablet",
                                    "99.99",
                                    "img/nexus.jpg"));
        put("tb003", new Item("Surface",
                                    "Tablet",
                                    "299.99",
                                    "img/surface.jpg"));
        put("tb004", new Item("Galaxy",
                                    "Tablet",
                                    "199.99",
                                    "img/galaxy-tab.jpg"));
        put("tb005", new Item("iPad",
                                    "Tablet",
                                    "249.99",
                                    "img/ipad.jpg"));
        put("lt001", new Item("MacBook",
                                    "Laptop",
                                    "899.99",
                                    "img/macbook.jpg"));
        put("lt002", new Item("Asus",
                                    "Laptop",
                                    "499.99",
                                    "img/asus.jpg"));
        put("lt003", new Item("Sony",
                                    "Laptop",
                                    "799.99",
                                    "img/sony-lt.jpg"));
        put("lt004", new Item("Lenovo",
                                    "Laptop",
                                    "399.99",
                                    "img/lenovo.jpg"));
        put("tv001", new Item("Panasonic",
                                    "HDTV",
                                    "749.99",
                                    "img/panasonic-tv.jpg"));
        put("tv002", new Item("Samsung",
                                    "HDTV",
                                    "299.99",
                                    "img/samsung-tv.jpg"));
        put("tv003", new Item("Sony",
                                    "HDTV",
                                    "449.99",
                                    "img/sony-tv.jpg"));
    }};

    /**
     * Return all items
     */
    public static Map<String, Item> getItems() {
        return(items);
    }

    /**
     * Return a specific item, by ID
     */
    public static Item getItem(String itemId) {
        return items.get(itemId);
    }

    /**
     * Copies all entries with specified category to new Hashmap and returns
     * @param   category    String value to match category
     */
    public static Map<String, Item> getItemsByCategory(String category) {

        Map<String, Item> catItems = new HashMap<String, Item>();

        for (Entry<String, Item> entry : items.entrySet()) {
            if (entry.getValue().getCategory().equals(category)) {
                catItems.put(entry.getKey(), entry.getValue());
            }
        }

        return catItems;
    }

    /**
     * Returns a list of all item categories represented in the inventory
     */
    public static ArrayList<String> getCategories() {

        ArrayList<String> categories = new ArrayList<String>();

        for (Item item : items.values()) {
            if (!categories.contains(item.getCategory())) {
                categories.add(item.getCategory());
            }
        }

        return (categories);

    }
}
