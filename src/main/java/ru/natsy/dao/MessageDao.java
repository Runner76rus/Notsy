package ru.natsy.dao;

import ru.natsy.exception.DaoException;
import ru.natsy.model.Message;
import ru.natsy.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {


    private static final String SAVE_SQL = "INSERT INTO message (text, id_user) VALUES (?, ?)";
    private static final String DELETE_SQL = "DELETE FROM message WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE message SET text = ? WHERE id = ?";
    private static final String GET_SQL = "SELECT * FROM message WHERE id = ?";
    private static final String GET_ALL_SQL = "SELECT * FROM message WHERE id_user = ?";

    private static final MessageDao INSTANCE = new MessageDao();

    private MessageDao() {
    }

    public static MessageDao getInstance() {
        return INSTANCE;
    }

    public boolean update(Message message) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1, message.getText());
            statement.setLong(2, message.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(long id) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void save(Message message) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, message.getText());
            statement.setLong(2, message.getUserId());

            statement.execute();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                message.setId(keys.getLong(1));
            }
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
                long userId = resultSet.getLong("id_user");
                return new Message(id, text, userId);
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
                long uId = resultSet.getLong("id_user");
                messages.add(new Message(id, text, uId));
            }

            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
