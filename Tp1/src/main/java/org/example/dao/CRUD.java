package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface CRUD<T> {
    List<T> getAll();
    Optional<T> getById(Integer id);
    void insert(T t);
    void update(T t);
    void delete(T t);
}
