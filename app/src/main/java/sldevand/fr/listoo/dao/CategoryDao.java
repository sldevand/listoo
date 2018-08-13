package sldevand.fr.listoo.dao;

import android.content.Context;
import android.database.Cursor;

import sldevand.fr.listoo.model.Category;

public class CategoryDao extends AbstractDao<Category> {

    private static CategoryDao instance;

    private CategoryDao(Context ctx) {
        super(ctx);
        tableName = "categories";
    }

    public static CategoryDao getInstance(Context ctx) {
        if (null == CategoryDao.instance) {
            CategoryDao.instance = new CategoryDao(ctx);
        }
        return CategoryDao.instance;
    }

    @Override
    protected Category makeFromCursor(Cursor cursor) {
        Category cat = new Category(-1, "Inconnu", "");
        cat.setId(cursor.getInt(cursor.getColumnIndex("id")));
        cat.setName(cursor.getString(cursor.getColumnIndex("name")));
        cat.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        return cat;
    }


}
