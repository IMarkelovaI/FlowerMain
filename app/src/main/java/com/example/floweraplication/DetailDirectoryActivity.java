package com.example.floweraplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.floweraplication.databinding.ActivityDetailDirectoryBinding;
import com.example.floweraplication.databinding.ActivityDirectoryBinding;
import com.example.floweraplication.databinding.ActivityUserPlantDetailBinding;
import com.google.firebase.auth.FirebaseAuth;

public class DetailDirectoryActivity extends AppCompatActivity {

    private ActivityDetailDirectoryBinding binding;
    private FirebaseAuth firebaseAuth;

    TextView PlName,Description;
    ImageView PlImage;

    private static final String TAG = "ADD_PLANT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailDirectoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Подождите");
        progressDialog. setCanceledOnTouchOutside(false);

        PlName=binding.PlName;
        Description=binding.Description;
        PlImage= binding.PlImage;

        Glide.with(DetailDirectoryActivity.this).load(getIntent().getStringExtra("picture")).into(PlImage);

        PlName.setText(getIntent().getStringExtra("name"));
        Description.setText(getIntent().getStringExtra("description"));


    }
}