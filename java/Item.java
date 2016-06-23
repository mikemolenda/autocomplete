/*
 * Item
 *
 * Provides data structures and methods for storing and retrieving item data.
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