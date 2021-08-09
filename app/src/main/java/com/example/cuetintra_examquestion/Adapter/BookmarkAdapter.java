package com.example.cuetintra_examquestion.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuetintra_examquestion.Activity.ShowPDF;
import com.example.cuetintra_examquestion.R;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.CustomViewHolder>{
    Context context;
    Intent intent;
    List<Pair<String, String>> BookmarkList;
    public BookmarkAdapter(Context context, List<Pair<String, String>>list) {
        this.context = context;
        BookmarkList = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.bookmark_item, parent, false);
        return new CustomViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bookmark_title.setText(BookmarkList.get(position).first);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, ShowPDF.class);
                intent.putExtra("title", BookmarkList.get(position).first);
                intent.putExtra("path", BookmarkList.get(position).second);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return BookmarkList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView bookmark_title;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            bookmark_title = itemView.findViewById(R.id.bookmark_title);
        }
    }
}
