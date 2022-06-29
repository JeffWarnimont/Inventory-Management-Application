package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Product class contains fields and methods for working with Product objects.
 Product objects have six fields (id, name, price, stock, min, max) as well as an optional list of associated parts.
 */
public class Product {

    //Declare fields for Product class
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    //Declare methods for the Product class
    /** This Product object constructor has six required parameters.
     * @param id is the unique product identifier
     * @param name is the product name
     * @param price is the product price
     * @param stock is the current product inventory level
     * @param min is the product maximum inventory level
     * @param max is the product minimum inventory level
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** This method adds a part to the associatedParts list.
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** This method removes a part from the associatedParts list.
     * @param selectedAssociatedPart the associatedPart to remove
     * @return true if found and removed
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part part : getAllAssociatedParts()) {
            if (part.getId() == selectedAssociatedPart.getId()) {
                associatedParts.remove(selectedAssociatedPart);
                return true;
            }
        }
        return false;
    }

    /** This method returns a product's list of associated parts.
     * @return the associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}
