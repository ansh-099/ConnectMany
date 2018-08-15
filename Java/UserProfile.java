package com.connect.ansh0.connectmany;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    TextView name_userprofile, status_userprofile,work_userprofile,email_userprofile,
            age_userprofile,dob_userprofile,gender_userprofile,country_userprofile;
    ImageView dp_userprofile;
    ArrayList<ProfileData> list= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Bundle extras = getIntent().getExtras();
        String profileName=  extras.getString("username");
        String profileImage=  extras.getString("userimage");
        final String profileId=  extras.getString("userid");
        final String emailAdd=extras.getString("useremail");

        name_userprofile=findViewById(R.id.name_userprofile);
        status_userprofile=findViewById(R.id.status_userprofile);
        work_userprofile=findViewById(R.id.work_userprofile);
        email_userprofile=findViewById(R.id.email_userprofile);
        age_userprofile=findViewById(R.id.age_userprofile);
        dob_userprofile=findViewById(R.id.dob_userprofile);
        gender_userprofile=findViewById(R.id.gender_userprofile);
        country_userprofile=findViewById(R.id.country_userprofile);

        dp_userprofile=findViewById(R.id.dp_userprofile);

        name_userprofile.setText(profileName);
        Picasso.get().load(profileImage).into(dp_userprofile);

//        FirebaseDatabase.getInstance().getReference().child("Profiles").child(profileId).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                ProfileData profileData= dataSnapshot.getValue(ProfileData.class);
//                status_userprofile.setText(profileData.status);
//                work_userprofile.setText(profileData.work);
//                age_userprofile.setText(profileData.age);
//                dob_userprofile.setText(profileData.dob);
//                gender_userprofile.setText(profileData.gender);
//                country_userprofile.setText(profileData.country);
//                email_userprofile.setText(emailAdd);
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



        getSupportActionBar().setTitle(profileName);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FirebaseDatabase.getInstance().getReference().child("Profiles").child(profileId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    ProfileData dataN = childSnapshot.getValue(ProfileData.class);
                    list.add(dataN);
                    work_userprofile.setText(list.get(0).work);
                    gender_userprofile.setText(list.get(0).gender);
                    status_userprofile.setText(list.get(0).status);
                    country_userprofile.setText(list.get(0).country);
                    dob_userprofile.setText(list.get(0).dob);
                    age_userprofile.setText(list.get(0).age);


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
