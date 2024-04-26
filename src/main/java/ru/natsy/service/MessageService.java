package ru.natsy.service;

import ru.natsy.model.Message;
import ru.natsy.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageService {


    public void addMassage(Message message) {
        try (Connection connection = ConnectionManager.get()) {
            String query = """
                    INSERT INTO message (text, id_user)
                    VALUES (?,?);
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, message.getText());
            statement.setLong(2, message.getUserId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Message getMessage(long id) {
        try (Connection connection = ConnectionManager.get()) {
            String query = """
                    SELECT * FROM message
                    WHERE id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String text = resultSet.getString("text");
                return new Message(id, text);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Message> getAll(long userId) {
        try (Connection connection = ConnectionManager.get()) {
            String query = """
                    SELECT * FROM message
                    WHERE id_user = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
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
