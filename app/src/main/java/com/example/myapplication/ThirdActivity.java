package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {

    RadioGroup radiogroup;
    //RadioButton yes,no;
    ConstraintLayout l,m;
    private EditText userName;
    private EditText userPassword,userEmail,userPhone,userHouse;
    private Button regButton;
    private FirebaseAuth firebaseAuth;
    String name,password,email,phone,house;
    String secname,secpassword,secemail,secphone,secupi;

    private EditText Sname,Spassword,Semail,Sphone,SUPI;
    private Button Sbutton;
    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setupUIViews();

        /*userName = (EditText)findViewById(R.id.etName);
        userPassword = (EditText)findViewById(R.id.etPassword);
        userEmail = (EditText)findViewById(R.id.etEmail);
        userPhone = (EditText)findViewById(R.id.etPhone);
        userHouse = (EditText)findViewById(R.id.etHouse_No);
        regButton = (Button)findViewById(R.id.btnSubmit);*/


        firebaseAuth = FirebaseAuth.getInstance();
        radiogroup=findViewById(R.id.radioGroup);
        l=findViewById(R.id.Society_Member);
        m=findViewById(R.id.Secretary);
        //yes=findViewById(R.id.Radio_Mem);
        //no=findViewById(R.id.Radio_Sec);
        l.setVisibility(l.INVISIBLE);
        m.setVisibility(l.INVISIBLE);
        mFirestore = FirebaseFirestore.getInstance();

        Sname = (EditText)findViewById(R.id.sName);
        Semail = (EditText)findViewById(R.id.sEmail);
        Spassword = (EditText)findViewById(R.id.sPassword);
        Sphone = (EditText)findViewById(R.id.sPhone);
        SUPI = (EditText)findViewById(R.id.sUPI);
        Sbutton = (Button)findViewById(R.id.sSubmit);


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname= userName.getText().toString();
                String uemail = userEmail.getText().toString();
                String upassword = userPassword.getText().toString();
                String uphone = userPhone.getText().toString();
                String uhouse = userHouse.getText().toString();
                Map<String,String> userMap = new HashMap<>();

                userMap.put("name",uname);
                userMap.put("email",uemail);
                userMap.put("password",upassword);
                userMap.put("phone",uphone);
                userMap.put("house",uhouse);

                mFirestore.collection("Society Member").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ThirdActivity.this, "Data Recorded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(ThirdActivity.this,"Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

                if(validate()){
                    //Upload data to database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                /*User user =new User(
                                    name,email,phone,house
                                );*/

                                //FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                                sendEmailVerification();
                                Toast.makeText(ThirdActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ThirdActivity.this,MainActivity.class));
                                sendUserData();
                                Toast.makeText(ThirdActivity.this,"Successfully Registered", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ThirdActivity.this,MainActivity.class));
                            }else{
                                Toast.makeText(ThirdActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        Sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sname= Sname.getText().toString();
                String semail = Semail.getText().toString();
                String spassword = Spassword.getText().toString();
                String sphone = Sphone.getText().toString();
                String supi = SUPI.getText().toString();
                Map<String,String> userMap = new HashMap<>();

                userMap.put("name",sname);
                userMap.put("email",semail);
                userMap.put("password",spassword);
                userMap.put("phone",sphone);
                userMap.put("UPI",supi);

                mFirestore.collection("Secretary").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ThirdActivity.this, "Data Recorded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(ThirdActivity.this,"Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

                if(Validate()){
                    //Upload data to database
                    String sec_email = Semail.getText().toString().trim();
                    String sec_password = Spassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(sec_email,sec_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                               /* User user =new User(
                                        name,email,phone,house
                                );*/

                                //FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                                SendEmailVerification();
                                Toast.makeText(ThirdActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ThirdActivity.this,MainActivity.class));
                                sendSecretaryData();
                                Toast.makeText(ThirdActivity.this,"Successfully Registered", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ThirdActivity.this,MainActivity.class));
                            }else{
                                Toast.makeText(ThirdActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });


    }

    private void setupUIViews(){
        userName = (EditText)findViewById(R.id.etName);
        userPassword = (EditText)findViewById(R.id.etPassword);
        userEmail = (EditText)findViewById(R.id.etEmail);
        userPhone = (EditText)findViewById(R.id.etPhone);
        userHouse = (EditText)findViewById(R.id.etHouse_No);
        regButton = (Button)findViewById(R.id.btnSubmit);

    }

    private Boolean validate(){
        Boolean result = false;

        name = userName.getText().toString();
        password = userPassword.getText().toString();
        email = userEmail.getText().toString();
        phone = userPhone.getText().toString();
        house = userHouse.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || house.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private Boolean Validate(){
        Boolean result = false;

        secname = Sname.getText().toString();
        secpassword = Spassword.getText().toString();
        secemail = Semail.getText().toString();
        secphone = Sphone.getText().toString();
        secupi = SUPI.getText().toString();

        if (secname.isEmpty() || secpassword.isEmpty() || secemail.isEmpty() || secphone.isEmpty() || secupi.isEmpty()){
            Toast.makeText(this,"Please enter all the details" , Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(ThirdActivity.this,"Successfully Registered , Verification Mail sent !", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(ThirdActivity.this,MainActivity.class));
                    }else{
                        Toast.makeText(ThirdActivity.this,"Verification Mail hasn't sent !",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void SendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        sendSecretaryData();
                        Toast.makeText(ThirdActivity.this,"Successfully Registered , Verification Mail sent !", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(ThirdActivity.this,MainActivity.class));
                    }else{
                        Toast.makeText(ThirdActivity.this,"Verification Mail hasn't sent !",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(name,email,phone,house);

    }

    private void sendSecretaryData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference(firebaseAuth.getUid());
        SecretaryProfile secretaryProfile = new SecretaryProfile(secname,secemail,secphone,secupi);
    }

    public void checkButton(View view){
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.Radio_Mem:
                if (checked)
                    l.setVisibility(l.VISIBLE);
                    m.setVisibility(m.INVISIBLE);
                break;
            case R.id.Radio_Sec:
                if (checked)
                    m.setVisibility(m.VISIBLE);
                    l.setVisibility(l.INVISIBLE);
                break;

        }
    }



    /*yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
            if (yes.isChecked()){
                l.setVisibility(l.VISIBLE);
                m.setVisibility(m.INVISIBLE);
            }
        }
    });*/

    /*no.checkButton(new CompoundButton.OnCheckedChangeListener(){
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
            if (no.isChecked()){
                m.setVisibility(m.VISIBLE);
                l.setVisibility(l.INVISIBLE);
            }
        }
    });*/

}
