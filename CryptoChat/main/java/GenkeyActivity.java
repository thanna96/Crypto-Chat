package com.example.thann.cryptochat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GenkeyActivity extends AppCompatActivity {

    Button buttonCopy;
    Button buttonGenKey;
    EditText pubKeyText;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genkey);
        buttonGenKey = (Button) findViewById(R.id.buttonGenKey);
        buttonCopy = (Button) findViewById(R.id.buttonCopy);

    }

    public void genKey() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg =
                KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        PublicKey pub = kp.getPublic();
        PrivateKey pvt = kp.getPrivate();
        String pubKeyAsString = Base64.encodeToString(pub.getEncoded(), Base64.NO_PADDING|Base64.NO_WRAP);
        String pvtKeyAsString = Base64.encodeToString(pvt.getEncoded(), Base64.NO_PADDING|Base64.NO_WRAP);
        pubKeyText = (EditText) findViewById(R.id.PubKeyText);
        pubKeyText.setText(pubKeyAsString);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).child("Info").
                child("PublicKey").setValue(pubKeyAsString);
        databaseReference.child(user.getUid()).child("Info").
                child("PrivateKey").setValue(pvtKeyAsString);
    }

    public void onClick(View v) throws NoSuchAlgorithmException {
        if (v == buttonGenKey) {
            genKey();
        }

        if (v == buttonCopy) {
            pubKeyText.setTextIsSelectable(true);
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Key", pubKeyText.getText().toString());
            clipboard.setPrimaryClip(clip);
        }
    }
}
