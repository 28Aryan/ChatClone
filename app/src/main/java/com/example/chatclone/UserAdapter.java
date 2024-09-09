package com.example.chatclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {

    MainActivity mainActivity;
    ArrayList<User> userArrayList;

    public UserAdapter(MainActivity mainActivity, ArrayList<User> userArrayList) {
        this.mainActivity = mainActivity;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        Inflate the layout for individual user items from the 'user_item.xml' layout file
//        LayoutInflater is used to convert XML layout into View objects
        View view = LayoutInflater.from(mainActivity).inflate(R.layout.user_item, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewholder holder, int position) {
        
//        Get the current User object from the userArrayList at the given position
        User users = userArrayList.get(position);
        holder.userName.setText(users.userName);
        holder.userStatus.setText(users.status);

//        Load the user's profile picture from the URL and set it into the ImageView using Picasso
        Picasso.get().load(users.profilePic).into(holder.profilerg);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CircleImageView profilerg;
        TextView userName, userStatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            profilerg = itemView.findViewById(R.id.profilerg);
            userName = itemView.findViewById(R.id.userName);
            userStatus = itemView.findViewById(R.id.userStatus);
        }
    }
}
