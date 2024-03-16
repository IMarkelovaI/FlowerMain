package com.example.floweraplication;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.floweraplication.adapters.AdapterPngAdmin;
import com.example.floweraplication.databinding.ActivityPngListAdminBinding;
import com.example.floweraplication.models.ModelPng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class PngListAdminActivity extends AppCompatActivity {

    private ActivityPngListAdminBinding binding;
    private ArrayList<ModelPng> Recycler = new ArrayList<ModelPng>();
    private AdapterPngAdmin adapterPngAdmin;
    private String name, image, id, purpose_id;
    private static final String TAG = "PNG_LIST_TAG";

    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPngListAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plant");
        recyclerView = binding.plantRv;
        adapterPngAdmin = new AdapterPngAdmin(getApplicationContext(), Recycler);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterPngAdmin);
        recyclerData();

        binding.ButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void recyclerData(){
        Recycler.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                allListData(dataSnapshot);
                adapterPngAdmin.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
    @SuppressLint("NewApi")
    public void allListData(final DataSnapshot dataSnapshot)
    {
        if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0)
        {
            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
            if (map.get("image") != null){
                image = map.get("image").toString();
            }
            if (map.get("name") != null){
                name = map.get("name").toString();
            }
            if (map.get("id") != null){
                id = map.get("id").toString();
            }
            if (map.get("purpose_id") != null){
                id = map.get("purpose_id").toString();
            }
        }
        Recycler.add(new ModelPng(id, name, image, purpose_id));
    }
}