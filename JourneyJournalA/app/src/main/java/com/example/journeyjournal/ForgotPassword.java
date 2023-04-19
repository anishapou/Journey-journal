package com.example.journeyjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        EditText email = findViewById(R.id.emailreset);
        Button reset = findViewById(R.id.resetbtn);
        DBHelper DB = new DBHelper(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString();

                Boolean checkuser = DB.checkemail(user);
                if(checkuser==true)
                {
                    Intent intent = new Intent(getApplicationContext(), Reset.class);
                    intent.putExtra("email", user);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(ForgotPassword.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}