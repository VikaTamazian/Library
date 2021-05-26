package lib.dao;

import java.sql.SQLException;
import java.util.List;

public interface CommonDao<T> {

    T create(T value) throws SQLException;

    T readOne(Integer value) throws SQLException;

    List<T> readAll() throws SQLException;

    boolean update(Integer value) throws SQLException;

    boolean delete(Integer value) throws SQLException;

}
