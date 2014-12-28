package org.everet.android.learn_db;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.Random;

public class MyListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private SimpleCursorAdapter adapter;
    ListView mListView;
    Button mBtnAdd, mBtnClear;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) view.findViewById(R.id.listView);
        mBtnAdd = (Button) view.findViewById(R.id.btnAdd);
        mBtnClear = (Button) view.findViewById(R.id.btnClear);

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[]{MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_ID};
        // Fields on the UI to which we map
        int[] to = new int[]{R.id.textViewName, R.id.textViewID};

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.item_row, null, from,
                to, 0);
        mListView.setAdapter(adapter);

        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MySQLiteHelper.COLUMN_NAME, generateString(10));
                getActivity().getContentResolver().insert(ArticleProvider.CONTENT_URI, values);
            }
        });

        mBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getContentResolver().delete(ArticleProvider.CONTENT_URI, null, null);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        DebugLog.d("i = " + i);
        String[] projection = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME};
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                ArticleProvider.CONTENT_URI, projection, null, null, MySQLiteHelper.COLUMN_ID + " DESC");
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        DebugLog.d("call");
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        DebugLog.d("call");
        adapter.swapCursor(null);
    }

    public static String generateString(int length) {
        Random rng = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

}
