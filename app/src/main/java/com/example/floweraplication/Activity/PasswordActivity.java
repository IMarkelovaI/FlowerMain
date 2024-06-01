package com.example.floweraplication.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class PasswordActivity extends AppCompatActivity {

    private ActivityPasswordBinding binding;
    Toolbar toolbar;
    FirebaseAuth auth;
    private static final String TAG = "ADD_PLANT_TAG";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential credential = EmailAuthProvider
                        .getCredential(auth.getInstance().getCurrentUser().getEmail(), binding.OldPass.getText().toString());
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d(TAG, "User re-authenticated.");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updatePassword(binding.NewPass.getText().toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(PasswordActivity.this, "Password updated!", Toast.LENGTH_LONG).show();

                                                }else {

                                                }
                                            }

                                        });
                            }
                        });
            }
        });
    }
}
