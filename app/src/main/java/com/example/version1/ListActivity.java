package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity {
    private ArrayList<Eitem> mEList;

    private RecyclerView mRecyclerView;
    private eAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutMManager;

    private Button buttonInsert;
    private Button buttonGoInsertListPage;
    //private Button buttonRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //createExampleList();
        getAllList();

    }
    public boolean onSupportNavigateUp(){
        finish();
        return super.onSupportNavigateUp();
    }
    public void openNewListPage(){
        Intent intent = new Intent(this, CreateNewList.class);
        startActivity(intent);
    }
    public void insertItem(int position){
        mEList.add(position, new Eitem(R.drawable.ic_android, "1","New Item At Position"+position, "This is XXX\n dwadwa\n dawdwa\n" , "Text information!"));
        mAdapter.notifyItemInserted(position);
    }
    public void removeItem(int position){
        String url="http://188.166.255.8:8080/api/v1/lists/"+mEList.get(position).getmid();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.DELETE, url, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Delete List success!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Delete List error!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Cookie", "" + LoginActivity.getCookie());
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
        Volley.newRequestQueue(this).add(jsonObjReq);
        //Intent intent = new Intent(this, ListActivity.class);
        //startActivity(intent);
        mEList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    /*public void changeItem(int position, String text){
        mEList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }*/

    public void openAllTaskPage(int position){
        Intent intent = new Intent(this, AllTask.class);
        intent.putExtra("ListNo", mEList.get(position).getmid());
        startActivity(intent);
    }

    public void createExampleList(){
        mEList = new ArrayList<>();
        mEList.add(new Eitem(R.drawable.ic_android, "1", "Task Name", "Incompleted" , "Completed:" ));
        mEList.add(new Eitem(R.drawable.ic_baseline_access_alarms,"1", "Task Name", "Incompleted:" , "Completed:"));
    }
    public void getAllList(){
        String url="http://188.166.255.8:8080/api/v1/lists";
        JsonArrayRequest jsonObjReq = new JsonArrayRequest (Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    mEList = new ArrayList<>();
                    String id ="";
                    String listName = "";
                    String incompleted;
                    String completed;
                    JSONObject jresponse;
                    JSONArray listAllTask;
                    //Toast.makeText(getApplicationContext(), "Create New List success!" +response.getJSONObject(0).getString("id"), Toast.LENGTH_SHORT).show();
                    for(int i = 0; i < response.length(); i++){
                        id = response.getJSONObject(i).getString("id");
                        listName = response.getJSONObject(i).getString("title");
                        listAllTask = response.getJSONObject(i).getJSONArray("tasks");
                        incompleted = "Incompleted:";
                        completed = "\nCompleted:";
                        for (int y=0; y < listAllTask.length(); y++){
                            JSONObject aTask = listAllTask.getJSONObject(y);
                            if (aTask.getBoolean("done") == true ){
                                completed += "\n"+aTask.getString("title");
                            }
                            else{
                                incompleted += "\n"+aTask.getString("title");
                            }
                        }
                        mEList.add(new Eitem(R.drawable.ic_android, id, listName, incompleted , completed));
                    }
                    buildRecyclerView();
                    setButtons();

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
    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutMManager = new LinearLayoutManager(this);
        mAdapter = new eAdapter(mEList);

        mRecyclerView.setLayoutManager(mLayoutMManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new eAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                openAllTaskPage(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }
    public void setButtons(){
        //buttonInsert = findViewById(R.id.btnInsertListPage);
        buttonGoInsertListPage = findViewById(R.id.button_insert_ALlListPage);
        //buttonRemove = findViewById(R.id.button_remove);


        buttonGoInsertListPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewListPage();
            }
        });

        /*buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem(0);
            }
        });*/

        /*buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionNo = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(positionNo);
            }
        });*/
    }
}