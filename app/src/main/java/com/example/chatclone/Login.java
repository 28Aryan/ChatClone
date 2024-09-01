package com.example.chatclone;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button log;
    EditText email,pass;
    FirebaseAuth auth;
    String Email,Pass,emailPattern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        log = findViewById(R.id.logButton);
        email = findViewById(R.id.editEmail);
        pass = findViewById(R.id.editPassword);
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        
        
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = email.getText().toString();
                Pass = pass.getText().toString();

                if((TextUtils.isEmpty(Email)) || (TextUtils.isEmpty(Pass))){
                    Toast.makeText(Login.this, "Enter Login Credentials", Toast.LENGTH_SHORT).show();

                } else if (!Email.matches(emailPattern)) {
                    email.setError("Invalid Format");

                } else if (Pass.length() < 6) {
                    pass.setError("Invalid Format");
                    Toast.makeText(Login.this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();

                }else {
                    auth.signInWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                try{
                                    Intent i = new Intent(Login.this,MainActivity.class);
                                    startActivity(i);
//                                    finish() is used to close the intent after the use.
//                                    or else when back is pressed the user is directed to that page again.
                                    finish();
                                }catch(Exception e){
                                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}