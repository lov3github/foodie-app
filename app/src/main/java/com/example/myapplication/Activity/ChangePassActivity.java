package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class ChangePassActivity extends AppCompatActivity {

    private EditText edt_pass, edt_new_pass, edt_enter_pass;
    private Button btn_save, btn_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        edt_pass = findViewById(R.id.edt_pass);
        edt_new_pass = findViewById(R.id.edt_new_pass);
        edt_enter_pass = findViewById(R.id.edt_enter_pass);
        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_pass.setText("");
                edt_new_pass.setText("");
                edt_enter_pass.setText("");
            }
        });
    }

}