package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** MainScreen is the controller class for the Main Screen.
 It contains FXML object fields and event handler methods to facilitate user interaction with the GUI.
 */
public class MainScreen implements Initializable {

    @FXML
    private TableView<Product> ProdTableView;
    @FXML
    private TableColumn<Product, Double> ProdCostCol;
    @FXML
    private TableColumn<Product, Integer> ProdInvLvlCol;
    @FXML
    private TableColumn<Product, String> ProdNameCol;
    @FXML
    private TableColumn<Product, Integer> ProdIdCol;

    @FXML
    private TableView<Part> PartTableView;
    @FXML
    private TableColumn<Part, Double> PartCostCol;
    @FXML
    private TableColumn<Part, String> PartNameCol;
    @FXML
    private TableColumn<Part, Integer> PartIdCol;
    @FXML
    private TableColumn<Part, Integer> PartInvLvlCol;

    @FXML
    private TextField SearchProdTxt;
    @FXML
    private TextField SearchPartTxt;

    @FXML
    private Label ProductsLbl;
    @FXML
    private Label PartsLbl;
    @FXML
    private Label InvMgmtSysLbl;


    /** This method initializes the Main Screen.  It also populates the two table views on the screen with relevant
     data.  All parts will be shown in the left table and all products will be shown in the right table.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Main Screen initialized.");

        PartTableView.setItems(Inventory.getAllParts());

        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProdTableView.setItems(Inventory.getAllProducts());

        ProdIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProdInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProdCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    /** This handler method closes the program when the Exit button is clicked.
     * @param actionEvent represents an Exit button click.
     */
    @FXML
    void OnActionExit(ActionEvent actionEvent) {
        System.out.println("Goodbye");
        System.exit(0);
    }


