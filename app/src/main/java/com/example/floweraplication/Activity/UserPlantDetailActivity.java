package com.example.floweraplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.floweraplication.MyAplication;
import com.example.floweraplication.UserPlantRedPlActivity;
import com.example.floweraplication.databinding.ActivityDobUserPlantBinding;
import com.example.floweraplication.databinding.ActivityUserPlantDetailBinding;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserPlantDetailActivity extends AppCompatActivity {

    private ActivityUserPlantDetailBinding binding;
    private FirebaseAuth firebaseAuth;

    TextView PlName,Sun,Height,Width,Description,Watering,Loosening,Transfer, watText, losText, traText;
    ImageView PlImage;
    ImageButton Redact;

    String Water,Loos,Transf;

    private static final String TAG = "ADD_PLANT_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPlantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar mToolbar = binding.toolbar;
        setSupportActionBar(mToolbar);
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
        watText = binding.watText;
        losText=binding.losText;
        traText=binding.traText;

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

        Watering = binding.Watering;
        Loosening = binding.Loosening;
        Transfer = binding.Transfer;

        Log.i(TAG, "AAAAAAAAAAAAAAAAAAAAA"+plant_id);

        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Plant").child(plant_id);
        ref1.child("Optimal_condition").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Water = snapshot.child("watering_time").getValue().toString();
                    Loos = snapshot.child("loosening_time").getValue().toString();
                    Transf = snapshot.child("transfer_time").getValue().toString();

                    Log.i(TAG, "Uriririri"+Water.toString());
                    Log.i(TAG, "Uririririddddddddddd"+Loos.toString());
                    Log.i(TAG, "Uririririaaaaaaaaaa"+Transf.toString());

                    loadW();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    private void loadW() {
        int w = Integer.parseInt(Water);
        int l = Integer.parseInt(Loos);
        int t = Integer.parseInt(Transf);
        watText.setText(Water+" дней");
        losText.setText(Loos+" дней");
        traText.setText(Transf+" дней");
        Log.i(TAG, "hhhhhhhhhhhhhhhhhhhhhh "+t);

        long timestamp = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        cal.add(Calendar.DAY_OF_YEAR, w);
        String dateW = DateFormat.format("dd/MM/yyyy", cal).toString();
        Watering.setText(dateW);

        Calendar cal1 = Calendar.getInstance(Locale.ENGLISH);
        cal1.setTimeInMillis(timestamp);
        cal1.add(Calendar.DAY_OF_YEAR, l);
        String dateL = DateFormat.format("dd/MM/yyyy", cal1).toString();
        Loosening.setText(dateL);

        Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
        cal2.setTimeInMillis(timestamp);
        cal2.add(Calendar.DAY_OF_YEAR, t);
        String dateT = DateFormat.format("dd/MM/yyyy", cal2).toString();
        Transfer.setText(dateT);
    }
}