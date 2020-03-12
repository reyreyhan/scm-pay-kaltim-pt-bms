package com.cardnfc.lib.bninfc.tapcashgo.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

public class CardProvider extends ContentProvider {
    public static final Uri CardProvideruri = Uri.parse("content://id.co.bni.tapcashgo.cardprovider/cards");
    @NonNull
    private static UriMatcher uriMatcher = new UriMatcher(-1);
    @Nullable
    private tapcashgo784a tapcashgo784a;
    public CardProvider() {
    }
    static {
        uriMatcher.addURI("id.co.bni.tapcashgo.cardprovider", "cards", 1);
        uriMatcher.addURI("id.co.bni.tapcashgo.cardprovider", "cards/#", 2);
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase writableDatabase = this.tapcashgo784a.getWritableDatabase();
        int i = 0;
        switch (CardProvider.uriMatcher.match(uri)) {
            case 1:
                i = writableDatabase.delete("cards", selection, selectionArgs);
                break;
            case 2:
                i = writableDatabase.delete("cards", "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (CardProvider.uriMatcher.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/id.co.bni.tapcashgo.card";
            case 2:
                return "vnd.android.cursor.item/id.co.bni.tapcashgo.card";
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        if (CardProvider.uriMatcher.match(uri) != 1) {
            throw new IllegalArgumentException("Incorrect URI: " + uri);
        }
        Long.valueOf(System.currentTimeMillis());
        Uri withAppendedId = ContentUris.withAppendedId(CardProvideruri, this.tapcashgo784a.getWritableDatabase().insertOrThrow("cards", null, values));
        getContext().getContentResolver().notifyChange(withAppendedId, null);
        return withAppendedId;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        this.tapcashgo784a = new tapcashgo784a(getContext());
        return true;

    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        switch (CardProvider.uriMatcher.match(uri)) {
            case 1:
                sQLiteQueryBuilder.setTables("cards");
                break;
            case 2:
                sQLiteQueryBuilder.setTables("cards");
                sQLiteQueryBuilder.appendWhere("_id = " + ((String) uri.getPathSegments().get(1)));
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor query = sQLiteQueryBuilder.query(this.tapcashgo784a.getReadableDatabase(), null, selection, selectionArgs, null, null, sortOrder);
        query.setNotificationUri(getContext().getContentResolver(), uri);
        return query;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int update;
        SQLiteDatabase writableDatabase = this.tapcashgo784a.getWritableDatabase();
        switch (CardProvider.uriMatcher.match(uri)) {
            case 1:
                update = writableDatabase.update("cards", values, selection, selectionArgs);
                break;
            case 2:
                update = writableDatabase.update("cards", values, "_id=" + ((String) uri.getPathSegments().get(1)) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return update;

    }
}
