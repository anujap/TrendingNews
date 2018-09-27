package com.example.anuja.trendingnews.app.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

/**
 * This is the MainActivity - the Dashboard Activity.
 * This activity is responsible for handling the following:
 * 1) Firebase Authentication
 */
public class MainActivity extends AppCompatActivity {

    public static final int RC_SIGN_IN = 1; // request code
    private static final String ANONYMOUS = "Anonymous";

    // firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String username;

    // navigation
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateFirebaseAuthentication();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    /**
     * function used to initiate firebase authentication
     */
    private void initiateFirebaseAuthentication() {
        this.username = ANONYMOUS;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    // user is signed in
                    onSignedIn(mFirebaseUser.getDisplayName());
                } else {
                    // user is signed out
                    onSignedOut();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    /**
     * function used when the user is signed in
     * @param user - logged in user name
     */
    private void onSignedIn(String user) {
        this.username = user;

        initializeToolbar();
        initializeNavigationDrawer();
    }

    /**
     * function used when the user is signed out
     */
    private void onSignedOut() {
        this.username = ANONYMOUS;
    }

    /**
     * function used to create navigation drawer
     */
    private void initializeNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();


                // swap fragments here

                return true;
            }
        });
    }

    /**
     * function to initialize the toolbar
     */
    private void initializeToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_settings:
                return true;
            case R.id.menu_signout:
                AuthUI.getInstance().signOut(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "User Signed IN!!", Toast.LENGTH_SHORT).show();
            } else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "User Signed OUT!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
