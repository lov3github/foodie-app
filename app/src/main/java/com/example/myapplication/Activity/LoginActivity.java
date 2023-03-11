package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_phone, edt_pass;
    private TextView txtchange_password, txt_view_food, txt_dang_ky_ngay;
    private Button btn_login, btn_login_fb, btn_login_gg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_phone = findViewById(R.id.edt_phone);
        edt_pass = findViewById(R.id.edt_pass);
        txtchange_password = findViewById(R.id.txtchange_password);
        txtchange_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ChangePassActivity.class);
                startActivity(intent);
            }
        });
        txt_view_food = findViewById(R.id.txt_view_food);
        txt_view_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ViewFoodActivity                 .class);
                startActivity(intent);
            }
        });
        txt_dang_ky_ngay = findViewById(R.id.txt_dang_ky_ngay);
        btn_login = findViewById(R.id.btn_login);
        btn_login_fb = findViewById(R.id.btn_login_fb);
        btn_login_gg = findViewById(R.id.btn_login_gg);
    }
}