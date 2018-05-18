package com.sheilambadi.android.retrofitproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sheilambadi.android.retrofitproject.R;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity";
    private static final String EMAIL = "email";
    private FirebaseAuth firebaseAuth;
    GoogleSignInClient googleSignInClient;
    AccessToken accessToken;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // request user data required by app
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // client with specified options
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // customize sign in button
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        // register button on click listener to sign in user when clicked
        signInButton.setOnClickListener(this);

        // get shared instance of FirebaseAuth object
        firebaseAuth = FirebaseAuth.getInstance();

        // facebook login
        callbackManager = CallbackManager.Factory.create();

        LoginButton mLoginButton = findViewById(R.id.fb_login_button);

        // Set the initial permissions to request from the user while logging in
        mLoginButton.setReadPermissions(Arrays.asList(EMAIL));

        // accessToken = AccessToken.getCurrentAccessToken();
        /*boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }*/

        // Register a callback to respond to the user
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        setResult(RESULT_OK);
                        accessToken = AccessToken.getCurrentAccessToken();

                        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

                        if (isLoggedIn){
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        setResult(RESULT_CANCELED);
                        Snackbar.make(findViewById(R.id.login_layout), "Login failed", Snackbar.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Snackbar.make(findViewById(R.id.login_layout), "Login failed " + exception.toString(), Snackbar.LENGTH_SHORT).show();
                    }
                });

        accessToken = AccessToken.getCurrentAccessToken();

        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // check for an existing signed in user
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        // handle sign in button taps
        switch (view.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent i = googleSignInClient.getSignInIntent();
        startActivityForResult(i, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // facebook login
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // result returned from sign in intent
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // google sign in was successful, authenticate with firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

                // Todo: save relevant user details in shared preference
            } catch (ApiException e){
                Log.w(TAG, "signInResult fail code = " + e.getStatusCode());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle " + account.getId());

        // exchange id token with firebase credentials and authenticate using firebase
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // sign in is successful, update UI
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                            saveUserData(user);
                        } else {
                            Snackbar.make(findViewById(R.id.login_layout), "Login failed", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void saveUserData(FirebaseUser user) {
        String username = user.getDisplayName();
        Toast.makeText(this, "Welcome, " + username, Toast.LENGTH_SHORT).show();
    }

    // Todo: sign out user
}
