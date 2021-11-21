package com.example.version1;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import java.util.ArrayList;

public class AllTaskAdapter extends RecyclerView.Adapter<AllTaskAdapter.ExampleViewHolder>{
    private ArrayList<eTaskItem> mTaskItem;
    private AllTaskAdapter.OnItemClickListener mTaskListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(AllTaskAdapter.OnItemClickListener listener){
        mTaskListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mTaskImageView1;
        public ImageView mTaskImageView2;
        public TextView mTaskTextView;
        public TextView mTaskTextView2;

        public ExampleViewHolder(@NonNull View itemView, AllTaskAdapter.OnItemClickListener listener) {
            super(itemView);
            mTaskImageView1 = itemView.findViewById(R.id.imageTaskAllTaskPage);
            mTaskImageView2 = itemView.findViewById(R.id.imageCompleteStatusAllTaskPage);
            mTaskTextView = itemView.findViewById(R.id.txtTaskNameAllTaskPage);
            mTaskTextView2 = itemView.findViewById(R.id.textTaskDetailAllTaskPage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public AllTaskAdapter(ArrayList<eTaskItem> taskItem){
        mTaskItem = taskItem;
    }
    @Override
    public AllTaskAdapter.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eachtask, parent, false);
        AllTaskAdapter.ExampleViewHolder evh = new AllTaskAdapter.ExampleViewHolder(v, mTaskListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        eTaskItem currentTask = mTaskItem.get(position);

        holder.mTaskImageView1.setImageResource(currentTask.getmImageResource());
        holder.mTaskImageView2.setImageResource(currentTask.getmImageResource2());
        holder.mTaskTextView.setText(currentTask.getMtitle());
        holder.mTaskTextView2.setText("Detail: \n"+currentTask.getMdetails());
    }

    @Override
    public int getItemCount() {
        if(mTaskItem==null || mTaskItem.size()==0 ) {
            return 0;
        }
        else{
            return mTaskItem.size();
        }
    }

}

