package org.everet.android.learn_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERISON = 1;

    public static final String TABLE_ARTICLE = "articles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERISON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_ARTICLE + " (" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + " text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLES IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}