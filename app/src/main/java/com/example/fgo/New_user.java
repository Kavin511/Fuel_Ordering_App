package com.example.fgo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class New_user extends AppCompatActivity {
EditText user_name,email,password;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button submit;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        user_name=findViewById(R.id.user_name);
        email=findViewById(R.id.mail);
        password=findViewById(R.id.pass);
        submit=findViewById(R.id.sign_submit);
        progressBar=findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        String v;
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        submit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String username = user_name.getText().toString().trim();
                   String pass = password.getText().toString().trim();
                   String mail = email.getText().toString().trim();
                   if (TextUtils.isEmpty(username)) {
                       user_name.setError("username is required");
                   }
                   if (TextUtils.isEmpty(mail)) {
                       email.setError("Mail id  is required");
                   }
                   if (TextUtils.isEmpty(pass) || pass.length() < 7) {
                       password.setError("Invlid password");
                   }
                   progressBar.setVisibility(View.VISIBLE);
                   mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               // Sign in success, update UI with the signed-in user's information
                               Toast.makeText(New_user.this, "Account created succesfullY", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(New_user.this,permissions_act.class));
                           } else {
                               Toast.makeText(New_user.this, "User already exist", Toast.LENGTH_SHORT).show();
                           }
                       }


                   });
               }

           });
       }
    }

