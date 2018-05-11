package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.utils.UserSession;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.model.personpkg.Person;
import com.foodorder.tatsuya.foodorder.task.FacebookRegistration;
import com.foodorder.tatsuya.foodorder.task.LoginTask;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;
import com.foodorder.tatsuya.foodorder.utils.EndPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements OnTaskCompleted<Boolean> {

    private Button btnLogin;
    private TextView tvRegister, tvFacebook;
    private EditText edtUsername, edtPassword;
    CallbackManager callbackManager = CallbackManager.Factory.create();
    private LoginButton btnLoginFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        if (AccessToken.getCurrentAccessToken() != null) {
            System.out.println("access token not null!");
        }
        else{
            System.out.println("access token is null!");
        }
    }

    private void init() {
        tvRegister = findViewById(R.id.tvRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
//        tvFacebook = findViewById(R.id.tv_facebook);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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


        this.btnLoginFacebook = findViewById(R.id.fb_login_btn);

        this.btnLoginFacebook.setReadPermissions(Arrays.asList("email", "user_birthday", "public_profile"));

        this.btnLoginFacebook.registerCallback(
                callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (object, response) -> {
                                    Log.d("response", object.toString());
                                    parseDataFromFB(object);
                                }
                        );
                        Bundle bundle = new Bundle();
                        bundle.putString("fields", "id, email, birthday, name");
                        request.setParameters(bundle);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("Login canceled.");
                    }

                    @Override
                    public void onError(FacebookException e) {
                        System.out.println("Login failed.");
                    }
                });
    }

    private void parseDataFromFB(JSONObject object) {
        String email = null;
        try {
            email = object.getString("email");
        } catch (JSONException e) {
            email = "test@gmail.com";
        }

        String name = null;
        try {
            name = object.getString("name");
            String userID = object.getString("id");
            Account account = new Account(userID, "NULL");
            Person person = new Person(account, 23, name);
            UserSession.getInstance().putAccount(this, account);
            new FacebookRegistration(this, new EndPoint<>(), person, email).execute();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            super.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void handle(Boolean value) {
        if (value) {
            Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            Account account = new Account(username, password);
            UserSession.getInstance().putAccount(this, account);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            super.startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
