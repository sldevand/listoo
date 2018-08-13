package sldevand.fr.listoo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import sldevand.fr.listoo.db.DbInit;
import sldevand.fr.listoo.model.AbstractModel;
import sldevand.fr.listoo.model.Category;

public abstract class AbstractDao<ENTITY extends AbstractModel> implements CRUDInterface<ENTITY >{

    protected String tableName;
    protected SQLiteDatabase db;
    protected AbstractDao(Context ctx){
        db = DbInit.getInstance(ctx).getWritableDatabase();
    }
    protected abstract ENTITY makeFromCursor(Cursor cursor);

    @Override
    public Long insert(ENTITY entity) throws SQLException {
        return db.insert(tableName, null, entity.toContentValues());
    }

    @Override
    public ENTITY findById(Integer id) throws SQLException {
        ENTITY entity=null;
        if (id > 0) {
            Cursor cursor = db.query(tableName, new String[]{"*"}, "id = ?", new String[]{id.toString()}, null, null, null);

            if (cursor.moveToFirst()) {
                entity= makeFromCursor(cursor);
            }
        }
        return entity;
    }

    public ENTITY findByName(String name) throws SQLException {
        ENTITY entity=null;
        if (!name.isEmpty()) {
            Cursor cursor = db.query(tableName, new String[]{"*"}, "name = ?", new String[]{name}, null, null, null);

            if (cursor.moveToFirst()) {
                entity= makeFromCursor(cursor);
            }
        }
        return entity;
    }


    @Override
    public List<ENTITY> findAll() throws SQLException {

        Cursor cursor = db.query(tableName, new String[]{"*"}, null, null, null, null, null);
        List<ENTITY> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            ENTITY entity = makeFromCursor(cursor);
            list.add(entity);

        }
        return list;
    }


    @Override
    public Integer update(Integer id, ENTITY entity) throws SQLException {
        return db.update(tableName, entity.toContentValues(), "id = ?", new String[]{id.toString()});
    }

    @Override
    public Integer deleteById(Integer id) throws SQLException {
        return db.delete(tableName, "id = ?", new String[]{String.valueOf(id)});
    }

    @Override
    public Integer count() throws SQLException {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        Integer count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        return count;

    }

    public Integer deleteByName(String name) throws SQLException {
        return db.delete(tableName, "name = ?", new String[]{name});
    }

    public Integer deleteAll() throws SQLException {
        return db.delete(tableName, null, null);
    }




}
