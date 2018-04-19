package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.UserSession;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.LoginTask;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

public class LoginActivity extends AppCompatActivity implements OnTaskCompleted<Boolean> {

    private Button btnLogin;
    private LoginButton loginButton;
    private TextView tvRegister, tvFacebook;
    private EditText edtUsername, edtPassword;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());

        init();
    }

    private void init() {
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        loginButton = (LoginButton) findViewById(R.id.tv_facebook);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "onCancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "onError", Toast.LENGTH_SHORT).show();

            }
        });


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .repeat(5)
                    .playOn(findViewById(R.id.edt_username));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .repeat(5)
                    .playOn(findViewById(R.id.edt_password));

            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();

        }
    }
}
