package ru.natsy.service;

import org.junit.jupiter.api.*;
import ru.natsy.exception.DaoException;
import ru.natsy.model.Message;
import ru.natsy.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class MessageServiceTest {

    private static final String ADD_USER_SQL = """
            INSERT INTO usr (username, password ,first_name,
                             second_name, email, phone_number)
            VALUES (?,?,?,?,?,?)
            """;
    private static final String DELETE_USER_SQL = "DELETE FROM usr WHERE id=?";
    private static final String SAVE_SQL = "INSERT INTO message (text, id_user) VALUES (?, ?)";
    private static final String DELETE_SQL = "DELETE FROM message WHERE id = ?";
    private static final String GET_SQL = "SELECT * FROM message WHERE id = ?";

    private static final Message message = new Message();

    MessageService messageService = new MessageService();


    @BeforeAll
    static void before() {
        message.setId(0);
        message.setText("test");

        //need to get valid user_id
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(ADD_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "test");
            statement.setString(2, "test");
            statement.setString(3, "test");
            statement.setString(4, "test");
            statement.setString(5, "test@test.com");
            statement.setString(6, "+7123456789");
            statement.execute();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                message.setUserId(keys.getLong(1));
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @AfterAll
    static void after() {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_SQL);
            statement.setLong(1, message.getUserId());
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Test
    void update() {
        save();
        try {
            Message newMsg = new Message();
            newMsg.setId(message.getId());
            newMsg.setText("foobar");
            newMsg.setUserId(message.getUserId());

            messageService.update(newMsg);

            Message m = read(message.getId());
            Assertions.assertNotEquals(message.getText(), m.getText());
        } finally {
            delete(message.getId());
        }
    }


    @Test
    void remove() {
        save();
        try {
            boolean removed = messageService.remove(message.getId());
            Assertions.assertTrue(removed);
        } finally {
            delete(message.getId());
        }
    }

    @Test
    void get() {
        save();
        try {
            Message msg = messageService.get(message.getId());
            System.out.println(msg.getUserId());
            Assertions.assertEquals(msg.getText(), "test");
            Assertions.assertEquals(msg.getUserId(), message.getUserId());
            if (msg.getId() > 0) messageService.remove(msg.getId());
        } finally {
            delete(message.getId());
        }
    }

    @Test
    void add() {
        try {
            messageService.add(message);
            if (message.getId() == 0) Assertions.fail();
            Message m = read(message.getId());
            Assertions.assertEquals(m.getText(), "test");
            Assertions.assertEquals(m.getUserId(), message.getUserId());
        } finally {
            delete(message.getId());
        }
    }

    @Test
    void getAll() {
        List<Message> srcMessages = new ArrayList<>();
        try {
            for (int i = 0; i < 5; i++) {
                Message m = new Message();
                m.setText("text" + i);
                m.setUserId(message.getUserId());
                messageService.add(m);
                srcMessages.add(m);
            }
            List<Message> newMessages = messageService.getAll(message.getUserId());
            if (newMessages == null || newMessages.size() < 5) Assertions.fail();
            for (Message srcMessage : srcMessages) {
                Assertions.assertTrue(newMessages.contains(srcMessage));
            }
        } finally {
            for (Message m : srcMessages) {
                if (m.getId() > 0) {
                    delete(m.getId());
                }
            }
        }
    }

    private Message read(long id) {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement statement = connection.prepareStatement(GET_SQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            Message m = new Message();
            m.setId(resultSet.getLong("id"));
            m.setText(resultSet.getString("text"));
            m.setUserId(resultSet.getLong("id_user"));
            return m;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void save() {
        try (Connection connection = ConnectionManager.get()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, message.getText());
            preparedStatement.setLong(2, message.getUserId());
            preparedStatement.execute();

            ResultSet keys = preparedStatement.getGeneratedKeys();
            keys.next();
            message.setId(keys.getLong(1));
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void delete(long id) {
        if (id > 0) {
            try (Connection connection = ConnectionManager.get()) {
                PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
                statement.setLong(1, id);
                statement.execute();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
}