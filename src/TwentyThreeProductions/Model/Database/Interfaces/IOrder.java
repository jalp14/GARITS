package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.Mechanic;
import TwentyThreeProductions.Domain.Order;

import java.util.ArrayList;

public interface IOrder {
    ArrayList<Order> getAll();
    void save(Order order);
    void update(Order order);
    void delete(Order order);
}
