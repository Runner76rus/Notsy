package ru.natsy.dao;

import ru.natsy.exception.DaoException;
import ru.natsy.model.User;
import ru.natsy.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<Long, User> {

    private static final UserDao instance = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO usr (username, password ,first_name,
                             second_name, email, phone_number)
            VALUES (?,?,?,?,?,?)
            """;
    private static final String DELETE_SQL = "DELETE FROM usr WHERE id=?";
    private static final String UPDATE_SQL = """
            UPDATE usr SET username=?,password=?,first_name=?
                         ,second_name=?,email=?,phone_number=?
            WHERE id=?
            """;
    private static final String GET_SQL = "SELECT * FROM usr WHERE id=?";
    private static final String GET_ALL_SQL = "SELECT * FROM usr";

    private UserDao() {
    }

    public static UserDao getInstance() {
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(GET_ALL_SQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User findById(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(GET_SQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getUser(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return null;
    }

    @Override
    public void save(User user) {
        save(user, SAVE_SQL);
    }

    @Override
    public boolean update(User user) {
        return save(user, UPDATE_SQL);
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private boolean save(User user, String query) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getSecondName());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhoneNumber());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        return user;
    }
}
