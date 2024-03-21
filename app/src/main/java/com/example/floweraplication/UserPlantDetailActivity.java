package com.example.floweraplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.floweraplication.Activity.AdminActivity;
import com.example.floweraplication.Activity.AdminButonsActivity;
import com.example.floweraplication.Activity.PlantDetailActivity;
import com.example.floweraplication.databinding.ActivityDobUserPlantBinding;
import com.example.floweraplication.databinding.ActivityPlantDetailBinding;
import com.example.floweraplication.databinding.ActivityUserPlantDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class UserPlantDetailActivity extends AppCompatActivity {

    private ActivityUserPlantDetailBinding binding;
    private FirebaseAuth firebaseAuth;

    TextView PlName,Sun,Height,Width,Description;
    ImageView PlImage;
    ImageButton Redact;

    private static final String TAG = "ADD_PLANT_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPlantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Подождите");
        progressDialog. setCanceledOnTouchOutside(false);

        Redact = binding.Redact;

        PlName=binding.PlName;
        Sun=binding.Sun;
        Height=binding.Height;
        Width=binding.Width;
        Description=binding.Description;
        PlImage= binding.PlImage;

        Glide.with(UserPlantDetailActivity.this).load(getIntent().getStringExtra("picturePl")).into(PlImage);

        PlName.setText(getIntent().getStringExtra("namePlU"));
        Sun.setText(getIntent().getStringExtra("sunPlU"));
        Height.setText(getIntent().getStringExtra("plant_sizePlU"));
        Width.setText(getIntent().getStringExtra("plant_widthPlU"));
        Description.setText(getIntent().getStringExtra("descriptionPlU"));

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("idPlU").toString();
        String plant_id = arguments.get("plant_idPlU").toString();
        String image = arguments.get("picturePl").toString();
        String name = arguments.get("namePlU").toString();
        String sun = arguments.get("sunPlU").toString();
        String hight = arguments.get("plant_sizePlU").toString();
        String width = arguments.get("plant_widthPlU").toString();
        String description = arguments.get("descriptionPlU").toString();

        binding.Redact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserPlantRedPlActivity.class);

                intent.putExtra("idPlU",id);
                intent.putExtra("descriptionPlU",description);
                intent.putExtra("namePlU",name);
                intent.putExtra("picturePl",image);
                intent.putExtra("plant_idPlU",plant_id);
                intent.putExtra("plant_sizePlU",hight);
                intent.putExtra("plant_widthPlU",width);
                intent.putExtra("sunPlU",sun);
                Log.d(TAG, "Пиздец"+name);

                startActivity(intent);
            }
        });

    }
}