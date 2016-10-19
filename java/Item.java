/*
 * Item
 *
 * Provides data structures and methods for storing and retrieving item data.
 */

public class Item {

    private String name;        // The item's name: make & model
    private String category;    // Category for the item, e.g. Laptop
    private String price;       // The price of the item, in USD
    private String imgSrc;      // Defines the source URL of image of the item

    public Item(String name, String category, String price, String imgSrc) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imgSrc = imgSrc;
    }

    // No setters since the data will be hard-coded

    public String getName() {
        return(name);
    }

    public String getCategory() {
        return(category);
    }

    public String getPrice() {
        return(price);
    }

    public String getImgSrc() {
        return imgSrc;
    }
}
