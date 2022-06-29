package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/** ModifyPart is the controller class for the Modify Part screen.
 It contains FXML object fields and event handler methods to facilitate user interaction with the GUI.
 */
public class ModifyPart implements Initializable {
    @FXML
    private Label ModifyPartLbl;
    @FXML
    private Label ModPartIdLbl;
    @FXML
    private Label ModPartNameLbl;
    @FXML
    private Label ModPartInvLbl;
    @FXML
    private Label ModPartPriceCostLbl;
    @FXML
    private Label ModPartMinLbl;
    @FXML
    private Label ModPartMachIdLbl;
    @FXML
    private RadioButton ModPartInHouseRB;
    @FXML
    private ToggleGroup ModifyPartRB;
    @FXML
    private RadioButton ModPartOutsourcedRB;
    @FXML
    private Label ModPartMaxLbl;
    @FXML
    private TextField ModPartIdTxt;
    @FXML
    private TextField ModPartNameTxt;
    @FXML
    private TextField ModPartInvTxt;
    @FXML
    private TextField ModPartPriceCostTxt;
    @FXML
    private TextField ModPartMinTxt;
    @FXML
    private TextField ModPartMachIdTxt;
    @FXML
    private TextField ModPartMaxTxt;


    /** This method initializes the Modify Part screen.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("This screen is used to modify existing In-House and Outsourced parts in master inventory.");
    }


    /** This handler method sets the text in ModPartMachLbl to "Machine Id" when the InHouse radio button is selected.
     * @param actionEvent represents InHouse radio button selection
     */
    @FXML
    void OnActionDisplayInHouse(ActionEvent actionEvent) {
        System.out.println("Modify In-House parts with Machine ID field.");
        ModPartMachIdLbl.setText("Machine Id");
    }


    /** This handler method sets the text in ModPartMachLbl to "Company Name" when the Outsourced radio button is selected.
     * @param actionEvent represents Outsourced radio button selection
     */
    @FXML
    void OnActionDisplayOutsourced(ActionEvent actionEvent) {
        System.out.println("Modify Outsourced parts with vendor Company Name field.");
        ModPartMachIdLbl.setText("Company Name");
    }


    /** This handler method modifies existing parts in inventory when the save button is clicked. It does this by parsing data from the text
     fields on this form and populating that data into either InHouse or Outsourced constructors depending on which radio button is
     selected. This method calls the Inventory.updatePart method to overwrite the fields of this part in inventory.  It also has built in error
     checking to ensure that min is less than max, inv falls between these values, and that all text fields follow variable type rules.
     If any of these errors are found, the user is notified via dialog box and cannot move forward until the error has been corrected.
     Finally, the method returns the user to the main screen once the part has been saved to inventory.
     * @param actionEvent represents a Save button click
     * @throws IOException An IO Exception is possible if the fxml file cannot be found.
     */
    @FXML
    void OnActionSaveModifyPart(ActionEvent actionEvent) throws IOException {

        try {
            int id = Integer.parseInt(ModPartIdTxt.getText());
            String name = ModPartNameTxt.getText();
            int stock = Integer.parseInt(ModPartInvTxt.getText());
            double price = Double.parseDouble(ModPartPriceCostTxt.getText());
            int min = Integer.parseInt(ModPartMinTxt.getText());
            int max = Integer.parseInt(ModPartMaxTxt.getText());

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

            if (ModPartInHouseRB.isSelected()) {
                int machineId = Integer.parseInt(ModPartMachIdTxt.getText());
                Part selectedPart = new InHouse(id, name, price, stock, min, max, machineId);
                int index = Inventory.getAllParts().lastIndexOf(selectedPart);
                Inventory.updatePart(index, selectedPart);
            }

            else if (ModPartOutsourcedRB.isSelected()) {
                String companyName = ModPartMachIdTxt.getText();
                Part selectedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                int index = Inventory.getAllParts().lastIndexOf(selectedPart);
                Inventory.updatePart(index, selectedPart);
            }

            System.out.println("Saving and returning to Main Screen.");

            Parent root = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 420));
            stage.show();
        }
        catch(NumberFormatException e)
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
    public void OnActionCancelModifyPart(ActionEvent actionEvent) throws IOException {

        System.out.println("Returning to Main Screen.");

        Parent root = FXMLLoader.load(getClass().getResource("/view/Main_Screen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 420));
        stage.show();
    }


    /**  This method is used to retrieve part object field data and populate this data into the correct text fields on
     this form.  It not only fills the text fields, but also selects the correct radio button and displays either "In House"
     or "Outsourced" in the ModPartMachIdLbl depending on which radio button is selected.  This method will be used to
     retrieve data from the Main Screen controller.
     @param part the part whose info will be retrieved.
     */
    public void SetModPartTextFields(Part part) {
        ModPartIdTxt.setText(String.valueOf(part.getId()));
        ModPartNameTxt.setText(part.getName());
        ModPartInvTxt.setText(String.valueOf(part.getStock()));
        ModPartPriceCostTxt.setText(String.valueOf(part.getPrice()));
        ModPartMinTxt.setText(String.valueOf(part.getMin()));
        ModPartMaxTxt.setText(String.valueOf(part.getMax()));

        if(part instanceof Outsourced) {
            ModPartOutsourcedRB.setSelected(true);
            ModPartMachIdLbl.setText("Company Name");
            ModPartMachIdTxt.setText(((Outsourced) part).getCompanyName());
        }
        else if(part instanceof InHouse) {
            ModPartInHouseRB.setSelected(true);
            ModPartMachIdLbl.setText("Machine Id");
            ModPartMachIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
    }
}
