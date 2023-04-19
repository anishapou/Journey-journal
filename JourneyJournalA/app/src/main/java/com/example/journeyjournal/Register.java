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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {


    EditText fullname, email, password, confirmpassword;

    Button signupbtn;
    TextView sign;
    ImageView fb, google, twitter;

    DBHelper DB;

    String emailRegEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);


        signupbtn = findViewById(R.id.signupbtn);

        sign = findViewById(R.id.sign);
        fb = findViewById(R.id.fb);
        google = findViewById(R.id.google);
        twitter = findViewById(R.id.twitter);

        DB = new DBHelper(this);

        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mfullname = fullname.getText().toString();
                String memail = email.getText().toString();
                String mpassword = password.getText().toString();
                String mconfirmpassword = confirmpassword.getText().toString();


                Pattern pattern = Pattern.compile(emailRegEx);
                Matcher matcher = pattern.matcher(email.getText().toString());


                if (TextUtils.isEmpty(mfullname)) {
                    fullname.setError("Full Name is Required");
                    return;
                }

                if (!matcher.find()) {
                    email.setError("Enter a valid Email Address");
                    return;
                }

                if (TextUtils.isEmpty(memail)) {
                    email.setError("Email is Required");
                    return;

                }
                if (TextUtils.isEmpty(mpassword)) {
                    password.setError("Password is Required");
                    return;

                }
                if (password.length() < 6) {
                    password.setError("Password must be >= 6 Characters");
                    return;

                }
                if (!mpassword.equals(mconfirmpassword)){
                    confirmpassword.setError("Password donot match.");
                    return;

                }

                if (memail.equals("") || mpassword.equals("") || mconfirmpassword.equals(""))
                    Toast.makeText(Register.this, "Please enter all the fields.", Toast.LENGTH_SHORT).show();
                else{
                    if(mpassword.equals(mconfirmpassword)){
                        Boolean checkuser = DB.checkemail(memail);
                        if(checkuser==false) {
                            Boolean insert = DB.insertData(memail, mpassword);
                            if (insert==true) {
                                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), DisplayData.class));

                            } else {
                                Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(Register.this, "Email Already Used! Please enter new email.", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(Register.this, "Password donot match.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));

            }
        });


    }
}