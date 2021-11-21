package com.example.version1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class XPostTask extends AppCompatActivity {
    private Button buttonCreate, buttonBack;
    private EditText taskTitle, taskDetail;
    private String listId="6199f34509162222acffa577";
    public String cookie = LoginActivity.getCookie();

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_posttask);

        buttonCreate = findViewById(R.id.postTask);
        /*
        buttonBack = findViewById(R.id.post_back);
         */
        taskTitle = findViewById(R.id.post_Title);
        taskDetail = findViewById(R.id.post_detail);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = taskTitle.getText().toString();
                String newDetail = taskDetail.getText().toString();
                postRequest(newTitle, newDetail);
                Intent intent = new Intent(v.getContext(), AllTask.class );
                v.getContext().startActivity(intent);
            }
        });

        /*buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AllTask.class);
                v.getContext().startActivity(intent);
            }
        });*/
    }

    public void postRequest(String title, String detail){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //String url = "https://reqres.in/api/users";

        String url = "http://188.166.255.8:8080/api/v1/tasks";

        JSONObject info = new JSONObject();
        try {
            info.put("title", ""+title);
            info.put("details", ""+detail);
            info.put("listId", ""+listId);
        } catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest jsRequest= new JsonObjectRequest(Request.Method.POST, url, info, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Create Success!", Toast.LENGTH_SHORT).show();
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Create failed!", Toast.LENGTH_SHORT).show();
                System.out.println(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Cookie", ""+cookie);
                System.out.println("header done: "+LoginActivity.getCookie());
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
        }
        ;
        requestQueue.add(jsRequest);
    }
}
