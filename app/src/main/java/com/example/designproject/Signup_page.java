package com.example.designproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static maes.tech.intentanim.CustomIntent.customType;

public class Signup_page extends AppCompatActivity {
    DatabaseHelper db;
    ProgressBar progressBar;
    EditText e1,e2,e3,e4;
    Button b1;
    private FirebaseAuth mAuth;

//     Moving the USER TO DIFFRENT PAGES OF THE LOGIN AND SIGNUP
    public void signup_page(View view){

    }
    public void login_page(View view){
        Intent intent =new Intent(getApplicationContext(),MainActivity.class);
        customType(this,"left-to-right");
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        db=new DatabaseHelper(this);
        e1=(EditText) findViewById(R.id.fullName);
        e2=(EditText) findViewById(R.id.email);
        e3=(EditText) findViewById(R.id.password);
        e4=(EditText) findViewById(R.id.cnfpassword);
        b1=(Button) findViewById(R.id.login);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                String s3=e3.getText().toString();
                String s4=e4.getText().toString();

                registerUser();

                if(s1.equals("") || s2.equals("") || s3.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty " , Toast.LENGTH_SHORT).show();
                }
                else  {
                    if(s3.equals(s4)){
                        Boolean chkemail = db.chkemail(s2);
                        if(chkemail==true){
                            Boolean insert = db.insert(s1,s2,s3);
                            if(insert==true){
//                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                                customType(Signup_page.this,"right-to-left");
//                                startActivity(intent);
                            }
                        }
                        else {
//                            Toast.makeText(getApplicationContext(), "Email Alreday Registered " , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
//                        Toast.makeText(getApplicationContext(), "Password do not match" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void registerUser() {
        final String name = e1.getText().toString().trim();
        final String email = e2.getText().toString().trim();
        String password = e3.getText().toString().trim();
//        final String phone = editTextPhone.getText().toString();

        if (email.isEmpty()) {
            e2.setError("Email is required");
            e2.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            e2.setError("Please enter a valid email");
            e2.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            e3.setError("Password is required");
            e3.requestFocus();
            return;
        }

        if (password.length() < 6) {
            e3.setError("Minimum length of password should be 6");
            e3.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    User user = new User(
                            name,
                            email
                    );
                    FirebaseDatabase.getInstance().getReference("User")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Signup_page.this,"registration sucsessful",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Signup_page.this, Nav_Page.class));

                            }
                        }
                    });
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, Nav_Page.class));
        }

    }
}