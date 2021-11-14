package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void goToSignUp(View view){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void goToSignIn(View view){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    public boolean onSupportNavigateUp(){
        finish();
        return super.onSupportNavigateUp();
    }

}