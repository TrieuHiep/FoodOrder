package com.foodorder.tatsuya.foodorder.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;

import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.foodorder.tatsuya.foodorder.R;
import com.foodorder.tatsuya.foodorder.model.personpkg.Account;
import com.foodorder.tatsuya.foodorder.task.LoginTask;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by Lenovo on 4/18/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnTaskCompleted<Boolean> {

    private EditText edtUser, edtPass;
    private Button btnLogin;
    private String user, pass;
    private Account account;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String name, email, birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); //dong nay cua t ị gach nay - ko qtrong
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        initView();

        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));// quan trong dong nay
        setLogin_Button();
    }

    private void setLogin_Button() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.d("LoginActivity", response.toString());
                                Log.d("JSON", response.getJSONObject().toString());
                                // Application code
                                try {
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday"); // 01/31/1980 format

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void initView() {
        edtUser = findViewById(R.id.edt_username);
        edtPass = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btnLogin);
        loginButton = (LoginButton) findViewById(R.id.tv_facebook);

        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:

                user = edtUser.getText().toString();
                pass = edtPass.getText().toString();
                account = new Account(user, pass);
                new LoginTask(LoginActivity.this, LoginActivity.this).execute(account);
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void handle(Boolean value) {
        if (value.equals(true)) {
            Toast.makeText(this, "Login Successfully ^^", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login Fail ^^", Toast.LENGTH_SHORT).show();
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .repeat(5)
                    .playOn(findViewById(R.id.edt_username));
            YoYo.with(Techniques.Shake)
                    .duration(300)
                    .repeat(5)
                    .playOn(findViewById(R.id.edt_password));
        }

    }
}
