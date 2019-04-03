package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Mechanic;
import TwentyThreeProductions.Domain.Payment;

import java.util.ArrayList;

public interface IPayment {
    ArrayList<Payment> getAll();
    void save(Payment payment);
    void saveCash(Payment payment);
    void update(Payment payment);
    void delete(Payment payment);
}
