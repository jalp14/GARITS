package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Part;

import java.util.ArrayList;

public interface IPart {
    ArrayList<Part> getAll();
    void save(Part part);
    void update(Part part);
    void delete(Part part);
}
