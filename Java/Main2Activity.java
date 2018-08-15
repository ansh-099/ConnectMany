package com.connect.ansh0.connectmany;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    ImageView imageForAdd;
    TextView nameForAdd;
    Uri imageUri;
    ImageButton imageButton;
    Button buttonSharePost;
    Button buttonHelp;
    StorageReference filePath;
    EditText edNewPost;
    String date,time,finalans,downloadUrl;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        imageForAdd= findViewById(R.id.imageForAdd);
        nameForAdd= findViewById(R.id.nameForAdd);
        buttonSharePost= findViewById(R.id.buttonSharePost);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonHelp=findViewById(R.id.buttonHelp);

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Main2Activity.this,HelpActivity.class);
                startActivity(i);
            }
        });
        buttonSharePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(imageUri== null)
            {
                Toast.makeText(Main2Activity.this, "Add Picture", Toast.LENGTH_SHORT).show();
            }
        else {
                loading = new ProgressDialog(Main2Activity.this);
                loading.setTitle("Adding New Post");
                loading.setMessage("Please Wait");
                loading.show();

                Calendar calForDate = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
                date = currentDate.format(calForDate.getTime());

                Calendar calForTime = Calendar.getInstance();
                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                time = currentTime.format(calForTime.getTime());
                finalans = date + time;

                filePath = FirebaseStorage.getInstance().getReference().child("Post Images").child(imageUri.getLastPathSegment() + finalans + ".jpg");
                filePath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {

                            FirebaseStorage.getInstance().getReference().child("Post Images").child(imageUri.getLastPathSegment() + finalans + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    downloadUrl = String.valueOf(uri);
                                    Log.d("hey", String.valueOf(downloadUrl));
                                    Date date1 = new Date();
                                    OnePost oneUnit = new OnePost();
                                    oneUnit.name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                                    oneUnit.dpUrl = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();

                                    Calendar calForTime = Calendar.getInstance();
                                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                                    time = currentTime.format(calForTime.getTime());
                                    oneUnit.date = String.valueOf(date1.getDate()) + "/" + String.valueOf(date1.getMonth() + 1) + "/" + String.valueOf(date1.getYear() - 100);
                                    oneUnit.time = String.valueOf(time);
                                    Log.d("hey", String.valueOf(time));
                                    oneUnit.text = edNewPost.getText().toString();
                                    oneUnit.picUrl = String.valueOf(downloadUrl);
                                    oneUnit.id= firebaseUser.getUid();
                                    oneUnit.emailAdd=firebaseUser.getEmail();
                                    FirebaseDatabase.getInstance().getReference().child("Posts").push().setValue(oneUnit);


//                                    FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).addChildEventListener(new ChildEventListener() {
//                                        @Override
//                                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                                            ProfileData data= dataSnapshot.getValue(ProfileData.class);
//                                            FirebaseDatabase.getInstance().getReference().
//                                                    child("UserProfiles").child(firebaseUser.getUid()).push().setValue(data);
//
//                                        }
//
//                                        @Override
//                                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                        }
//
//                                        @Override
//                                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                                        }
//
//                                        @Override
//                                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });



                                    Toast.makeText(Main2Activity.this, "Posted Successfully", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(Main2Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                                    // Handle any errors
                                }
                            });


                            Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });


            }
            }
        });

        String nameDisplay =  firebaseUser.getDisplayName();
        String imageDisplay = firebaseUser.getPhotoUrl().toString();

        Picasso.get().load(imageDisplay).into(imageForAdd);
        nameForAdd.setText(nameDisplay);
        edNewPost= findViewById(R.id.edNewPost);
        getSupportActionBar().setTitle("New Post");
        imageButton= findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

    }

    void OpenGallery()
    {

        Intent i= new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, 101);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 101 && requestCode == RESULT_OK && data != null){
        imageUri=data.getData();
        imageButton.setImageDrawable(getDrawable(R.drawable.ic_done_black_24dp));
        

//        }
    }



}
