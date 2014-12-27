package org.everet.android.learn_db;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.Random;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        List<Article> values = mDatasource.getAllArticle();

//        mAdapter = new ArrayAdapter<Article>(this, android.R.layout.simple_list_item_1, values);
//        mListView.setAdapter(mAdapter);

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
//        String[] from = new String[] { MySQLiteHelper.COLUMN_NAME };
        // Fields on the UI to which we map
//        int[] to = new int[] { R.id.label };

//        adapter = new SimpleCursorAdapter(this, R.layout.todo_row, null, from, to, 0);

//        mListView.setListAdapter(adapter);

//        mBtnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Article article = mDatasource.createArticle(generateString(10));
////                mAdapter.add(article);
////                mAdapter.notifyDataSetChanged();
//            }
//        });
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
