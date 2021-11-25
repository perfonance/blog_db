package model;

public abstract class Person {
    public final Role role;
    public final String username;
    public final String password;

    public Person(String username, Role role, String password) {
        this.username = username;
        this.role = role;
        this.password = password;
    }
}
