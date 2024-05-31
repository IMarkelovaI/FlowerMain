package com.example.floweraplication.Activity;

import static java.util.regex.Pattern.matches;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.floweraplication.ProfileUser;
import com.example.floweraplication.databinding.ActivityPasswordBinding;
import com.example.floweraplication.databinding.ActivityProfileuserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PasswordActivity extends AppCompatActivity {

    private ActivityPasswordBinding binding;
    Toolbar toolbar;
    FirebaseAuth auth;
    private static final String TAG = "ADD_PLANT_TAG";
    private ProgressDialog progressDialog;

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES = "apppref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser.equals("")){
            Toast.makeText(PasswordActivity.this, "Что-то случилось, перезайдите в аккаунт", Toast.LENGTH_LONG).show();
            startActivity(new Intent(PasswordActivity.this, ProfileUser.class));
            finish();
        }

        binding.buttonChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateDate();
            }
        });
    }
    private String passwordOld = "",passwordNew = "",passwordRepeatNew = "";
    private void validateDate() {
        passwordOld = binding.OldPass.getText().toString() .trim();
        passwordNew = binding.NewPass.getText().toString() .trim();
        passwordRepeatNew = binding.NewPassRep.getText().toString() .trim();

        Log.i(TAG, "OOOOOOOOO"+passwordOld);
        Log.i(TAG, "SSSSSSSSSSSSSS"+passwordNew);
        Log.i(TAG, "Uriririri"+passwordRepeatNew);

        if (passwordOld.isEmpty()) {
            binding.OldPass.setError("Введите старый пароль");
        }
        else {
            if (passwordNew.isEmpty()) {
                binding.NewPass.setError("Введите новый пароль");
            }
            else if (passwordNew.length()<6) {
                binding.NewPass.setError("Введите минимум 6 символов");
            }
            if (passwordRepeatNew.isEmpty()) {
                binding.NewPassRep.setError("Повторно введите новый пароль");
            }
            else if (!Objects.equals(passwordRepeatNew, passwordNew)){
                binding.NewPassRep.setError("Пароли не совпадают!");
            }
            else if (passwordNew.equals(passwordOld)) {
                binding.NewPass.setError("Вы ввели старый пароль");
            }
            else {
                updateUserInfo();
            }
        }
    }

    private void updateUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "FFFFFFFFFFFFFFF"+ user);

            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), passwordOld);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User re-authenticated.");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(binding.NewPass.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(PasswordActivity.this, "Пароль обновлен", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(PasswordActivity.this, ProfileUser.class));
                                                    finish();

                                                }
                                                else {
                                                    try {
                                                        task.getException();
                                                    }
                                                    catch (Exception e){
                                                        Toast .makeText(PasswordActivity.this, ""+e.getMessage(), Toast. LENGTH_SHORT).show();
                                                    }

                                                }
                                            }

                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast .makeText(PasswordActivity.this, ""+e.getMessage(), Toast. LENGTH_SHORT).show();
                                            }
                                        });
                            }
                            else {
                                binding.OldPass.setError("Вы ввели не тот пароль");
                                /*try {
                                    task.getException();
                                }
                                catch (Exception e){
                                    Toast .makeText(PasswordActivity.this, ""+e.getMessage(), Toast. LENGTH_SHORT).show();
                                }*/
                            }
                        }
                    });

    }
}
