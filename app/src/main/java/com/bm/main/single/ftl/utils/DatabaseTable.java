package com.bm.main.single.ftl.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;

public class DatabaseTable
{
    protected Database database;

    protected SQLiteDatabase writable;
    protected SQLiteDatabase readable;

    @Nullable
    private String tableName = null;

    public DatabaseTable(Context context, String tableName)
    {
        database = new Database(context, tableName);

        this.tableName = tableName;
    }

    public void open() throws SQLException
    {
        if(writable != null) writable.close();
        if(readable != null) readable.close();

        writable = database.getWritableDatabase();
        readable = database.getReadableDatabase();
    }

    @Nullable
    public String read(String key)
    {
        Cursor cursor = readable.query(getTableName(), new String[] {"key", "value"}, "`key` = ?", new String[] {key}, null, null, null, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();

            return cursor.getString(1);
        }
        else
        {
            return null;
        }
    }

    public long save(String key, String value)
    {
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);

        return writable.insert(getTableName(), null, values);
    }

    public long update(String key, String value)
    {
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);

        return writable.update(getTableName(), values, "`key` = ?", new String[]{key});
    }

    public void close()
    {
        database.close();
    }

    @Nullable
    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
}
