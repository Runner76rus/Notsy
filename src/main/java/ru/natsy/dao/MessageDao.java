package ru.natsy.dao;

import ru.natsy.exception.DaoException;
import ru.natsy.model.Message;
import ru.natsy.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {


    private static final String SAVE_SQL = "INSERT INTO message (text, id_user) VALUES (?, ?)";
    private static final String DELETE_SQL = "DELETE FROM message WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE message SET text = ? WHERE id = ?";
    private static final String GET_SQL = "SELECT * FROM message WHERE id = ?";
    private static final String GET_ALL_SQL = "SELECT * FROM message WHERE id_user = ?";

    private static final MessageDao INSTANCE = new MessageDao();

    private MessageDao(){}

    public static MessageDao getInstance() {
        return INSTANCE;
    }

    public boolean update(Message message){
        try(Connection connection = ConnectionManager.get()){
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, message.getText());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(long id){
        try(Connection connection = ConnectionManager.get()){
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void save(Message message) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL);
            statement.setString(1, message.getText());
            statement.setLong(2, message.getUserId());

            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Message findById(long id) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(GET_SQL);
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

    public List<Message> findAll(long userId) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_SQL);
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
