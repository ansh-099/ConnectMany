package com.connect.ansh0.connectmany;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FIllSettingsPage extends AppCompatActivity {
    FirebaseUser firebaseUser;
    EditText fill_age,fill_gender,fill_dob,fill_status,fill_work,fill_country;
    TextView fill_name,fill_email;
    ImageView fill_image;
    Button fill_save;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_settings_page);
        getSupportActionBar().setTitle("Fill Details");
       firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        fill_age=findViewById(R.id.fill_age);
        fill_gender=findViewById(R.id.Fill_gender);
        fill_dob=findViewById(R.id.fill_dob);
        fill_status=findViewById(R.id.fill_status);
        fill_work=findViewById(R.id.fill_work);
        fill_country=findViewById(R.id.fill_country);
        fill_name=findViewById(R.id.fill_name);
        fill_image=findViewById(R.id.fill_image);
        fill_save=findViewById(R.id.fill_save);
        fill_email=findViewById(R.id.fill_email);



        Picasso.get().load(firebaseUser.getPhotoUrl()).into(fill_image);
        fill_name.setText(firebaseUser.getDisplayName());
        fill_email.setText(firebaseUser.getEmail());


            fill_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String age=fill_age.getText().toString();
                    String gender=fill_gender.getText().toString();
                    String dob=fill_dob.getText().toString();
                    String status=fill_status.getText().toString();
                    String work=fill_work.getText().toString();
                    String country=fill_country.getText().toString();

                    if(age.equals("") || gender.equals("") || dob.equals("") || status.equals("") || work.equals("")
                             || country.equals(""))
                    {
                        Toast.makeText(FIllSettingsPage.this, "Fill All The Requirements", Toast.LENGTH_SHORT).show();
                    }else{
                        loading = new ProgressDialog(FIllSettingsPage.this);
                        loading.setTitle("Please Wait");
                        loading.show();

                        ProfileData profileData= new ProfileData();
                        profileData.age=age;
                        profileData.gender=gender;
                        profileData.dob=dob;
                        profileData.status=status;
                        profileData.work=work;
                        profileData.country=country;
                        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).push().setValue(profileData);
                        loading.dismiss();
                        Toast.makeText(FIllSettingsPage.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(FIllSettingsPage.this,DisclamerActivity.class);
                        startActivity(i);
                    }



                }
            });



    }
}
