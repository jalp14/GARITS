package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.OrderPart;

import java.util.ArrayList;

public interface IOrderPart {
    ArrayList<OrderPart> getAll();
    void save(OrderPart orderPart);
    void update(OrderPart orderPart);
    void delete(OrderPart orderPart);
}
