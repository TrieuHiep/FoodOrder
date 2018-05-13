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
import com.foodorder.tatsuya.foodorder.task.SocialRegistration;
import com.foodorder.tatsuya.foodorder.task.LoginTask;
import com.foodorder.tatsuya.foodorder.task.OnTaskCompleted;
import com.foodorder.tatsuya.foodorder.utils.EndPoint;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements OnTaskCompleted<Boolean> {

    //system sign in
    private Button btnLogin;
    private TextView tvRegister;
    private EditText edtUsername, edtPassword;

    //facebook sign in
    CallbackManager callbackManager = CallbackManager.Factory.create();
    private LoginButton btnLoginFacebook;

    //google sign in
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 2;
    private SignInButton googleBut;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initSystemComponents();
        initFacebookComponents();
        initGoogleComponents();
    }

    private void initSystemComponents() { // initialize system components and handle theirs events
        this.tvRegister = findViewById(R.id.tvRegister);
        this.btnLogin = findViewById(R.id.btnLogin);
        this.edtUsername = findViewById(R.id.edtUsername);
        this.edtPassword = findViewById(R.id.edtPassword);
        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
        btnLogin.setOnClickListener(view -> {
            String username = edtUsername.getText().toString();
            String password = edtPassword.getText().toString();
            Account account = new Account();
            account.setPassword(password);
            account.setUsername(username);
            //send login request to server and handle the result from server in LoginActivity
            new LoginTask(LoginActivity.this,
                    LoginActivity.this).execute(account);
        });
    }

    private void initFacebookComponents() { // initialize facebook components and handle theirs events
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

    private void initGoogleComponents() { // initialize google components and handle theirs events
        this.googleBut = super.findViewById(R.id.sign_in_button);
        this.googleBut.setOnClickListener(view -> {
            googleSignIn();
        });
        findViewById(R.id.sign_out_button).setOnClickListener(view -> {
            googleSignOut();
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build(); // config Sign in
        this.mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        this.mAuth = FirebaseAuth.getInstance(); // initialize_auth
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

            //send registration request to server to create new Account
            new SocialRegistration(this, new EndPoint<>(), person, email).execute();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            super.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Account loggedAccount = new Account(user.getUid(), "NULL");
                                Person person = new Person(loggedAccount, 23, user.getDisplayName());
                                UserSession.getInstance().putAccount(LoginActivity.this, loggedAccount);
                                //send registration request to server to create new Account
                                new SocialRegistration(this, new EndPoint<>(), person, user.getEmail()).execute();
                                updateUI(user);
                                LoginActivity.super.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                updateUI(null);
                            }
                        }
                );
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void googleSignOut() {
        mAuth.signOut(); //firebase sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            updateUI(null);
        });// Google sign out
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                updateUI(null);
            }
        } else {
            this.callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void handle(Boolean value) { //handle result from server
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
}
