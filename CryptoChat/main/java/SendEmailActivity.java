package com.example.thann.cryptochat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendEmailActivity extends AppCompatActivity {

    EditText et_email,et_subject,et_message;
    Button b_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        et_email = (EditText) findViewById(R.id.et_email);
        et_subject = (EditText) findViewById(R.id.et_subject);
        et_message = (EditText) findViewById(R.id.et_message);

        Intent i = this.getIntent();
        String Message = i.getExtras().getString("EncryptedMessage");
        String Email = i.getExtras().getString("Email");
        et_message.setText(Message);
        et_subject.setText("Top Secret Message");
        et_email.setText(Email);

        b_send = (Button) findViewById(R.id.Bsend);
        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = et_email.getText().toString();
                String subject = et_subject.getText().toString().trim();
                String message = et_message.getText().toString().trim();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                intent.setType("message/rfc822");

                startActivity(intent.createChooser(intent, "Select Email App"));
            }
        });

    }
}
