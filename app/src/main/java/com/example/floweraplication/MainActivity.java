package com.example.floweraplication;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.floweraplication.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Подождите немного");
        progressDialog.setCanceledOnTouchOutside(false);


        //nandle click, begin register
        binding.buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        binding.linkToAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AuthActivity.class));
            }
        });
    }

    private String name ="", email ="", password = "";
    private void validateData() {

        //Before creating account, lets do some data validationx/
        // get data

        name = binding.loginUser.getText().toString().trim();
        email = binding.loginEmail.getText().toString() .trim();
        password = binding.loginPass.getText().toString().trim();
        String repeatLoginPass = binding.repeatLoginPass.getText().toString().trim();
        //validate data
        if (name.isEmpty()) {
            binding.loginUser.setError("Логин не может быть пустым");
        }
        if (password.isEmpty()) {
            binding.loginPass.setError("Пароль не может быть пустым");
        }
        if (repeatLoginPass.isEmpty()) {
            binding.repeatLoginPass.setError("Повторите пароль");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.loginEmail.setError("Неверный шаблон электронной почты или ее не заполнили");
        }
        else{
            createUserAccount();
        }
    }

    private void createUserAccount() {
        //show progress
        progressDialog.setMessage("Создание аккаунта");
        progressDialog.show();

        //create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account creation success, now add in firebase realtime database
                        updateUserInfo();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        //account creating failed
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateUserInfo() {
        progressDialog.setMessage("Сохранение информации пользователя");
        long temeatamp = System.currentTimeMillis();

        String id = firebaseAuth.getUid();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("id", id);
        hashMap.put("email", email);
        hashMap.put("name", name);
        hashMap.put("profileImage", "");//add empty, will do later
        hashMap.put("userType", "user");//add empty, will do later

        //set data to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users") ;
        ref.child(id)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //data added to db
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Акаунт создан", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                    finish();

            }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        //data failed adding to db
                        progressDialog.dismiss();
                        Toast .makeText(MainActivity.this, ""+e.getMessage(), Toast. LENGTH_SHORT).show();
            }
                });


    }
}