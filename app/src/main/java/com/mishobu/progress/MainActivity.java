package com.mishobu.progress;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends Activity {

    ProgressBar bar;
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            bar.incrementProgressBy(5);
            super.handleMessage(msg);
        }
    };

    AtomicBoolean isRunning = new AtomicBoolean(false);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar = (ProgressBar) findViewById(R.id.pbProgress);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bar.setProgress(0);

        Thread background = new Thread(new Runnable()  {
            public void run() {
                try {
                    for(int i = 0; i <= 20;i++) {
                        Thread.sleep(1000);
                        handler.sendMessage(handler.obtainMessage());
                    }
                } catch (Throwable t) {
                    // termina el thread en segundo blanco
                }
            }
        });

        isRunning.set(true);
        background.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning.set(false);
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
