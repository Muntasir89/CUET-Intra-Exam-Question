package com.example.cuetintra_examquestion.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuetintra_examquestion.Adapter.CourseAdapter;
import com.example.cuetintra_examquestion.Model.CourseModel;
import com.example.cuetintra_examquestion.Model.DeptNameModel;
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

public class Course extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbar_title;
    CourseAdapter courseAdapter;
    List<CourseModel> CourseList = new ArrayList<>();
    RecyclerView Recycler_Course;
    FirebaseFirestore objectFrestre = FirebaseFirestore.getInstance();
    CollectionReference CourseNameCol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_course);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(getIntent().getStringExtra("title"));
        
        Recycler_Course = findViewById(R.id.Recycler_Course);
        Recycler_Course.setHasFixedSize(true);
        Recycler_Course.setLayoutManager(new GridLayoutManager(this, 2));

        courseAdapter = new CourseAdapter(this, CourseList, getIntent().getExtras().get("name").toString());
        Recycler_Course.setAdapter(courseAdapter);

        CourseNameCol = objectFrestre.collection(getIntent().getExtras().get("name").toString());

        LoadCourseList();
    }

    private void LoadCourseList() {
        CourseNameCol.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    CourseModel model = new CourseModel(d.get("course").toString(), d.get("priority").toString(), d.get("shortname").toString());
                    CourseList.add(model);
                }
                Collections.sort(CourseList, new Comparator<CourseModel>() {
                    @Override
                    public int compare(CourseModel obj1, CourseModel obj2) {
                        return obj1.getPriority().compareTo(obj2.getPriority());
                    }
                });
                courseAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Course.this, "Failed to load data", Toast.LENGTH_SHORT).show();
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