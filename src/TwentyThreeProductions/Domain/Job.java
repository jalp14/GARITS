package TwentyThreeProductions.Domain;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

public class Job {

    private int jobID;
    private String username;
    private int customerID;
    private String registrationID;
    private Date dateBookedIn;
    private String description;
    private String sparePartsUsed;
    private String status;
    private String paidFor;
    private Date dateCompleted;
    private String checked;
    private Time duration;
    private List<PartJob> partJobs;
    private List<JobTask> jobTasks;

    public Job(){}

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public Date getDateBookedIn() {
        return dateBookedIn;
    }

    public void setDateBookedIn(Date dateBookedIn) {
        this.dateBookedIn = dateBookedIn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSparePartsUsed() {
        return sparePartsUsed;
    }

    public void setSparePartsUsed(String sparePartsUsed) {
        this.sparePartsUsed = sparePartsUsed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaidFor() {
        return paidFor;
    }

    public void setPaidFor(String paidFor) {
        this.paidFor = paidFor;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public List<PartJob> getPartJobs() {
        return partJobs;
    }

    public void setPartJobs(List<PartJob> partJobs) {
        this.partJobs = partJobs;
    }

    public List<JobTask> getJobTasks() {
        return jobTasks;
    }

    public void setJobTasks(List<JobTask> jobTasks) {
        this.jobTasks = jobTasks;
    }
}
