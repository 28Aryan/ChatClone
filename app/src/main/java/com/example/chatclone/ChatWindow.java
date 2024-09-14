package com.example.chatclone;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatWindow extends AppCompatActivity {

    String receiver_img, receiver_uid, receiver_name;
    CircleImageView profile;
    TextView profileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        receiver_name = getIntent().getStringExtra("Name");
        receiver_uid = getIntent().getStringExtra("Uid");
        receiver_img = getIntent().getStringExtra("ReceiveImg");

        profile = findViewById(R.id.chatProfile);
        profileName = findViewById(R.id.receiverName);

        Picasso.get().load(receiver_img).into(profile);

        profileName.setText(""+receiver_name);

    }
}