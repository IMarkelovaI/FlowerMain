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
import android.graphics.BitmapFactory;
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

import com.example.floweraplication.R;
import com.example.floweraplication.databinding.ActivityPhotoplantDobBinding;
import com.example.floweraplication.models.ModelPng;
import com.example.floweraplication.models.ModelUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

public class ActivityPhotoplantDob extends AppCompatActivity {
    private ProgressDialog progressDialog;

    private static final String TAG = "ADD_PLANT_TAG";

    private ArrayList<ModelPng> pngArrayList;
    public ModelUser model;
    private FirebaseAuth firebaseAuth;
    private ActivityPhotoplantDobBinding binding;

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
    String plant_id;
    String W_watering_time;

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPhotoplantDobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

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

        PlName.setText(getIntent().getStringExtra("PlName"));

        //byte[] bytes = getIntent().getByteArrayExtra("Bitmap");
        //Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0, bytes.length);


        Uri uri = Uri.parse(getIntent().getStringExtra("Pa"));
        pngView.setImageURI(uri);

        binding.Dob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Bundle arguments = getIntent().getExtras();
                String plant_name = arguments.get("PlName").toString();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("Plant")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                    if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0)
                                    {
                                        if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                                            String name = String.valueOf(dataSnapshot.child("name").getValue());
                                            if(name.equals(plant_name)){
                                                plant_id = String.valueOf(dataSnapshot.child("id").getValue());

                                                validateData();
                                            }
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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

        name = binding.PlName.getText().toString().trim();
        sun = binding.Sun.getText().toString().trim();
        plant_size = binding.Height.getText().toString().trim();
        plant_width = binding.Width.getText().toString().trim();
        description  = binding.Description.getText().toString().trim();

        if (TextUtils.isEmpty(sun)){
            Toast.makeText(this,  "Введите люксы", Toast.LENGTH_SHORT).show();
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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Plant");
        reference.child(plant_id).child("Optimal_condition").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                W_watering_time =""+snapshot.child("watering_time").getValue();

                long timestamp = System.currentTimeMillis();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
                Date now = new Date();
                String fileName = formatter.format(now);

                Bundle arguments = getIntent().getExtras();
                String plant_name = arguments.get("PlName").toString();
                Log.i(TAG, "JJJJJJKJJJJJJJ"+plant_id);

                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTimeInMillis(timestamp);
                String dateW = DateFormat.format("dd/MM/yyyy", cal).toString();

                int www = Integer.parseInt(W_watering_time);
                cal.add(Calendar.DAY_OF_YEAR, www);
                Log.e(TAG, "cal.add(Calendar.DAY_OF_YEAR, w) " + cal);
                String dateWa = DateFormat.format("dd/MM/yyyy", cal).toString();
                Log.e(TAG, "Watering следующее " + dateWa);

                Uri uri = Uri.parse(getIntent().getStringExtra("Pa"));

                if(pngUri!=null){
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName+".png");
                    storageReference.putFile(pngUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //get url of uploaded image
                                    Toast.makeText(ActivityPhotoplantDob.this, "Растение создается....", Toast.LENGTH_SHORT).show();
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful());
                                    Uri downloadImageUri = uriTask.getResult();

                                    if (uriTask.isSuccessful()){
                                        //setup data to save
                                        HashMap<String,Object> hashMap = new HashMap<>();
                                        hashMap.put("id", ""+timestamp);
                                        hashMap.put("plant_id", ""+plant_id);
                                        hashMap.put("name", ""+name);
                                        hashMap.put("sun", ""+sun);
                                        hashMap.put("plant_size", ""+plant_size);
                                        hashMap.put("plant_width", ""+plant_width);
                                        hashMap.put("description", ""+description);
                                        hashMap.put("picture",""+downloadImageUri); //uri of uploaded image
                                        hashMap.put("next_day_of_watering",""+dateWa);

                                        //save to db
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                        ref.child(firebaseAuth.getUid()).child("User_plant").child(""+timestamp)
                                                .setValue(hashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

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

                                                                        Intent intent = new Intent(getApplicationContext(), UserPlantDetailActivity.class);

                                                                        intent.putExtra("idPlU",timestamp);
                                                                        intent.putExtra("plant_idPlU",plant_id);
                                                                        intent.putExtra("picturePl",downloadImageUri.toString());
                                                                        intent.putExtra("namePlU",name);
                                                                        intent.putExtra("sunPlU",sun);
                                                                        intent.putExtra("plant_sizePlU",plant_size);
                                                                        intent.putExtra("plant_widthPlU",plant_width);
                                                                        intent.putExtra("descriptionPlU",description);

                                                                        startActivity(intent);
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        progressDialog.dismiss();
                                                                    }
                                                                });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

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

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName+".png");
                    storageReference.putFile(uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //get url of uploaded image
                                    Toast.makeText(ActivityPhotoplantDob.this, "Растение создается....", Toast.LENGTH_SHORT).show();
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful());
                                    Uri downloadImageUri = uriTask.getResult();

                                    if (uriTask.isSuccessful()){
                                        //setup data to save
                                        HashMap<String,Object> hashMap = new HashMap<>();
                                        hashMap.put("id", ""+timestamp);
                                        hashMap.put("plant_id", ""+plant_id);
                                        hashMap.put("name", ""+name);
                                        hashMap.put("sun", ""+sun);
                                        hashMap.put("plant_size", ""+plant_size);
                                        hashMap.put("plant_width", ""+plant_width);
                                        hashMap.put("description", ""+description);
                                        hashMap.put("picture",""+downloadImageUri); //uri of uploaded image
                                        hashMap.put("next_day_of_watering",""+dateWa);

                                        //save to db
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                                        ref.child(firebaseAuth.getUid()).child("User_plant").child(""+timestamp)
                                                .setValue(hashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

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

                                                                        Intent intent = new Intent(getApplicationContext(), UserPlantDetailActivity.class);

                                                                        intent.putExtra("idPlU",timestamp);
                                                                        intent.putExtra("plant_idPlU",plant_id);
                                                                        intent.putExtra("picturePl",uri.toString());
                                                                        intent.putExtra("namePlU",name);
                                                                        intent.putExtra("sunPlU",sun);
                                                                        intent.putExtra("plant_sizePlU",plant_size);
                                                                        intent.putExtra("plant_widthPlU",plant_width);
                                                                        intent.putExtra("descriptionPlU",description);

                                                                        startActivity(intent);
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        progressDialog.dismiss();
                                                                    }
                                                                });
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void pngPickIntent() {

        Intent intent = new Intent();
        intent. setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser (intent, "Выбор PNG"), PNG_PICK_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PNG_PICK_CODE) {

                pngUri = data.getData();

                pngUri1 = data.getData();

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

                Uri uri = Uri.parse(getIntent().getStringExtra("Pa"));
                pngUri = uri;
            }
        }
        else {

            Toast.makeText(this, "Отменен выбор изображения", Toast.LENGTH_SHORT).show();
        }
    }
}