package com.example.cuetintra_examquestion.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cuetintra_examquestion.Adapter.BookmarkAdapter;
import com.example.cuetintra_examquestion.R;
import com.example.cuetintra_examquestion.Util.SQLiteUtil;

import java.util.ArrayList;
import java.util.List;

public class BookmarksFrag extends Fragment {
    List<Pair<String, String>> BookmarkList;
    RecyclerView Recycler_bookmark;
    BookmarkAdapter adapter;
    SQLiteUtil objSQL;
    public BookmarksFrag() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        Recycler_bookmark = view.findViewById(R.id.Recycler_bookmark);
        Recycler_bookmark.setHasFixedSize(true);
        Recycler_bookmark.setLayoutManager(new LinearLayoutManager(getContext()));
        LoadBookmarkList();

        adapter = new BookmarkAdapter(getContext(), BookmarkList);
        Recycler_bookmark.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    private void LoadBookmarkList() {
        objSQL = new SQLiteUtil(getContext());
        BookmarkList = objSQL.getPathList();
    }
}