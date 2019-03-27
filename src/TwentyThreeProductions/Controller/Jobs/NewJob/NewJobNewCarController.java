package TwentyThreeProductions.Controller.Jobs.NewJob;

import TwentyThreeProductions.Domain.Car;
import TwentyThreeProductions.Domain.Job;
import TwentyThreeProductions.Domain.Manufacturer;
import TwentyThreeProductions.Domain.Mechanic;
import TwentyThreeProductions.Model.CustomerReference;
import TwentyThreeProductions.Model.Database.DAO.CarDAO;
import TwentyThreeProductions.Model.Database.DAO.JobDAO;
import TwentyThreeProductions.Model.Database.DAO.ManufacturerDAO;
import TwentyThreeProductions.Model.Database.DAO.MechanicDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SceneSwitch;
import TwentyThreeProductions.Model.SystemAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class NewJobNewCarController {

    private SceneSwitch sceneSwitch;

    private CustomerReference customerReference;

    @FXML
    private StackPane newJobNewCarStackPane;

    @FXML
    private Text usernameLbl;

    @FXML
    private Text usertypeLbl;

    @FXML
    private Label welcomeMessage;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXButton addCarBtn;

    @FXML
    private Label customerNameLbl;

    @FXML
    private Label regHeading;

    @FXML
    private JFXTextField registrationField;

    @FXML
    private Label makeHeading;

    @FXML
    private Label modelHeading;

    @FXML
    private Label serialHeading;

    @FXML
    private Label chassisNoHeading;

    @FXML
    private Label colourHeading;

    @FXML
    private Label manufacturerHeading;

    @FXML
    private JFXTextField makeField;

    @FXML
    private JFXTextField modelField;

    @FXML
    private JFXTextField serialField;

    @FXML
    private JFXTextField chassisNoField;

    @FXML
    private JFXTextField colourField;

    @FXML
    private JFXTextField manufacturerField;

    @FXML
    void addCarBtnClicked(ActionEvent event) {
        if (registrationField.getText().isEmpty() || makeField.getText().isEmpty() ||
                modelField.getText().isEmpty() || serialField.getText().isEmpty() ||
                chassisNoField.getText().isEmpty() || colourField.getText().isEmpty() ||
                manufacturerField.getText().isEmpty()) {
            SystemAlert systemAlert = new SystemAlert(newJobNewCarStackPane,
                    "Failure", "Blank field(s)");
        } else {
            Car car = new Car();
            CarDAO carDAO = new CarDAO();
            Job job = new Job();
            JobDAO jobDAO = new JobDAO();
            MechanicDAO mechanicDAO = new MechanicDAO();
            boolean doesRegistrationIDExist = false;
            for (Car c : carDAO.getAll()) {
                if (registrationField.getText().equals(car.getRegistrationID())) {
                    doesRegistrationIDExist = true;
                    break;
                }
            }
            if (doesRegistrationIDExist) {
                SystemAlert systemAlert = new SystemAlert(newJobNewCarStackPane,
                        "Failure", "Registration ID already exists");
            } else {
                try {
                    Integer.parseInt(registrationField.getText());
                    car.setRegistrationID(registrationField.getText());
                    car.setMake(makeField.getText());
                    Integer.parseInt(chassisNoField.getText());
                    car.setChassisNumber(chassisNoField.getText());
                    car.setModel(modelField.getText());
                    car.setEngSerial(serialField.getText());
                    car.setColour(colourField.getText());
                    car.setCustomerID(customerReference.getCustomer().getCustomerID());
                    car.setManufacturerID("-1");
                    ManufacturerDAO manufacturerDAO = new ManufacturerDAO();
                    boolean isManufacturerTableEmpty = false;
                    if ((manufacturerDAO.getAll().isEmpty())) {
                        isManufacturerTableEmpty = true;
                    } else {
                        for (Manufacturer m : manufacturerDAO.getAll()) {
                            if (m.getCompanyName().equals(manufacturerField.getText())) {
                                car.setManufacturerID(String.valueOf(m.getManufacturerID()));
                                break;
                            }
                        }
                    }
                    if (isManufacturerTableEmpty) {
                        SystemAlert systemAlert = new SystemAlert(newJobNewCarStackPane,
                                "Failure", "No manufacturer in system database");
                    } else if (car.getManufacturerID().equals("-1")) {
                        SystemAlert systemAlert = new SystemAlert(newJobNewCarStackPane,
                                "Failure", "Manufacturer does not exist");
                    } else {
                        carDAO.save(car);
                        int jobID = 1;
                        for (Job j : jobDAO.getAll()) {
                            jobID++;
                        }
                        job.setJobID(jobID);
                        boolean isMechanicTableEmpty = false;
                        if (usertypeLbl.getText().equals("Mechanic") || usertypeLbl.getText().equals("Foreperson")) {
                            job.setUsername(usernameLbl.getText().substring(8));
                        } else if (mechanicDAO.getAll().isEmpty()) {
                            isMechanicTableEmpty = true;
                        } else {
                            for (Mechanic m : mechanicDAO.getAll()) {
                                job.setUsername(m.getUsername());
                                break;
                            }
                        }
                        if (isMechanicTableEmpty) {
                            SystemAlert systemAlert = new SystemAlert(newJobNewCarStackPane,
                                    "Failure", "No mechanic in system database");
                        } else {
                            job.setCustomerID(Integer.parseInt(customerReference.getCustomer().getCustomerID()));
                            job.setRegistrationID(car.getRegistrationID());
                            java.util.Date currentDate = new java.util.Date();
                            java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
                            job.setDateBookedIn(sqlDate);
                            job.setDescription("Work done on car");
                            job.setStatus("Pending");
                            job.setPaidFor("False");
                            jobDAO.save(job);
                            SystemAlert systemAlert = new SystemAlert(newJobNewCarStackPane,
                                    "Success", "Job added for new car");
                            clearInputs();
                        }
                    }
                } catch (Exception e) {
                    SystemAlert systemAlert = new SystemAlert(newJobNewCarStackPane,
                            "Failure", "Invalid input type");
                }
            }
        }
    }

    @FXML
    void backBtnClicked(ActionEvent event) {
        clearInputs();
        sceneSwitch.switchScene(NavigationModel.NEW_JOB_CAR_MENU_ID);
    }

    public void initialize() {
        System.out.println("AddCartoCustomer controller init");
        sceneSwitch = SceneSwitch.getInstance();
        sceneSwitch.addScene(newJobNewCarStackPane, NavigationModel.NEW_JOB_NEW_CAR_ID);
        customerReference = customerReference.getInstance();
        customerNameLbl.setText("Name: " + customerReference.getCustomer().getFirstName() + " " + customerReference.getCustomer().getLastName());
        clearInputs();
    }

    public void clearInputs() {
        registrationField.clear();
        makeField.clear();
        chassisNoField.clear();
        serialField.clear();
        modelField.clear();
        colourField.clear();
        manufacturerField.clear();
    }
}

