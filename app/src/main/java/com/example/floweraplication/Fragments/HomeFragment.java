package com.example.floweraplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.floweraplication.AdapterHomeFragment;
import com.example.floweraplication.ModelUserFlow;
import com.example.floweraplication.adapters.AdapterPngUser;
import com.example.floweraplication.databinding.FragmentHomeBinding;
import com.example.floweraplication.models.ModelPng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class HomeFragment extends Fragment  {

    private FragmentHomeBinding binding;
    private FirebaseAuth firebaseAuth;
    Button buttonLight;

    Context context;

    private ArrayList<ModelUserFlow> Recycler;
    private AdapterHomeFragment adapterHomeFragment;
    private String description,id,name,picture,plant_id,plant_size,plant_width,sun,user_id;
    private static final String TAG = "PNG_LIST_TAG";
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        //init firebase auth
        buttonLight = binding.buttonLight;
        return binding.getRoot();


        /*buttonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LightingActivity.class);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = binding.moiFlow;
        databaseReference = FirebaseDatabase.getInstance().getReference("User_plant");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterHomeFragment);
        recyclerView.setHasFixedSize(true);

        Recycler = new ArrayList<>();
        adapterHomeFragment = new AdapterHomeFragment(getContext(), Recycler);
        recyclerView.setAdapter(adapterHomeFragment);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0)
                    {
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        if (map.get("description") != null){
                            description = map.get("description").toString();
                        }
                        if (map.get("id") != null){
                            id = map.get("id").toString();
                        }
                        if (map.get("name")!= null){
                            name = map.get("name").toString();
                        }
                        if (map.get("picture")!= null){
                            picture= map.get("picture").toString();
                        }
                        if (map.get("plant_id")!= null){
                            plant_id= map.get("plant_id").toString();
                        }
                        if (map.get("plant_size")!= null){
                            plant_size= map.get("plant_size").toString();
                        }
                        if (map.get("plant_width")!= null){
                            plant_width= map.get("plant_width").toString();
                        }
                        if (map.get("sun")!= null){
                            sun= map.get("sun").toString();
                        }
                        if (map.get("user_id")!= null){
                            user_id= map.get("user_id").toString();
                        }

                    }
                    Recycler.add(new ModelUserFlow(description,id,name,picture,plant_id,plant_size,plant_width,sun,user_id));
                }
                adapterHomeFragment.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}