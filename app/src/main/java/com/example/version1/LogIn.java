package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import android.os.Bundle;


import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

public class LogIn extends AppCompatActivity {

    EditText etUsername, etPassword;

    // Is there any constrain on password?
    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        viewInitializations();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onSupportNavigateUp(){
        finish();
        return super.onSupportNavigateUp();
    }

    void viewInitializations() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        // To show back button in actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    // Checking if the input in form is valid
    boolean validateInput() {

        if (etUsername.getText().toString().equals("")) {
            etUsername.setError("Please Enter Your Username");
            return false;
        }
        if (etPassword.getText().toString().equals("")) {
            etPassword.setError("Please Enter Password");
            return false;
        }

        // checking the proper email format
        if (!isUsernameValid(etUsername.getText().toString())) {
            etUsername.setError("Please Enter Valid Username");
            return false;
        }

        // checking minimum password Length
        if (etPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }

        return true;
    }

    // TODO: Check if the username is valid
    boolean isUsernameValid(String username) {
        return true;
    }

    // Hook Click Event

    public void performSignIn (View v) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();

            // call API
            // aProgressBar.show()
            final String Login_URL = "http://188.166.255.8:8080/login";


            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Login_URL, response -> {

                // TODO: Set up a progress bar
                try {

                    System.out.println("response");
                    JSONObject jsonObject = new JSONObject(response);


                    // String success = jsonObject.getString("success");
                    // String message = jsonObject.getString("message");

                    String password1 = jsonObject.getString("name");
                    String username1 = jsonObject.getString("username");

                    Toast.makeText(getApplicationContext(), "Logged In  Success", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Registration Error !1" + e, Toast.LENGTH_LONG).show();
                }
            }, error -> {
                //aProgressBar.dismiss();
                Toast.makeText(getApplicationContext(), "Registration Error !2" + error, Toast.LENGTH_LONG).show();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

        }
    }
}