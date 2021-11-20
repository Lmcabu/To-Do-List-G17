package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class CreateNewList extends AppCompatActivity {

    private Button btnInsertListPage;
    private EditText newListNameListPage;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_list);

        btnInsertListPage = findViewById(R.id.btnInsertListPage);
        newListNameListPage = findViewById(R.id.newListNameListPage);
        postRequest();
    }
    public void postRequest(){
        JSONObject js = new JSONObject();
        try {
            js.put("password", "LeeWOPINGMOSTLIKEC444444444");
            js.put("username", "LeeWOPINGMOSTLIKEC444444444");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url="http://188.166.255.8:8080/api/v1/users";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, js,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                btnInsertListPage.setText("123"+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Login Success!"+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
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
    }
}