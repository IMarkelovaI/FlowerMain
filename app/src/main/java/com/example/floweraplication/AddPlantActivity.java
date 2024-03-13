package com.example.floweraplication;

import static android.app.PendingIntent.getActivity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.floweraplication.databinding.ActivityAddPlantBinding;
import com.example.floweraplication.models.ModelPng;
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

    private ActivityAddPlantBinding binding;
    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    private static final String TAG = "ADD_PNG_TAG";
    private static final int PNG_PICK_CODE =1000;
    private Bitmap bitmap;

    private ArrayList<String> categoryTitleArrayList, categoryIdArrayList;
    private ArrayList<String> purposeTitleArrayList, purposeIdArrayList;

    private Uri pngUri;

    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String PhotoUrl;

    //ActivityResultLauncher<String> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                binding.imageView.setImageURI(result);


            }
        });*/
        storageReference=firebaseStorage.getReference();

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Подождите");
        progressDialog.setCanceledOnTouchOutside(false);

        database = FirebaseDatabase.getInstance();
        firebaseStorage =FirebaseStorage.getInstance();

        loadPngTypes();
        loadPngPurposes();

        binding.buttonNas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPlantActivity.this, AdminButonsActivity.class));
            }
        });
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


                /*reference.putFile(pngUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                ModelPng model = new ModelPng();
                                model.setPicture(uri.toString());

                                //name,type_id,description,habitat,size,purpose_id,degree_of_toxicity,endurance,picture;

                                model.setId(id.getText());
                            }
                        });
                    }
                });*/
                validateData();
                UploadImage();
            }
        });
    }

    //есть
    private String name ="", description ="", habitat ="", size ="", Edur="", Tox="",picture="";
    private void validateData() {
        //Before adding validate data
        // get data
        name = binding.TypeName.getText().toString().trim();
        description = binding.TypeDescr.getText().toString().trim();
        habitat = binding.TypeHabitat.getText().toString().trim();
        size = binding.TypeSize.getText().toString().trim();
        picture = String.valueOf(pngUri);

        if (binding.checkBox.isChecked()){
            Edur = "true";
        }
        else {
            Edur = "false";
        }
        if (binding.checkBox2.isChecked()){
            Tox = "true";
        }
        else {
            Tox = "false";
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
        else if (TextUtils.isEmpty(selectedCategoryTitle)){
            Toast.makeText(this,  "Выберите тип", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(selectedPurposeTitle)){
            Toast.makeText(this,  "Выберите предназначение", Toast.LENGTH_SHORT).show();
        }

        else if (pngUri==null){
            Toast.makeText(this,  "Выберите фото", Toast.LENGTH_SHORT).show();
        }
        else {
            //uploadPngToStorage();
            /*DatabaseReference reference = database.getReference().child("Plant")
                    .child(System.currentTimeMillis()+"");
            ModelPng modelPng=new ModelPng(name,description,habitat,size,"","","","","","PhotoUrl");
            reference.*/
            //uploadPngInfoToDd();
        }

    }

    /*private void uploadPngToStorage() {
        Log.d(TAG, "uploadPngToStorage: uploading to storage");

        progressDialog.setMessage("Идет доавление фото");
        progressDialog.show();

        long timestamp = System.currentTimeMillis();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);

        storageReference.putFile(pngUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: Image upload failed bue to");
                        Log.d(TAG, "onSuccess: getting image url");

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        String uploadedPngUrl =""+uriTask.getResult();

                        uploadPngInfoToDd(uploadedPngUrl, timestamp);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure: PNG upload failed due to"+e.getMessage());
                        Toast.makeText(AddPlantActivity.this, "Загрузить изображение не удалось из-за"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }*/

    private void uploadPngInfoToDd(String uploadedPngUrl, long timestamp) {
        Log.d(TAG, "uploadPngInfoToDd: uploading Png info to firebase db");

        progressDialog.setMessage("Картинка добавляется");
        String uid = firebaseAuth.getUid();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("id", ""+timestamp);
        hashMap.put("name", ""+name);
        hashMap.put("type_id", ""+selectedCategoryId);
        hashMap.put("description", ""+description);
        hashMap.put("habitat", ""+habitat);
        hashMap.put("size", ""+size);
        hashMap.put("purpose_id", ""+selectedPurposeId);
        hashMap.put("degree_of_toxicity", ""+Tox);
        hashMap.put("endurance", ""+Edur);
        hashMap.put("picture",""+PhotoUrl);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Plant");
        ref.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onSuccess: Successfully uploaded");
                        Toast.makeText(AddPlantActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to upload to db due to"+e.getMessage());
                        Toast.makeText(AddPlantActivity.this, "Failed to upload to db due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadPngPurposes() {
        Log.d(TAG, "loadPngTypes: Loading png purpose");
        purposeTitleArrayList = new ArrayList<>();
        purposeIdArrayList = new ArrayList<>();

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Purpose");
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                purposeTitleArrayList.clear();
                purposeIdArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    String purposeId = ""+ds.child("id").getValue();
                    String purposeName =""+ds.child("name").getValue();

                    purposeTitleArrayList.add(purposeName);
                    purposeIdArrayList.add(purposeId);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadPngTypes() {
        Log.d(TAG, "loadPngTypes: Loading png types");
        categoryTitleArrayList = new ArrayList<>();
        categoryIdArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Type");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryTitleArrayList.clear();
                categoryIdArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    String categoryId = ""+ds.child("id").getValue();
                    String categoryName =""+ds.child("name").getValue();

                    categoryTitleArrayList.add(categoryName);
                    categoryIdArrayList.add(categoryId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String selectedCategoryId, selectedCategoryTitle;
    private void typePickDialog() {
        Log.d(TAG, "typePickDialog: showing type pick dialog");

        String[] categoriesArray = new String[categoryTitleArrayList.size()];
        for (int i = 0; i< categoryTitleArrayList.size(); i++){
            categoriesArray[i]= categoryTitleArrayList.get(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите тип")
                .setItems(categoriesArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedCategoryTitle= categoryTitleArrayList.get(which);
                        selectedCategoryId = categoryIdArrayList.get(which);
                        binding.TypeC.setText(selectedCategoryTitle);

                        Log.d(TAG, "onClick: Selected Type:"+selectedCategoryId+" "+selectedCategoryTitle);
                    }
                })
                .show();
    }

    private String selectedPurposeId, selectedPurposeTitle;
    private void purposePickDialog() {

        Log.d(TAG, "purposePickDialog: showing purpose pick dialog");

        String[] purposesArray = new String[purposeTitleArrayList.size()];
        for (int i = 0; i< purposeTitleArrayList.size(); i++){
            purposesArray[i]= purposeTitleArrayList.get(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите предназначение")
                .setItems(purposesArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedPurposeTitle= purposeTitleArrayList.get(which);
                        selectedPurposeId = purposeIdArrayList.get(which);
                        binding.Pur.setText(selectedPurposeTitle);

                        Log.d(TAG, "onClick: Selected Purpose:"+selectedPurposeId+" "+selectedPurposeTitle);
                    }
                })
                .show();

    }

    //есть
    private void pngPickIntent() {

        Log.d(TAG, "pngPickIntent: starting png pick intent");
        Intent intent = new Intent();
        intent. setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser (intent, "Select PNG"), PNG_PICK_CODE);
        launcher.launch(intent);
    }

    //есть
    ActivityResultLauncher<Intent> launcher
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if (data != null && data.getData()!=null){
                        pngUri=data.getData();
                        try {
                            bitmap= MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),pngUri
                            );

                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (pngUri != null){
                        binding.imageView.setImageBitmap(bitmap);
                    }
                }
    });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PNG_PICK_CODE) {

                binding.imageView.setImageURI(pngUri);
                Log.d(TAG, "onActivityResult: PNG Picked");
                pngUri = data.getData();
                Log.d(TAG, "onActivityResult: URI: " + pngUri);
            }
        } else {
            Log.d(TAG, "onActivityResult: cancelled picking png");
            Toast.makeText(this, "Отменен выбор изображения", Toast.LENGTH_SHORT).show();
        }
    }

    //есть
    private void UploadImage(){
        if (pngUri != null) {
            final StorageReference myRef = storageReference.child("photo/" + pngUri.getLastPathSegment());
            myRef.putFile(pngUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if (uri!=null){
                                PhotoUrl = uri.toString();
                                UploadImage();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPlantActivity.this, "Failed to upload to db due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}