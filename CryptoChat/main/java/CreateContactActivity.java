package com.example.thann.cryptochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateContactActivity extends AppCompatActivity  implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    EditText editTextName;
    EditText editTextPhone;
    EditText editTextEmail;
    EditText editTextKey;
    Button buttonAdd;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");

        editTextName = (EditText) findViewById(R.id.ACname);
        editTextPhone = (EditText) findViewById(R.id.ACPhone);
        editTextEmail = (EditText) findViewById(R.id.ACemail);
        editTextKey = (EditText) findViewById(R.id.ACPK);
        buttonAdd = (Button) findViewById(R.id.ACbutton);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        buttonAdd.setOnClickListener(this);

    }


    public void addContact() {
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String key = editTextKey.getText().toString().trim();

        if(!TextUtils.isEmpty(name)||
        !TextUtils.isEmpty(phone)||!TextUtils.isEmpty(email)
                ||!TextUtils.isEmpty(key)){

            Contacts contacts = new Contacts(name,email,phone,key);

            FirebaseUser user = firebaseAuth.getCurrentUser();

            databaseReference.child(user.getUid()).child("Contacts").child(name).setValue(contacts);
            Toast.makeText(this,"New Contact Added!",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Please Fill Blank Fields", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            addContact();

        }
    }
}
