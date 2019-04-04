package TwentyThreeProductions.Model.HelperClasses;

import TwentyThreeProductions.Domain.Report;
import TwentyThreeProductions.Model.DBLogic;
import TwentyThreeProductions.Model.Database.DAO.ReportDAO;
import TwentyThreeProductions.Model.NavigationModel;
import TwentyThreeProductions.Model.SystemNotification;
import net.sf.jasperreports.engine.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ReportHelper {

    public ReportHelper() {}


    public static enum ReportType {
        MOT,
        STOCK_LEVEL,
        MOT_REMINDER,
    }

    public static void saveReportToDB(String name) {
       ReportDAO reportDAO = new ReportDAO();
        Report report = new Report();
        report.setReportType(ReportHelper.ReportType.STOCK_LEVEL.toString());
        report.setUsername(DBLogic.getDBInstance().getUsername());
        report.setFileLocation(name);
        reportDAO.save(report);
    }

    public static void generateMOTDueReminder(String args[]) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/TwentyThreeProductions/PDFs/Template/MOTReminder.jrxml");

            /*
            /////Sample Data that you will pass in the args[]\\\\\
            String customerName = "Jalp Desai";
            String customerCity = "London";
            String customerAdd = "12 Street Street";
            String customerPostcode = "CR0 0NS";
            String customerAddNew = "Dear Jalp Desai,";
            String manfname = "Shady Shower Comapny";
            String manfstreet = "12 Shower Street";
            String manfcity = "Liverpool";
            String vechReg = "AA90 8JB";
            String renewaldate = "12-02-2019";
            */

            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("cust_name", args[0]);
            parameter.put("house_street_name", args[1]);
            parameter.put("cust_city", args[2]);
            parameter.put("cust_postcode", args[3]);
            parameter.put("manf_name", args[4]);
            parameter.put("manf_street", args[5]);
            parameter.put("manf_city", args[6]);
            parameter.put("cust_add_name", args[7]);
            parameter.put("vech_reg_no", args[8]);
            parameter.put("mot_renewal_date", args[9]);

            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            String timeStamp = sdf.format(new Date());
            String fileName = "MOT" + args[0] + timeStamp + ".pdf";
            String fileLocation = "src/TwentyThreeProductions/PDFs/ExportFile/";
            saveReportToDB(fileName);
            JasperExportManager.exportReportToPdfFile(print, fileLocation + fileName);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public static void generatefirstlatereminder(String args[]) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/TwentyThreeProductions/PDFs/Template/FirstReminder.jrxml");

            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("cust_name", args[0]);
            parameter.put("house_street_name", args[1]);
            parameter.put("cust_city", args[2]);
            parameter.put("cust_postcode", args[3]);
            parameter.put("manuf_name", args[4]);
            parameter.put("manuf_street", args[5]);
            parameter.put("city_postcode", args[6]);
            parameter.put("cust_add_name", args[7]);
            parameter.put("cust_invo_no", args[8]);
            parameter.put("vech_reg_no", args[9]);
            parameter.put("cust_total_amount", args[10]);
            parameter.put("cust_invo_date", args[11]);

            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            String timeStamp = sdf.format(new Date());
            String fileName = "firstreminder" + args[0] + timeStamp + ".pdf";
            String fileLocation = "src/TwentyThreeProductions/PDFs/ExportFile/";
            saveReportToDB(fileName);
            JasperExportManager.exportReportToPdfFile(print, fileLocation + fileName);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public static void generateseconglatereminder(String args[]) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/TwentyThreeProductions/PDFs/Template/FirstReminder.jrxml");

            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("cust_name", args[0]);
            parameter.put("house_street_name", args[1]);
            parameter.put("cust_city", args[2]);
            parameter.put("cust_postcode", args[3]);
            parameter.put("manuf_name", args[4]);
            parameter.put("manuf_street", args[5]);
            parameter.put("city_postcode", args[6]);
            parameter.put("cust_add_name", args[7]);
            parameter.put("cust_invo_no", args[8]);
            parameter.put("vech_reg_no", args[9]);
            parameter.put("cust_total_amount", args[10]);
            parameter.put("cust_invo_date", args[11]);

            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            String timeStamp = sdf.format(new Date());
            String fileName = "secondreminder" + args[0] + timeStamp + ".pdf";
            String fileLocation = "src/TwentyThreeProductions/PDFs/ExportFile/";
            saveReportToDB(fileName);
            JasperExportManager.exportReportToPdfFile(print, fileLocation + fileName);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    public static void generatelastlatereminder(String args[]) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/TwentyThreeProductions/PDFs/Template/FirstReminder.jrxml");

            HashMap<String, Object> parameter = new HashMap<>();
            parameter.put("cust_name", args[0]);
            parameter.put("house_street_name", args[1]);
            parameter.put("cust_city", args[2]);
            parameter.put("cust_postcode", args[3]);
            parameter.put("manuf_name", args[4]);
            parameter.put("manuf_street", args[5]);
            parameter.put("city_postcode", args[6]);
            parameter.put("cust_add_name", args[7]);
            parameter.put("cust_invo_no", args[8]);
            parameter.put("vech_reg_no", args[9]);
            parameter.put("cust_total_amount", args[10]);
            parameter.put("cust_invo_date", args[11]);

            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
            String timeStamp = sdf.format(new Date());
            String fileName = "lastreminder" + args[0] + timeStamp + ".pdf";
            String fileLocation = "src/TwentyThreeProductions/PDFs/ExportFile/";
            saveReportToDB(fileName);
            JasperExportManager.exportReportToPdfFile(print, fileLocation + fileName);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }



}
