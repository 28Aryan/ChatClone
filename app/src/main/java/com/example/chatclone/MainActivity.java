package com.example.chatclone;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
//      gets the current user if not logged in then redirects it to login page
        if(auth.getCurrentUser() == null){
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }
}