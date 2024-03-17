package com.example.floweraplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.floweraplication.Activity.UserActivity;
import com.example.floweraplication.LightingActivity;
import com.example.floweraplication.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment  {

    private HomeFragment binding;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Button buttonLight = (Button) v.findViewById(R.id.buttonLight);

        buttonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserActivity activity = (UserActivity) v.getContext();
                //Fragment myFragment = new ();

                //startActivity(new Intent(UserActivity.this, LightingActivity.class));
                //activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();
            }
        });
        return v;
       // return inflater.inflate(R.layout.fragment_home, container, false);


        /*binding.buttonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new Intent(HomeFragment.this, LightFragment.class));
            }
        });*/

        /*super.onCreate(savedInstanceState);

        binding = HomeFragment.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
        binding.buttonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeFragment.this, LightingActivity.class));
            }
        });*/
    }

}