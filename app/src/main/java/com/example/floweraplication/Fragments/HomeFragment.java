package com.example.floweraplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.floweraplication.LightingActivity;
import com.example.floweraplication.R;
import com.example.floweraplication.UserActivity;
import com.example.floweraplication.databinding.ActivityUserBinding;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    private HomeFragment binding;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

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