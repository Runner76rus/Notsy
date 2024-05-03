package ru.natsy.model;

import java.util.Objects;

public class Message {
    private long id;
    private String text;

    private long userId;

    public Message() {
    }

    public Message(long id, String text, long userId) {
        this.id = id;
        this.text = text;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id && userId == message.userId && Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, userId);
    }

    @Override
    public String toString() {
        return "Message{" +
               "id=" + id +
               ", text='" + text + '\'' +
               ", userId=" + userId +
               '}';
    }
}
