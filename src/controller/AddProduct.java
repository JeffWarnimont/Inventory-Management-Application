package controller;

import javafx.collections.FXCollections;
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
import main.Main;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** AddProduct is the controller class for the Add Product screen.
 It contains FXML object fields and event handler methods to facilitate user interaction with the GUI.
 */
public class AddProduct implements Initializable {

    @FXML
    private TableView<Part> AddTableView;
    @FXML
    private TableColumn<Part, Integer> AddPartIdCol;
    @FXML
    private TableColumn<Part, String> AddPartNameCol;
    @FXML
    private TableColumn<Part, Integer> AddInventoryLevelCol;
    @FXML
    private TableColumn<Part, Double> AddCostCol;

    @FXML
    private TableView<Part> RemoveTableView;
    @FXML
    private TableColumn<Part, Integer> RemovePartIdCol;
    @FXML
    private TableColumn<Part, String> RemovePartNameCol;
    @FXML
    private TableColumn<Part, Integer> RemoveInventoryLevelCol;
    @FXML
    private TableColumn<Part, Double> RemoveCostCol;

    @FXML
    private TextField AddProdSearchTxt;
    @FXML
    private TextField AddProdIdTxt;
    @FXML
    private TextField AddProdMinTxt;
    @FXML
    private TextField AddProdMaxTxt;
    @FXML
    private TextField AddProdPriceTxt;
    @FXML
    private TextField AddProdInvTxt;
    @FXML
    private TextField AddProdNameTxt;

    @FXML
    private Label AddProductLbl;
    @FXML
    private Label AddProdIdLbl;
    @FXML
    private Label AddProdNameLbl;
    @FXML
    private Label AddProdInvLbl;
    @FXML
    private Label AddProdMaxLbl;
    @FXML
    private Label AddProdMinLbl;
    @FXML
    private Label AddProdPriceLbl;

    private ObservableList<Part> associatedPartsToAdd = FXCollections.observableArrayList();


    /** This method initializes the Add Product screen.  It also populates the two table views on the screen with relevant
     data.  All addable parts will be shown in the top table and all added parts will be shown in the bottom table.  As this
     form is for adding new products, the bottom table will remain empty unless/until the user adds parts(Products
     are not required to have associated parts).
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Add Product Screen initialized.");

        AddTableView.setItems(Inventory.getAllParts());

        AddPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        RemoveTableView.setItems(associatedPartsToAdd);

        RemovePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        RemovePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        RemoveInventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        RemoveCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    /** This handler method adds a selected part to the list of associated parts for a product.  If no part
     was selected an error message will display via dialog box.
     * @param actionEvent represents an Add button click.
     */
    @FXML
    void OnActionAddPart(ActionEvent actionEvent) {

        Part part = AddTableView.getSelectionModel().getSelectedItem();

        if(part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No part was selected to add.");
            alert.showAndWait();
        }

        else {
            System.out.println("Adding selected part to product.");
            associatedPartsToAdd.add(part);
        }
    }


    /** This handler method removes a selected part from the associated parts for a product.  Before the part can be removed,
     the user must confirm the removal via dialog box.  A dialog box will also alert the user if no item was selected for removal.
     * @param actionEvent represents a Remove button click.
     */
    @FXML
    void OnActionRemovePart(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This part will be removed from this product. Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {

            Part part = RemoveTableView.getSelectionModel().getSelectedItem();

            if(part == null) {
                Alert alertB = new Alert(Alert.AlertType.ERROR);
                alertB.setContentText("No part was selected to remove.");
                alertB.showAndWait();
            }

            else {
                associatedPartsToAdd.remove(part);
            }
        }
    }


    /** This handler method saves new products to inventory when the save button is clicked. It does this by parsing data from the text
     fields on this form and populating that data into a product constructor. This method calls the main.generateUniqueProductID method
     to create a unique id for the part.  It also has built in error checking to ensure that min is less than max, inv falls between
     these values, and that all text fields follow variable type rules. If any of these errors are found, the user is notified via
     dialog box and cannot move forward until the error has been corrected.   When saving the part to inventory it also saves a list
     of associated parts for the product.  Finally, the method returns the user to the main screen once the product has been saved to inventory.
     * @param actionEvent represents a Save button click.
     * @throws IOException An IO Exception is possible if the fxml file cannot be found.
     */
    @FXML
    void OnActionSave(ActionEvent actionEvent) throws IOException {

        try {

            int inv = Integer.parseInt(AddProdInvTxt.getText());
            int min = Integer.parseInt(AddProdMinTxt.getText());
            int max = Integer.parseInt(AddProdMaxTxt.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Max must be greater than Min.");
                alert.showAndWait();
                return;
            }

            if (!(inv >= min && inv <= max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Inv must be between Min and Max.");
                alert.showAndWait();
                return;
            }

            double price = Double.parseDouble(AddProdPriceTxt.getText());
            int id = Main.generateUniqueProductID();
            String name = AddProdNameTxt.getText();


            Product product = new Product(id, name, price, inv, min, max);
            for(Part part : associatedPartsToAdd) {
                product.addAssociatedPart(part);
            }
            Inventory.addProduct(product);


            System.out.println("Saving and returning to Main Screen.");

            Parent root = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 420));
            stage.show();
            }
        catch (NumberFormatException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid data type.");
            alert.showAndWait();
        }
    }


    /** This handler method navigates back to the main screen without saving any data when the cancel button is clicked.
     * @param actionEvent represents a cancel button click.
     * @throws IOException An IO Exception is possible if the fxml file cannot be found.
     */
    @FXML
    void OnActionCancel(ActionEvent actionEvent) throws IOException {

        System.out.println("Returning to Main Screen.");

        Parent root = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 420));
        stage.show();
    }


    /** This handler method is used to search the list of available parts to associate to the product by name(full or partial) or ID.
     Results will be displayed in the top table view(AddTableView).  If no results are found or an invalid format search
     is performed, an error message will display.  If an empty search is performed the table view will populate with all available parts.
     Once the search is performed by pushing the enter key, the search text box(AddProdSearchTxt) will also be cleared.
     * @param actionEvent represents the ENTER key being pushed to initiate a part search.
     */
    @FXML
    void OnPartSearch(ActionEvent actionEvent) {

        String part = AddProdSearchTxt.getText();
        ObservableList<Part> allMatchingParts = Inventory.lookupPart(part);

        if (allMatchingParts.size() == 0) {
            try {
                int id = Integer.parseInt(part);
                Part p = Inventory.lookupPart(id);

                if (p != null) {
                    AddTableView.getSelectionModel().select(p);
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
            AddTableView.setItems(allMatchingParts);
        }
        AddProdSearchTxt.setText("");
    }

}
