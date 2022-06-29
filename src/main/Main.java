package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
import java.io.IOException;

/** The main class is the initial entrypoint into the program. This program is an inventory management system.
 Inventory consists of parts and products. Parts can be either in house or outsourced and products can contain associated
 parts, but are not required to. This system allows for parts and products to be added to, removed from, or modified in
 inventory as well as having search functionality by name or id.
 FUTURE ENHANCEMENT In its current state this inventory system has no way of dealing with discontinued items that still
 have inventory or new items that we have information on, but are yet to stock.  Currently, all parts and products must have
 an inventory level within the min to max range.  If a part or product were to be discontinued, it may be ideal to allow the
 stock level to go below minimum or if we were introducing a new item we may want to generate an id and set up our min, max
 and other attributes without yet having any stock.  This could be accomplished by changing or initializing the min on each
 item to 0, but this may be tedious and require several modify operations.  My idea is to have radio button selections for
 part/product status.  Status could include Active, Discontinued, and In Development for example.  These radio buttons could
 be tied to few or many actions such as changing labels, or variables.  Even the name field could change from a secret code
 during development to the actual name of the product at launch. If utilized correctly with event handlers one radio button
 selection could make several modifications at once and each selection could have its own set of rules to follow.
 */
public class Main extends Application {

    /** The start method loads the main screen of the program.
     * @param stage holds the scene to show. In this instance it will show the Main Screen.
     * @throws IOException An IO Exception is possible if the fxml file cannot be found.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
        stage.setTitle("Jeff_Warnimont_C482_PA");
        stage.setScene(new Scene(root, 1000, 420));
        stage.show();
    }

    static int partID = 3;

    /** This method creates unique part Ids when called. They will be contiguous starting at ID# 4. 1, 2, and 3 were used to set up test data.
     * @return the partID
     */
    public static int generateUniquePartID() {
        partID = ++partID;
        return partID;
    }

    static int productID = 1001;

    /** This method creates unique product IDs when called. They will be contiguous starting at ID# 1002. 1000 and 1001 were used to set up test data.
     * @return the productID
     */
    public static int generateUniqueProductID() {
        productID = ++productID;
        return productID;
    }

    /** The main method launches the program with all arguments passed.
     * @param args the arguments passed to the launcher.
     */
    public static void main(String[] args){

        //Test data objects for parts and products
        Product product1 = new Product(1000, "Giant Bike", 299.99, 5, 2,10);
        Product product2 = new Product(1001, "Tricycle", 99.99, 3, 2,10);
        Outsourced outsourced1 = new Outsourced(1, "Brakes", 15.00, 10, 2,20,"Big Company");
        InHouse inHouse1 = new InHouse(2, "Wheel", 11.00, 16, 2,20,12341);
        InHouse inHouse2 = new InHouse(3, "Seat", 15.00, 10, 2,20,54627);

        //Add test data to part and product inventories
        Inventory.addPart(outsourced1);
        Inventory.addPart(inHouse1);
        Inventory.addPart(inHouse2);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch(args);
    }


}
