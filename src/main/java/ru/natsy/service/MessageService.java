package ru.natsy.service;

import ru.natsy.dao.MessageDao;
import ru.natsy.model.Message;

import java.util.List;

public class MessageService {

    private final MessageDao messageDao = MessageDao.getInstance();

    public void add(Message message) {
        messageDao.save(message);
    }

    public Message get(long id) {
        return messageDao.findById(id);
    }

    public List<Message> getAll(long userId) {
    return messageDao.findAll(userId);
    }

    public boolean delete(long id) {
        return messageDao.delete(id);
    }

    public boolean update(Message message) {
        return messageDao.update(message);
    }
}
