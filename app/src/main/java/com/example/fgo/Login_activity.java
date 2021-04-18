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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_activity extends AppCompatActivity {
    EditText user_name,email,password;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        user_name=findViewById(R.id.user_name);
        email=findViewById(R.id.mail);
        password=findViewById(R.id.pass);
        submit=findViewById(R.id.sign_submit);
        progressBar=findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(New_user.this,mapactivity.class));

                String pass = password.getText().toString().trim();
                String mail = email.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    email.setError("Mail id  is required");
                }
                if (TextUtils.isEmpty(pass) || pass.length() < 7) {
                    password.setError("Invlid password");
                }

                mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login_activity.this,"Logged in.",Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(),permissions_act.class));
                            startActivity(new Intent(Login_activity.this,permissions_act.class));

                        }
                        else
                        {
                            Toast.makeText(Login_activity.this,"Invalid!!"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
