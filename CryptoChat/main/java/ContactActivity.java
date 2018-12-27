package com.example.thann.cryptochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity  {

    List<Contacts> contact;
    DatabaseReference databasecontacts;
    ListView listViewContact;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contact = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        listViewContact = (ListView) findViewById(R.id.ContactList);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databasecontacts = FirebaseDatabase.getInstance().getReference("UserInfo").child(user.getUid()).child("Contacts");

        listViewContact.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ContactActivity.this, ContactInfoActivity.class);
                intent.putExtra("name",contact.get(position).getname());
                intent.putExtra("phone",contact.get(position).getPhone());
                intent.putExtra("email",contact.get(position).getEmail());
                intent.putExtra("key",contact.get(position).getKey());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databasecontacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contact.clear();

                for(DataSnapshot contactSnapshot : dataSnapshot.getChildren()){
                    Contacts contacts = contactSnapshot.getValue(Contacts.class);
                    contact.add(contacts);
                }
                ContactList contactListAdapter = new ContactList(ContactActivity.this, contact);
                listViewContact.setAdapter(contactListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addContactButton(View view) {
        Intent intent = new Intent(this, CreateContactActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

}
