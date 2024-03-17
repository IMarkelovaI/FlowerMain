package com.example.floweraplication.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.floweraplication.R;
import com.example.floweraplication.adapters.AdapterPngAdmin;
import com.example.floweraplication.adapters.AdapterPngUser;
import com.example.floweraplication.databinding.ActivityPngListAdminBinding;
import com.example.floweraplication.databinding.FragmentFlowBinding;
import com.example.floweraplication.models.ModelPng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private FragmentFlowBinding binding;
    private ArrayList<ModelPng> Recycler;
    private AdapterPngUser adapterPngUser;
    private String name, image, purpose_id, id, degree_of_toxicity, description,endurance,habitat,size,type_id;
    private static final String TAG = "PNG_LIST_TAG";
    private FirebaseAuth auth;

    RecyclerView recyclerView;
    DatabaseReference databaseReference;





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentFlowBinding.inflate(getLayoutInflater());


        /*View myview=inflater.inflate(R.layout.fragment_flow,container,false);
        recyclerView = binding.plantRv;
        auth= FirebaseAuth.getInstance();
        FirebaseUser mUser=auth.getCurrentUser();
        String id = mUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ExpenseDatabase").child(id);
        recyclerView=myview.findViewById(R.id.plantRv);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        return myview;*/
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
                    adapterPngUser.getFilter().filter(s);
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Recycler = new ArrayList<>();
        adapterPngUser = new AdapterPngUser(getContext(), Recycler);
        recyclerView.setAdapter(adapterPngUser);


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
                    Recycler.add(new ModelPng(name, image, purpose_id, id, degree_of_toxicity, description,endurance,habitat,size,type_id));
                }
                adapterPngUser.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plant");
        recyclerView = binding.plantRv;

        adapterPngUser = new AdapterPngUser(getActivity(), Recycler);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterPngUser);
        recyclerData();

    }

    private void recyclerData(){
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
    }/*

    /*@Override
    public void onStart(){
        super.onStart();

        FirebaseRecyclerOptions<Data> options=
                new FirebaseRecyclerOptions.Builder<Data>()
                        .setQuery(mExpenseDatabase,Data.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Data, com.example.expenseapp.MyViewHolder>(options) {

            public com.example.expenseapp.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new com.example.expenseapp.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_recycler_data, parent, false));
            }

            protected void onBindViewHolder(com.example.expenseapp.MyViewHolder holder, int position, @NonNull Data model) {
                holder.setAmmount(model.getAmount());
                holder.setType(model.getType());
                holder.setNote(model.getNote());
                holder.setDate(model.getDate());
            }
        };
        recyclerView.setAdapter(adapter);
    }*/

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

    /*@Override
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

    }*/

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