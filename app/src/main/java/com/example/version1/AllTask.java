package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllTask extends AppCompatActivity {
    private ArrayList<eTaskItem> eAllTask;

    private RecyclerView mRecyclerViewTask;
    private AllTaskAdapter mallTaskAdapter;
    private RecyclerView.LayoutManager mLayoutMManagerTask;

    private Button newTask, editTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);
        //addAllTask();

        Intent intent = getIntent();
        String id = intent.getStringExtra("ListNo");
        getAllTask(id);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        newTask = findViewById(R.id.button_insert_ALlTaskPage);
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), XPostTask.class);
                intent.putExtra("ListId", id);
                v.getContext().startActivity(intent);
            }
        });

        /*
        editTask = findViewById(R.id.demo_edit);
        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), XPatchTask.class);
                v.getContext().startActivity(intent);
            }
        });
         */
    }
    public boolean onSupportNavigateUp(){
        finish();
        return super.onSupportNavigateUp();
    }
    public void addAllTask(){
        //eAllTask = new ArrayList<>();
        //eAllTask.add(new eTaskItem(R.drawable.ic_android, R.drawable.ic_baseline_close, "Task Name", "Detail: XXXX"));
        //eAllTask.add(new eTaskItem(R.drawable.ic_android, R.drawable.ic_baseline_check, "Task Name", "Detail: XXXX"));
    }
    public void openXPatchTask(int position){
        Intent intent = new Intent(this, XPatchTask.class);
        String [] selectedTask = new String[4];
        selectedTask[0] = eAllTask.get(position).getMid();
        selectedTask[1] = eAllTask.get(position).getMlistId();
        selectedTask[2] = eAllTask.get(position).getMdetails();
        selectedTask[3] = eAllTask.get(position).getMtitle();
        intent.putExtra("SelectedTask", selectedTask);
        startActivity(intent);
    }
    public void buildRecyclerView(){
        mRecyclerViewTask = findViewById(R.id.recyclerViewAllTaskPage);
        mRecyclerViewTask.setHasFixedSize(true);
        mLayoutMManagerTask = new LinearLayoutManager(this);
        mallTaskAdapter = new AllTaskAdapter(eAllTask);

        mRecyclerViewTask.setLayoutManager(mLayoutMManagerTask);
        mRecyclerViewTask.setAdapter(mallTaskAdapter);

        mallTaskAdapter.setOnItemClickListener(new AllTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openXPatchTask(position);
            }

            @Override
            public void onDeleteClick(int position) {
            }
        });




    }
    public void getAllTask(String id){
        String url="http://188.166.255.8:8080/api/v1/lists/"+id;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest (Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    eAllTask = new ArrayList<>();
                    JSONArray listAllTask;
                    listAllTask = response.getJSONArray("tasks");

                    for (int i=0; i < listAllTask.length(); i++){
                        JSONObject aTask = listAllTask.getJSONObject(i);
                        if (aTask.getBoolean("done") == true ){
                            eAllTask.add(new eTaskItem(R.drawable.ic_android, R.drawable.ic_baseline_check_box, aTask.getString("id"),aTask.getString("listId"),aTask.getString("details"), aTask.getString("title")));
                        }
                        else{
                            eAllTask.add(new eTaskItem(R.drawable.ic_android, R.drawable.ic_baseline_check_box_outline_blank, aTask.getString("id"),aTask.getString("listId"),aTask.getString("details"), aTask.getString("title")));
                        }
                    }
                        //eAllTask.add(new Eitem(R.drawable.ic_android, id, listName, incompleted , completed));
                    buildRecyclerView();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "Create New List success!" +response, Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Create New List error!"+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Cookie", "" + LoginActivity.getCookie());
                return headers;
            }
        };
        Volley.newRequestQueue(this).add(jsonObjReq);
    }
}