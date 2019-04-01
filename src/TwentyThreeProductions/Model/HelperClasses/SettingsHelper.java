package TwentyThreeProductions.Model.HelperClasses;

//import net.sf.jasperreports.renderers.SimpleDataRenderer;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class SettingsHelper  {

    private static String FILE_NAME = "GARITS_SETTINGS.txt";
    private FileWriter fileWriter;
    private File file;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private static SettingsHelper settingsHelper = null;
    private String backupDate;
    private String dateFromFile;

    private Calendar calendar;

    private SettingsHelper() {
        file = new File(FILE_NAME);
        calendar = Calendar.getInstance();
    }

    public static SettingsHelper getInstance() {
        if (settingsHelper == null){
            settingsHelper = new SettingsHelper();
        }
        return settingsHelper;
    }

    public void setBackupDate(String backupDate) {
        this.backupDate = backupDate;
    }


    public boolean checkDate() {
        readDate();
        LocalDateTime localDateTime = LocalDateTime.now();
        String reformatted = "";
        String currentDate = Integer.toString(localDateTime.getYear()) + "-" + Integer.toString(localDateTime.getMonthValue()) + "-" + Integer.toString(localDateTime.getDayOfMonth());
        System.out.println("Today's Date : " + currentDate);
        SimpleDateFormat myFormat  = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
            reformatted = format.format(myFormat.parse(currentDate));
            System.out.println("Reformatted Date : " + reformatted);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFromFile.equals(reformatted);
    }

    public String getDateFromFile() {
        return dateFromFile;
    }



    public String setupNextMonth() {
        int currentDay = Integer.parseInt(backupDate.substring(8,10));
        int currentMonth = Integer.parseInt(backupDate.substring(5,7));
        int currentYear = Integer.parseInt(backupDate.substring(0,4));
        calendar.set(currentYear, currentMonth, currentDay);

        Calendar nextMonth = (Calendar) calendar.clone();
        nextMonth.add(Calendar.MONTH, 0);

        System.out.println("Current Date : " + currentDay + ":" + currentMonth + ":" + currentYear);
        System.out.println("Next Date : " + nextMonth.getTime());

        return nextMonth.getTime().toString();
    }

    public void writeDate() {
        try {
            fileWriter = new FileWriter(file, false);
            fileWriter.write("Backup Date" + "," + backupDate + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDate() {
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] tokens = line.split(",");
                dateFromFile = tokens[1];
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
