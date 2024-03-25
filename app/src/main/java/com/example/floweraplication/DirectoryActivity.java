package com.example.floweraplication;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;

import com.example.floweraplication.Activity.AdminActivity;
import com.example.floweraplication.adapters.AdapterCategory;
import com.example.floweraplication.databinding.ActivityAdminBinding;
import com.example.floweraplication.databinding.ActivityDirectoryBinding;
import com.example.floweraplication.models.ModelCategory;
import com.example.floweraplication.models.ModelPng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DirectoryActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ActivityDirectoryBinding binding;

    private ArrayList<ModelType> categoryArrayList;
    private ArrayList<ModelDesiase> desiaseArrayList;
    private ArrayList<ModelSoil_type> soil_typeArrayList;
    private ArrayList<ModelFertilizer> fertilizerArrayList;

    private AdapterDirectoryType adapterDirectoryType;
    private AdapterDirectoryDesiase adapterDirectoryDesiase;
    private AdapterDirectorySoil_type adapterDirectorySoilType;
    private AdapterDirectoryFertilizer adapterDirectoryFertilizer;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDirectoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar2;

        toolbar.setTitleTextAppearance(this, R.style.FontForTitle);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, typedValue, true);
        @ColorInt int color = typedValue.data;
        toolbar.setTitleTextColor(color);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Справочник");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        loadTypes();
        loadDesiase();
        loadSoil_type();
        loadfertilizer();

    }

    private void loadTypes() {
        categoryArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Type");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryArrayList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    ModelType model = ds.getValue(ModelType.class);

                    categoryArrayList.add(model);
                }
                adapterDirectoryType = new AdapterDirectoryType(DirectoryActivity.this, categoryArrayList);
                Collections.sort(categoryArrayList, ModelType.BY_NAME_ALPHABETICAL);
                binding.recyclerViewType.setAdapter(adapterDirectoryType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadDesiase() {
        desiaseArrayList = new ArrayList<>();

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Desiase");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                desiaseArrayList.clear();
                for(DataSnapshot ds1: snapshot.getChildren()){
                    ModelDesiase model1 = ds1.getValue(ModelDesiase.class);

                    desiaseArrayList.add(model1);
                }
                adapterDirectoryDesiase = new AdapterDirectoryDesiase(DirectoryActivity.this, desiaseArrayList);
                binding.recyclerViewDesiase.setAdapter(adapterDirectoryDesiase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadSoil_type() {
        soil_typeArrayList = new ArrayList<>();

        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Soil_type");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                soil_typeArrayList.clear();
                for(DataSnapshot ds2: snapshot.getChildren()){
                    ModelSoil_type model2 = ds2.getValue(ModelSoil_type.class);

                    soil_typeArrayList.add(model2);
                }
                adapterDirectorySoilType = new AdapterDirectorySoil_type(DirectoryActivity.this, soil_typeArrayList);
                binding.recyclerSoilType.setAdapter(adapterDirectorySoilType);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadfertilizer() {
        fertilizerArrayList = new ArrayList<>();

        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("Fertilizer");
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fertilizerArrayList.clear();
                for(DataSnapshot ds3: snapshot.getChildren()){
                    ModelFertilizer model3 = ds3.getValue(ModelFertilizer.class);

                    fertilizerArrayList.add(model3);
                }
                adapterDirectoryFertilizer = new AdapterDirectoryFertilizer(DirectoryActivity.this, fertilizerArrayList);
                binding.recyclerViewFertilizer.setAdapter(adapterDirectoryFertilizer);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}