package ru.natsy.service;

import ru.natsy.model.Message;
import ru.natsy.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageService {


    public void addMassage(Message message) {
        try (Connection connection = ConnectionManager.open()) {
            String query = """
                    INSERT INTO message (text, id_user)
                    VALUES ('%s','%d');
                    """.formatted(
                    message.getText(),
                    message.getUserId()
            );
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Message getMessage(long id) {
        try (Connection connection = ConnectionManager.open()) {
            String query = """
                    SELECT * FROM message
                    WHERE id = %d;
                    """.formatted(id);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()){
                String text = resultSet.getString("text");
                return new Message(id, text);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Message> getAll(long userId) {
        try (Connection connection = ConnectionManager.open()) {
            String query = """
                    SELECT * FROM message
                    WHERE id_user = %d;
                    """.formatted(userId);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Message> messages = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String text = resultSet.getString("text");

                messages.add(new Message(id, text));
            }

            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
