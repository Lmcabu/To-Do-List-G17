package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.*;


import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;

    // Is there any constrain on password?
    final int MIN_PASSWORD_LENGTH = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

            String requestStr="username="+username+"&password="+password;
            HttpURLConnection connection = null;

            try {
                //Create connection
                URL url = new URL("http://188.166.255.8:8080/login");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                connection.setRequestProperty("Content-Length",
                        Integer.toString(requestStr.getBytes().length));
                // connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches(false);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream());
                wr.writeBytes(requestStr);
                wr.close();

                //Get Response
                InputStream is = connection.getInputStream();

                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();

                Toast.makeText(getApplicationContext(),"Login Success!",Toast.LENGTH_SHORT).show();
                goToList();



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Registration Error !" + e, Toast.LENGTH_LONG).show();

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }





        }



    public void goToSignUp(View v) {
        // Open SignUp Activity if the user wants to signup
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goToList() {
        // Open list activity
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

}
