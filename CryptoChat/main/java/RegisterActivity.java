package com.example.thann.cryptochat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.thann.cryptochat.R.id.editEmail;
import static com.example.thann.cryptochat.R.id.editPassword;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText PasswordText;
    private EditText EmailText;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.regButton);
        PasswordText = (EditText)findViewById(editPassword);
        EmailText = (EditText)findViewById(editEmail);
        buttonRegister.setOnClickListener(this);
    }


    private void registerUser(){
        String email = EmailText.getText().toString().trim();
        String password = PasswordText.getText().toString().trim();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Fill in Blank Section(s)",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        progressDialog.dismiss();
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Registered Succesfully",
                                Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), SuccessActivity.class));

                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Could not register. Please try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SuccessActivity.class);
        if(v == buttonRegister)
        {
            registerUser();
        }
    }
}
