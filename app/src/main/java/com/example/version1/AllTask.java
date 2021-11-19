package com.example.version1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AllTask extends AppCompatActivity {
    private ArrayList<eTaskItem> eAllTask;

    private RecyclerView mRecyclerViewTask;
    private AllTaskAdapter mallTaskAdapter;
    private RecyclerView.LayoutManager mLayoutMManagerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_task);
        addAllTask();
        buildRecyclerView();
    }
    public void addAllTask(){
        eAllTask = new ArrayList<>();
        eAllTask.add(new eTaskItem(R.drawable.ic_android, R.drawable.ic_baseline_close, "Task Name", "Detail: XXXX"));
        eAllTask.add(new eTaskItem(R.drawable.ic_android, R.drawable.ic_baseline_check, "Task Name", "Detail: XXXX"));
    }
    public void buildRecyclerView(){
        mRecyclerViewTask = findViewById(R.id.recyclerViewAllTaskPage);
        mRecyclerViewTask.setHasFixedSize(true);
        mLayoutMManagerTask = new LinearLayoutManager(this);
        mallTaskAdapter = new AllTaskAdapter(eAllTask);

        mRecyclerViewTask.setLayoutManager(mLayoutMManagerTask);
        mRecyclerViewTask.setAdapter(mallTaskAdapter);
    }
}