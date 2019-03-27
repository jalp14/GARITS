package TwentyThreeProductions.Controller.Parts;

import TwentyThreeProductions.Domain.Part;
import TwentyThreeProductions.Domain.Manufacturer;
import TwentyThreeProductions.Model.Database.DAO.ManufacturerDAO;
import TwentyThreeProductions.Model.Database.DAO.PartDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class AddNewPartController {

    private SceneSwitch sceneSwitch;

    @FXML
    private StackPane addNewPartStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton addPartBtn;

    @FXML
    private Label nameLabel;

    @FXML
    private Label partIDLabel;

    @FXML
    private Label manufacturerLabel;

    @FXML
    private Label vehicleTypeLabel;

    @FXML
    private Label yearsLabel;

    @FXML
    private Label priceSymbolLabel;

    @FXML
    private Label priceDecimalLabel;

    @FXML
    private Label stockLevelLabel;

    @FXML
    private JFXTextField nameField;

    @FXML
    private JFXTextField partIDField;

    @FXML
    private JFXTextField manufacturerField;

    @FXML
    private JFXTextField vehicleTypeField;

    @FXML
    private JFXTextField yearsField;

    @FXML
    private JFXTextField priceWholeNumField;

    @FXML
    private JFXTextField priceDecimalField;

    @FXML
    private JFXTextField stockLevelField;

    @FXML
    void addPartBtnClicked(ActionEvent event) throws IOException {
        // If there is at least one blank field when the details for the new part are submitted,
        // the system presents an alert stating that the new part could not be added to the system
        // database due to the blank field(s).
        if (nameField.getText().isEmpty() || partIDField.getText().isEmpty() ||
                manufacturerField.getText().isEmpty() || vehicleTypeField.getText().isEmpty() ||
                yearsField.getText().isEmpty() || priceWholeNumField.getText().isEmpty() ||
                priceDecimalField.getText().isEmpty() || stockLevelField.getText().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(addNewPartStackPane,
                    "Failure", "Blank field(s)");
        }

        // Otherwise, the system will take the inputs from this page and add them to the
        // appropriate fields for the new part object that will be put into the system database.
        else {
            Part part = new Part();
            PartDAO partDAO = new PartDAO();
            ManufacturerDAO manufacturerDAO = new ManufacturerDAO();
            part.setName(nameField.getText());
            part.setPartID(partIDField.getText());

            // The system will set the Manufacturer ID to -1, which is a value that cannot be achieved
            // normally, to check whether a proper ID can be given. The system gets the data for every
            // manufacturer within the system database and checks to see if the name that was inputted
            // matches any of the names within the system database. If it does, it will replace the
            // manufacturer ID with the actual ID for the company and break out of the loop.
            part.setManufacturerID(-1);
            boolean isManufacturerTableEmpty = false;
            if ((manufacturerDAO.getAll().isEmpty())) {
                isManufacturerTableEmpty = true;
            } else {
                for (Manufacturer m : manufacturerDAO.getAll()) {
                    if (m.getCompanyName().equals(manufacturerField.getText())) {
                        part.setManufacturerID(m.getManufacturerID());
                        break;
                    }
                }
            }
            if (isManufacturerTableEmpty) {
                SystemAlert systemAlert = new SystemAlert(addNewPartStackPane,
                        "Failure", "No manufacturer in system database");
            }
            
            // The system checks whether the Manufacturer ID has remained as -1 or has changed to an
            // acceptable value. If it has remained as -1, the system will produce another alert stating
            // that the part could not be added as the company name that was inputted does not belong to an
            // actual company within the system database, so an ID could not be retrieved.
            else if (part.getManufacturerID() == -1) {
                SystemAlert systemAlert = new SystemAlert(addNewPartStackPane,
                        "Failure", "Manufacturer does not exist");
            }

            // Once the system has confirmed that the company name inputted by the user is valid
            // and set the manufacturer ID to the appropriate value, the system continues adding
            // the user's inputs to the part object to be put into the system database.
            else {
                part.setVehicleType(vehicleTypeField.getText());

                // The system tests whether the values for the Years field and Price fields are number
                // values as they should be. If they are, then the system will continue to add their values
                // to the part object to be put into the system database. Otherwise, the system will produce
                // another alert stating that there are fields with invalid characters inputted into them.
                try {
                    Integer.parseInt(yearsField.getText());
                    part.setYear(yearsField.getText());

                    // The system checks that whether or not the decimal value that has been inputted
                    // is below 100. If it is not below 100, the system will produce another alert stating
                    // that the part could not be added to the system database as the decimal value provided
                    // was too high
                    if (Integer.parseInt(priceDecimalField.getText()) > 99) {
                        SystemAlert systemAlert = new SystemAlert(addNewPartStackPane,
                                "Failure", "Decimal price value too high");
                    }

                    // Once the system has confirmed that the decimal value is below 100, and thus
                    // accurate for the possible decimal values, the system adds the whole number value to the
                    // corrected decimal value to create a value for the total price, adding it to the part object
                    // to be added to the system database, as well as adding the stock level.
                    else {
                        part.setPrice(String.valueOf(Integer.parseInt(priceWholeNumField.getText()) +
                                (Float.parseFloat(priceDecimalField.getText()) / 100)));
                        Integer.parseInt(stockLevelField.getText());
                        part.setStockLevel(stockLevelField.getText());

                        // After each entry has been added to the part object, the system runs the operation to
                        // add the part to the system database. Once this operation is complete, the system produces
                        // an alert stating that the addition of the part to the system database was a success
                        partDAO.save(part);
                        SystemAlert systemAlert = new SystemAlert(addNewPartStackPane,
                                "Success", "Added part");
                        clearFields();
                    }
                }
                catch(Exception e) {
                    SystemAlert systemAlert = new SystemAlert(addNewPartStackPane,
                            "Failure", "Invalid characters found in field(s)");
                }
            }
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        clearFields();
        sceneSwitch.switchScene(NavigationModel.PARTS_MAIN_ID);
    }

    public void initialize() {
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(addNewPartStackPane, NavigationModel.ADD_NEW_PART_ID);
    }

    public void clearFields() {
        nameField.clear();
        partIDField.clear();
        manufacturerField.clear();
        vehicleTypeField.clear();
        yearsField.clear();
        priceWholeNumField.clear();
        priceDecimalField.clear();
        stockLevelField.clear();
    }

}

