package com.example.floweraplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.floweraplication.databinding.ActivityAdminButonsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminButonsActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ActivityAdminButonsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminButonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        binding.addTyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminButonsActivity.this, AdminActivity.class));
            }
        });

        binding.addPuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminButonsActivity.this, PurposeActivity.class));
            }
        });

        binding.addPlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminButonsActivity.this, AddPlantActivity.class));
            }
        });
    }
    private void checkUser() {

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser==null) {

            startActivity(new Intent(this, AuthActivity.class));
            finish();
        }

        else {

            String email = firebaseUser.getEmail();

            binding.textView.setText(email) ;
        }
    }
}