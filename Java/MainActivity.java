package com.connect.ansh0.connectmany;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
public static final int RC_SIGN_IN= 101;
    FirebaseUser firebaseUser;
    Button btnFeed;
    EditText editFeed;
    FirebaseRecyclerAdapter adapter;
    ProgressDialog loading;
    RecyclerView rvFeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        NavigationView navigationView1= findViewById(R.id.nav_view);
//        View hView =  navigationView1.inflateHeaderView(R.layout.nav_header_main);
////        ImageView imgvw = (ImageView)hView.findViewById(R.id.imageView);
//        TextView tv = (TextView)hView.findViewById(R.id.tvNamenav);
////        imgvw .setImageResource();
//        tv.setText("new text");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Feed News");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){


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


        rvFeed= findViewById(R.id.rvFeed);


//       btnFeed.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               OnePost onePost= new OnePost();
//               onePost.time="10:05AM";
////               onePost.date= "25/08/2018";
//               onePost.counter=0;
//               onePost.dpUrl= "http://4.bp.blogspot.com/-k_xroWmviH4/UQg0rhqrA-I/AAAAAAAAA_Q/otiHAnXjkU0/s1600/Pictures-hot-girls-wallpapers-sexy-hd-photos-hot-girls-wallpaper-3.jpg:%22";
//               onePost.picUrl="https://www.quirkybyte.com/wp-content/uploads/2017/07/f9a78bb5777fc5fc4b1b151220441500-sexy-hot-girls-super-girls.jpg";
//               FirebaseDatabase.getInstance().getReference().child("Posts").push().setValue(onePost);
//
//
//           }
//       });
        rvFeed.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));



        Display();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.main, menu);
////        MenuInflater mi= getMenuInflater();
////        mi.inflate(R.menu.new_post,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//
//        int id=item.getItemId();
//        if(id == R.id.addPost)
//        {
//            Intent i= new Intent(MainActivity.this,Main2Activity.class);
//            startActivity(i);
//        }
//        return super.onOptionsItemSelected(item);
//
//
//    }
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_logout) {
        loading = new ProgressDialog(MainActivity.this);
        loading.setTitle("Logging Out");
        loading.setMessage("Please Wait");
        loading.show();
        FirebaseAuth.getInstance().signOut();
        loading.dismiss();
        finish();
        Intent i= new Intent(MainActivity.this,FirstPage.class);
        startActivity(i);
        return true;
    }

    return super.onOptionsItemSelected(item);
}


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addPost) {

            Intent i= new Intent(MainActivity.this,Main2Activity.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_chat) {
            Intent i= new Intent(MainActivity.this,Main3Activity.class);
            startActivity(i);

        }else if(id == R.id.nav_profile)
        {
            Intent i= new Intent(MainActivity.this,Profile.class);
            startActivity(i);

        }else if(id == R.id.nav_setting)
        {
            Intent i= new Intent(MainActivity.this,SettingActivity.class);
            startActivity(i);
        }else if(id == R.id.nav_aboutus)
        {
            Intent i= new Intent(MainActivity.this,AboutUsActivity.class);
            startActivity(i);

        }else if(id == R.id.nav_share)
        {

            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Try this social media app called ConnectMany, also available on Play Store link- https://play.google.com/store/apps/details?id=com.connect.ansh0.connectmany");
           startActivity(whatsappIntent);
        }else if(id == R.id.nav_feedback)
        {
            Intent i= new Intent(MainActivity.this,Feedback.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    void Display()
    {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Posts");
        FirebaseRecyclerOptions<OnePost> options =
                new FirebaseRecyclerOptions.Builder<OnePost>()
                        .setQuery(query, OnePost.class)
                        .build();

        adapter= new
                FirebaseRecyclerAdapter<OnePost, PostsViewHolder>(options) {
                    @NonNull
                    @Override
                    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.one_unit, parent, false);

                        return new PostsViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull OnePost model) {
                        final OnePost onePost= model;
                        holder.OneUnitName.setText(onePost.name);
                        holder.OneUnitTime.setText(onePost.time);
                        holder.OneUnitDate.setText(onePost.date);
                        holder.OneUnitText.setText(onePost.text);
                        Picasso.get().load(onePost.picUrl).into(holder.OneUnitImage);
                        Picasso.get().load(onePost.dpUrl).into(holder.OneUnitProfilePic);

                        holder.rvCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent i= new Intent(MainActivity.this,UserProfile.class);
                                i.putExtra("username", onePost.name);
                                i.putExtra("userimage",onePost.dpUrl);
                                i.putExtra("userid",onePost.id);
                                i.putExtra("useremail",onePost.emailAdd);
                                startActivity(i);
                            }
                        });

                    }
                };
        rvFeed.setAdapter(adapter);

        Timer timer = new Timer();
        timer.schedule(new ScrollTask(), 2000 // delay before task is to be executed,

        );//period between successive task executions.)
    }

//    private void scrollMyListViewToBottom() {
//        rvFeed.post(new Runnable() {
//            @Override
//            public void run() {
//                // Select the last row so it will scroll into view...
//                rvFeed.setSelection(0);
//            }
//        });
//    }
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
}

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {

            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button



                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {

                    return;
                }

            }
        }
    }

//    String name;
//    String text;
//    String dpUrl;
//    String picUrl;
//    int counter;
//    String date;
//    String time;
//    String year;
//    String branch;

    public static class PostsViewHolder extends RecyclerView.ViewHolder{
        TextView OneUnitName,OneUnitText,OneUnitDate,OneUnitTime;
        ImageView OneUnitProfilePic,OneUnitImage;
        CardView rvCard;
        public PostsViewHolder(View itemView) {
            super(itemView);

            OneUnitName= itemView.findViewById(R.id.OneUnitName);
            OneUnitText= itemView.findViewById(R.id.OneUnitText);
            OneUnitDate= itemView.findViewById(R.id.OneUnitDate);
            OneUnitTime= itemView.findViewById(R.id.OneUnitTime);
            OneUnitProfilePic= itemView.findViewById(R.id.OneUnitProfilePic);
            OneUnitImage= itemView.findViewById(R.id.OneUnitImage);
            rvCard=itemView.findViewById(R.id.rvCard);

        }








    }



    private class ScrollTask extends TimerTask {
        public void run() {
            rvFeed.post(new Runnable() {
                public void run() {



                    rvFeed.smoothScrollToPosition(adapter.getItemCount());
                }
            });
        }
    }

}
