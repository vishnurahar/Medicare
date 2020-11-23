package com.example.designproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static maes.tech.intentanim.CustomIntent.customType;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2;
    Button b1;
    DatabaseHelper db;
    FirebaseAuth mAuth;

    public void signup_page(View view){
        Intent intent=new Intent(getApplicationContext(),Signup_page.class);
        startActivity(intent);
        customType(this,"right-to-left");
    }
    public void login_page(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHelper(this);
        e1=(EditText)findViewById(R.id.email);
        e2=(EditText) findViewById(R.id.password);

        b1=(Button)findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=e1.getText().toString();
                String password= e2.getText().toString();

                userLogin();

                Boolean Chkemailpassword = db.emailpassword(email , password);
                if(Chkemailpassword==true) {
                    int login_id=db.getID(email,password);
//                    Toast.makeText(getApplicationContext(), "Successfully Logged In" , Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),Nav_Page.class);
                    intent.putExtra("ID", login_id);
                    customType(MainActivity.this,"left-to-rigjt");
//                    startActivity(intent);
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Invalid Credientials " , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void userLogin() {
        String email = e1.getText().toString().trim();
        String password = e2.getText().toString().trim();

        if (email.isEmpty()) {
            e1.setError("Email is required");
            e1.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            e1.setError("Please enter a valid email");
            e1.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            e2.setError("Password is required");
            e2.requestFocus();
            return;
        }

        if (password.length() < 6) {
            e2.setError("Minimum lenght of password should be 6");
            e2.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, Nav_Page.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, Nav_Page.class));
        }
    }
}