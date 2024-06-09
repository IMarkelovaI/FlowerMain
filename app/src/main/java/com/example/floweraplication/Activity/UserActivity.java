package com.example.floweraplication.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.floweraplication.Fragments.DodFllowFragment;
import com.example.floweraplication.Fragments.FlowFragment;
import com.example.floweraplication.Fragments.HomeFragment;
import com.example.floweraplication.R;
import com.example.floweraplication.databinding.ActivityUserBinding;
import com.example.floweraplication.models.ModelPurpose;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;
    private FirebaseAuth firebaseAuth;

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();

    FlowFragment flowFragment = new FlowFragment();
    DodFllowFragment dodFllowFragment = new DodFllowFragment();

    public ArrayList <ModelPurpose> purposeArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

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
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,dodFllowFragment).commit();

                }
                else if (itemId ==R.id.flowers)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,flowFragment).commit();
                }
                return true;
            }
        });
    }

    public void checkUser() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser==null) {

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else {

        }
    }
}
