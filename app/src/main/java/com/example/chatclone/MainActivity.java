package com.example.chatclone;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    FirebaseAuth auth;
    RecyclerView userRecyclerview;
    UserAdapter adapter;
    ArrayList<User> userArrayList;
    ImageView userLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

//        dbRef is used for getting the reference of the data in the database.
//        We pass .child("user") as we are storing the our data in user with an unique id.
        DatabaseReference dbRef = db.getReference().child("user");

//        Allocating memory for user arrayList.
        userArrayList = new ArrayList<>();



        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dbSnap:snapshot.getChildren()){
//                    Convert each child into a User object (assuming 'User' is a model class representing the data)
//                    Storing all the data in userArrayList which is present in users i.e. our realtime databse
                    User users = dbSnap.getValue(User.class);
                    userArrayList.add(users);
                }
//                Notify the adapter that the dataset has changed so the UI (e.g., RecyclerView) can update and display the new data
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });

        userRecyclerview = findViewById(R.id.userRecyclerview);
        userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(MainActivity.this,userArrayList);
        userRecyclerview.setAdapter(adapter);
        userLogout = findViewById(R.id.logout);

        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this,R.style.dialog);
                dialog.setContentView(R.layout.dialog_layout);
                Button no,yes;
                yes = dialog.findViewById(R.id.yesBtn);
                no = dialog.findViewById(R.id.noBtn);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(MainActivity.this, Login.class);
                        startActivity(i);
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

//      gets the current user if not logged in then redirects it to login page.
        if(auth.getCurrentUser() == null){
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }
}