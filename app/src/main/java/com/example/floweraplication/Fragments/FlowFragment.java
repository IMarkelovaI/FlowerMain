package com.example.floweraplication.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.floweraplication.adapters.AdapterPngAdmin;
import com.example.floweraplication.adapters.AdapterPngUser;
import com.example.floweraplication.databinding.ActivityPngListAdminBinding;
import com.example.floweraplication.databinding.FragmentFlowBinding;
import com.example.floweraplication.models.ModelPng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class FlowFragment extends Fragment {

    private @NonNull FragmentFlowBinding binding;
    private ArrayList<ModelPng> Recycler;
    private AdapterPngUser adapterPngUser;
    private String name, image, id, purpose_id;
    private static final String TAG = "PNG_LIST_TAG";

    RecyclerView recyclerView;
    DatabaseReference databaseReference;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentFlowBinding.inflate(LayoutInflater.from(getContext()), container, false);
        Log.d(TAG, "onCreate:Purpose: "+name);


        recyclerView = binding.plantRv;

        //setUpRecyclerView();


        /*FirebaseDatabase database = FirebaseDatabase.getInstance();

        final ArrayList<ModelPng> Recycler = new ArrayList<>();
        final AdapterPngUser adapter = new AdapterPngUser(getContext(), Recycler);

        binding.plantRv.setAdapter(adapter);
        binding.plantRv.setLayoutManager(new LinearLayoutManager(getContext()));

        database.collection("users")
                .orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                            User user = snapshot.toObject(User.class);
                            users.add(user);

                        }
                        adapter.notifyDataSetChanged();
                    }
                });*/
        return binding.getRoot();
    }

    /*public void setUpRecyclerView(){
        Query query = FirebaseDatabase.getInstance().getReference("Plant");

        FirebaseRecyclerOptions<ModelPng> options = new FirebaseRecyclerOptions.Builder<ModelPng>()
                .setQuery(query, kidData.class)
                .build();

        adapter = new KidAdapter(options);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.plantRv;
        databaseReference = FirebaseDatabase.getInstance().getReference("Plant");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Recycler = new ArrayList<>();
        adapterPngUser = new AdapterPngUser(getContext(), Recycler);
        recyclerView.setAdapter(adapterPngUser);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /*private void recyclerData(){
        Recycler.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                allListData(dataSnapshot);
                adapterPngUser.notifyDataSetChanged();
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
    }*/

}