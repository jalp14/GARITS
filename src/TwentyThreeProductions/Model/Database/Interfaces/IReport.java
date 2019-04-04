package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Report;

import java.util.ArrayList;

public interface IReport {
    ArrayList<Report> getAll();
    void save(Report report);
    void update(Report report);
    void delete(Report report);
    public String getHtmlLocation(String location);
}
