package com.cardnfc.lib.bninfc.tapcashgo.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class tapcashgo784a extends SQLiteOpenHelper {
    private static final String[] a = new String[]{"_id", "type", "serial", "data", "scanned_at"};

    @Nullable
    public static Cursor cursor(@NonNull Context context) {
        return context.getContentResolver().query(CardProvider.CardProvideruri, a, null, null, "scanned_at DESC, _id DESC");
    }

    public tapcashgo784a(Context context) {
        super(context, "cards.db", null, 2);
    }

    public void onCreate(@NonNull SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE cards (_id        INTEGER PRIMARY KEY, type       TEXT NOT NULL, serial     TEXT NOT NULL, data       BLOB NOT NULL, scanned_at LONG);");
    }

    public void onUpgrade(@NonNull SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i == 1 && i2 == 2) {
            sQLiteDatabase.beginTransaction();
            try {
                sQLiteDatabase.execSQL("ALTER TABLE cards RENAME TO cards_old");
                onCreate(sQLiteDatabase);
                sQLiteDatabase.execSQL("INSERT INTO cards (type, serial, data) SELECT type, serial, data from cards_old");
                sQLiteDatabase.execSQL("DROP TABLE cards_old");
                sQLiteDatabase.setTransactionSuccessful();
            } finally {
                sQLiteDatabase.endTransaction();
            }
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
