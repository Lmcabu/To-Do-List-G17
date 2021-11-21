package com.example.version1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class XPatchTask extends AppCompatActivity {
    private Button buttonUpdate, buttonDelete, buttonBack;
    private CheckBox checkbox;
    private EditText taskTitle, taskDetail;
    private String taskId, oldTitle, oldDetail;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_patchtask);

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
                String state ="false";
                if (checkbox.isChecked()){
                    state = "true";
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

    public void updateRequest(String title, String detail, String state){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "http://188.166.255.8:8080/api/v1/tasks";

        StringRequest stringRequest= new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("updateasdasdasdasdasdasdasdasdaasdasdasdasdasdasdasdasdaddasdasdasdasdasd");
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
                params.put("done", state);
                //params.put("listId", listId);
                System.out.println("updateasdasdasdasdasdasdasdasdaasdasdasdasdasdasdasdasdaddasdasdasdasdasd");
                return params;
            }


        };
        requestQueue.add(stringRequest);
    }

    public void deleteRequest(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://188.166.255.8:8080/api/v1/tasks/"+taskId;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("deleteasdasdasdasdasdasdasdasdaasdasdasdasdasdasdasdasdaddasdasdasdasdasd");
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            }
        });
        requestQueue.add(stringRequest);
    }
}