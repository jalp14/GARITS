package TwentyThreeProductions.Domain;

import java.sql.Date;

public class Backup {

    private int backupID;
    private Date date;
    private String fileLocation;

    public Backup(){}

    public int getBackupID() {
        return backupID;
    }

    public void setBackupID(int backupID) {
        this.backupID = backupID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
