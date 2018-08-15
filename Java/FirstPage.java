package com.connect.ansh0.connectmany;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class FirstPage extends AppCompatActivity {
    FirebaseUser firebaseUser;
    public static final int RC_SIGN_IN= 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        getSupportActionBar().setTitle("Login");



        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Button loginFP= findViewById(R.id.loginFP);
        loginFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                                        new AuthUI.IdpConfig.EmailBuilder().build()))
                                .build(),
                        RC_SIGN_IN);

            }
        });

        if(firebaseUser != null){
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            ProfileData profileData= new ProfileData();
            profileData.country="";
            FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).push().setValue(profileData);

            Intent i= new Intent(FirstPage.this,AlwaysOpenFirst.class);
            startActivity(i);



        }else{
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder().setIsSmartLockEnabled(false)
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.EmailBuilder().build()))
                            .build(),
                    RC_SIGN_IN);

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                ProfileData profileData= new ProfileData();
                profileData.country="";
                FirebaseDatabase.getInstance().getReference().child("Profiles").child(firebaseUser.getUid()).push().setValue(profileData);
                Intent i= new Intent(FirstPage.this,AlwaysOpenFirst.class);
                startActivity(i);


            } else {
                finish();
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                finish();


                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {

                    return;
                }

            }
        }
    }
}
