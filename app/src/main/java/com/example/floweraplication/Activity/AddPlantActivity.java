package com.example.floweraplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.floweraplication.R;
import com.example.floweraplication.databinding.ActivityAddPlantBinding;
import com.example.floweraplication.models.ModelCategory;
import com.example.floweraplication.models.ModelPurpose;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Nullable;



public class AddPlantActivity extends AppCompatActivity {

    Dialog dialog;

    private ActivityAddPlantBinding binding;

    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private static final String TAG = "ADD_PNG_TAG";
    private static final int PNG_PICK_CODE =1000;

    private ArrayList<ModelCategory> categoryArrayList;
    private ArrayList<ModelPurpose> purposeArrayList;
    private Uri pngUri = null;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Подождите");
        progressDialog.setCanceledOnTouchOutside(false);


        loadPngTypes();
        loadPngPurposes();

        binding.buttonNas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPlantActivity.this, AdminButonsActivity.class));
            }
        });

        binding.buttonDobF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pngPickIntent();
            }
        });

        binding.TypeC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typePickDialog();
            }
        });

        binding.Pur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purposePickDialog();
            }
        });

        binding.buttonDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploudData();
                binding.TypeName.setText("");
                binding.TypeDescr.setText("");
                binding.TypeHabitat.setText("");
                binding.TypeSize.setText("");
                binding.TypeC.setText("");
                binding.Pur.setText("");
                binding.checkBox.setActivated(false);
                binding.checkBox2.setActivated(false);
                binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_addflower_grin));
            }
        });
    }

    private String name ="", description ="", habitat ="", size ="", type_id ="", purpose_id ="", Edur="", Tox="";

    private void uploudData() {
        //Before adding validate data
        // get data
        name = binding.TypeName.getText().toString().trim();
        description = binding.TypeDescr.getText().toString().trim();
        habitat = binding.TypeHabitat.getText().toString().trim();
        size = binding.TypeSize.getText().toString().trim();
        type_id = binding.TypeC.getText().toString().trim();
        purpose_id = binding.Pur.getText().toString().trim();
        if (binding.checkBox.isChecked()){
            Edur = "Прихотливое";
        }
        else {
            Edur = "Неприхотливое";
        }
        if (binding.checkBox2.isChecked()){
            Tox = "Токсичное";
        }
        else {
            Tox = "Нетоксичное";
        }
        //validate if not empty
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,  "Введите название растения", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description)){
            Toast.makeText(this,  "Введите описание", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(habitat)){
            Toast.makeText(this,  "Введите место обитания в природе", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(size)){
            Toast.makeText(this,  "Введите размер растения", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(type_id)){
            Toast.makeText(this,  "Выберите тип", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(purpose_id)){
            Toast.makeText(this,  "Выберите предназначение", Toast.LENGTH_SHORT).show();
        }

        else if (pngUri==null){
            Toast.makeText(this,  "Выберите фото", Toast.LENGTH_SHORT).show();
        }
        else {
            createPlant();
        }

    }

    private void createPlant() {
        //progressDialog.setMessage("Добавление нового растения");
        //progressDialog.show();
        binding.progressbar.setVisibility(View.VISIBLE);

        long timestamp = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);


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
                            hashMap.put("name", ""+name);
                            hashMap.put("type_id", ""+type_id);
                            hashMap.put("description", ""+description);
                            hashMap.put("habitat", ""+habitat);
                            hashMap.put("size", ""+size);
                            hashMap.put("purpose_id", ""+purpose_id);
                            hashMap.put("degree_of_toxicity", ""+Tox);
                            hashMap.put("endurance", ""+Edur);
                            hashMap.put("image",""+downloadImageUri); //uri of uploaded image

                            //save to db
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Plant");
                            ref.child(""+timestamp).setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            //db updated
                                            progressDialog.dismiss();
                                            binding.progressbar.setVisibility(View.INVISIBLE);
                                            Log.d(TAG, "onSuccess: Successfully uploaded"+downloadImageUri);
                                            Toast.makeText(AddPlantActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            //failed updating db
                                            binding.progressbar.setVisibility(View.INVISIBLE);
                                            //progressDialog.dismiss();
                                            Log.d(TAG, "onFailure: Failed to upload to db due to"+e.getMessage());
                                            Toast.makeText(AddPlantActivity.this, "Failed to upload to db due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.progressbar.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "onFailure: Failed to upload to db due to"+e.getMessage());
                        Toast.makeText(AddPlantActivity.this, "Failed to upload to db due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void pngPickIntent() {

        Log.d(TAG, "pngPickIntent: starting png pick intent");
        Intent intent = new Intent();
        intent. setType("image/*");
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
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),pngUri);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            if (pngUri != null){
                binding.imageView.setImageBitmap(bitmap);
            }


        } else {
            Log.d(TAG, "onActivityResult: cancelled picking png");
            Toast.makeText(this, "Отменен выбор изображения", Toast.LENGTH_SHORT).show();
        }
    }

    //loadID
    private void loadPngPurposes() {

        Log.d(TAG, "loadPngTypes: Loading png purpose");
        purposeArrayList = new ArrayList<>();

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Purpose");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelPurpose model1 = ds.getValue(ModelPurpose.class);
                    purposeArrayList.add(model1);
                    Log.d(TAG, "onDataChange"+model1.getName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadPngTypes() {
        Log.d(TAG, "loadPngTypes: Loading png types");
        categoryArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Type");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    ModelCategory model = ds.getValue(ModelCategory.class);
                    categoryArrayList.add(model);
                    Log.d(TAG, "onDataChange"+model.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void typePickDialog() {
        Log.d(TAG, "typePickDialog: showing type pick dialog");

        String[] categoriesArray = new String[categoryArrayList.size()];
        for (int i=0; i<categoryArrayList.size(); i++){
            categoriesArray[i]=categoryArrayList.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите тип")
                .setItems(categoriesArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String category= categoriesArray[which];
                        binding.TypeC.setText(category);

                        Log.d(TAG, "onClick: Selected Type:"+category);
                    }
                })
                .show();
    }

    private void purposePickDialog() {

        Log.d(TAG, "purposePickDialog: showing purpose pick dialog");

        String[] purposesArray = new String[purposeArrayList.size()];
        for (int i=0; i<purposeArrayList.size(); i++){
            purposesArray[i]=purposeArrayList.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите предназначение")
                .setItems(purposesArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String purpose= purposesArray[which];
                        binding.Pur.setText(purpose);

                        Log.d(TAG, "onClick: Selected Purpose:"+purpose);
                    }
                })
                .show();

    }


}