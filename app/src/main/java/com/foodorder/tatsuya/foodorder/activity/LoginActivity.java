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
import com.foodorder.tatsuya.foodorder.UserSession;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.LoginTask;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

public class LoginActivity extends AppCompatActivity implements OnTaskCompleted<Boolean> {

    private Button btnLogin;
    private TextView tvRegister, tvFacebook;
    private EditText edtUsername, edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvFacebook = findViewById(R.id.tv_facebook);


        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, null);
                startActivity(i);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                Account account = new Account();
                account.setPassword(password);
                account.setUsername(username);

               new LoginTask(LoginActivity.this,
                        LoginActivity.this).execute(account);
            }
        });

        tvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

    @Override
    public void handle(Boolean value) {
        if (value) {
            Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            Account account = new Account(username,password);
            UserSession.getInstance().putAccount(this,account);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            super.startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
