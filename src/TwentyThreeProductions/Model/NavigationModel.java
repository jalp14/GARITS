package TwentyThreeProductions.Model;

import TwentyThreeProductions.Controller.MainScreen.MainAdminController;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class NavigationModel {
    // URLs
    // Login
    public static String LOGIN_URL = "src/TwentyThreeProductions/View/LoginScreen.fxml";
    // Main Screen
    public static String MAIN_ADMIN_URL = "src/TwentyThreeProductions/View/MainScreen/MainScreenAdmin.fxml";
    public static String MAIN_FFR_URL = "src/TwentyThreeProductions/View/MainScreen/MainScreenFFR.fxml";
    public static String MAIN_MECHANIC_URL = "src/TwentyThreeProductions/View/MainScreen/MainScreenMechanic.fxml";
    // User Management
    public static String USER_MANAGEMENT_URL = "src/TwentyThreeProductions/View/Users/UsersMain.fxml";
    public static String ADD_NEW_USER_URL = "src/TwentyThreeProductions/View/Users/AddNewUser.fxml";
    public static String EDIT_USER_URL = "src/TwentyThreeProductions/View/Users/EditUser.fxml";
    public static String SELECT_USER_URL = "src/TwentyThreeProductions/View/Users/SelectUser.fxml";
    // Database Management
    public static String DB_MANAGEMENT_URL = "src/TwentyThreeProductions/View/Database/DbManagement.fxml";
    public static String DB_RESTORE_URL = "src/TwentyThreeProductions/View/Database/DbRestore.fxml";
    public static String DB_BACKUP_SETTINGS_URL = "src/TwentyThreeProductions/View/Database/AutomaticBackupSettings.fxml";
    // Customers
    public static String ADD_CUSTOMER_TO_CAR_URL = "src/TwentyThreeProductions/View/Customers/AddCarToCustomer.fxml";
    public static String ADD_NEW_CUSTOMER_URL = "src/TwentyThreeProductions/View/Customers/AddNewCustomer.fxml";
    public static String CONFIGURE_DISCOUNT_URL = "src/TwentyThreeProductions/View/Customers/ConfigureDiscount.fxml";
    public static String EDIT_DISCOUNT_URL = "src/TwentyThreeProductions/View/Customers/EditDiscount.fxml";
    public static String CUSTOMER_MAIN_URL = "src/TwentyThreeProductions/View/Customers/CustomersMain.fxml";
    public static String EDIT_CUSTOMER_URL = "src/TwentyThreeProductions/View/Customers/EditCustomer.fxml";
    public static String REMOVE_CUSTOMER_URL = "src/TwentyThreeProductions/View/Customers/RemoveCustomer.fxml";
    public static String SEARCH_CUSTOMER_URL = "src/TwentyThreeProductions/View/Customers/SearchCustomer.fxml";
    // Jobs
    public static String JOBS_MAIN_URL = "src/TwentyThreeProductions/View/Jobs/JobsMain.fxml";
    // Jobs - Edit/Monitor
    public static String ADD_PART_TO_JOB_URL = "src/TwentyThreeProductions/View/Jobs/EditMonitor/AddPartToJob.fxml";
    public static String ADD_TASK_TO_JOB_URL = "src/TwentyThreeProductions/View/Jobs/EditMonitor/AddTaskToJob.fxml";
    public static String EDIT_MONITOR_CHOOSE_URL = "src/TwentyThreeProductions/View/Jobs/EditMonitor/EditMonitorChooseJob.fxml";
    public static String EDIT_MONITOR_JOB_URL = "src/TwentyThreeProductions/View/Jobs/EditMonitor/EditMonitorJob.fxml";
    // Jobs - NewJob
    public static String NEW_CUSTOMER_CONFIGURE_DISCOUNT_URL = "src/TwentyThreeProductions/View/Jobs/NewJob/NewCustomerConfigureDiscount.fxml";
    public static String NEW_JOB_CAR_MENU_URL = "src/TwentyThreeProductions/View/Jobs/NewJob/NewJobCarMenu.fxml";
    public static String NEW_JOB_EXISTING_CUSTOMER_URL = "src/TwentyThreeProductions/View/Jobs/NewJob/NewJobExistingCustomer.fxml";
    public static String NEW_JOB_EXISTING_VEHICLE_URL = "src/TwentyThreeProductions/View/Jobs/NewJob/NewJobExistingVehicle.fxml";
    public static String NEW_JOB_MENU_URL = "src/TwentyThreeProductions/View/Jobs/NewJob/NewJobMenu.fxml";
    public static String NEW_JOB_NEW_CUSTOMER_URL = "src/TwentyThreeProductions/View/Jobs/NewJob/NewJobNewCustomer.fxml";
    public static String NEW_JOB_NEW_VEHICLE_URL = "src/TwentyThreeProductions/View/Jobs/NewJob/NewJobNewVehicle.fxml";
    public static String PART_ONLY_SELECT_URL = "src/TwentyThreeProductions/View/Jobs/NewJob/PartOnlySelect.fxml";
    // Parts
    public static String ADD_NEW_PART_URL = "src/TwentyThreeProductions/View/Parts/AddNewPart.fxml";
    public static String PARTS_MAIN_URL = "src/TwentyThreeProductions/View/Parts/PartsMain.fxml";
    public static String REMOVE_PART_URL = "src/TwentyThreeProductions/View/Parts/RemovePart.fxml";
    public static String SEARCH_UPDATE_STOCK_URL = "src/TwentyThreeProductions/View/Parts/SearchUpdateStock.fxml";
    public static String UPDATE_STOCK_URL = "src/TwentyThreeProductions/View/Parts/UpdateStock.fxml";
    // Reports
    public static String REPORTS_MAIN_URL = "src/TwentyThreeProductions/View/Reports/ReportsMain.fxml";
    public static String EDIT_AUTO_REPORT_URL = "src/TwentyThreeProductions/View/Reports/EditAutoReports.fxml";
    public static String SELECT_REPORT_TO_VIEW_URL = "src/TwentyThreeProductions/View/Reports/SelectReportToView.fxml";
    public static String VIEW_REPORT_URL = "src/TwentyThreeProductions/View/Reports/ViewReport.fxml";
    public static String NEW_AVE_TIME_PRICE_URL = "src/TwentyThreeProductions/View/Reports/NewReports/NewAveTimePrice.fxml";
    public static String NEW_NO_VEHICLES_MONTHLY_URL = "src/TwentyThreeProductions/View/Reports/NewReports/NewNoVehiclesMonthly.fxml";
    public static String NEW_REPORT_MENU_URL = "src/TwentyThreeProductions/View/Reports/NewReports/NewReportMenu.fxml";
    public static String NEW_STOCK_LEVEL_URL = "src/TwentyThreeProductions/View/Reports/NewReports/NewStockLevel.fxml";
    // Notifications
    public static String NOTIFICATIONS_MAIN_URL = "src/TwentyThreeProductions/View/Notifications/Notification.fxml";
    // Settings
    public static String SETTINGS_URL = "src/TwentyThreeProductions/View/Settings.fxml";

    public static URL tmpURL;
    private static DBLogic dbController = DBLogic.getDBInstance();
    private static SceneSwitch sceneSwitch = SceneSwitch.getInstance ();

    // Scene IDs
    public static String LOGIN_ID = "Login";
    public static String MAIN_ADMIN_ID = "MainAdmin";
    public static String MAIN_FFR_ID = "MainFFR";
    public static String MAIN_MECHANIC_ID = "MainMechanic";
    public static String USER_MANAGEMENT_ID = "UserManagement";
    public static String ADD_NEW_USER_ID = "AddNewUser";
    public static String EDIT_USER_ID = "EditUser";
    public static String SELECT_USER_ID = "SelectUser";
    public static String DB_MANAGEMENT_ID = "DBManagement";
    public static String DB_RESTORE_ID = "DBRestore";
    public static String ADD_CUSTOMER_TO_CAR_ID = "AddNewCustomerToCar";
    public static String ADD_NEW_CUSTOMER_ID = "AddNewCustomer";
    public static String CONFIGURE_DISCOUNT_ID = "ConfigureDiscount";
    public static String CUSTOMER_MAIN_ID = "CustomerMain";
    public static String EDIT_CUSTOMER_ID = "EditCustomer";
    public static String REMOVE_CUSTOMER_ID = "RemoveCustomer";
    public static String SEARCH_CUSTOMER_ID = "SearchCustomer";
    public static String JOBS_MAIN_ID = "JobsMain";
    public static String ADD_PART_TO_JOB_ID = "AddPartToJob";
    public static String ADD_TASK_TO_JOB_ID = "AddTaskToJob";
    public static String EDIT_MONITOR_CHOOSE_ID = "EditMonitorChoose";
    public static String EDIT_MONITOR_JOB_ID = "EditMonitorJob";
    public static String NEW_JOB_CAR_MENU_ID = "NewJobCarMenu";
    public static String NEW_CUSTOMER_CONFIGURE_DISCOUNT_ID = "NewCustomerConfigureDiscount";
    public static String NEW_JOB_EXISTING_CUSTOMER_ID = "NewJobExistingCustomer";
    public static String NEW_JOB_EXISTING_VEHICLE_ID = "NewJobExistingVehicle";
    public static String NEW_JOB_MENU_ID = "NewJobMenu";
    public static String NEW_JOB_NEW_CUSTOMER_ID = "NewJobNewCustomer";
    public static String NEW_JOB_NEW_VEHICLE_ID = "NewJobNewVehicle";
    public static String PART_ONLY_SELECT_ID = "PartOnlySelect";
    public static String ADD_NEW_PART_ID = "AddNewPart";
    public static String PARTS_MAIN_ID = "PartsMain";
    public static String REMOVE_PART_ID = "RemovePart";
    public static String SEARCH_UPDATE_STOCK_ID = "SearchUpdateStock";
    public static String UPDATE_STOCK_ID = "UpdateStock";
    public static String REPORTS_MAIN_ID = "ReportsMain";
    public static String NOTIFICATIONS_MAIN_ID = "NotificationMain";
    public static String EDIT_DISCOUNT_ID = "EditDiscount";
    public static String SETTINGS_ID = "Settings";
    public static String AUTOMATIC_BACKUP_SETTINGS_ID = "AutoBackupSettings";
    public static String EDIT_AUTO_REPORTS_ID = "EditAutoReport";
    public static String SELECT_REPORT_TO_VIEW_ID = "SelectReportToView";
    public static String VIEW_REPORT_ID = "ViewReport";
    public static String NEW_AVE_TIME_PRICE_ID = "NewAveTimePrice";
    public static String NEW_REPORT_MENU_ID = "NewReportMenu";
    public static String NEW_STOCK_LEVEL_ID = "NewStockLevel";
    public static String NEW_NO_VEHICLES_MONTHLY_ID = "NewNoVehiclesMonthly";


    public enum user_type {
        NONE,
        ADMIN,
        FRANCHISEE,
        RECEPTIONIST,
        FOREPERSON,
        MECHANIC
    }

    private static user_type type = user_type.NONE;

    public static user_type getType() {
        return type;
    }


    private NavigationModel() {
    }
    // Add constraints for views you want to instantiate
    public static URL getURL(String name) throws IOException {
        if (name.equals(LOGIN_ID)) {
            return tmpURL = new File(LOGIN_URL).toURI().toURL();
        } else if (name.equals(MAIN_ADMIN_ID)) {
            return tmpURL = new File(MAIN_ADMIN_URL).toURI().toURL();
        } else  if (name.equals(MAIN_MECHANIC_ID)){
            return tmpURL = new File(MAIN_MECHANIC_URL).toURI().toURL();
        } else if ((name.equals(MAIN_FFR_ID))) {
            return tmpURL = new File(MAIN_FFR_URL).toURI().toURL();
        } else if ((name.equals(USER_MANAGEMENT_ID))) {
            return tmpURL = new File(USER_MANAGEMENT_URL).toURI().toURL();
        } else if ((name.equals(ADD_NEW_USER_ID))) {
            return tmpURL = new File(ADD_NEW_USER_URL).toURI().toURL();
        } else if (name.equals(EDIT_USER_ID)) {
            return tmpURL = new File(EDIT_USER_URL).toURI().toURL();
        } else if (name.equals(SELECT_USER_ID)) {
            return tmpURL = new File(SELECT_USER_URL).toURI().toURL();
        } else if ((name.equals(DB_MANAGEMENT_ID))) {
            return tmpURL = new File(DB_MANAGEMENT_URL).toURI().toURL();
        } else if ((name.equals(DB_RESTORE_ID))) {
            return tmpURL = new File(DB_RESTORE_URL).toURI().toURL();
        } else if (name.equals(ADD_NEW_CUSTOMER_ID)) {
            return tmpURL = new File(ADD_NEW_CUSTOMER_URL).toURI().toURL();
        } else if (name.equals(ADD_CUSTOMER_TO_CAR_ID)) {
            return tmpURL = new File(ADD_CUSTOMER_TO_CAR_URL).toURI().toURL();
        } else if (name.equals(CONFIGURE_DISCOUNT_ID)) {
            return tmpURL = new File(CONFIGURE_DISCOUNT_URL).toURI().toURL();
        } else if (name.equals(CUSTOMER_MAIN_ID)) {
            return tmpURL = new File(CUSTOMER_MAIN_URL).toURI().toURL();
        } else if (name.equals(EDIT_CUSTOMER_ID)) {
            return tmpURL = new File(EDIT_CUSTOMER_URL).toURI().toURL();
        } else if (name.equals(REMOVE_CUSTOMER_ID)) {
            return tmpURL = new File(REMOVE_CUSTOMER_URL).toURI().toURL();
        } else if (name.equals(SEARCH_CUSTOMER_ID)) {
            return tmpURL = new File(SEARCH_CUSTOMER_URL).toURI().toURL();
        } else if (name.equals(ADD_PART_TO_JOB_ID)) {
            return tmpURL = new File(ADD_PART_TO_JOB_URL).toURI().toURL();
        } else if (name.equals(ADD_TASK_TO_JOB_ID)) {
            return tmpURL = new File(ADD_TASK_TO_JOB_URL).toURI().toURL();
        } else if (name.equals(EDIT_MONITOR_CHOOSE_ID)) {
            return tmpURL = new File(EDIT_MONITOR_CHOOSE_URL).toURI().toURL();
        } else if (name.equals(EDIT_MONITOR_JOB_ID)) {
            return tmpURL = new File(EDIT_MONITOR_JOB_URL).toURI().toURL();
        } else if(name.equals(NEW_CUSTOMER_CONFIGURE_DISCOUNT_ID)) {
            return tmpURL = new File(NEW_CUSTOMER_CONFIGURE_DISCOUNT_URL).toURI().toURL();
        } else if (name.equals(NEW_JOB_CAR_MENU_ID)) {
            return tmpURL = new File(NEW_JOB_CAR_MENU_URL).toURI().toURL();
        } else if (name.equals(NEW_JOB_EXISTING_CUSTOMER_ID)) {
            return tmpURL = new File(NEW_JOB_EXISTING_CUSTOMER_URL).toURI().toURL();
        } else if (name.equals(NEW_JOB_EXISTING_VEHICLE_ID)) {
            return tmpURL = new File(NEW_JOB_EXISTING_VEHICLE_URL).toURI().toURL();
        } else if (name.equals(NEW_JOB_MENU_ID)) {
            return tmpURL = new File(NEW_JOB_MENU_URL).toURI().toURL();
        } else if (name.equals(NEW_JOB_NEW_CUSTOMER_ID)) {
            return tmpURL = new File(NEW_JOB_NEW_CUSTOMER_URL).toURI().toURL();
        } else if (name.equals(NEW_JOB_NEW_VEHICLE_ID)) {
            return tmpURL = new File(NEW_JOB_NEW_VEHICLE_URL).toURI().toURL();
        } else if (name.equals(PART_ONLY_SELECT_ID)) {
            return tmpURL = new File(PART_ONLY_SELECT_URL).toURI().toURL();
        } else if (name.equals(JOBS_MAIN_ID)) {
            return tmpURL = new File(JOBS_MAIN_URL).toURI().toURL();
        } else if (name.equals(ADD_NEW_PART_ID)) {
            return tmpURL = new File(ADD_NEW_PART_URL).toURI().toURL();
        } else if (name.equals(PARTS_MAIN_ID)) {
            return tmpURL = new File(PARTS_MAIN_URL).toURI().toURL();
        } else if (name.equals(REMOVE_PART_ID)) {
            return new File(REMOVE_PART_URL).toURI().toURL();
        } else if (name.equals(SEARCH_UPDATE_STOCK_ID)) {
            return tmpURL = new File(SEARCH_UPDATE_STOCK_URL).toURI().toURL();
        } else if (name.equals(UPDATE_STOCK_ID)) {
            return tmpURL = new File(UPDATE_STOCK_URL).toURI().toURL();
        } else if (name.equals(REPORTS_MAIN_ID)) {
            return tmpURL = new File(REPORTS_MAIN_URL).toURI().toURL();
        } else if (name.equals(NOTIFICATIONS_MAIN_ID)) {
            return tmpURL = new File(NOTIFICATIONS_MAIN_URL).toURI().toURL();
        } else if (name.equals(EDIT_DISCOUNT_ID)) {
            return tmpURL = new File(EDIT_DISCOUNT_URL).toURI().toURL();
        } else if (name.equals(SETTINGS_ID)) {
            return  tmpURL = new File(SETTINGS_URL).toURI().toURL();
        } else if (name.equals(AUTOMATIC_BACKUP_SETTINGS_ID)) {
            return tmpURL = new File(DB_BACKUP_SETTINGS_URL).toURI().toURL();
        } else if (name.equals(EDIT_AUTO_REPORTS_ID)) {
            return tmpURL = new File(EDIT_AUTO_REPORT_URL).toURI().toURL();
        } else if (name.equals(SELECT_REPORT_TO_VIEW_ID)) {
            return tmpURL = new File(SELECT_REPORT_TO_VIEW_URL).toURI().toURL();
        } else if (name.equals(VIEW_REPORT_ID)) {
            return tmpURL = new File(VIEW_REPORT_URL).toURI().toURL();
        } else if (name.equals(NEW_AVE_TIME_PRICE_ID)) {
            return tmpURL = new File(NEW_AVE_TIME_PRICE_URL).toURI().toURL();
        } else if (name.equals(NEW_NO_VEHICLES_MONTHLY_ID)) {
            return tmpURL = new File(NEW_NO_VEHICLES_MONTHLY_URL).toURI().toURL();
        } else if (name.equals(NEW_REPORT_MENU_ID)) {
            return tmpURL = new File(NEW_REPORT_MENU_URL).toURI().toURL();
        } else if (name.equals(NEW_STOCK_LEVEL_ID)) {
            return tmpURL = new File(NEW_STOCK_LEVEL_URL).toURI().toURL();
        }
        else {
            System.out.println("User type not found");
            return null;
        }
    }

    public static void detectUserType(Scene scene) throws IOException {
        System.out.println("Detecting User");
        if (dbController.getUser_type().equals("ADMIN")) {
            type = user_type.ADMIN;
            sceneSwitch.activateScene(MAIN_ADMIN_ID, scene);
        } else if (dbController.getUser_type().equals("FRANCHISEE")) {
            type = user_type.FRANCHISEE;
            sceneSwitch.activateScene(MAIN_FFR_ID, scene);
        } else if (dbController.getUser_type().equals("RECEPTIONIST")) {
            type = user_type.RECEPTIONIST;
            sceneSwitch.activateScene(MAIN_FFR_ID, scene);
        } else if (dbController.getUser_type().equals("FOREPERSON")) {
            type = user_type.FOREPERSON;
            sceneSwitch.activateScene(MAIN_FFR_ID, scene);
        } else if (dbController.getUser_type().equals("MECHANIC")) {
            type = user_type.MECHANIC;
            sceneSwitch.activateScene(MAIN_MECHANIC_ID, scene);
        }
        System.out.println("Detected User : " + type);
    }

}