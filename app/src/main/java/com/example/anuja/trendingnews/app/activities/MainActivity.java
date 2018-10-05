package com.example.anuja.trendingnews.app.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anuja.trendingnews.R;
import com.example.anuja.trendingnews.app.fragments.NewsFragment;
import com.example.anuja.trendingnews.app.fragments.CategoriesFragment;
import com.example.anuja.trendingnews.app.fragments.CommentsFragment;
import com.example.anuja.trendingnews.app.fragments.FavoritesFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileDescriptor;
import java.io.IOException;
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
    private Uri userPhotoUri;
    private ImageView ivUserDisplayPhoto;

    Toolbar mToolbar;

    // navigation
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextView tvNavigationHeaderUserName;

    Fragment fragment = null;

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
                    onSignedIn(mFirebaseUser.getDisplayName(), mFirebaseUser.getPhotoUrl());
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
    private void onSignedIn(String user, Uri userPhotoUri) {
        this.username = user;
        this.userPhotoUri = userPhotoUri;

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
        tvNavigationHeaderUserName = (TextView) findViewById(R.id.tv_user_name);
        ivUserDisplayPhoto = (ImageView) findViewById(R.id.img_user_photo);

        setUsernameDetails();
        performDrawerToggle();

        mNavigationView.setCheckedItem(R.id.menu_categories);
        displaySelectedFragment(R.id.menu_categories);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                displaySelectedFragment(menuItem.getItemId());
                // swap fragments here
                setTitle(menuItem.getTitle());
                return true;
            }
        });
    }

    private void setUsernameDetails() {
        tvNavigationHeaderUserName.setText(username);
        uriToBitmap(userPhotoUri);
    }

    /**
     * function used to convert the uri to bitmap
     * @param selectedFileUri
     */
    private void uriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            ivUserDisplayPhoto.setImageBitmap(image);

            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * function used to perform the actionbar drawer toggle
     */
    private void performDrawerToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * function called to display the fragment based on the menu
     * item selected from the navigation drawer
     */
    private void displaySelectedFragment(int menuItemId) {

        switch(menuItemId) {
            case R.id.menu_categories:
                fragment = new CategoriesFragment();
                break;
            case R.id.menu_all_news:
                fragment = new NewsFragment();
                break;
            case R.id.menu_favorites:
                fragment = new FavoritesFragment();
                break;
            case R.id.menu_comments:
                fragment = new CommentsFragment();
                break;
            default:
                break;
        }

        if(fragment != null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }
    }

    /**
     * function to initialize the toolbar
     */
    private void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
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
