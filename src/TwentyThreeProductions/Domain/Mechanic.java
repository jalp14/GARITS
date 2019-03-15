package TwentyThreeProductions.Domain;

public class Mechanic {

    private String username;
    private float hourlyRate;

    public Mechanic(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
