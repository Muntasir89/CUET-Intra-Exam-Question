package com.example.cuetintra_examquestion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuetintra_examquestion.Activity.ShowPDF;
import com.example.cuetintra_examquestion.Activity.Year;
import com.example.cuetintra_examquestion.Model.DeptNameModel;
import com.example.cuetintra_examquestion.Model.YearModel;
import com.example.cuetintra_examquestion.R;

import java.util.List;


public class YearAdapter extends RecyclerView.Adapter<YearAdapter.YearViewHolder> {
    static Intent intent;
    Context context;
    List<YearModel> YearList;
    public YearAdapter(Context context, List<YearModel> list) {
        this.context = context;
        YearList = list;
    }

    @NonNull
    @Override
    public YearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.year_item, parent, false);
        return new YearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YearViewHolder holder, int position){
        holder.Year.setText(YearList.get(position).getYear());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, ShowPDF.class);
                intent.putExtra("path", YearList.get(position).getPath());
                intent.putExtra("year", YearList.get(position).getYear());
                intent.putExtra("title", YearList.get(position).getYear());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return YearList.size();
    }

    public class YearViewHolder extends RecyclerView.ViewHolder{
        TextView Year;
        public YearViewHolder(@NonNull View itemView) {
            super(itemView);
            Year = itemView.findViewById(R.id.Year);
        }
    }
}
