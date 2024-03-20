package com.example.floweraplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.floweraplication.Activity.AddPurposeActivity;
import com.example.floweraplication.Activity.AdminButonsActivity;
import com.example.floweraplication.Activity.AuthActivity;
import com.example.floweraplication.Activity.PlantDetailActivity;
import com.example.floweraplication.Activity.UserActivity;
import com.example.floweraplication.databinding.ActivityAddPlantBinding;
import com.example.floweraplication.databinding.ActivityDobUserPlantBinding;
import com.example.floweraplication.databinding.ActivityPlantDetailBinding;
import com.example.floweraplication.models.ModelCategory;
import com.example.floweraplication.models.ModelPng;
import com.example.floweraplication.models.ModelUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class DobUserPlantActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private static final String TAG = "ADD_PLANT_TAG";

    private ArrayList<ModelPng> pngArrayList;
    public ModelUser model;
    private FirebaseAuth firebaseAuth;
    private ActivityDobUserPlantBinding binding;

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES = "apppref";

    private Uri pngUri = null;
    Bitmap bitmap;
    TextView PlName;
    ImageView pngView;
    Button Dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDobUserPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        //configure progress dialog

        progressDialog = new ProgressDialog( this);
        progressDialog.setTitle("Подождите");
        progressDialog. setCanceledOnTouchOutside(false);

        Dob = binding.Dob;
        pngView = binding.pngView;
        PlName = binding.PlName;

        PlName.setText(getIntent().getStringExtra("PlName"));
        Glide.with(DobUserPlantActivity.this).load(getIntent().getStringExtra("pngView")).into(pngView);


        binding.Dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                validateData();
            }
        });

    }
    private String description ="";
    private String sun="";
    private String plant_size="";
    private String plant_width="";
    private void validateData() {
        //Before adding validate data
        // get data
        sun = binding.Sun.getText().toString().trim();
        plant_size = binding.Height.getText().toString().trim();
        plant_width = binding.Width.getText().toString().trim();
        description  = binding.Description.getText().toString().trim();
        //validate if not empty
        if (TextUtils.isEmpty(sun)){
            Toast.makeText(this,  "Введите солнце", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(plant_size)){
            Toast.makeText(this,  "Введите высоту растения", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(plant_width)){
            Toast.makeText(this,  "Введите ширину растения", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description)){
            Toast.makeText(this,  "Введите описание", Toast.LENGTH_SHORT).show();
        }
        else {
            addPurposeFirebase();
        }
    }

    private void addPurposeFirebase() {
        //show progress
        progressDialog.setMessage("Растение добавляется добавляется");
        progressDialog.show();

        Bundle arguments = getIntent().getExtras();
        String plant_id = arguments.get("idPl").toString();


        SharedPreferences sharedPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String user_id = sharedPref.getString("mAppIUD", "unknown");


        //get timestamp
        long timestamp = System.currentTimeMillis();
        //setup info to add in firebase db

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", ""+timestamp);
        hashMap.put("plant_id", ""+plant_id);
        hashMap.put("user_id", ""+user_id);
        hashMap.put("name", ""+PlName);
        hashMap.put("picture", ""+pngView);
        hashMap.put("sun", ""+sun);
        hashMap.put("plant_size", ""+plant_size);
        hashMap.put("plant_width", ""+plant_width);
        hashMap.put("description", ""+description);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("User_plan");
        ref.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(DobUserPlantActivity.this, "Растение пользователя добавлено", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DobUserPlantActivity.this, UserActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(DobUserPlantActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}