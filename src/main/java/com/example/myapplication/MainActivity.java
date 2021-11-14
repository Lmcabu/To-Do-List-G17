package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Eitem> mEList;

    private RecyclerView mRecyclerView;
    private eAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutMManager;

    private Button buttonInsert;
    private Button buttonRemove;
    private EditText editTextInsert;
    private EditText editTextRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();

        buttonInsert = findViewById(R.id.button_insert);
        buttonRemove = findViewById(R.id.button_remove);
        editTextInsert = findViewById(R.id.edittext_insert);
        editTextRemove = findViewById(R.id.edittext_remove);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionNo = Integer.parseInt(editTextInsert.getText().toString());
                insertItem(positionNo);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionNo = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(positionNo);
            }
        });
    }
    public void insertItem(int position){
        mEList.add(position, new Eitem(R.drawable.ic_android, "New Item At Position"+position, "This is XXX" ));
        mAdapter.notifyItemInserted(position);
    }
    public void removeItem(int position){
        mEList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String text){
        mEList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createExampleList(){
        mEList = new ArrayList<>();
        mEList.add(new Eitem(R.drawable.ic_android, "Task Name", "Task"));
        mEList.add(new Eitem(R.drawable.ic_baseline_access_alarms, "Task Name", "Task"));
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
                changeItem(position, "Clicked");
            }
        });
    }
}