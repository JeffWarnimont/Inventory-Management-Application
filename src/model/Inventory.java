package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** The Inventory class contains two observableLists of objects (allParts and allProducts) and methods for working with these lists.
 */
public class Inventory {

    //Declare fields for Inventory class
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    //Declare methods for Inventory class

    /** This method is used to add new parts to inventory.
     * @param newPart is the part to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /** This method is used to add new products to inventory.
     * @param newProduct is the product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /** This method searches the list of all parts for a match to a part ID.
     * @param partId is the id to search for
     * @return the part if found
     */
    public static Part lookupPart(int partId) {
        for(Part part : allParts) {
            if(part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /** This method searches the list of all parts for a match to a name string.
     * @param partName is the string to search for
     * @return the matchingParts
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for(Part part : allParts) {
            if(part.getName().contains(partName)) {
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /** This method searches the list of all products for a match to a product ID.
     * @param productId is the id to search for
     * @return the product if found
     */
    public static Product lookupProduct(int productId) {
        for(Product product : allProducts) {
            if(product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /** This method searches the list of all products for a match to a name string.
     * @param productName is the string to search for
     * @return the matchingProducts
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for(Product product : allProducts) {
            if(product.getName().contains(productName)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /** This method is used to update/edit/modify/replace part objects in inventory.
     * @param index is the part location in memory
     * @param selectedPart is the part to be modified
     */
    public static void updatePart(int index, Part selectedPart) {

        for(Part part : getAllParts()) {
            if(part.getId() == selectedPart.getId()) {
                index = getAllParts().indexOf(part);
                allParts.set(index, selectedPart);
            }
        }
    }

    /** This method is used to update/edit/modify/replace product objects in inventory.
     * @param index is the product location in memory
     * @param selectedProduct is the product to be modified
     */
    public static void updateProduct(int index, Product selectedProduct) {
        for(Product product : getAllProducts()) {
            if(product.getId() == selectedProduct.getId()) {
                index = getAllProducts().indexOf(product);
                allProducts.set(index, selectedProduct);
            }
        }
    }
    /** This method is used to remove product objects from inventory.
     * @param selectedProduct the product selected for removal
     * @return true if found and deleted
     */
    public static boolean deleteProduct(Product selectedProduct){
        for(Product product : getAllProducts()) {
            if(product.getId() == selectedProduct.getId()) {
                allProducts.remove(selectedProduct);
                return true;
            }
        }
        return false;
    }

    /** This method is used to remove part objects from inventory.
     * @param selectedPart the part selected for removal
     * @return true if found and deleted
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : getAllParts()) {
            if (part.getId() == selectedPart.getId()) {
                allParts.remove(selectedPart);
                return true;
            }
        }
        return false;
    }

    /**
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
