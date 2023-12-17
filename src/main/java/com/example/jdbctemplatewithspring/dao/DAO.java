package com.example.jdbctemplatewithspring.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    List<T> list();

    Optional<T> get(int id);

    int create(T t);

    boolean update(T t, int id);

    boolean delete(int id);
}
