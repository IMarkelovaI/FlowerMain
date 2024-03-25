package com.example.floweraplication;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.floweraplication.databinding.ActivityProfileuserBinding;
import com.example.floweraplication.databinding.ActivityUserPlantRedPlBinding;
import com.google.android.material.materialswitch.MaterialSwitch;


public class ProfileUser extends AppCompatActivity {
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    boolean nightMODE;
    SharedPreferences.Editor editor;
    private ActivityProfileuserBinding binding;
    MaterialSwitch materialSwitch;
    private static final String TAG = "ADD_PLANT_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileuserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        materialSwitch = binding.switch3;


       sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night", false);
        if (nightMODE){
            materialSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Log.i(TAG, "Uriririri"+nightMODE);
        }
        materialSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nightMODE){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                    Log.i(TAG, "Uriririri"+nightMODE);

                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                    Log.i(TAG, "Uriririri"+nightMODE);

                }
                editor.apply();
            }
        });
    }
}