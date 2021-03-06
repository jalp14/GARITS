package TwentyThreeProductions;

import TwentyThreeProductions.Domain.*;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DAO.*;
import TwentyThreeProductions.Model.Database.DBConnectivity;
import TwentyThreeProductions.Model.Database.DBHelper;
import TwentyThreeProductions.Model.Database.DBServer;
import TwentyThreeProductions.Model.HelperClasses.ReportHelper;
import TwentyThreeProductions.Model.HelperClasses.SettingsHelper;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SystemNotification;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class Main extends Application {
//////////////////////////// Main class of the system \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Init main method
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Welcome to GARITS");
          URL url = new File("src/TwentyThreeProductions/View/LoginScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root,1200,750);
        scene.getStylesheets().add("TwentyThreeProductions/CSS/test.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        DBServer dbServer = DBServer.getInstance();
        checkForAutomaticBackup();
        checkForAutomaticStockReportGeneration();
        //checkForUpcomingMots();
    }

    public void checkForAutomaticBackup() {
        SettingsHelper helper = SettingsHelper.getInstance();
        Backup backup;
        if (helper.checkDate()) {
            backup = new Backup();
            BackupDAO backupDAO = new BackupDAO();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                String timeStamp = sdf.format(new Date());
                backup.setDate(timeStamp);
                backup.setFileLocation(timeStamp + ".zip");
                System.out.println("Backup Date : " + backup.getDate());
                if (DBHelper.getOSName().startsWith("Win")) {

                } else {
                    Process proc = Runtime.getRuntime().exec(new String[]{"./backup.sh", timeStamp});
                }
                backupDAO.save(backup);;
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

    public void checkForAutomaticStockReportGeneration() {
        ReportSettingsDAO reportSettingsDAO = new ReportSettingsDAO();
        ReportSettings reportSettings;
        reportSettings = reportSettingsDAO.getStockSettings();
        LocalDateTime localDateTime = LocalDateTime.now();
        String reformatted = "";
        String currentDate = Integer.toString(localDateTime.getYear()) + "-" + Integer.toString(localDateTime.getMonthValue()) + "-" + Integer.toString(localDateTime.getDayOfMonth());
        SimpleDateFormat myFormat  = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
            reformatted = format.format(myFormat.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(reportSettings.getNextDate().toString());
        if (reformatted.equals(reportSettings.getNextDate().toString())) {
            System.out.println("Generating report");
          setupReport();

            String backupDate = reportSettings.getNextDate().toString();
            Calendar calendar = Calendar.getInstance();
            int currentDay = Integer.parseInt(backupDate.substring(8,10));
            int currentMonth = Integer.parseInt(backupDate.substring(5,7));
            int currentYear = Integer.parseInt(backupDate.substring(0,4));
            calendar.set(currentYear, currentMonth, currentDay);

            Calendar nextMonth = (Calendar) calendar.clone();
            nextMonth.add(Calendar.MONTH, 0);

            reportSettings.setNextDate(new java.sql.Date(nextMonth.getTimeInMillis()));
            reportSettingsDAO.updateDate(reportSettings);


        }
    }

    public void setupReport() {
        try {
          JasperReport stockReport = JasperCompileManager.compileReport("src/TwentyThreeProductions/PDFs/Template/Stock_Level.jrxml");

        DBConnectivity dbConnectivity = new DBConnectivity();
        connection = dbConnectivity.connection(connection);
        JasperPrint  printReport = JasperFillManager.fillReport(stockReport, null, connection);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            String timeStamp = sdf.format(new Date());
            String fileName = "StockReport" + timeStamp + ".pdf";
            String fileLocation = "src/TwentyThreeProductions/PDFs/ExportFile/";
            JasperExportManager.exportReportToPdfFile(printReport, fileLocation + fileName);

            ReportDAO reportDAO = new ReportDAO();
            Report report = new Report();
            report.setReportType(ReportHelper.ReportType.STOCK_LEVEL.toString());
            report.setUsername(DBLogic.getDBInstance().getUsername());
            report.setFileLocation(fileName);
            reportDAO.save(report);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }

/*    public void checkForUpcomingMots() {
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        VehicleDAO vehicleDAO = new VehicleDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        ManufacturerDAO manufacturerDAO = new ManufacturerDAO();
        ReportHelper reportHelper = new ReportHelper();
        for(Vehicle v: vehicleDAO.getAll()) {
            if(v.getLastMOT().equals(null)) {
                Date firstMotDate = v.getVehicleDate();
                firstMotDate.setYear(firstMotDate.getYear() + 3);
                if(sqlDate.equals(firstMotDate)) {
                    String[] parameters = new String[9];
                    for(Customer c: customerDAO.getAll()) {
                        if(v.getCustomerID().equals(c.getCustomerID())) {
                            parameters[0] = "Dear " +c.getFirstName() + " " + c.getLastName() + ",";
                            parameters[1] = c.getCustomerHouseName() + ", " + c.getCustomerStreetName();
                            parameters[2] = c.getCustomerPostcode();
                        }
                    }
                    parameters[8] = v.getRegNo();
                    parameters[9] = String.valueOf(firstMotDate);
                    reportHelper.generateMOTDueReminder(parameters);
                }
            }
            Date nextMotDate = v.getLastMOT();
            nextMotDate.setYear(nextMotDate.getYear() + 1);
            if(sqlDate.equals(nextMotDate)) {
                String[] parameters = new String[9];
                for(Customer c: customerDAO.getAll()) {
                    if(v.getCustomerID().equals(c.getCustomerID())) {
                        parameters[0] = "Dear " +c.getFirstName() + " " + c.getLastName() + ",";
                        parameters[1] = c.getCustomerHouseName() + ", " + c.getCustomerStreetName();
                        parameters[2] = c.getCustomerPostcode();
                    }
                }
                parameters[8] = v.getRegNo();
                parameters[9] = String.valueOf(nextMotDate);
                reportHelper.generateMOTDueReminder(parameters);
            }
        }
    }

    public void checkForLatePayments() {
        java.util.Date currentDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());
        JobDAO jobDAO = new JobDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        ReportHelper reportHelper = new ReportHelper();
        for(Job j: jobDAO.getAll()) {
            if(j.getStatus().equals("Completed") && j.getPaidFor().equals("False")) {
                Date firstLateReminderDate = j.getDateCompleted();
                firstLateReminderDate.setMonth(firstLateReminderDate.getMonth() + 1);
                if(sqlDate.equals(firstLateReminderDate)) {
                    String[] parameters = new String[9];
                    for(Customer c: customerDAO.getAll()) {
                        if(v.getCustomerID().equals(c.getCustomerID())) {
                            parameters[0] = "Dear " +c.getFirstName() + " " + c.getLastName() + ",";
                            parameters[1] = c.getCustomerHouseName() + ", " + c.getCustomerStreetName();
                            parameters[2] = c.getCustomerPostcode();
                        }
                    }
                    parameters[8] = v.getRegNo();
                    parameters[9] = String.valueOf(firstMotDate);
                    reportHelper.generateMOTDueReminder(parameters);
                }
            }
            Date nextMotDate = v.getLastMOT();
            nextMotDate.setYear(nextMotDate.getYear() + 1);
            if(sqlDate.equals(nextMotDate)) {
                String[] parameters = new String[9];
                for(Customer c: customerDAO.getAll()) {
                    if(v.getCustomerID().equals(c.getCustomerID())) {
                        parameters[0] = "Dear " +c.getFirstName() + " " + c.getLastName() + ",";
                        parameters[1] = c.getCustomerHouseName() + ", " + c.getCustomerStreetName();
                        parameters[2] = c.getCustomerPostcode();
                    }
                }
                parameters[8] = v.getRegNo();
                parameters[9] = String.valueOf(nextMotDate);
                reportHelper.generateMOTDueReminder(parameters);
            }
        }
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
