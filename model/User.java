package model;

public class User extends Person {
    public User(String username, String password) {
        super(username, Role.USER, password);
    }


}
