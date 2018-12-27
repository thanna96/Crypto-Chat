package com.example.thann.cryptochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePageActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView textViewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewUser = (TextView) findViewById(R.id.Welcome);
        textViewUser.setText("Welcome, " +user.getEmail());
    }

    /** Called when the user taps the contacts button */
    public void contactButton(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the about me button */
    public void abtMeButton(View view) {
        Intent intent = new Intent(this, AboutMeActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the encrypt button */
    public void encryptButton(View view) {
        Intent intent = new Intent(this, EncryptActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the decrypt button */
    public void decryptButton(View view) {
        Intent intent = new Intent(this, DecryptActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the keygen button */
    public void genkeyButton(View view) {
        Intent intent = new Intent(this, GenkeyActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the settings button */
    public void settingsButton(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
