package com.connect.ansh0.connectmany;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {
EditText etFeedBack;
Button btnFeedBack;
ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().setTitle("FeedBack");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etFeedBack= findViewById(R.id.etFeedBack);
        btnFeedBack=findViewById(R.id.btnFeedBack);
        btnFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = new ProgressDialog(Feedback.this);
                loading.setTitle("Sending FeedBack");
                loading.setMessage("Please Wait");
                loading.show();
                String feedback=etFeedBack.getText().toString();
                etFeedBack.setText("");
                FirebaseDatabase.getInstance().getReference().child("FeedBack").push().setValue(feedback);

                Handler h= new Handler();
                Runnable r= new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        Intent i= new Intent(Feedback.this,AfterFeedBack.class);
                        startActivity(i);

                    }
                };
                h.postDelayed(r,700);

            }
        });



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
