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
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {

    private Button resetPassword, btnRegister, btnsignin;
    private EditText emailAddress;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        emailAddress = findViewById(R.id.etEmailAddress);
        resetPassword = findViewById(R.id.btnResetPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnsignin =findViewById(R.id.btnsignin);
        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(PasswordActivity.this, Register.class));
            }
        });

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(PasswordActivity.this, MainActivity.class));
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = emailAddress.getText().toString().trim();

                if(useremail.isEmpty()){
                    Toast.makeText(PasswordActivity.this, "Please Enter your Email Address", Toast.LENGTH_SHORT).show();

                }else{
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PasswordActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(PasswordActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(PasswordActivity.this, "Please confirm email address", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
