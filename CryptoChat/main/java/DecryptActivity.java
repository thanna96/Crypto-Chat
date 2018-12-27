package com.example.thann.cryptochat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

public class DecryptActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    DatabaseReference database;
    String privKey;
    EditText input;
    EditText output;
    Button decrypt;
    Button Clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        decrypt = (Button) findViewById(R.id.bDecrypt);
        Clear = (Button) findViewById(R.id.bClear);
        input = (EditText) findViewById(R.id.inputText2);
        output = (EditText) findViewById(R.id.outputText2);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("UserInfo").child(user.getUid()).child("Info").child("PrivateKey");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                output.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static byte[] decrypt(PrivateKey privateKey, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encrypted);
    }
    public void decryptButton () throws Exception {
        output = (EditText) findViewById(R.id.outputText2);
        input = (EditText) findViewById(R.id.inputText2);
        String privKey = output.getText().toString();
        String Message = input.getText().toString();
        byte[] encodedMessage = Base64.decode(Message, Base64.NO_PADDING|Base64.NO_WRAP);
        byte[] encodedKey = Base64.decode(privKey, Base64.NO_PADDING|Base64.NO_WRAP);

        PKCS8EncodedKeySpec keySpecPv = new PKCS8EncodedKeySpec(encodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpecPv);

        byte[] encrypted  =  decrypt(key, encodedMessage);
        String secretMessage = Base64.encodeToString(encrypted, Base64.NO_PADDING|Base64.NO_WRAP);
        output.setText(secretMessage);
    }

    public void onClick(View v) throws Exception {
        if (v == decrypt) {
            decryptButton();
        }
        if (v == Clear){
            output.setText(null);
            input.setText(null);
        }
    }
}
