package com.example.journeyjournal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button loginbtn;
    TextView sign, forgotpassword;
    ImageView fb, google, twitter;

    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginbtn);
        sign = findViewById(R.id.sign);
        forgotpassword = findViewById(R.id.forgotpassword);
        fb = findViewById(R.id.fb);
        google = findViewById(R.id.google);
        twitter = findViewById(R.id.twitter);

        DB= new DBHelper(this);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String memail = email.getText().toString();
                String mpassword = password.getText().toString();


                if (TextUtils.isEmpty(memail)){
                    email.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(mpassword)){
                    password.setError("Password is Required.");
                    return;
                }
                if(password.length()< 6){
                    password.setError("Password Must be atleast 6 characters");
                    return;
                }

                if(memail.equals("") || mpassword.equals(""))
                    Toast.makeText(Login.this, "Please Enter Both Fields.", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkemailpassword = DB.checkemailpassword(memail, mpassword);
                    if (checkemailpassword==true) {
                        Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DisplayData.class));
                    }
                    else{
                        Toast.makeText(Login.this, "Invalid Email or Password.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });

    }
}