package sldevand.fr.listoo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import sldevand.fr.listoo.model.Category;
import sldevand.fr.listoo.model.Element;

public class ElementDao extends AbstractDao<Element> {

    private static ElementDao instance;
    private CategoryDao categoryDao;

    private ElementDao(Context ctx) {
        super(ctx);
        tableName = "elements";
    }

    public static ElementDao getInstance(Context ctx) {
        if (null == ElementDao.instance) {
            ElementDao.instance = new ElementDao(ctx);
        }
        return ElementDao.instance;
    }


    public List<Element> findByCategoryId(Integer id) throws SQLException {

        Cursor cursor = db.query(tableName, new String[]{"*"}, "categoryId = ?", new String[]{id.toString()}, null, null, null);
        List<Element> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            Element entity = makeFromCursor(cursor);
            list.add(entity);
        }
        return list;
    }

    @Override
    public Element makeFromCursor(Cursor cursor) {
        Element elt = new Element(-1, "Unknown", -1);
        elt.setId(cursor.getInt(cursor.getColumnIndex("id")));
        elt.setName(cursor.getString(cursor.getColumnIndex("name")));
        elt.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        elt.setCategoryId(cursor.getInt(cursor.getColumnIndex("categoryId")));
        return elt;
    }


}
