package com.example.cuetintra_examquestion.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuetintra_examquestion.Adapter.CourseAdapter;
import com.example.cuetintra_examquestion.Adapter.YearAdapter;
import com.example.cuetintra_examquestion.Model.CourseModel;
import com.example.cuetintra_examquestion.Model.YearModel;
import com.example.cuetintra_examquestion.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Year extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbar_title;
    YearAdapter yearAdapter;
    List<YearModel> YearList = new ArrayList<>();
    RecyclerView Recycler_Year;
    FirebaseFirestore objectFrestre = FirebaseFirestore.getInstance();
    CollectionReference YearCol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_year);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getIntent().getStringExtra("title"));

        Recycler_Year = findViewById(R.id.Recycler_Year);
        Recycler_Year.setHasFixedSize(true);
        Recycler_Year.setLayoutManager(new LinearLayoutManager(this));

        yearAdapter = new YearAdapter(Year.this, YearList);
        Recycler_Year.setAdapter(yearAdapter);

        YearCol = objectFrestre.collection(getIntent().getExtras().get("name").toString());
        //Toast.makeText(this, ""+getIntent().getExtras().get("name").toString(), Toast.LENGTH_SHORT).show();
        LoadCourseList();
    }
    private void LoadCourseList() {
        YearCol.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    YearModel model = new YearModel(d.get("path").toString(), d.get("year").toString());
                    YearList.add(model);
                }
                Collections.sort(YearList, new Comparator<YearModel>() {
                    @Override
                    public int compare(YearModel obj1, YearModel obj2) {
                        return obj1.getYear().compareTo(obj2.getYear());
                    }
                });
                yearAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Year.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home://Previous Activity
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}