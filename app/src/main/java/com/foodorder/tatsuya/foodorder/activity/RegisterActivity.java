package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.model.personpkg.Person;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;
import com.foodorder.tatsuya.foodorder.task.RegistrationTask;
import com.foodorder.tatsuya.foodorder.utils.EndPoint;

public class RegisterActivity extends AppCompatActivity
        implements OnTaskCompleted<Boolean> {
    private Button btnRegsiter;
    private EditText edtName, edtAge, edtEmail, edtUsername, edtPassword;


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

                Person person = new Person(new Account(username,password),age,name);
                new RegistrationTask(RegisterActivity.this, RegisterActivity.this, person, email)
                        .execute();
            }
        });

    }

    @Override
    public void handle(Boolean value) {
        if (value) {
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
            super.finish();
        }
        else{
            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
