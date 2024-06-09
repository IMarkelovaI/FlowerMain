package com.example.floweraplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.floweraplication.databinding.ActivityAddPurposeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddPurposeActivity extends AppCompatActivity {

    private ActivityAddPurposeBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPurposeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog( this);
        progressDialog.setTitle("Подождите");
        progressDialog. setCanceledOnTouchOutside(false);

        binding.buttonPurpose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                validateData();
            }
        });
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPurposeActivity.this, PurposeActivity.class));
            }
        });
    }
    private String name ="";
    private String description ="";
    private void validateData() {

        name = binding.PurposeText.getText().toString().trim();
        description = binding.PurposeDesText.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,  "Введите предназначение", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(description)){
            Toast.makeText(this,  "Введите описание", Toast.LENGTH_SHORT).show();
        }
        else {
            addPurposeFirebase();
        }
    }
    private void addPurposeFirebase() {

        progressDialog.setMessage("Предназначение добавляется");
        progressDialog.show();

        long timestamp = System.currentTimeMillis();

        String id = firebaseAuth.getUid();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", ""+name);
        hashMap.put("id", ""+timestamp);
        hashMap.put("description", ""+description);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Purpose");
        ref.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(AddPurposeActivity.this, "Предназначение добавлено", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AddPurposeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}