package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //(for back button)

        passwordEmail = (EditText)findViewById(R.id.etPasswordEmail);
        resetPassword = (Button)findViewById(R.id.btnResetPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = passwordEmail.getText().toString().trim();

                if (useremail.equals("")){
                    Toast.makeText(PasswordActivity.this,"Please Enter your Registered Email ID",Toast.LENGTH_SHORT).show();

                }else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this,"Password Reset Email sent",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this,MainActivity.class));
                            }else{
                                Toast.makeText(PasswordActivity.this,"Password in Sending Email",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

}
