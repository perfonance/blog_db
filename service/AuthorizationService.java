package service;

import model.Person;
import model.Role;
import utils.DBQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorizationService {
    private final Connection connection;

    public AuthorizationService(Connection connection) {
        this.connection = connection;
    }

    public Role login(String username, String password) throws SQLException {
        PreparedStatement loginStatement = connection.prepareStatement(DBQueries.login());
        loginStatement.setString(1, username);
        loginStatement.setString(2, password);
        ResultSet resultSet = loginStatement.executeQuery();
        if (resultSet.next()) {
            String role = resultSet.getString("role");
            if (role.equals("admin")) {
                return Role.ADMIN;
            } else {
                return Role.USER;
            }
        } else {
            return null;
        }
    }

    public void addUser(Connection connection, Person person) throws SQLException {
        PreparedStatement addUser = connection.prepareStatement(DBQueries.addUser());

        addUser.setString(1, person.username);
        addUser.setString(2, person.password);
        if (person.role == Role.ADMIN) {
            addUser.setString(3, "admin");
        } else {
            addUser.setString(3, "user");
        }
        addUser.executeQuery();
    }

    public void removeUser(Connection connection, String username) throws SQLException {
        PreparedStatement removeUser = connection.prepareStatement(DBQueries.removeUser());
        removeUser.setString(1, username);
        removeUser.executeUpdate();
    }
}
