package com.example.version1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class XPostTask extends AppCompatActivity {
    private Button buttonCreate, buttonBack;
    private EditText taskTitle, taskDetail;
    //private String listId="61996683c80418710bb0b3bc";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_posttask);

        // To show back button in actionbar
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("asdasdasdasdasdasdasdasdaasdasdasdasdasdasdasdasdaddasdasdasdasdasd");
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("details", detail);
                //params.put("listId", listId);
                System.out.println("asdasdasdasdasdasdasdasdaasdasdasdasdasdasdasdasdaddasdasdasdasdasd");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
