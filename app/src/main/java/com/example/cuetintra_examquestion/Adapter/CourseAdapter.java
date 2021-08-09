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
import com.example.cuetintra_examquestion.Activity.Year;
import com.example.cuetintra_examquestion.Model.CourseModel;
import com.example.cuetintra_examquestion.Model.DeptNameModel;
import com.example.cuetintra_examquestion.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{
    static Intent intent;
    Context context;
    String PreName="";
    List<CourseModel> CourseList;

    public CourseAdapter(Context context, List<CourseModel> list, String nme) {
        this.context = context;
        CourseList = list;
        PreName = nme;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.Course_Name.setText(CourseList.get(position).getCourseName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                intent = new Intent(context, Year.class);
                intent.putExtra("name",  PreName+"_"+CourseList.get(position).getShortName());
                intent.putExtra("title", CourseList.get(position).getCourseName());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return CourseList.size();
    }
    public class CourseViewHolder extends RecyclerView.ViewHolder{
        TextView Course_Name;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            Course_Name = itemView.findViewById(R.id.Course_Name);
        }
    }
}
