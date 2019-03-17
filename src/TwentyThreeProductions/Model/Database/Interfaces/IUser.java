package TwentyThreeProductions.Model.Database.Interfaces;

import TwentyThreeProductions.Domain.User;

import java.util.ArrayList;

public interface IUser {
    ArrayList<User> getAll();
    void save(User user);
    void update(User user);
    void delete(User user);
}
