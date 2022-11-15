package com.example.uberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class DriverLoginActivity extends AppCompatActivity {
     private EditText memail, mpassword;
     private Button login, register;
     FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        // working on action bar set color and text
        ActionBarMenu();

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
                    Intent intent=new Intent(DriverLoginActivity.this, DriverMapsActivity.class);
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
                mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(DriverLoginActivity.this, "Somenthing went worng"+email+""+password, Toast.LENGTH_SHORT).show();
                        }
                        else{
//                   Intent intent=new Intent(getApplicationContext(),DriverMapsActivity.class);
//                   startActivity(intent);
//                   return;
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
            mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String user_id=mauth.getCurrentUser().getUid();
                        DatabaseReference currentuser_db= FirebaseDatabase.getInstance().getReference().child("Users").child("Driver").child(user_id);
                        currentuser_db.setValue(true);
                    }

                }
            });
            }
        });

    }

    private void ActionBarMenu() {
        getSupportActionBar().setTitle("Driver Login Activity");
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0F9D58"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
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