package com.foodorder.tatsuya.foodorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foodorder.tatsuya.foodorder.controller.RegsitrationTask;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.model.personpkg.Person;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

/**
 * Created by hiennv on 04/04/2018.
 */

public class RegisterActivity extends Activity implements OnTaskCompleted<Boolean>{
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
                Log.i("age", age+"");
                String email = edtEmail.getText().toString();
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                Account acc = new Account();
                acc.setUsername(username);
                acc.setPassword(password);

                Person person = new Person();
                person.setAccount(acc);
                person.setAge(age);
                person.setFullName(name);

                new RegsitrationTask(RegisterActivity.this, RegisterActivity.this).execute(person);


            }
        });

    }

    @Override
    public void handle(Boolean value) {
        if(value.equals(true)){
            Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
        }
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
