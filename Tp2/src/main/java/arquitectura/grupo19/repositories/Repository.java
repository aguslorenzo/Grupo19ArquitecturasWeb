package arquitectura.grupo19.repositories;

import java.sql.SQLException;

public interface Repository<T> {
    T find(int id);
    void insert(T obj) throws SQLException;
    void update(int id, T obj);
    void delete(int id);
}
