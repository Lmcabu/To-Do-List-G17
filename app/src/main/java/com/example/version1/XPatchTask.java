package com.example.version1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class XPatchTask extends AppCompatActivity {
    private Button buttonUpdate, buttonDelete, buttonBack;
    private CheckBox checkbox;
    private EditText taskTitle, taskDetail;
    private String taskId="619a130209162222acffa57e", oldTitle="new try", oldDetail="new try";
    public String cookie = LoginActivity.getCookie();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_patchtask);

        Intent intent = getIntent();
        String [] id = intent.getStringArrayExtra("SelectedTask");
        String a ="";
        for(int i = 0; i < id.length;i++){
            a += (id[i]+"   |");
        }
        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
        // To show back button in actionbar
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        buttonUpdate = findViewById(R.id.updateTask);
        buttonDelete = findViewById(R.id.update_delete);
        /*
        buttonBack = findViewById(R.id.post_back);
         */
        checkbox = findViewById(R.id.doneCheck);

        taskTitle = findViewById(R.id.update_Title);
        taskDetail = findViewById(R.id.update_detail);

        taskTitle.setText(oldTitle);
        taskDetail.setText(oldDetail);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = taskTitle.getText().toString();
                String newDetail = taskDetail.getText().toString();
                Boolean state = false;
                if (checkbox.isChecked()){
                    System.out.println("checked the box");
                    state = true;
                }
                updateRequest(newTitle, newDetail, state);
                Intent intent = new Intent(v.getContext(), AllTask.class );
                v.getContext().startActivity(intent);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRequest();
                Intent intent = new Intent(v.getContext(), AllTask.class );
                v.getContext().startActivity(intent);
            }
        });

        /*
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AllTask.class);
                v.getContext().startActivity(intent);
            }
        });
        */
    }

    public void updateRequest(String title, String detail, Boolean state){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "http://188.166.255.8:8080/api/v1/tasks";

        JSONObject info = new JSONObject();
        try {
            info.put("title", ""+title);
            info.put("details", ""+detail);
            info.put("done", ""+state);
            info.put("id", ""+taskId);
        } catch (JSONException e){
            e.printStackTrace();
        }

        JsonObjectRequest jsRequest= new JsonObjectRequest(Request.Method.PATCH, url, info, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Update Success!", Toast.LENGTH_SHORT).show();
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Update failed!", Toast.LENGTH_SHORT).show();
                System.out.println(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Cookie", ""+cookie);
                System.out.println("header done: "+cookie);
                return headers;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONObject result = null;

                    if (jsonString != null && jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }


        };
        requestQueue.add(jsRequest);
    }

    public void deleteRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://188.166.255.8:8080/api/v1/tasks/"+taskId;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Delete Success!", Toast.LENGTH_SHORT).show();
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Delete failed!", Toast.LENGTH_SHORT).show();
                System.out.println(error);
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Cookie",""+cookie);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
}
