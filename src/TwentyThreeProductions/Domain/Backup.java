package TwentyThreeProductions.Domain;

import java.sql.Date;
import java.time.LocalDateTime;

public class Backup {

    private int backupID;
    private String date;
    private String fileLocation;

    public Backup(){}

    public int getBackupID() {
        return backupID;
    }

    public void setBackupID(int backupID) {
        this.backupID = backupID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
