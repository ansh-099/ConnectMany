package com.connect.ansh0.connectmany;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    ArrayList<ProfileData> list= new ArrayList<>();
    TextView work_profile, gender_profile, status_profile, country_pofile,dob_profile, age_profile,name_profile,email_profile;
    ImageView image_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Settings", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i= new Intent(Profile.this,SettingActivity.class);
                startActivity(i);

            }
        });

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        work_profile=findViewById(R.id.work_profile);
        gender_profile=findViewById(R.id.gender_profile);
        status_profile=findViewById(R.id.status_profile);
        country_pofile=findViewById(R.id.country_profile);
        dob_profile=findViewById(R.id.dob_profile);
        age_profile=findViewById(R.id.age_profile);

        image_profile=findViewById(R.id.dp_profile);
        name_profile=findViewById(R.id.name_profile);
        email_profile=findViewById(R.id.email_profile);
        if(firebaseUser != null){
            email_profile.setText(firebaseUser.getEmail());
            name_profile.setText(firebaseUser.getDisplayName());
            Picasso.get().load(firebaseUser.getPhotoUrl()).into(image_profile);
        }else{
            Picasso.get().load(R.drawable.profile_image).into(image_profile);
        }





//        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).push().setValue(prof);
//        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                ProfileData profileData=dataSnapshot.getValue(ProfileData.class);
////                work_profile.setText(profileData.work);
////                gender_profile.setText(profileData.gender);
////                status_profile.setText(profileData.status);
////                country_pofile.setText(profileData.country);
////                dob_profile.setText(profileData.dob);
////                age_profile.setText(profileData.age);
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

        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    ProfileData dataN = childSnapshot.getValue(ProfileData.class);
                    list.add(dataN);
                    work_profile.setText(list.get(0).work);
                    gender_profile.setText(list.get(0).gender);
                    status_profile.setText(list.get(0).status);
                    country_pofile.setText(list.get(0).country);
                    dob_profile.setText(list.get(0).dob);
                    age_profile.setText(list.get(0).age);


                }

            }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

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
