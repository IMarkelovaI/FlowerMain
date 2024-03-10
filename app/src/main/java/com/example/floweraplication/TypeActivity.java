package com.example.floweraplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.floweraplication.databinding.ActivityAdminBinding;
import com.example.floweraplication.databinding.ActivityTypeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TypeActivity extends AppCompatActivity {

    private ActivityTypeBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        
        //configure progress dialog

        progressDialog = new ProgressDialog( this);
        progressDialog.setTitle("Подождите");
        progressDialog. setCanceledOnTouchOutside(false);
        
        //nandle click, begin upload category
        binding.buttonType.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                validateData();
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TypeActivity.this, AdminActivity.class));
            }
        });
    }
    private String name ="";
    private String description ="";
    private void validateData() {
        //Before adding validate data
        // get data
        name = binding.TypeText.getText().toString().trim();
        description = binding.TypeDesText.getText().toString().trim();
        //validate if not empty
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,  "Введите категорию", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description)){
            Toast.makeText(this,  "Введите описание", Toast.LENGTH_SHORT).show();
        }
        else {
            addTypeFirebase();
        }
    }
    private void addTypeFirebase() {
        //show progress
        progressDialog.setMessage("Тип добавляется");
        progressDialog.show();
        //get timestamp
        long timestamp = System.currentTimeMillis();
        //setup info to add in firebase db
        String id = firebaseAuth.getUid();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", ""+name);
        hashMap.put("id", ""+timestamp);
        hashMap.put("description", ""+description);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Type");
        ref.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(TypeActivity.this, "Тип добавлен", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TypeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}