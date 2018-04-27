package com.example.kvaru.shoppingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static android.widget.Toast.LENGTH_SHORT;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String EMAIL = "email";
    private LoginButton loginButton;
    private Button btnSignIn, btnSignUp, btnSignout;
    private EditText edEmail,edPwd;
    private CallbackManager callbackManager;
    private TextView tvNavHeaderEmail;
    private String email, fbName;
    private String password;
    private boolean flag =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        // logger.logPurchase(BigDecimal.valueOf(4.32), Currency.getInstance("USD"));

        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignout = (Button) findViewById(R.id.btnSignOut);
        edEmail = (EditText) findViewById(R.id.etUsername);
        edPwd = (EditText) findViewById(R.id.etPassword);
        tvNavHeaderEmail = (TextView) findViewById(R.id.tvNavHeaderEmail);
        // If you are using in a fragment, call loginButton.setFragment(this);
        btnSignout.setVisibility(View.GONE);


        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        AccessToken accessToken = loginResult.getAccessToken();
                        Profile profile = Profile.getCurrentProfile();
                        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            Log.d("OBJ", "fb json object: " + object.get("name"));
                                            Log.d("RES", "fb graph response: " + response);
                                            fbName = String.valueOf(object.get("name"));
                                            Log.d("OBJNAME", "fb json object: " + fbName);
                                            Intent fbIntent = new Intent(getApplication(), MainActivity.class);
                                            fbIntent.putExtra("fbName", fbName);
                                            fbIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_TASK_ON_HOME | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(fbIntent);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "name");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  startActivity(new Intent(LogInActivity.this, MainActivity.class));
//                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
//            }
//        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmailPwd();
                if (email.equals("") && password.equals("")){
                    Toast.makeText(getApplicationContext(),"Email or Password is empty",LENGTH_SHORT).show();

                }else{
                    registerAccount(email,password);
                }

            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });




    }

    public void getEmailPwd(){
            email = edEmail.getText().toString();
            password = edPwd.getText().toString();
    }

    public void signIn(){
    getEmailPwd();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter Valid Email", LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter Valid password", LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("Login", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent userIntent = new Intent(getApplication(),MainActivity.class);
                            userIntent.putExtra("userEmail",user.getEmail());
                            startActivity(userIntent);
                            edEmail.setText("");
                            edPwd.setText("");
                        }else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void registerAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("User", "account created");
                            Toast.makeText(LogInActivity.this,"Account has been created", LENGTH_SHORT).show();
                        } else {
                            Log.d("User", "register account failed", task.getException());
                            Toast.makeText(LogInActivity.this,
                                    "account registration failed.",
                                    LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void logOut() {
        mAuth.signOut();
        updateUI(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }



    private void updateUI(FirebaseUser user) {

        if (user != null) {
            buttonVisibilityGone();
        } else {
            buttonVisibilityVisible();
        }
    }

    public void buttonVisibilityGone(){
        btnSignIn.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        btnSignUp.setVisibility(View.GONE);
        btnSignout.setVisibility(View.VISIBLE);
    }

    public void buttonVisibilityVisible(){
        btnSignIn.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        btnSignUp.setVisibility(View.VISIBLE);
        btnSignout.setVisibility(View.GONE);
    }

}
