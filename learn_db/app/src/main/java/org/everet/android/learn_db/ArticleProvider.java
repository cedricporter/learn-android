package org.everet.android.learn_db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by cedricporter on 14/12/27.
 */
public class ArticleProvider extends ContentProvider {

    MySQLiteHelper mDBHelper;

    static final int ARTICLES = 10;
    static final int ARTICLE_ID = 20;

    static final String AUTHORITY = "org.everet.android.learn_db.contentprovider";
    static final String BASE_PATH = "articles";
    static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
//    static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/articles";
//    static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/article";

    static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, ARTICLES);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", ARTICLE_ID);
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new MySQLiteHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(MySQLiteHelper.TABLE_ARTICLE);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case ARTICLES:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDBHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case ARTICLES:
                id = sqlDB.insert(MySQLiteHelper.TABLE_ARTICLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDBHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case ARTICLES:
                rowsDeleted = sqlDB.delete(MySQLiteHelper.TABLE_ARTICLE, selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
