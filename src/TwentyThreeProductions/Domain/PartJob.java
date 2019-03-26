package TwentyThreeProductions.Domain;

public class PartJob {

    private int jobID;
    private String partID;
    private String stockUsed;

    public PartJob(){}

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getPartID() {
        return partID;
    }

    public void setPartID(String partID) {
        this.partID = partID;
    }

    public String getStockUsed() {
        return stockUsed;
    }

    public void setStockUsed(String stockUsed) {
        this.stockUsed = stockUsed;
    }
}
