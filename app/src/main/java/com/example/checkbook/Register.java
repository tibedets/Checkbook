package com.example.checkbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private EditText userName, userPassword, userEmail;
    private Button regButton, signinButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data
                    String user_Email = userEmail.getText().toString().trim();
                    String user_Password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_Email, user_Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendEmailVerification();
                            }else{
                                Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });
    }

    private void setupUIViews(){
        userName = findViewById(R.id.etnamer);
        userPassword = findViewById(R.id.etpassr);
        userEmail = findViewById(R.id.etemailr);
        regButton = findViewById(R.id.btnregr);
        signinButton = findViewById(R.id.btnsigninr);
    }

    private Boolean validate(){
        Boolean result = false;

        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show();
        }else if(name.isEmpty()){
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
        }else if(password.isEmpty()) {
            Toast.makeText(this, "Please enter an password", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "Successfully Registered, Verification email sent!", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(Register.this, MainActivity.class));
                    }else{
                        Toast.makeText(Register.this, "Registration Unsuccessful. Verification email not sent!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
