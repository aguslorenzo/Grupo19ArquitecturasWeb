package arquitectura.grupo19.repositories;

import java.sql.SQLException;

public interface Repository<T, ID> {
    T find(ID id);
    void insert(T obj) throws SQLException;
    void update(ID id, T obj);
    void delete(ID id);
}
