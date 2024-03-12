package com.example.floweraplication;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.floweraplication.adapters.AdapterPngAdmin;
import com.example.floweraplication.databinding.ActivityPngListAdminBinding;
import com.example.floweraplication.models.ModelPng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PngListAdminActivity extends AppCompatActivity {

    private ActivityPngListAdminBinding binding;
    private ArrayList<ModelPng> pngArrayList;
    private AdapterPngAdmin adapterPngAdmin;
    private String type_id, name;
    private static final String TAG = "PNG_LIST_TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPngListAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        type_id = intent.getStringExtra("type_id");
        name = intent.getStringExtra("name");

        loadPngList();

        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    adapterPngAdmin.getFilter().filter(s);
                }
                catch (Exception e){
                    Log.d(TAG, "onTextChanged: "+e.getMessage());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.ButtonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadPngList() {
        pngArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Plant");
        ref.orderByChild("type_id").equalTo(type_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pngArrayList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelPng model = ds.getValue(ModelPng.class);
                            pngArrayList.add(model);

                            Log.d(TAG, "onDataChange: "+model.getId()+""+model.getName());
                        }
                        adapterPngAdmin = new AdapterPngAdmin(PngListAdminActivity.this, pngArrayList);
                        binding.plantRv.setAdapter(adapterPngAdmin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}