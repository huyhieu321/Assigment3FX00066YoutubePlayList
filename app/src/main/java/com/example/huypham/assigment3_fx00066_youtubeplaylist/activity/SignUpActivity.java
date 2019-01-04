package com.example.huypham.assigment3_fx00066_youtubeplaylist.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.huypham.assigment3_fx00066_youtubeplaylist.R;
import com.example.huypham.assigment3_fx00066_youtubeplaylist.sqlite.SQliteHelper;

public class SignUpActivity extends AppCompatActivity {
    Button btnSignUp;
    LinearLayout btnSignIn;
    SQliteHelper helper;
    AppCompatEditText edtUserName,edtPassword,edtConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign Up");
        helper = new SQliteHelper(this);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = String.valueOf(edtPassword.getText());
                String confirmPassword = String.valueOf(edtConfirmPassword.getText());
                String userName = String.valueOf(edtUserName.getText());
                // Check sign up information not null
                if(password == null || confirmPassword == null || userName == null){
                    Toast.makeText(SignUpActivity.this,"Please check your information",Toast.LENGTH_SHORT).show();
                }
                else {
                    // check password and confirm password already matched
                    if (isPasswordMatch(password, confirmPassword)) {
                        // check userName not exist before
                        if (helper.isCheckUserExist(userName)) {
                            Toast.makeText(SignUpActivity.this, userName + " already registered , please use another name", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            helper.addUser(userName, password);
                            MainActivity.open(SignUpActivity.this);
                        }
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "Password not match, please try again", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        btnSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.open(SignUpActivity.this);
            }
        });
    }
    public static void open(Activity activity){
        Intent intent = new Intent(activity,SignUpActivity.class);
        activity.startActivity(intent);
    }

    public boolean isPasswordMatch(String password,String confirmPassword){
        if(password.equals(confirmPassword))
        return true;
        else return false;
    }
}
