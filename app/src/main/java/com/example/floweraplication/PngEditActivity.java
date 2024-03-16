package com.example.floweraplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.floweraplication.databinding.ActivityPngEditBinding;
import com.example.floweraplication.databinding.RowPngAdminBinding;

public class PngEditActivity extends AppCompatActivity {

    private ActivityPngEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPngEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}