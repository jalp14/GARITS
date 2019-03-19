package TwentyThreeProductions.Domain;

import java.util.List;

public class Admin {

    private String username;
    private String password;
    private String name;
    private List<User> users;
    private List<Backup> backups;

    public Admin(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Backup> getBackups() {
        return backups;
    }

    public void setBackups(List<Backup> backups) {
        this.backups = backups;
    }
}
