package com.example.floweraplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.floweraplication.Fragments.DodFllowFragment;
import com.example.floweraplication.Fragments.FlowFragment;
import com.example.floweraplication.Fragments.HomeFragment;
import com.example.floweraplication.databinding.ActivityAdminBinding;
import com.example.floweraplication.databinding.ActivityAuthBinding;
import com.example.floweraplication.databinding.ActivityUserBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    private FirebaseAuth firebaseAuth;

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    FlowFragment flowFragment = new FlowFragment();
    DodFllowFragment dodFllowFragment = new DodFllowFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        bottomNavigationView = binding.bottomNavigation;
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int itemId = item.getItemId();

                if (itemId ==R.id.home)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                }
                else if (itemId ==R.id.add_flowers)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,flowFragment).commit();
                }
                else if (itemId ==R.id.flowers)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,dodFllowFragment).commit();
                }
                return false;
            }
        });

        //binding.bottomNavigationView.setBackground(null);
    }

    /*private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }*/

}