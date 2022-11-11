package com.example.uberapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private Button drButton,cusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#0000ff"));
        getSupportActionBar().setTitle("Home Activity");
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        drButton=findViewById(R.id.driver_button);
        cusButton=findViewById(R.id.customer_button);
        // driver activity gets opening
        drButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this, DriverActivity.class);
                    startActivity(intent);
                    finish();
            }
        });

        // customer activity gets opening
        cusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}