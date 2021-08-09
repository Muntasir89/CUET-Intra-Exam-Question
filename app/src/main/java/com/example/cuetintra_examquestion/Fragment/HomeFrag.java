package com.example.cuetintra_examquestion.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cuetintra_examquestion.Adapter.DeptNameAdapter;
import com.example.cuetintra_examquestion.Model.DeptNameModel;
import com.example.cuetintra_examquestion.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFrag extends Fragment {
    Context context;
    RecyclerView DeptName;
    DeptNameAdapter adapter;
    FirebaseFirestore objectFrestre = FirebaseFirestore.getInstance();
    CollectionReference DeptNameCollection = objectFrestre.collection("DeptName");
    List<DeptNameModel> NameList = new ArrayList<>();

    public HomeFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        DeptName = view.findViewById(R.id.DeptName);
        DeptName.setHasFixedSize(true);
        DeptName.setLayoutManager(new LinearLayoutManager(getContext()));

        loadNameList();

        adapter = new DeptNameAdapter(getContext(), NameList);
        DeptName.setAdapter(adapter);

        return view;
    }
    private void loadNameList() {
        DeptNameCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot d:list){
                    DeptNameModel model = new DeptNameModel(d.get("dept").toString(), d.get("priority").toString());
                    NameList.add(model);
                }
                Collections.sort(NameList, new Comparator<DeptNameModel>() {
                    @Override
                    public int compare(DeptNameModel obj1, DeptNameModel obj2) {
                        return obj1.getPriority().compareTo(obj2.getPriority());
                    }
                });
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}