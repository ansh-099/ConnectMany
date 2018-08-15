package com.connect.ansh0.connectmany;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    ImageView setting_image;
    ArrayList<ProfileData> list= new ArrayList<>();
TextView setting_name,setting_email;
EditText setting_age,setting_country,setting_dob,setting_gender,setting_status,setting_work;
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    Button setting_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setting_name=findViewById(R.id.setting_name);
        setting_email=findViewById(R.id.setting_email);
        setting_dob=findViewById(R.id.setting_dob);
        setting_gender=findViewById(R.id.setting_gender);
        setting_save=findViewById(R.id.setting_save);
        setting_age=findViewById(R.id.setting_age);
        setting_country=findViewById(R.id.setting_country);
        setting_image=findViewById(R.id.setting_image);
        setting_status = findViewById(R.id.setting_status);
        setting_work=findViewById(R.id.setting_work);
        Picasso.get().load(firebaseUser.getPhotoUrl()).into(setting_image);




        setting_name.setText(firebaseUser.getDisplayName());
        setting_email.setText(firebaseUser.getEmail());

        setting_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setting_age.getText().toString().equals("") || setting_gender.getText().toString().equals("") ||
                        setting_dob.getText().toString().equals("") || setting_status.getText().toString().equals("") ||
                        setting_work.getText().toString().equals("")
                        || setting_country.getText().toString().equals("")){
                    Toast.makeText(SettingActivity.this, "Fill All Requirements", Toast.LENGTH_SHORT).show();
                }else{
            ProfileData change= new ProfileData();
            change.age=setting_age.getText().toString();
            change.work=setting_work.getText().toString();
            change.status=setting_status.getText().toString();
            change.gender=setting_gender.getText().toString();
            change.country=setting_country.getText().toString();
            change.dob=setting_dob.getText().toString();
            FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).removeValue();
            FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).push().setValue(change);
                Toast.makeText(SettingActivity.this, "Settings Changed", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SettingActivity.this,Profile.class);
                startActivity(i);}
            }
        });
        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    ProfileData dataN = childSnapshot.getValue(ProfileData.class);
                    list.add(dataN);
                    setting_work.setText(list.get(0).work);
                    setting_gender.setText(list.get(0).gender);
                    setting_status.setText(list.get(0).status);
                    setting_country.setText(list.get(0).country);
                    setting_dob.setText(list.get(0).dob);
                    setting_age.setText(list.get(0).age);


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





//        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                ProfileData profileData= dataSnapshot.getValue(ProfileData.class);
//                setting_age.setText(profileData.age);
//                setting_country.setText(profileData.country);
//                setting_dob.setText(profileData.dob);
//                setting_gender.setText(profileData.gender);
//                setting_status.setText(profileData.status);
//                setting_work.setText(profileData.work);
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });





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
