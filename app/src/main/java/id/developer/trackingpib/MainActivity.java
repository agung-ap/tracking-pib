package id.developer.trackingpib;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import id.developer.trackingpib.util.GlobalFunction;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private String userStatus, uid;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference databaseReference;

    private CardView chat;
    private CardView pib;
    private CardView aboutUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("beranda");

        preferences = this.getSharedPreferences(getString(R.string.CREDENTIAL), Context.MODE_PRIVATE);
        userStatus = preferences.getString(getString(R.string.GET_USER_STATUS),null);
        uid = preferences.getString(getString(R.string.GET_UID),null);

        //init firebase
        auth = FirebaseAuth.getInstance();
        //get current user
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        bindView();
        cardMenu();
    }

    private void bindView(){
        chat = findViewById(R.id.chat_card);
        pib = findViewById(R.id.data_pib_card);
        aboutUs = findViewById(R.id.about_us_card);
    }

    private void cardMenu(){

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userStatus.equals("USER")){
                    startActivity(new Intent(MainActivity.this, ChatRoom.class));

                }else {
                    startActivity(new Intent(MainActivity.this, ChatListActivity.class));
                }
            }
        });

        pib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MenuPibActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.log_out){
            auth.signOut();
            GlobalFunction.addUidAndUserStatusPref(MainActivity.this, "", "");
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }




}