    /** This handler method deletes a product from inventory.  It has built in error checking and will notify the user via
     dialog box if no product was selected or if the selected product has associated parts and cannot be deleted.  If the
     selected product is safe for deletion, a confirmation dialog box will appear for the user to confirm deletion.
     * @param actionEvent represents a Delete button click.
     */
    @FXML
    void OnActionDeleteProduct(ActionEvent actionEvent) {

        Product product = ProdTableView.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This product will be deleted permanently from inventory. Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    if (product.getAllAssociatedParts().size() != 0) {
                        Alert alertB = new Alert(Alert.AlertType.ERROR, "This product has associated parts and cannot be deleted");
                        alertB.showAndWait();
                    }
                    else {
                        Inventory.deleteProduct(product);
                    }

                } catch (NullPointerException e) {
                    Alert alertC = new Alert(Alert.AlertType.ERROR);
                    alertC.setContentText("Cannot delete. No product was selected.");
                    alertC.showAndWait();
                }
            }
    }


    /** This handler method navigates to the Modify Product screen when a product selection is made and the Modify button
     is clicked.  This method also creates an instance of the ModifyProduct Controller Class and calls the SetModProdFormFields
     method to pass the selected product to the ModifyProduct controller where the data will be parsed and displayed on the
     Modify Product screen. This method has built in error checking and will notify the user via dialog box if no selection was made.
     * @param actionEvent represents a Modify button click.
     * @throws IOException An IO Exception is possible if the fxml file cannot be found.
     */
    @FXML
    void OnActionModifyProduct(ActionEvent actionEvent) throws IOException {

        System.out.println("Navigating to the Modify Product window.");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Modify_Product.fxml"));
        loader.load();

        try {
            ModifyProduct ModProd = loader.getController();
            ModProd.SetModProdFormFields(ProdTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e)
        {
            Alert alertB = new Alert(Alert.AlertType.ERROR);
            alertB.setContentText("no product was selected to modify.");
            alertB.showAndWait();
        }
    }


    /** This handler method navigates to the Add Product screen when the Add button is clicked.
     * @param actionEvent represents  an Add button click.
     * @throws IOException An IO Exception is possible if the fxml file cannot be found.
     */
    @FXML
    void OnActionAddProduct(ActionEvent actionEvent) throws IOException {

        System.out.println("Navigating to the Add Product window.");

        Parent root = FXMLLoader.load(getClass().getResource("/view/Add_Product.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }


    /** This handler method deletes a part from inventory.  It has built in error checking and will notify the user via
     dialog box if no part was selected.  A confirmation dialog box will appear for the user to confirm deletion as well.
     * @param actionEvent represents a Delete button click.
     */
    @FXML
    void OnActionDeletePart(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This part will be deleted permanently from inventory. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            try{
                Inventory.deletePart(PartTableView.getSelectionModel().getSelectedItem());
            }
            catch(NullPointerException e)
            {
                Alert alertB = new Alert(Alert.AlertType.ERROR);
                alertB.setContentText("Cannot delete. No part was selected.");
                alertB.showAndWait();
            }
        }
    }


    /** This handler method navigates to the Modify Part screen when a part selection is made and the Modify button
     is clicked.  This method also creates an instance of the ModifyPart Controller Class and calls the SetModPartTextFields
     method to pass the selected part to the ModifyPart controller where the data will be parsed and displayed on the
     Modify Part screen. This method has built in error checking and will notify the user via dialog box if no selection was made.
     * @param actionEvent represents a Modify button click.
     * @throws IOException An IO Exception is possible if the fxml file cannot be found.
     */
    @FXML
    void OnActionModifyPart(ActionEvent actionEvent) throws IOException {

        System.out.println("Navigating to the Modify Part Window.");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Modify_Part.fxml"));
        loader.load();

        try{
            ModifyPart ModPart = loader.getController();
            ModPart.SetModPartTextFields(PartTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch(NullPointerException e)
        {

        Alert alertB = new Alert(Alert.AlertType.ERROR);
        alertB.setContentText("No part was selected to modify.");
        alertB.showAndWait();
        }
    }


    /** This handler method navigates to the Add Part screen when the Add button is clicked.
     * @param actionEvent represents  an Add button click.
     * @throws IOException An IO Exception is possible if the fxml file cannot be found.
     */
    @FXML
    void OnActionAddPart(ActionEvent actionEvent) throws IOException {

        System.out.println("Navigating to the Add Part Window.");

        Parent root = FXMLLoader.load(getClass().getResource("/view/Add_Part.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 370, 390));
        stage.show();
    }


    /** This handler method is used to search the list of all parts by name(full or partial) or ID.
     Results will be displayed in the left table view(PartTableView).  If no results are found or an invalid format search
     is performed, an error message will display.  If an empty search is performed the table view will populate with all parts.
     Once the search is performed by pushing the enter key, the search text box(SearchPartTxt) will also be cleared.
     RUNTIME ERROR  I kept getting NumberFormatException errors when I originally wrote these search methods.  It was a
     structural error on my part.  I was trying to run the name and id searches as separate statements and just call the
     lookup methods from inventory, but realized I needed to run them as a nested loop within the MainScreen controller
     to get the results I was looking for.  I rewrote the method to utilize a nested loop structure within this class and later added
     error checking to catch this exception.
     * @param actionEvent represents the ENTER key being pushed to initiate a part search.
     */
    @FXML
    void OnPartSearch(ActionEvent actionEvent) {

         String part = SearchPartTxt.getText();
         ObservableList<Part> allMatchingParts = Inventory.lookupPart(part);

         if (allMatchingParts.size() == 0) {
             try {
                 int id = Integer.parseInt(part);
                 Part p = Inventory.lookupPart(id);

                 if (p != null) {
                     PartTableView.getSelectionModel().select(p);
                 }
                 else {
                     System.out.println("No results found. Enter valid search term.");
                 }
             }
             catch (NumberFormatException e)
             {
                 System.out.println("No results found. Enter valid search term.");
             }
         }
         else {
             PartTableView.setItems(allMatchingParts);
         }
        SearchPartTxt.setText("");
    }


    /** This handler method is used to search the list of all products by name(full or partial) or ID.
     Results will be displayed in the right table view(ProdTableView).  If no results are found or an invalid format search
     is performed, an error message will display.  If an empty search is performed the table view will populate with all parts.
     Once the search is performed by pushing the enter key, the search text box(SearchProdTxt) will also be cleared.
     * @param actionEvent represents the ENTER key being pushed to initiate a part search.
     */
    @FXML
    void OnProductSearch(ActionEvent actionEvent) {

        String product = SearchProdTxt.getText();
        ObservableList<Product> allMatchingProducts = Inventory.lookupProduct(product);

        if (allMatchingProducts.size() == 0) {
            try {
                int id = Integer.parseInt(product);
                Product p = Inventory.lookupProduct(id);

                if (p != null) {
                    ProdTableView.getSelectionModel().select(p);
               }
                else {
                    System.out.println("No results found. Enter valid search term.");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("No results found. Enter valid search term.");
            }
        }
        else {
            ProdTableView.setItems(allMatchingProducts);
        }
        SearchProdTxt.setText("");
    }

}






