package com.example.thann.cryptochat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class EncryptActivity extends AppCompatActivity {

    private EditText Input;
    private TextView output;
    private FirebaseAuth firebaseAuth;
    List<Contacts> contact;
    Spinner spinner;
    DatabaseReference databasecontacts;
    Button encryptText;
    Button buttonCopy;
    Button Clear;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        buttonCopy = (Button) findViewById(R.id.bCopy);
        encryptText = (Button) findViewById(R.id.bDecrypt);
        Input = (EditText) findViewById(R.id.inputText2);
        output = (TextView) findViewById(R.id.outputText2);
        contact = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        spinner = (Spinner) findViewById(R.id.spinner);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databasecontacts = FirebaseDatabase.getInstance().getReference("UserInfo").child(user.getUid()).child("Contacts");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String key = contact.get(position).getKey();
                Input.setText(key);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databasecontacts = FirebaseDatabase.getInstance().getReference("UserInfo").child(user.getUid()).child("Contacts");
        databasecontacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact.clear();

                for(DataSnapshot contactSnapshot : dataSnapshot.getChildren()) {
                    Contacts contacts = contactSnapshot.getValue(Contacts.class);
                    contact.add(contacts);
                }

                ContactKeyList contactListAdapter = new ContactKeyList(EncryptActivity.this, contact);
                contactListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(contactListAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void encryptButton () throws Exception {
        output = (TextView) findViewById(R.id.outputText2);
        String message = output.getText().toString().trim();
        String keyString = Input.getText().toString().trim();
        byte[] encodedKey = Base64.decode(keyString, Base64.NO_PADDING|Base64.NO_WRAP);

        X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(spec);

        byte[] encrypted  =  encrypt(key, message);
        String secretMessage = Base64.encodeToString(encrypted, Base64.NO_PADDING|Base64.NO_WRAP);
        output.setText(secretMessage);

    }


    public static byte[] encrypt(PublicKey publicKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] message2 = Base64.decode(message, Base64.NO_PADDING|Base64.NO_WRAP);
        return cipher.doFinal(message2);
    }


    public void onClick(View v) throws Exception {
        if (v == encryptText) {
            encryptButton();
        }
        if (v == buttonCopy) {
            output.setTextIsSelectable(true);
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Key", output.getText().toString());
            clipboard.setPrimaryClip(clip);
        }
        if (v == Clear){
            output.setText(null);
        }

    }
    public void emailButton(View view) {
        int position = spinner.getSelectedItemPosition();
        String email = contact.get(position).getEmail();
        Intent intent = new Intent(this, SendEmailActivity.class);
        intent.putExtra("EncryptedMessage",output.getText().toString());
        intent.putExtra("Email",email);
        startActivity(intent);
    }

}
