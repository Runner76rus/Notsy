package ru.natsy.dao;

import java.util.List;

public interface Dao<K, E> {

    E findById(K id);

    List<E> findAll();

    void save(E e);

    boolean update(E e);

    boolean delete(K e);
}
