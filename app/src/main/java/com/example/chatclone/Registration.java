package com.example.chatclone;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class Registration extends AppCompatActivity {

    CircleImageView profile;
    TextView login;
    Button register;
    EditText username,email,pass,passConfirm;
    String User,Email,Pass,PassC,imgUri,status;
    Uri imgURI;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";       //Regex for email
    FirebaseAuth auth;
    FirebaseDatabase db;
    FirebaseStorage store;
    ProgressDialog pd,pd1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        pd = new ProgressDialog(this);
        pd.setMessage("Establishing Connection!!");
        pd.setCancelable(false);

        pd1 = new ProgressDialog(this);
        pd1.setMessage("Redirecting to Login");
        pd1.setCancelable(false);

        register = findViewById(R.id.buttonRegister);
        username = findViewById(R.id.editUsername);
        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.editPassword);
        passConfirm = findViewById(R.id.editPasswordConfirm);
        login = findViewById(R.id.textLogin);
        profile = findViewById(R.id.profilerg);

//        Creating the instance
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        store = FirebaseStorage.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd1.show();
                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                All the get text should be inside the onClickListener other wise it will have null values
                User = username.getText().toString();
                Email = email.getText().toString();
                Pass = pass.getText().toString();
                PassC = passConfirm.getText().toString();
                status = "Hey I'm using my application";

//                Checking for Empty cells
                if(TextUtils.isEmpty(User) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pass) || TextUtils.isEmpty(PassC)){
                    pd.dismiss();
                    pd1.dismiss();
                    Toast.makeText(Registration.this, "Enter all credentials", Toast.LENGTH_SHORT).show();

                } else if (!Email.matches(emailPattern)) {  //Checking email pattern using its regex
                    pd.dismiss();
                    pd1.dismiss();
                    email.setError("Invalid email!");

                } else if (Pass.length() < 6) {
                    pd.dismiss();
                    pd1.dismiss();
                    pass.setError("Password must be greater than 6 characters!");

                } else if (!Pass.equals(PassC)) {
                    pd1.dismiss();
                    pd.dismiss();
                    passConfirm.setError("Incorrect password");

                }else {
                    auth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                String id = task.getResult().getUser().getUid();
                                DatabaseReference ref = db.getReference().child("user").child(id);
                                StorageReference storeRef = store.getReference().child("user").child(id);

                                if(imgURI != null){

//                                    This is when the user chooses the profile pic to be uploaded
//                                    putFile is the must to upload the image in the databse
                                    storeRef.putFile(imgURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful()){
                                                storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {

                                                        imgUri = uri.toString();
                                                        User user = new User(id, User, Email, Pass, imgUri, status);
                                                        ref.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if(task.isSuccessful()){
                                                                    pd1.dismiss();
                                                                    pd.show();
                                                                    Intent i = new Intent(Registration.this, MainActivity.class);
                                                                    startActivity(i);
                                                                    finish();
                                                                }else {
                                                                    Toast.makeText(Registration.this, "Error creating user", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }else {
//                                    This is when the user does not chooses the profile pic to be uploaded
//                                    link is the default image for profile uploaded in the firebase database(access token)
                                    imgUri = "https://firebasestorage.googleapis.com/v0/b/chatclone-b8381.appspot.com/o/man.png?alt=media&token=15cdc426-dc77-4cba-aa57-140b9766882d";

//                                   Setting all the values in the database
                                    User user = new User(id, User, Email, Pass, imgUri, status);
                                    ref.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                pd.show();
                                                Intent i = new Intent(Registration.this, MainActivity.class);
                                                startActivity(i);
                                                finish();
                                            }else {
                                                Toast.makeText(Registration.this, "Error creating user", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }


                            }
                        }
                    });
                }
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(i.ACTION_GET_CONTENT);
                startActivityForResult(i.createChooser(i, "Select Image"), 10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if (data != null){
                imgURI = data.getData();
                profile.setImageURI(imgURI);
            }
        }
    }
}