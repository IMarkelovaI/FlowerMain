package com.example.floweraplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.floweraplication.adapters.AdapterPurpose;
import com.example.floweraplication.databinding.ActivityPurposeBinding;
import com.example.floweraplication.models.ModelPurpose;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PurposeActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ActivityPurposeBinding binding;

    private ArrayList<ModelPurpose> purposeArrayList;
    private AdapterPurpose adapterPurpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPurposeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        loadPurpose();

        binding.addPurposeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PurposeActivity.this, AddPurposeActivity.class));
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PurposeActivity.this, AdminButonsActivity.class));
            }
        });
    }
    private void loadPurpose() {
        purposeArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Purpose");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                purposeArrayList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    ModelPurpose model = ds.getValue(ModelPurpose.class);

                    purposeArrayList.add(model);
                }
                adapterPurpose = new AdapterPurpose(PurposeActivity.this, purposeArrayList);
                binding.PurposeRv.setAdapter(adapterPurpose);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}