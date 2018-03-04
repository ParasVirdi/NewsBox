package com.gradledevextreme.light.newsbox.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.gradledevextreme.light.newsbox.R;

public class LoginActivity extends AppCompatActivity {




    private FirebaseAuth mAuth;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions googleSignInOptions;
    private SignInButton signbtn;
    private static final int REQ_CODE = 9001;
    private FirebaseAuth.AuthStateListener authStateListener;
    private ProgressDialog prog;
    private ImageView logoImage;
    private TextView newsBox;
    private TextView welcomeText;
    private Button getStarted;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_login);




        logoImage= (ImageView) findViewById(R.id.logoImage);
        newsBox = (TextView) findViewById(R.id.logotext);
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        getStarted = (Button) findViewById(R.id.getstarted);
        signbtn = (SignInButton) findViewById(R.id.google_btn);
        prog = new ProgressDialog(LoginActivity.this);
        prog.setMessage("Logging in...");
        prog.setIndeterminate(true);
        prog.setCanceledOnTouchOutside(false);




        //set Alpha Animation
        AlphaAnimation animation = new AlphaAnimation(0f, 1.0f);
        animation.setDuration(500);
        animation.setStartOffset(500);
        animation.setFillAfter(true);
        newsBox.startAnimation(animation);
        welcomeText.startAnimation(animation);
        logoImage.startAnimation(animation);




        //to set typeface
        Typeface type = Typeface.createFromAsset(getAssets(), "creation.ttf");
        newsBox.setTypeface(type);
        welcomeText.setTypeface(type);




        //on click listener on get started
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotomain();

            }
        });




        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();




        //shared preferences for one time login
        SharedPreferences settings = getSharedPreferences(NavigationActivity.PREFS_NAME, 0);
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);
        if (hasLoggedIn) {
            gotomain();
        }




         googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();




        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignIn();
            }
        });


    }




    //GOOGLE sign IN
    private void mSignIn() {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(i, REQ_CODE);

    }




    // to get result after sign in method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQ_CODE == requestCode) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
                firebaseAuthWithGoogle(googleSignInAccount);
                prog.show();

            }

        }
    }




    // to run firebaseAuth with google
    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            prog.dismiss();
                            Toast.makeText(LoginActivity.this, "Authentication failed!Check your internet connectivity!", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            prog.dismiss();
                            startActivity(i);
                            finish();
                        }
                    }
                });
    }




    public void gotomain() {

        Intent registerActivity = new Intent(LoginActivity.this, NavigationActivity.class);
        registerActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(registerActivity);
        this.finish();

    }




}
