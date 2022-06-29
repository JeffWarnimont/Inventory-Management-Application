package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** AddPart is the controller class for the Add Part screen.
 It contains FXML object fields and event handler methods to facilitate user interaction with the GUI.
 */
public class AddPart implements Initializable {

    @FXML
    private ToggleGroup AddPartRB;
    @FXML
    private RadioButton OutsourcedRB;
    @FXML
    private RadioButton InHouseRB;
    @FXML
    private Label AddPartMaxLbl;
    @FXML
    private Label AddPartMachIdLbl;
    @FXML
    private Label AddPartMinLbl;
    @FXML
    private Label AddPartPriceCostLbl;
    @FXML
    private Label AddPartInvLbl;
    @FXML
    private Label AddPartNameLbl;
    @FXML
    private Label AddPartIdLbl;
    @FXML
    private Label AddPartLbl;
    @FXML
    private TextField AddPartIdTxt;
    @FXML
    private TextField AddPartNameTxt;
    @FXML
    private TextField AddPartInvTxt;
    @FXML
    private TextField AddPartPriceCostTxt;
    @FXML
    private TextField AddPartMinTxt;
    @FXML
    private TextField AddPartMachIdTxt;
    @FXML
    private TextField AddPartMaxTxt;


    /** This method initializes the Add Part screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("This screen is used to add In-House and Outsourced parts to master inventory.");
    }


    /** This handler method sets the text in AddPartMachLbl to "Machine Id" when the InHouse radio button is selected.
     * @param actionEvent represents InHouse radio button selection
     */
    @FXML
    void OnActionDisplayInHouse(ActionEvent actionEvent) {
        System.out.println("Add In-House parts with Machine ID field.");
        AddPartMachIdLbl.setText("Machine Id");
    }


    /** This handler method sets the text in AddPartMachLbl to "Company Name" when the Outsourced radio button is selected.
     * @param actionEvent represents Outsourced radio button selection
     */
    @FXML
    void OnActionDisplayOutsourced(ActionEvent actionEvent) {
        System.out.println("Add Outsourced parts with vendor Company Name field.");
        AddPartMachIdLbl.setText("Company Name");
    }


    /** This handler method saves new parts to inventory when the save button is clicked. It does this by parsing data from the text
     fields on this form and populating that data into either InHouse or Outsourced constructors depending on which radio button is
     selected. This method calls the main.generateUniquePartID method to create a unique id for the part.  It also has built in error
     checking to ensure that min is less than max, inv falls between these values, and that all text fields follow variable type rules.
     If any of these errors are found, the user is notified via dialog box and cannot move forward until the error has been corrected.
     Finally, the method returns the user to the main screen once the part has been saved to inventory.
     * @param actionEvent represents a Save button click
     * @throws IOException An IO Exception is possible if the fxml file cannot be found.
     */
    @FXML
    void OnActionSaveAddPart(ActionEvent actionEvent) throws IOException {

        try {

            int stock = Integer.parseInt(AddPartInvTxt.getText());
            int min = Integer.parseInt(AddPartMinTxt.getText());
            int max = Integer.parseInt(AddPartMaxTxt.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Max must be greater than Min.");
                alert.showAndWait();
                return;
            }

            if (!(stock >= min && stock <= max)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Inv must be between Min and Max.");
                alert.showAndWait();
                return;
            }

            int id = Main.generateUniquePartID();
            double price = Double.parseDouble(AddPartPriceCostTxt.getText());
            String name = AddPartNameTxt.getText();

            if (InHouseRB.isSelected()) {
                int machineId = Integer.parseInt(AddPartMachIdTxt.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            }

            else if (OutsourcedRB.isSelected()) {
                String companyName = AddPartMachIdTxt.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }

            System.out.println("Saving and returning to Main Screen.");

            Parent root = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
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
    void OnActionCancelAddPart(ActionEvent actionEvent) throws IOException {

        System.out.println("Returning to Main Screen.");

        Parent root = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 420));
        stage.show();
    }
}
