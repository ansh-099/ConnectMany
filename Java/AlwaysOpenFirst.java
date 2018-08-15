package com.connect.ansh0.connectmany;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AlwaysOpenFirst extends AppCompatActivity {
//    ProgressDialog loading;
//    int i=0;
    ArrayList<ProfileData> datas=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_always_open_first);

//        loading = new ProgressDialog(AlwaysOpenFirst.this);
//        loading.setTitle("Please Wait");
//        loading.show();
                final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//                FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        ProfileData dataN=dataSnapshot.getValue(ProfileData.class);
//                        datas.add(dataN);
//
//                            if(dataN.country.equals("") )
//                            {
//                                Toast.makeText(AlwaysOpenFirst.this, String.valueOf(dataN.country), Toast.LENGTH_SHORT).show();
//                                  FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).removeValue();
//                                loading.dismiss();
//
//                                Handler h= new Handler();
//                                Runnable r= new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Intent i= new Intent(AlwaysOpenFirst.this,FIllSettingsPage.class);
//                                        startActivity(i);
//
//                                    }
//                                };
//                                h.postDelayed(r,700);
//
//
//                        }else {
//                            loading.dismiss();
//
//                            Handler h= new Handler();
//                            Runnable r= new Runnable() {
//                                @Override
//                                public void run() {
//                                    Intent i = new Intent(AlwaysOpenFirst.this, MainActivity.class);
//                                    startActivity(i);
//
//                                }
//                            };
//                            h.postDelayed(r,700);
//
//                        }
//                        loading.dismiss();
//                        Toast.makeText(AlwaysOpenFirst.this, datas.get(i).country, Toast.LENGTH_SHORT).show();
//                        i++;
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                    ProfileData dataN=childSnapshot.getValue(ProfileData.class);
            datas.add(dataN);

                    if(!datas.get(0).country.equals("")){

                        Intent i= new Intent(AlwaysOpenFirst.this,MainActivity.class);
                        startActivity(i);
                    }else {


                        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).removeValue();
                        Intent i= new Intent(AlwaysOpenFirst.this,FIllSettingsPage.class);
                        startActivity(i);
                    }

//                    Log.d("hey",dataN.country);
//                    Toast.makeText(AlwaysOpenFirst.this, dataN.country, Toast.LENGTH_SHORT).show();
//                    if(!dataN.country.equals(""))
//                    {
//                        Intent i= new Intent(AlwaysOpenFirst.this,MainActivity.class);
//                        startActivity(i);
//
//                    }else{
//
////                        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).removeValue();
//                        Intent i= new Intent(AlwaysOpenFirst.this,FIllSettingsPage.class);
//                        startActivity(i);
//
//                    }

//                    if(dataN.country.equals("") )
//                    {
//                        Toast.makeText(AlwaysOpenFirst.this, String.valueOf(dataN.country), Toast.LENGTH_SHORT).show();
//                        FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).removeValue();
//                        loading.dismiss();
//
//                        Handler h= new Handler();
//                        Runnable r= new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent i= new Intent(AlwaysOpenFirst.this,FIllSettingsPage.class);
//                                startActivity(i);
//
//                            }
//                        };
//                        h.postDelayed(r,700);
//
//
//                    }else {
//                        loading.dismiss();
//
//                        Handler h= new Handler();
//                        Runnable r= new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent i = new Intent(AlwaysOpenFirst.this, MainActivity.class);
//                                startActivity(i);
//
//                            }
//                        };
//                        h.postDelayed(r,700);
//
//                    }
//                    loading.dismiss();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Handler h= new Handler();
//                        Runnable r= new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(AlwaysOpenFirst.this, datas.get(0).country, Toast.LENGTH_SHORT).show();
//
//                            }
//                        };
//                        h.postDelayed(r,2000);



    }
}
