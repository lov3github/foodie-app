package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class AppTourActivity extends AppCompatActivity {

    private Button btnFd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_tour);
        btnFd = findViewById(R.id.btn_fd);
        btnFd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });
    }

    private void nextActivity() {
        Intent intent = new Intent(AppTourActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}