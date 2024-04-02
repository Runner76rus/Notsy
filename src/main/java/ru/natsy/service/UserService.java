package ru.natsy.service;

import ru.natsy.model.User;
import ru.natsy.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService {

    public static void main(String[] args) {
        try (Connection connection = ConnectionManager.open()) {
            String query = """
                    INSERT INTO usr (username, password ,first_name, second_name, email, phone_number)
                    VALUES ('admin','12345','Антон','Yazgevich','yazgevich@mail.ru','+79806569561');
                    """;
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(long id) {
        return null;
    }

    public void addUser(User user) {
        try (Connection connection = ConnectionManager.open()) {
            String query = """
                    INSERT INTO usr (username, password ,first_name, second_name, email, phone_number)
                    VALUES ('%s','%s','%s','%s','%s','%s');
                    """.formatted(
                    user.getUsername(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getSecondName(),
                    user.getEmail(),
                    user.getPhoneNumber());
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
    }

    public void removeUser(long id) {
    }

}
