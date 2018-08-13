package sldevand.fr.listoo.dao;


import android.database.SQLException;

import java.util.List;

public interface CRUDInterface<ENTITY> {

    Long insert(ENTITY entity) throws SQLException;

    ENTITY findById(Integer id) throws SQLException;

    List<ENTITY> findAll() throws SQLException;

    Integer update(Integer id, ENTITY entity) throws SQLException;

    Integer deleteById(Integer id) throws SQLException;

    Integer count() throws SQLException;
}
