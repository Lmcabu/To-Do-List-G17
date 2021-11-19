package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();
        setButtons();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://188.166.255.8:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ListJsonAPI listJsonApi = retrofit.create(ListJsonAPI.class);

        Call<List<ListData>> call = listJsonApi.getListData();

        call.enqueue(new Callback<List<ListData>>() {
            @Override
            public void onResponse(Call<List<ListData>> call, Response<List<ListData>> response) {
                if(response.isSuccessful()){
                    mEList = new ArrayList<>();
                    List<ListData> listDatas = response.body();
                    for (ListData alistData : listDatas){
                        mEList.add(new Eitem(R.drawable.ic_android, ""+alistData.getTitle(), "Incompleted:", "Completed:" ));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ListData>> call, Throwable t) {
                //buttonGoInsertListPage.setText("123123123");
                buttonGoInsertListPage.setText(t.getMessage());
            }
        });

    }
    public void openNewListPage(){
        Intent intent = new Intent(this, CreateNewList.class);
        startActivity(intent);
    }
    public void insertItem(int position){
        mEList.add(position, new Eitem(R.drawable.ic_android, "New Item At Position"+position, "This is XXX\n dwadwa\n dawdwa\n" , "Text information!"));
        mAdapter.notifyItemInserted(position);
    }
    public void removeItem(int position){
        mEList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    /*public void changeItem(int position, String text){
        mEList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }*/

    public void openAllTaskPage(){
        Intent intent = new Intent(this, AllTask.class);
        startActivity(intent);
    }

    public void createExampleList(){
        mEList = new ArrayList<>();
        mEList.add(new Eitem(R.drawable.ic_android, "Task Name", "Incompleted" , "Completed:" ));
        mEList.add(new Eitem(R.drawable.ic_baseline_access_alarms, "Task Name", "Incompleted:" , "Completed:"));
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
            public void onItemClick(int position) {
                openAllTaskPage();
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