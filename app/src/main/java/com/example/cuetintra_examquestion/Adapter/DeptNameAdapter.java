package com.example.cuetintra_examquestion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuetintra_examquestion.Activity.Course;
import com.example.cuetintra_examquestion.Model.DeptNameModel;
import com.example.cuetintra_examquestion.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class DeptNameAdapter extends RecyclerView.Adapter<DeptNameAdapter.NameViewHolder> {
    static Intent intent;
    Context context;
    List<DeptNameModel>NameList;
    public DeptNameAdapter(Context context, List<DeptNameModel> list) {
        this.context = context;
        NameList = list;
    }

    @NonNull
    @Override
    public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.deptname_item, parent, false);
        return new NameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NameViewHolder holder, int position) {
        holder.text_DeptName.setText(NameList.get(position).getName());
        //holder.priority.setText(NameList.get(position).getPriority());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Course.class);
                intent.putExtra("name", NameList.get(position).getName());
                intent.putExtra("title", NameList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return NameList.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder{
        TextView text_DeptName, priority;
        public NameViewHolder(@NonNull View itemView) {
            super(itemView);
            text_DeptName = itemView.findViewById(R.id.text_deptName);
            //priority = itemView.findViewById(R.id.text_priority);
        }
    }
}
