package org.everet.android.customviewgroup;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private TextSwitcher mTextSwitcher;
    private String[] strs = new String[]
            {
                    "Android将军",
                    "ios将军",
                    "Cocos2d-X将军",
                    "将军"
            };
    int i = 0;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

        mTextSwitcher = (TextSwitcher) findViewById(R.id.textView);
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(MainActivity.this);
                t.setGravity(Gravity.CENTER);
//                t.setTextSize(20 + rand.nextInt(40));
                t.setTextSize(40);
                return t;
            }
        });
        mTextSwitcher.setInAnimation(in);
        mTextSwitcher.setOutAnimation(out);
        mTextSwitcher.setText("Start");

        mTextSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextSwitcher.setText(strs[i++ % strs.length]);
            }
        });
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
