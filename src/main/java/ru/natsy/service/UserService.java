package ru.natsy.service;

import ru.natsy.model.User;
import ru.natsy.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserService {

    public User getUser(long id) {
        return null;
    }

    public void addUser(User user) {
        try (Connection connection = ConnectionManager.get()) {
            String query = """
                    INSERT INTO usr (username, password ,first_name, second_name, email, phone_number)
                    VALUES (?,?,?,?,?,?);
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getSecondName());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhoneNumber());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
    }

    public void removeUser(long id) {
    }

}
