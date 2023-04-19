package com.example.journeyjournal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Reset extends AppCompatActivity {

    TextView email;
    EditText password, confirmpassword;
    Button resetbuttonconfirm;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        email = findViewById(R.id.emailresettext);
        password = findViewById(R.id.password_reset);
        confirmpassword = findViewById(R.id.confirmpassword_reset);
        resetbuttonconfirm = findViewById(R.id.resetbuttonconfirm);

        DB = new DBHelper(this);

        Intent intent = getIntent();
        email.setText(intent.getStringExtra("email"));

        resetbuttonconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String memail = email.getText().toString();
                String pass = password.getText().toString();
                String confirmpass = confirmpassword.getText().toString();
                if (pass.equals(confirmpass)) {

                    Boolean checkpasswordupdate = DB.updatepassword(memail, pass);
                    if (checkpasswordupdate == true) {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        Toast.makeText(Reset.this, "Password reset successful.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Reset.this, "Password reset failed.", Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(Reset.this, "Password donot match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}