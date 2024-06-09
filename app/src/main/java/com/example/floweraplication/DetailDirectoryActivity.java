package com.example.floweraplication;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
    Toolbar toolbar;
    private static final String TAG = "ADD_PLANT_TAG";

    @Override
    public boolean onSupportNavigateUp()
    {
     onBackPressed();
     return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailDirectoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolbar = binding.toolbar;

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Подождите");
        progressDialog. setCanceledOnTouchOutside(false);

        PlName=binding.PlName;
        Description=binding.Description;
        PlImage= binding.PlImage;


        Glide.with(DetailDirectoryActivity.this).load(getIntent().getStringExtra("picture")).into(PlImage);

        PlName.setText(getIntent().getStringExtra("name"));
        String s = PlName.getText().toString();
        Log.w(TAG, "onSuccess: Successfully uploaded"+s);

        Log.w(TAG, "onSuccess: Successfully uploaded"+PlName.getText());
        Description.setText(getIntent().getStringExtra("description"));

    }
}
