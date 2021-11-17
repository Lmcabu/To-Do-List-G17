package com.example.version1;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.EditText;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;




public class SignUpActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etEmail, etPassword, etRepeatPassword;
    final int MIN_PASSWORD_LENGTH = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        viewInitializations();
    }


    void viewInitializations() {
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etRepeatPassword = findViewById(R.id.et_repeat_password);

        // To show back button in actionbar
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    // Checking if the input in form is valid
    boolean validateInput() {
        if (etFirstName.getText().toString().equals("")) {
            etFirstName.setError("Please Enter First Name");
            return false;
        }
        if (etLastName.getText().toString().equals("")) {
            etLastName.setError("Please Enter Last Name");
            return false;
        }
        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Please Enter Email");
            return false;
        }
        if (etPassword.getText().toString().equals("")) {
            etPassword.setError("Please Enter Password");
            return false;
        }
        if (etRepeatPassword.getText().toString().equals("")) {
            etRepeatPassword.setError("Please Enter Repeat Password");
            return false;
        }

        // checking the proper email format
        if (!isEmailValid(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Valid Email");
            return false;
        }

        // checking minimum password Length
        if (etPassword.getText().length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }

        // Checking if repeat password is same
        if (!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
            etRepeatPassword.setError("Password does not match");
            return false;
        }
        return true;
    }

    boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Hook Click Event

    public void performSignUp(View v) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();

            //Attention: xianxian set the username to be the combination of user's firstname+lastname without any space between them
            String username=firstName+lastName;

            String password = etPassword.getText().toString().trim();

            //TODO server要不要加这个email？
            String email = etEmail.getText().toString().trim();


            // generate a json string

            try {
                String paraJsonStr = new JSONObject().put("username", username)
                        .put("password", password).toString();
            } catch (JSONException e){
                e.printStackTrace();
            }




            // call API
            String URL = "http://188.166.255.8:8080/api/v1/users";


            // generate a json string

            try {
                String paraJsonStr = new JSONObject().put("username", username)
                        .put("password", password).toString();
            } catch (JSONException e){
                e.printStackTrace();
            }




            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try{
                                // after success response from server
                                // JSONObject jsonObject = new JSONObject(response);
                                // String username=jsonObject.getString("username");
                                // String password=jsonObject.getString("password");

                                // Log.i("regis info(u+p)", username+"   "+password);
                                Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                // if any exception is been caught
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(),"Registration Error !1"+e,Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    // if server fails to response on time
                    Toast.makeText(getApplicationContext(),"Registration Error !2"+error,Toast.LENGTH_LONG).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put("name",username);
                    params.put("password",password);
                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


            }
        }
    }

