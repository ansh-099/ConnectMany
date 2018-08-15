package com.connect.ansh0.connectmany;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class AfterFeedBack extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_feed_back);

        getSupportActionBar().setTitle("FeedBack");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Handler h= new Handler();
        Runnable r= new Runnable() {
            @Override
            public void run() {

                Intent i= new Intent(AfterFeedBack.this,MainActivity.class);
                startActivity(i);

            }
        };
        h.postDelayed(r,1300);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);

    }

}
