package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Customer;
import TwentyThreeProductions.Domain.Invoice;

import java.util.ArrayList;

public interface IInvoice {
    ArrayList<Invoice> getAll();
    void save(Invoice invoice);
    void update(Invoice invoice);
    void delete(Invoice invoice);
}
