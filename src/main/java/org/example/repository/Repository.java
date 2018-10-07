package org.example.repository;

import java.util.List;

public interface Repository<T, ID> {

    void delete(T t);

    void update(T t);

    ID create(T t);

    T get(ID id);

    List<T> getAll();

    void deleteAll();

    void flush();
}
