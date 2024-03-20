package com.example.floweraplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.floweraplication.DobUserPlantActivity;
import com.example.floweraplication.databinding.ActivityAuthBinding;
import com.example.floweraplication.models.ModelUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;

    private FirebaseAuth firebaseAuth;
    public SharedPreferences apppref;
    public static final String APP_PREFERENCES = "apppref";

    private ProgressDialog progressDialog;
    public ModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Подождите немного");
        progressDialog.setCanceledOnTouchOutside(false);

        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        binding.linkToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthActivity.this, MainActivity.class));
            }
        });

       binding.buttonAuth.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               validateDate();
           }
       });
    }

    private String email ="", password = "";

    private void validateDate() {

        email = binding.emailUserAuth.getText().toString() .trim();
        password = binding.loginPassAuth.getText().toString().trim();

        if (password.isEmpty()) {
            binding.loginPassAuthLayout.setError("Пароль не может быть пустым");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email) .matches()){
        binding.emailUserAuthLayout.setError("Неверный шаблон электронной почты или ее не заполнили");
        }
        else {
            loginUser();
        }
    }

    private void loginUser() {
        progressDialog.setMessage("Вход в аккаунт");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        checkUser();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AuthActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private void checkUser() {

        progressDialog.setMessage("Поиск пользователя");

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ref.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        progressDialog.dismiss();
                        //get user type
                        String userType = ""+snapshot.child("userType").getValue();

                        String id = ""+snapshot.child("id").getValue();

                        SharedPreferences.Editor editor = apppref.edit();
                        editor.clear();

                        //check user type
                        if (userType.equals("user")){
                            //this is simple user, open user dashboard
                            startActivity(new Intent(AuthActivity.this, UserActivity.class)) ;
                            editor.putString("mAppIUD", id);
                            editor.apply();
                            finish();
                        }

                        else if (userType.equals("admin")){
                            //this is admin, open admin dashboard
                            startActivity(new Intent(AuthActivity.this, AdminButonsActivity.class)) ;
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }
}