package com.gradledevextreme.light.newsbox.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.gradledevextreme.light.newsbox.BottomNavigation.Buzz;
import com.gradledevextreme.light.newsbox.BottomNavigation.ForYou;
import com.gradledevextreme.light.newsbox.BottomNavigation.HeadLines;
import com.gradledevextreme.light.newsbox.BottomNavigation.Notifications;
import com.gradledevextreme.light.newsbox.Fragment_main;
import com.gradledevextreme.light.newsbox.R;


public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {




    private Toolbar toolbar;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mAuth;
    private ImageView profileimage;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView nameheader, emailheader;
    private View hView;
    private NavigationView navigationView;
    private SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyPrefsFile";
    private SharedPreferences settings;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);




        // to remove status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("News Box");
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        setSupportActionBar(toolbar);




        // to add shared preferences variable
        settings = getSharedPreferences(NavigationActivity.PREFS_NAME, 0);
        editor = settings.edit();
        editor.putBoolean("hasLoggedIn", true);
        editor.putBoolean("hasSign", true);
        //Done by anshuman for default  pref India
        editor.putString("location","India");
        editor.apply();




        // Bottom Navigation View
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




        //fragment
        Fragment_main fragment = new Fragment_main();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();




        //Declaration
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        hView = navigationView.getHeaderView(0);
        profileimage = (ImageView) hView.findViewById(R.id.profilepic);
        nameheader = (TextView) hView.findViewById(R.id.mName);
        emailheader = (TextView) hView.findViewById(R.id.mEmail);




        //To open Fragment when activty is created
        HeadLines frag  = new HeadLines();
        android.support.v4.app.FragmentTransaction fragmentTransactio =
                getSupportFragmentManager().beginTransaction();
        fragmentTransactio.replace(R.id.fragment_main, frag);
        fragmentTransactio.commit();




        //mauth listerner
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    String name = mAuth.getCurrentUser().getDisplayName();
                    String email = mAuth.getCurrentUser().getEmail();
                    String url = mAuth.getCurrentUser().getPhotoUrl().toString();

                    nameheader.setText(name);
                    emailheader.setText(email);
                    Glide.with(getApplicationContext()).load(url).into(profileimage);

                }
            }
        };




        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();




        googleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(NavigationActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();




        navigationView.setNavigationItemSelectedListener(this);
    }




    @Override
    protected void onStart() {
        mAuth.addAuthStateListener(authStateListener);
        super.onStart();
    }




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.Headlines:
                    HeadLines fragment = new HeadLines();
                    android.support.v4.app.FragmentTransaction fragmentTransaction =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_main, fragment);
                    fragmentTransaction.commit();
                    toolbar.setTitle("Headlines");
                    return true;

                case R.id.ForYou:
                    ForYou fragment1 = new ForYou();
                    android.support.v4.app.FragmentTransaction fragmentTransaction1 =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(R.id.fragment_main, fragment1);
                    fragmentTransaction1.commit();
                    toolbar.setTitle("For You");
                    return true;

                case R.id.Buzz:
                    Buzz fragment2 = new Buzz();
                    android.support.v4.app.FragmentTransaction fragmentTransaction2 =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fragment_main, fragment2);
                    fragmentTransaction2.commit();
                    toolbar.setTitle("Buzz");
                    return true;

                case R.id.Notifications:

                    Notifications fragment3 = new Notifications();
                    android.support.v4.app.FragmentTransaction fragmentTransaction3 =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fragment_main, fragment3);
                    fragmentTransaction3.commit();
                    toolbar.setTitle("Notifications");
                    return true;

            }
            return false;
        }

    };




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            if (mAuth != null) {
                // to clear login sharePreferenecs
                editor.clear();
                editor.commit();
                mAuth.signOut();
                if (googleApiClient.isConnected()) {
                    googleApiClient.clearDefaultAccountAndReconnect();
                }
                finishAffinity();
                Intent registerActivity = new Intent(NavigationActivity.this, LoginActivity.class);
                startActivity(registerActivity);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.General) {
            // Handle the camera action
        } else if (id == R.id.Yourinterest) {

        } else if (id == R.id.LanCountry) {

        } else if (id == R.id.savedarticles) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.FE) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
