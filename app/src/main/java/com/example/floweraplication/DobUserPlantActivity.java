package com.example.floweraplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.floweraplication.Activity.PlantDetailActivity;
import com.example.floweraplication.databinding.ActivityAddPlantBinding;
import com.example.floweraplication.databinding.ActivityDobUserPlantBinding;
import com.example.floweraplication.databinding.ActivityPlantDetailBinding;
import com.example.floweraplication.models.ModelCategory;
import com.example.floweraplication.models.ModelPng;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DobUserPlantActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private static final String TAG = "ADD_PLANT_TAG";

    private ArrayList<ModelPng> pngArrayList;
    private FirebaseAuth firebaseAuth;
    private ActivityDobUserPlantBinding binding;

    private Uri pngUri = null;
    Bitmap bitmap;
    TextView PlName,PlText,PlType,PlHabitat,PlSize,PlEndurance,PlPurpose,PlTox;
    ImageView pngView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDobUserPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pngView = binding.pngView;


        Glide.with(DobUserPlantActivity.this).load(getIntent().getStringExtra("pngView")).into(pngView);

    }
}