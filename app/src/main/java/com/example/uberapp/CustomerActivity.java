package com.example.uberapp;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerActivity extends AppCompatActivity {

    private EditText memail, mpassword;
    private Button login, register;
    FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        getSupportActionBar().setTitle("Driver Mode");

        memail =(EditText) findViewById(R.id.ed_username);
        mpassword =(EditText) findViewById(R.id.ed_password);
        login=(Button) findViewById(R.id.login_Button);
        register=(Button)findViewById(R.id.reg_Button);

        mauth =FirebaseAuth.getInstance();
        firebaseAuthListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent=new Intent(CustomerActivity.this, MapActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=memail.getText().toString();
                String password=mpassword.getText().toString();
                mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(CustomerActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(CustomerActivity.this, "Somenthing went worng", Toast.LENGTH_SHORT).show();
                        }
                        else{

                        }

                    }
                });

            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=memail.getText().toString();
                String password=mpassword.getText().toString();
                mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(CustomerActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String user_id=mauth.getCurrentUser().getUid();
                            DatabaseReference currentuser_db= FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(user_id);
                            currentuser_db.setValue(true);
                        }

                    }
                });


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mauth.removeAuthStateListener(firebaseAuthListener);
    }

}