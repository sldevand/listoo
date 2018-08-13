package sldevand.fr.listoo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbInit extends SQLiteOpenHelper {

    private static String DB_NAME = "listoo";
    private static DbInit instance;

    private DbInit(Context ctxt) {
        super(ctxt, DB_NAME, null, 1);

    }

    public static DbInit getInstance(Context ctxt) {
        if (instance == null) {
            instance = new DbInit(ctxt);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createCategoryTable(db);
        createElementTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createCategoryTable(SQLiteDatabase db) {
        String sql = "CREATE TABLE categories (" +
                "   id INTEGER PRIMARY KEY NOT NULL" +
                ",  name TEXT NOT NULL" +
                ",  description TEXT" +
                ")";
        db.execSQL(sql);
    }

    private void createElementTable(SQLiteDatabase db) {


        String sql = "CREATE TABLE elements (" +
                "   id INTEGER PRIMARY KEY NOT NULL" +
                ",  name TEXT NOT NULL" +
                ",  description TEXT" +
                ",  categoryId INTEGER NOT NULL" +
                ",  FOREIGN KEY(categoryId) REFERENCES categories(id)" +
                ")";
        db.execSQL(sql);
    }
}

