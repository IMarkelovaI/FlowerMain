package com.example.floweraplication.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.floweraplication.AdapterPngUserDobFlow;
import com.example.floweraplication.R;
import com.example.floweraplication.adapters.AdapterPngUser;
import com.example.floweraplication.databinding.FragmentDodFllowBinding;
import com.example.floweraplication.databinding.FragmentFlowBinding;
import com.example.floweraplication.models.ModelPng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;


public class DodFllowFragment extends Fragment {

    private FragmentDodFllowBinding binding;
    private ArrayList<ModelPng> Recycler;
    private AdapterPngUserDobFlow adapterPngUserDobFlow;
    private String name, image, purpose_id, id, degree_of_toxicity, description,endurance,habitat,size,type_id;
    private static final String TAG = "PNG_LIST_TAG";
    private FirebaseAuth auth;

    RecyclerView recyclerView;
    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDodFllowBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }
    @Override
    public void onResume() {
        super.onResume();
        binding.searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterPngUserDobFlow.getFilter().filter(s);
                }
                catch (Exception e){

                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = binding.plantRv;
        databaseReference = FirebaseDatabase.getInstance().getReference("Plant");


        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterPngUserDobFlow);

        Recycler = new ArrayList<>();
        adapterPngUserDobFlow = new AdapterPngUserDobFlow(getContext(), Recycler);
        recyclerView.setAdapter(adapterPngUserDobFlow);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0)
                    {
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        if (map.get("image") != null){
                            image = map.get("image").toString();
                        }
                        if (map.get("name") != null){
                            name = map.get("name").toString();
                        }
                        if (map.get("purpose_id")!= null){
                            purpose_id = map.get("purpose_id").toString();
                        }
                        if (map.get("id")!= null){
                            id= map.get("id").toString();
                        }
                        if (map.get("degree_of_toxicity")!= null){
                            degree_of_toxicity= map.get("degree_of_toxicity").toString();
                        }
                        if (map.get("description")!= null){
                            description= map.get("description").toString();
                        }
                        if (map.get("endurance")!= null){
                            endurance= map.get("endurance").toString();
                        }
                        if (map.get("habitat")!= null){
                            habitat= map.get("habitat").toString();
                        }
                        if (map.get("size")!= null){
                            size= map.get("size").toString();
                        }
                        if (map.get("type_id")!= null){
                            type_id= map.get("type_id").toString();
                        }

                    }
                    Collections.sort(Recycler,ModelPng.BY_NAME_ALPHABETICAL);
                    Recycler.add(new ModelPng(name, image, purpose_id, id, degree_of_toxicity, description,endurance,habitat,size,type_id));
                }
                adapterPngUserDobFlow.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}