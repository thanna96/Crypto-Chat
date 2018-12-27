package com.example.thann.cryptochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactInfoActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    DatabaseReference databasecontacts;

    TextView name,phone,email,key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        key = (TextView) findViewById(R.id.key);

        Intent i = this.getIntent();

        String Sname = i.getExtras().getString("name");
        String Sphone = i.getExtras().getString("phone");
        String Semail = i.getExtras().getString("email");
        String Skey = i.getExtras().getString("key");

        name.setText(Sname);
        phone.setText(Sphone);
        email.setText(Semail);
        key.setText(Skey);
    }

    public void delContactButton(View view) {
        Intent i = this.getIntent();
        String Sname = i.getExtras().getString("name");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databasecontacts = FirebaseDatabase.getInstance().getReference("UserInfo").child(user.getUid()).child("Contacts").child(Sname);
        databasecontacts.getRef().setValue(null);
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }


}
