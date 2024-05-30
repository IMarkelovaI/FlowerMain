package com.example.floweraplication.Activity;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.floweraplication.R;
import com.example.floweraplication.UserPlantRedPlActivity;
import com.example.floweraplication.databinding.ActivityDobUserPlantBinding;
import com.example.floweraplication.models.ModelPng;
import com.example.floweraplication.models.ModelUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Nullable;

public class DobUserPlantActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private static final String TAG = "ADD_PLANT_TAG";

    private ArrayList<ModelPng> pngArrayList;
    public ModelUser model;
    private FirebaseAuth firebaseAuth;
    private ActivityDobUserPlantBinding binding;

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES = "apppref";
    private static final int PNG_PICK_CODE =1000;

    private Uri pngUri = null;
    private Uri pngUri1 = null;
    Bitmap bitmap;
    TextView PlName;
    ImageView pngView;
    Button Dob;
    Toolbar toolbar;
    String watering_time, transfer_time, loosening_time;

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDobUserPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();


        //configure progress dialog
        toolbar = binding.toolbar;
        toolbar.setTitleTextAppearance(this, R.style.FontForTitle);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(com.google.android.material.R.attr.colorOnBackground, typedValue, true);
        @ColorInt int color = typedValue.data;
        toolbar.setTitleTextColor(color);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Добавление");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog( this);
        progressDialog.setTitle("Подождите");
        progressDialog. setCanceledOnTouchOutside(false);

        Dob = binding.Dob;
        pngView = binding.pngView;
        PlName = binding.PlName;

        Uri uri = Uri.parse(getIntent().getStringExtra("pngView"));
        Log.i(TAG,"KKKKKKKKKKKKKKKKKK"+uri);

        PlName.setText(getIntent().getStringExtra("PName"));
        Glide.with(DobUserPlantActivity.this).load(getIntent().getStringExtra("pngView")).into(pngView);

        Log.d(TAG, "onSuccess: Successfully uploaded"+pngView);


        binding.Dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                    validateData();
            }
        });

        binding.Redact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                pngPickIntent();
            }
        });

    }
    private String description ="";
    private String sun="";
    private String plant_size="";
    private String plant_width="";
    private String name ="";
    private String pictureU="";
    private void validateData() {
        //Before adding validate data
        // get data
        name = binding.PlName.getText().toString().trim();
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


            createPlant();
        }
    }

    private void createPlant() {
        //progressDialog.setMessage("Добавление нового растения");
        //progressDialog.show();


        long timestamp = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);

        Bundle arguments = getIntent().getExtras();
        String plant_id = arguments.get("idPl").toString();

        //SharedPreferences sharedPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        //String user_id = sharedPref.getString("mAppIUD", "unknown");

        Uri uri = Uri.parse(getIntent().getStringExtra("pngView"));

        if(pngUri!=null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName+".png");
            storageReference.putFile(pngUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //get url of uploaded image
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri downloadImageUri = uriTask.getResult();

                            if (uriTask.isSuccessful()){
                                //setup data to save
                                HashMap<String,Object> hashMap = new HashMap<>();
                                hashMap.put("id", ""+timestamp);
                                hashMap.put("plant_id", ""+plant_id);
                                //hashMap.put("user_id", ""+user_id);
                                hashMap.put("name", ""+name);
                                hashMap.put("sun", ""+sun);
                                hashMap.put("plant_size", ""+plant_size);
                                hashMap.put("plant_width", ""+plant_width);
                                hashMap.put("description", ""+description);
                                hashMap.put("picture",""+downloadImageUri);
                                //hashMap.put("pots_id", "");

                                //save to db
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                ref.child(firebaseAuth.getUid()).child("User_plant").child(""+timestamp)
                                        .setValue(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                //db updated

                                                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                                                cal.setTimeInMillis(timestamp);
                                                String dateW = DateFormat.format("dd/MM/yyyy", cal).toString();

                                                progressDialog.dismiss();
                                                Log.d(TAG, "onSuccess: Successfully uploaded"+downloadImageUri);

                                                HashMap<String,Object> hashMap = new HashMap<>();
                                                hashMap.put("id", ""+timestamp);
                                                hashMap.put("last_day_of_watering", ""+dateW);
                                                hashMap.put("last_day_of_loosening", ""+dateW);
                                                hashMap.put("last_day_of_transport", ""+dateW);

                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                                ref.child(firebaseAuth.getUid()).child("User_plant").child(String.valueOf(timestamp)).child("Last_care").child(""+timestamp)
                                                        .updateChildren(hashMap)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                progressDialog.dismiss();

                                                                startActivity(new Intent(DobUserPlantActivity.this, UserActivity.class));
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                progressDialog.dismiss();
                                                            }
                                                        });
                                                //startActivity(new Intent(DobUserPlantActivity.this, UserActivity.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //failed updating db
                                                //progressDialog.dismiss();
                                                Log.d(TAG, "onFailure: Failed to upload to db due to"+e.getMessage());
                                            }
                                        });
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Failed to upload to db due to"+e.getMessage());
                        }
                    });
        }
        else{

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id", ""+timestamp);
            hashMap.put("plant_id", ""+plant_id);
            //hashMap.put("user_id", ""+user_id);
            hashMap.put("name", ""+name);
            hashMap.put("picture", ""+uri);
            hashMap.put("sun", ""+sun);
            hashMap.put("plant_size", ""+plant_size);
            hashMap.put("plant_width", ""+plant_width);
            hashMap.put("description", ""+description);
            //hashMap.put("pots_id", "");

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getUid()).child("User_plant").child(""+timestamp)
                    .setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                            cal.setTimeInMillis(timestamp);
                            String dateW = DateFormat.format("dd/MM/yyyy", cal).toString();

                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("id", ""+timestamp);
                            hashMap.put("last_day_of_watering", ""+dateW);
                            hashMap.put("last_day_of_loosening", ""+dateW);
                            hashMap.put("last_day_of_transport", ""+dateW);

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                            ref.child(firebaseAuth.getUid()).child("User_plant").child(String.valueOf(timestamp)).child("Last_care").child(""+timestamp)
                                    .updateChildren(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressDialog.dismiss();

                                            startActivity(new Intent(DobUserPlantActivity.this, UserActivity.class));
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                        }
                                    });
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

    private void addPlantFirebase() {
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
        hashMap.put("name", ""+name);
        hashMap.put("picture", ""+pngView);
        hashMap.put("sun", ""+sun);
        hashMap.put("plant_size", ""+plant_size);
        hashMap.put("plant_width", ""+plant_width);
        hashMap.put("description", ""+description);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("User_plant");
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
    private void pngPickIntent() {

        Log.d(TAG, "pngPickIntent: starting png pick intent");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser (intent, "Select PNG"), PNG_PICK_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PNG_PICK_CODE) {
                Log.d(TAG, "onActivityResult: PNG Picked");
                pngUri = data.getData();
                Log.d(TAG, "onActivityResult: URI: " + pngUri);

                pngUri1 = data.getData();
                Log.d(TAG, "onActivityResult: URI: " + pngUri1);
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pngUri);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            if (pngUri != null){
                binding.pngView.setImageBitmap(bitmap);
            }
            else {

                Uri uri = Uri.parse(getIntent().getStringExtra("pngView"));
                pngUri1 = uri;
            }
        }
        else {
            Log.d(TAG, "onActivityResult: cancelled picking png");
            Toast.makeText(this, "Отменен выбор изображения", Toast.LENGTH_SHORT).show();
        }
    }
}