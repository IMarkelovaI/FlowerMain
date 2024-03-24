package com.example.floweraplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.floweraplication.UserPlantRedPlActivity;
import com.example.floweraplication.databinding.ActivityAdminButonsBinding;
import com.example.floweraplication.databinding.ActivityPlantDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PlantDetailActivity extends AppCompatActivity {

    private ActivityPlantDetailBinding binding;

    TextView PlName,PlText,PlType,PlHabitat,PlSize,PlEndurance,PlPurpose,PlTox;
    ImageView PlImage;
    FloatingActionButton Redact;

    Toolbar toolbar;
    private static final String TAG = "ADD_PLANT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Redact = binding.floatingActionButton3;
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        PlName = binding.PlName;
        PlText = binding.PlText;
        PlType = binding.PlType;
        PlHabitat = binding.PlHabitat;
        PlSize = binding.PlSize;
        PlEndurance = binding.PlEndurance;
        PlPurpose = binding.PlPurpose;
        PlTox = binding.PlTox;
        PlImage = binding.PlImage;

        Glide.with(PlantDetailActivity.this).load(getIntent().getStringExtra("PlImage")).into(PlImage);

        PlName.setText(getIntent().getStringExtra("PlName"));
        PlText.setText(getIntent().getStringExtra("PlText"));
        PlType.setText(getIntent().getStringExtra("PlType"));
        PlHabitat.setText(getIntent().getStringExtra("PlHabitat"));
        PlSize.setText(getIntent().getStringExtra("PlSize"));
        PlEndurance.setText(getIntent().getStringExtra("PlEndurance"));
        PlPurpose.setText(getIntent().getStringExtra("PlPurpose"));
        PlTox.setText(getIntent().getStringExtra("PlTox"));

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("PlId").toString();
        String image = arguments.get("PlImage").toString();
        String name = arguments.get("PlName").toString();

        binding.floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), DobUserPlantActivity.class);

                intent.putExtra("idPl",id);
                intent.putExtra("PName",name);
                intent.putExtra("pngView",image);
                Log.d(TAG, "Пиздец"+name);

                startActivity(intent);
            }
        });
    }
}