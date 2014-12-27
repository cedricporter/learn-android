package org.everet.android.learn_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    ListView mListView;
    Button mBtnAdd;
    ArticleDataSource mDatasource;
    ArrayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        mBtnAdd = (Button) findViewById(R.id.btnAdd);

        mDatasource = new ArticleDataSource(this);
        mDatasource.open();

        List<Article> values = mDatasource.getAllArticle();

        mAdapter = new ArrayAdapter<Article>(this, android.R.layout.simple_list_item_1, values);
        mListView.setAdapter(mAdapter);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Article article = mDatasource.createArticle(generateString(10));
                mAdapter.add(article);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    class ArticleDataSource {
        SQLiteDatabase mSQLiteDatabase;
        MySQLiteHelper mDBHelper;
        private String[] mAllColumns = {
                MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME,
        };

        public ArticleDataSource(Context context) {
            mDBHelper = new MySQLiteHelper(context);
        }

        public void open() {
            mSQLiteDatabase = mDBHelper.getWritableDatabase();
        }

        public void close() {
            mDBHelper.close();
        }

        public Article createArticle(String name) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NAME, name);
            long insertID = mSQLiteDatabase.insert(MySQLiteHelper.TABLE_ARTICLE, null, values);
            Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_ARTICLE, mAllColumns, MySQLiteHelper.COLUMN_ID + " = " + insertID, null, null, null, null);
            cursor.moveToFirst();
            Article article = cursorToArticle(cursor);
            cursor.close();
            return article;
        }

        public List<Article> getAllArticle() {
            List<Article> list = new ArrayList<>();

            Cursor cursor = mSQLiteDatabase.query(MySQLiteHelper.TABLE_ARTICLE, mAllColumns, null, null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Article article = cursorToArticle(cursor);
                list.add(article);
                cursor.moveToNext();
            }
            cursor.close();
            return list;
        }

        private Article cursorToArticle(Cursor cursor) {
            Article article = new Article();
            article.setId(cursor.getLong(0));
            article.setName(cursor.getString(1));
            return article;
        }
    }

    public static String generateString(int length)
    {
        Random rng = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
