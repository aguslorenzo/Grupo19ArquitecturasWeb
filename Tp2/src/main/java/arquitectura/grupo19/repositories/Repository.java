package arquitectura.grupo19.repositories;

import java.sql.SQLException;

public interface Repository<T> {
    T find(long id);
    void insert(T obj) throws SQLException;
    void update(long id, T obj);
    void delete(long id);
}
