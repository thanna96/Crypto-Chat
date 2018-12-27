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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private Button buttonSignin;
    private EditText PasswordText;
    private EditText EmailText;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.regButton);
        buttonSignin = (Button) findViewById(R.id.email_sign_in_button);
        PasswordText = (EditText)findViewById(R.id.password);
        EmailText = (EditText)findViewById(R.id.email);
        buttonRegister.setOnClickListener(this);
        buttonSignin.setOnClickListener(this);
    }

    private void userLogin(){
        String email = EmailText.getText().toString().trim();
        String password = PasswordText.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }


        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            progressDialog.setMessage("Logging in User...");
                            progressDialog.show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                        }
                        else if(!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Incorrect Email or Password",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(false);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonSignin)
        {
            userLogin();
        }

        if(v == buttonRegister)
        {
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
