package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.foodorder.tatsuya.foodorder.R;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegsiter;
    private EditText edtName, edtAge, edtEmail, edtUsername, edtPassword;
    private TextView tvMyPerInfo, tvAccountInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();


    }

    private void init() {
        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        edtEmail = findViewById(R.id.edt_email);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnRegsiter = findViewById(R.id.btn_register);
        btnRegsiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                int age = Integer.parseInt(edtAge.getText().toString().trim());
                String email = edtEmail.getText().toString();
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
