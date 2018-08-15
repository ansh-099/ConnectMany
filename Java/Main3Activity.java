package com.connect.ansh0.connectmany;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main3Activity extends AppCompatActivity {
    FirebaseRecyclerAdapter adapter;
    RecyclerView rvChat;
    Button buttonChat;
    Chat_RecyclerAdapter chat_recyclerAdapter;
    EditText etChat;
    ArrayList<chatOnePost> AllChats= new ArrayList<>();
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        rvChat= findViewById(R.id.rvChat);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rvChat.setLayoutManager(new LinearLayoutManager(this));
        chat_recyclerAdapter= new Chat_RecyclerAdapter(AllChats);
        rvChat.setAdapter(chat_recyclerAdapter);
        FirebaseDatabase.getInstance().getReference().child("Chats").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatOnePost oneChatU= dataSnapshot.getValue(chatOnePost.class);
                AllChats.add(oneChatU);
                chat_recyclerAdapter.notifyDataSetChanged();
                etChat.setText("");

                Timer timer = new Timer();
                timer.schedule(new ScrollTask(),0);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Chats");
        FirebaseRecyclerOptions<chatOnePost> options =
                new FirebaseRecyclerOptions.Builder<chatOnePost>()
                        .setQuery(query, chatOnePost.class)
                        .build();
        buttonChat= findViewById(R.id.buttonChat);
        etChat= findViewById(R.id.etChat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etChat.getText().toString().equals(""))
                {
                    Toast.makeText(Main3Activity.this, "Enter Some Text", Toast.LENGTH_SHORT).show();
                }else{
                    chatOnePost OneChat= new chatOnePost();
                    OneChat.ChatName=firebaseUser.getDisplayName().toString();
                    OneChat.ChatText=etChat.getText().toString();
                    OneChat.ChatImages=firebaseUser.getPhotoUrl().toString();
                    OneChat.idChat=firebaseUser.getUid();
                    OneChat.emailChat=firebaseUser.getEmail();
                    FirebaseDatabase.getInstance().getReference().child("Chats").push().setValue(OneChat);

                }
            }
        });


//        adapter= new
//                FirebaseRecyclerAdapter<chatOnePost, ChatViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull chatOnePost model) {
//                        chatOnePost chat= model;
//                        holder.name_chat.setText(model.ChatName);
//                        holder.chat_text.setText(model.ChatText);
//                        Picasso.get().load(chat.ChatImages).into(holder.image_dp_chat);
//                        Log.d("hey","no");
//                    }
//
//                    @NonNull
//                    @Override
//                    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext())
//                                .inflate(R.layout.chat_one_unit, parent, false);
//Log.d("hey","no1");
//                        return new ChatViewHolder(view);
//                    }


//                };




    }



//    public static class ChatViewHolder extends RecyclerView.ViewHolder{
//        TextView name_chat,chat_text;
//        ImageView image_dp_chat;
//        public ChatViewHolder(View itemView) {
//            super(itemView);
//
//           name_chat= itemView.findViewById(R.id.name_chat);
//           chat_text= itemView.findViewById(R.id.chat_text);
//           image_dp_chat= itemView.findViewById(R.id.image_dp_chat);
//
//        }
//
//
//
//
//
//
//    }

    class  ScrollTask extends TimerTask {
        public void run() {
            rvChat.post(new Runnable() {
                public void run() {



                    rvChat.smoothScrollToPosition(chat_recyclerAdapter.getItemCount()-1);
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }




    public class Chat_RecyclerAdapter extends RecyclerView.Adapter<Chat_RecyclerAdapter.ChatViewHolder> {
        ArrayList<chatOnePost> AllChats= new ArrayList<>();
        Chat_RecyclerAdapter(ArrayList<chatOnePost> Chatss){
            AllChats=Chatss;


        }
        @NonNull
        @Override
        public Chat_RecyclerAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater li= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView=li.inflate(R.layout.chat_one_unit,parent,false);
            return new Chat_RecyclerAdapter.ChatViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull Chat_RecyclerAdapter.ChatViewHolder holder, final int position) {

            chatOnePost oneUnitChat= AllChats.get(position);
            holder.name_chat.setText(oneUnitChat.ChatName);
            holder.chat_text.setText(oneUnitChat.ChatText);
            Picasso.get().load(oneUnitChat.ChatImages).into(holder.image_dp_chat);
            holder.chatCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent(Main3Activity.this, UserProfile.class);
                    i.putExtra("username", AllChats.get(position).ChatName);
                    i.putExtra("userimage",AllChats.get(position).ChatImages);
                    i.putExtra("userid",AllChats.get(position).idChat);
                    i.putExtra("useremail",AllChats.get(position).emailChat);
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return AllChats.size();
        }



        public  class ChatViewHolder extends RecyclerView.ViewHolder{
            TextView name_chat,chat_text;
            ImageView image_dp_chat;
            CardView chatCard;
            public ChatViewHolder(View itemView) {
                super(itemView);

                name_chat= itemView.findViewById(R.id.name_chat);
                chat_text= itemView.findViewById(R.id.chat_text);
                image_dp_chat= itemView.findViewById(R.id.image_dp_chat);
                chatCard=itemView.findViewById(R.id.chatCard);

            }






        }
    }





}