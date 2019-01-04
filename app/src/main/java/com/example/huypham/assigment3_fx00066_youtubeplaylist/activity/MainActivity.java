package com.example.huypham.assigment3_fx00066_youtubeplaylist.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

public class MainActivity extends AppCompatActivity {
    private Button btnSignUp;
    private LinearLayout btnSignIn;
    private AppCompatEditText edtUser, edtPassword;
    SharedPreferences sharedPreference;
    String userName;
    SQliteHelper helper;
    public static void open(Activity activity){
        Intent intent = new Intent(activity,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Sign In");
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        edtUser = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);

        // Check login before
        sharedPreference = getSharedPreferences("dataLogin",MODE_PRIVATE);
        boolean isSignIn = sharedPreference.getBoolean("isSignIn",false);
        helper = new SQliteHelper(this);
        userName = sharedPreference.getString("userName",null);
        if(isSignIn && userName!=null){
            VideoPlayListActivity.open(MainActivity.this,userName);
            finish();
        }

        // If login first time, put to sharePreference flag and userName.
        btnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check user info if true ->
                String userName = String.valueOf(edtUser.getText());
                String password = String.valueOf(edtPassword.getText());
                if(helper.isCheckUserSignIn(userName,password)){
                    Editor editor = sharedPreference.edit();
                    editor.putBoolean("isSignIn",true);
                    editor.putString("userName",userName);
                    editor.commit();
                    VideoPlayListActivity.open(MainActivity.this,userName);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"“Sorry, your username and password are incorrect. Please try again!",Toast.LENGTH_SHORT).show();
                }
                //false alert
            }
        });
        btnSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              SignUpActivity.open(MainActivity.this);
            }
        });

    }

}
