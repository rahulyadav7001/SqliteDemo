package com.ryandro.sqlitedatabasedemo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
    private DataBaseHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    public DataBaseManager(Context context) {
        this.context = context;
    }

    public DataBaseManager open() throws SQLException{
        dbHelper = new DataBaseHelper(context);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DataBaseHelper.SUBJECT, name);
        contentValue.put(DataBaseHelper.DESC, desc);
        sqLiteDatabase.insert(DataBaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[]{DataBaseHelper._ID, DataBaseHelper.SUBJECT, DataBaseHelper.DESC};
        Cursor cursor = sqLiteDatabase.query(DataBaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.SUBJECT, name);
        contentValues.put(DataBaseHelper.DESC, desc);
        int i = sqLiteDatabase.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        sqLiteDatabase.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper._ID + "=" + _id, null);
    }
}
