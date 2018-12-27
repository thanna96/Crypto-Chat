package com.example.thann.cryptochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutMeActivity extends AppCompatActivity  implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,DBinfoRef;
    private EditText editTextName, editTextEmail,
    editTextUsername, editTextPublicKey;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextName = (EditText) findViewById(R.id.name);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPublicKey = (EditText) findViewById(R.id.publickey);
        buttonSave = (Button) findViewById(R.id.savebutton);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DBinfoRef = FirebaseDatabase.getInstance().getReference("UserInfo").child(user.getUid()).child("Info");

        DBinfoRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                editTextName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DBinfoRef.child("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                editTextEmail.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DBinfoRef.child("PublicKey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pk = dataSnapshot.getValue(String.class);
                editTextPublicKey.setText(pk);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DBinfoRef.child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String u = dataSnapshot.getValue(String.class);
                editTextUsername.setText(u);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonSave.setOnClickListener(this);
    }

    private void saveUserInfo(){
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String publickey = editTextPublicKey.getText().toString().trim();

        UserInfo userinfo = new UserInfo(name, username, email, publickey);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).child("Info").setValue(userinfo);

        Toast.makeText(this, "Information Saved...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        if(v == buttonSave){
            saveUserInfo();

        }

    }

    public void PrivateKeyButton(View view) {
        Intent intent = new Intent(this, PrivateKey.class);
        startActivity(intent);
    }
}
