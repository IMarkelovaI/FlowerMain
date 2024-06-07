package com.example.floweraplication;

import static android.app.PendingIntent.getActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.floweraplication.Activity.AuthActivity;
import com.example.floweraplication.Activity.DobUserPlantActivity;
import com.example.floweraplication.Activity.PasswordActivity;
import com.example.floweraplication.Activity.UserActivity;
import com.example.floweraplication.databinding.ActivityProfileuserBinding;
import com.example.floweraplication.databinding.ActivityUserPlantRedPlBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.annotation.Nullable;


public class ProfileUser extends AppCompatActivity {
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    boolean nightMODE;
    SharedPreferences.Editor editor;
    private ActivityProfileuserBinding binding;
    private StorageReference storageReference;
    private StorageTask uploadTask;
    MaterialSwitch materialSwitch;
    private static final String TAG = "ADD_PLANT_TAG";
    private static final int PNG_PICK_CODE =1000;
    Bitmap bitmap;
    Dialog dialog;

    FirebaseAuth auth;
    Button logOut;
    FloatingActionButton RedactPic;
    TextView loginP, emailP, delete;
    ImageView picture;
    ProgressDialog progressDialog;

    private Uri imageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileuserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        loadUserInfo();
        loginP = binding.loginUser2;
        emailP = binding.loginUser;
        picture = binding.imageView3;
        logOut = binding.Logout;
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileUser.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        binding.Redact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                pngPickIntent();
            }
        });

        binding.DeleteRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ProfileUser.this);
                dialog.setContentView(R.layout.row_exit);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                Button close = dialog.findViewById(R.id.button4);
                Button Yas = dialog.findViewById(R.id.button2);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Yas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle arguments = getIntent().getExtras();

                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Users");
                        ref1.child(auth.getUid())
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ProfileUser.this, "Пользователь удален", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(ProfileUser.this, AuthActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(ProfileUser.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                    }
                });
                dialog.show();
            }
        });

        binding.ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileUser.this, PasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadUserInfo(){
        Log.d(TAG, "Загрузка пользовательских данных..."+auth.getUid());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email =""+snapshot.child("email").getValue();
                String id =""+snapshot.child("id").getValue();
                String name =""+snapshot.child("name").getValue();
                String profileImage =""+snapshot.child("profileImage").getValue();
                String userType =""+snapshot.child("userType").getValue();

                loginP.setText(name);
                emailP.setText(email);
                if (profileImage != "")
                {
                    //Uri uri = Uri.parse("profileImage");
                    Log.i(TAG,"ебучий криптонит!!!!"+profileImage);

                    RequestOptions options = new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL);

                    Glide.with(getApplicationContext())
                            .load((profileImage).toString())
                            .apply(options)
                            .into(picture);
                }

                reference.child(auth.getUid()).child("User_plant").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String s =""+snapshot.getChildrenCount();
                        Log.e(TAG,"s каунт "+s);
                        binding.textView35.setText(s);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                imageUri = data.getData();
                Log.d(TAG, "onActivityResult: URI: " + imageUri);

                imageUri = data.getData();
                Log.d(TAG, "onActivityResult: URI: " + imageUri);
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            if (imageUri != null){
                binding.imageView3.setImageBitmap(bitmap);
            }
            else {

                Uri uri = Uri.parse(getIntent().getStringExtra("pngView"));
                imageUri = uri;
            }
        }
        else {
            Log.d(TAG, "onActivityResult: cancelled picking png");
            Toast.makeText(this, "Отменен выбор изображения", Toast.LENGTH_SHORT).show();
        }
        updateUser();
    }

    /*private void pngPickIntent() {
        PopupMenu popupMenu = new PopupMenu(this, binding.Redact);
        popupMenu.getMenu().add(Menu.NONE, 0, 0, "Camera");
        popupMenu.getMenu().add(Menu.NONE, 1, 1, "Gallery");
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int which = item.getItemId();
                if (which==0){
                    pickImageCamera();
                } else if (which==1) {
                    pickImageGallery();
                }
                return false;
            }
        });
    }

    private void pickImageCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Pick");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Sample Image Description");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(Intent.createChooser (intent, "Select PNG"), PNG_PICK_CODE);
        //cameraActivityResultLauncher.launch(intent);
    }
    private void pickImageGallery() {
        Log.d(TAG, "pngPickIntent: starting png pick intent");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent. setType("image/*");
        startActivityForResult(Intent.createChooser (intent, "Select PNG"), PNG_PICK_CODE);
        //galleryActivityResultLauncher.launch(intent);

        Log.d(TAG, "pngPickIntent: starting png pick intent");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser (intent, "Select PNG"), PNG_PICK_CODE);
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PNG_PICK_CODE) {
                Log.d(TAG, "onActivityResult: PNG Picked");
                imageUri = data.getData();
                Log.d(TAG, "onActivityResult: URI: " + imageUri);
                //binding.imageView3.setImageURI(imageUri);
            }
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            if (imageUri != null){
                binding.imageView3.setImageBitmap(bitmap);
            }
            else {

                Uri uri = Uri.parse(getIntent().getStringExtra("pngView"));
                imageUri = uri;
            }
        }
        else {
            Log.d(TAG, "onActivityResult: cancelled picking png");
            Toast.makeText(this, "Отменен выбор изображения", Toast.LENGTH_SHORT).show();
        }
        updateUser();
    }*/

    /*private ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d(TAG, "onActivityResult: " + imageUri);
                        Intent data = result.getData();
                        //binding.imageView3.setImageURI(data.getData());
                        Log.e(TAG, "Камераааааааааа: " + imageUri);
                        updateUser();
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d(TAG, "onActivityResult: " + imageUri);
                        Intent data = result.getData();
                        imageUri = data.getData();
                        Log.d(TAG, "onActivityResult: : " + imageUri);
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                        if (imageUri != null){
                            binding.imageView3.setImageBitmap(bitmap);
                        }
                        else {

                            Uri uri = Uri.parse(getIntent().getStringExtra("pngView"));
                            imageUri = uri;
                        }

                        //binding.imageView3.setImageURI(imageUri);
                        Log.e(TAG, "Галереяяяяяяяя: " + imageUri);
                        updateUser();
                    }
                }
            }
    );*/

    private void updateUser(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("users/"+fileName+".png");
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //get url of uploaded image
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadImageUri = uriTask.getResult();

                        if (uriTask.isSuccessful()){
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("profileImage", ""+downloadImageUri);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                            databaseReference.child(auth.getUid())
                                    .updateChildren(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(ProfileUser.this, "Изображение успешно обновлено", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfileUser.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
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