package com.example.floweraplication.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.floweraplication.LightingActivity;
import com.example.floweraplication.ProfileUser;
import com.example.floweraplication.adapters.AdapterHomeFragment;
import com.example.floweraplication.models.ModelUserFlow;
import com.example.floweraplication.databinding.FragmentHomeBinding;
import com.example.floweraplication.photopredictor;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    ImageView Profile;

    Context context;

    private ArrayList<ModelUserFlow> Recycler;
    private AdapterHomeFragment adapterHomeFragment;
    private String description,id,name,picture,plant_id,plant_size,plant_width,sun,user_id;
    private static final String TAG = "PNG_LIST_TAG";
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    boolean nightMODE;
    SharedPreferences.Editor editor;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    MaterialSwitch materialSwitch;

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES = "apppref";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());

        //getFragmentManager().beginTransaction().detach(HomeFragment.this).attach(HomeFragment.this).commit();
        //init firebase auth
        buttonLight = binding.buttonLight;
        materialSwitch = binding.switch33;
        Profile = binding.ProfileButton;

       sharedPreferences = getActivity().getSharedPreferences("MODE",Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night", false);
        if (nightMODE){
            materialSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        materialSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nightMODE){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);

                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);

                }
                editor.apply();
            }
        });

        //buttonLight.setOnClickListener(new View);

        buttonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LightingActivity.class);
                startActivity(intent);
            }
        });
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), photopredictor.class);
                startActivity(intent);
            }
        });


        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileUser.class);
                startActivity(intent);
            }
        });
        //loadUserInfo();

        return binding.getRoot();
    }

    /*public static void checkIsPlant(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() ==null){

        }
        else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getUid()).child("User_plant").child(plant_id)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.moiFlow;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterHomeFragment);
        recyclerView.setHasFixedSize(true);

        Recycler = new ArrayList<>();
        adapterHomeFragment = new AdapterHomeFragment(getContext(), Recycler);
        recyclerView.setAdapter(adapterHomeFragment);

        //SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        //String user = preferences.getString("mAppIUD", "unknown");

        FirebaseUser useri=FirebaseAuth.getInstance().getCurrentUser();
        String userid =useri.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

        databaseReference.child("User_plant")
                .addValueEventListener(new ValueEventListener() {
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
                       // }
                    }
                    Recycler.add(new ModelUserFlow(description,id,name,picture,plant_id,plant_size,plant_width,sun));
                }
                loadUserInfo();
                adapterHomeFragment.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadUserInfo(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid =user.getUid();
        Log.i(TAG,"dfdfdfdf"+userid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profileImage =""+snapshot.child("profileImage").getValue();
                if (getActivity() == null) {
                    return;
                }
                if (profileImage != "")
                {
                    //Uri uri = Uri.parse("profileImage");
                    RequestOptions options = new RequestOptions()
                            .fitCenter()
                            .bitmapTransform(new RoundedCorners(14))
                            .diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(getContext())
                            .load((profileImage).toString())
                            .apply(options)
                            .transform(new CenterCrop(), new RoundedCorners(100))
                            .into(binding.ProfileButton);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}