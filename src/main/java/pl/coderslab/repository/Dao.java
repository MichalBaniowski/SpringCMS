package pl.coderslab.repository;

import java.util.List;

public interface Dao<T,PK> {
    List<T> all();

    PK create(T entity);

    T retrieve(PK id);

    T update(T entity);

    boolean delete(PK id);
}
