package com.bm.main.scm.shortcutbadger.util;

import android.database.Cursor;
import androidx.annotation.Nullable;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author leolin
 */
public class CloseHelper {

    public static void close(@Nullable Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }


    public static void closeQuietly(@Nullable Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException var2) {

        }
    }
}
