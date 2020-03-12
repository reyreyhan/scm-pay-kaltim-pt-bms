package com.bm.main.single.ftl.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper
{
    @Nullable
    protected String tableName = null;

    public Database(Context context, String tableName)
    {
        super(context, "sbf", null, 2);

        this.tableName = tableName;
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase database)
    {
        database.execSQL("create table " + tableName + " ("
                + " key VARCHAR(25) NOT NULL, "
                + " value TEXT)");
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);

        onCreate(db);
    }
}