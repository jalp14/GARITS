package TwentyThreeProductions.Model.Database.Interfaces;


import TwentyThreeProductions.Domain.Discount;


public interface IDiscount {
    Discount getDiscount(int customerID);
    void save(Discount discount);
    void update(Discount discount);
    void delete(int customerID);
}
