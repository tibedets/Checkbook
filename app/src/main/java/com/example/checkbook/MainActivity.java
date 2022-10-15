package com.example.checkbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText userName, userPassword;
    private Button regButton, signinButton;
    private TextView resetPassword;
    //private int counter = 5;
    private FirebaseAuth firebaseAuth;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userName = (EditText)findViewById(R.id.etname);
        userPassword = (EditText)findViewById(R.id.etpass);
        regButton = (Button)findViewById(R.id.btnreg);
        signinButton = findViewById(R.id.btnsignin);
        resetPassword = findViewById(R.id.tvForgotPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        firebaseAuth.signOut();

        if(user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, ListActivity.class));
        }

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate(userName.getText().toString(), userPassword.getText().toString());
                //startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
            }
        });
    }

    private void validate(final String userName, String userPassword) {
        if(userName.isEmpty()){
            Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show();
        }else if(userPassword.isEmpty()){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }else {
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(MainActivity.this, Main2Activity.class));
                        checkEmailVerification();
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void checkEmailVerification(){
       FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
       Boolean emailflag = firebaseUser.isEmailVerified();

       if(emailflag){
           startActivity(new Intent(MainActivity.this, ListActivity.class));
       }else{
           Toast.makeText(this, "Verify your email address", Toast.LENGTH_SHORT).show();
           firebaseAuth.signOut();
       }

    }



}
