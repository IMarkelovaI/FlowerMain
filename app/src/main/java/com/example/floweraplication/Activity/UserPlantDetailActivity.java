package com.example.floweraplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.floweraplication.Fragments.HomeFragment;
import com.example.floweraplication.MyAplication;
import com.example.floweraplication.UserPlantRedPlActivity;
import com.example.floweraplication.databinding.ActivityDobUserPlantBinding;
import com.example.floweraplication.databinding.ActivityUserPlantDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UserPlantDetailActivity extends AppCompatActivity {

    private ActivityUserPlantDetailBinding binding;
    private FirebaseAuth firebaseAuth;

    TextView PlName,Sun,Height,Width,Description,Watering,Loosening,Transfer, watText, losText, traText;
    ImageView PlImage;
    ImageButton Redact;

    String Water,Loos,Transf;
    Button last;

    TextView WaterPlant,LoosPlant,TransfPlant;

    String last_day_of_watering, last_day_of_transport,last_day_of_loosening;
    String loser, watka,trans;

    String abundance_of_watering="", air_hamidity_id = "",fertilizer_id="", optimal_temperature="",soil_type_id="";

    private static final String TAG = "ADD_PLANT_TAG";

    @Override
    public boolean onSupportNavigateUp()
    {
        Intent intent = new Intent(UserPlantDetailActivity.this, UserActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPlantDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar mToolbar = binding.toolbar;
        setSupportActionBar(mToolbar);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Подождите");
        progressDialog. setCanceledOnTouchOutside(false);

        Redact = binding.Redact;
        last = binding.WLT;

        PlName=binding.PlName;
        Sun=binding.Sun;
        Height=binding.Height;
        Width=binding.Width;
        Description=binding.Description;
        PlImage= binding.PlImage;
        watText = binding.watText;
        losText=binding.losText;
        traText=binding.traText;


        Glide.with(UserPlantDetailActivity.this).load(getIntent().getStringExtra("picturePl")).into(PlImage);

        PlName.setText(getIntent().getStringExtra("namePlU"));
        Sun.setText(getIntent().getStringExtra("sunPlU"));
        Height.setText(getIntent().getStringExtra("plant_sizePlU"));
        Width.setText(getIntent().getStringExtra("plant_widthPlU"));
        Description.setText(getIntent().getStringExtra("descriptionPlU"));

        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("idPlU").toString();
        String plant_id = arguments.get("plant_idPlU").toString();
        String image = arguments.get("picturePl").toString();
        String name = arguments.get("namePlU").toString();
        String sun = arguments.get("sunPlU").toString();
        String hight = arguments.get("plant_sizePlU").toString();
        String width = arguments.get("plant_widthPlU").toString();
        String description = arguments.get("descriptionPlU").toString();

        Watering = binding.Watering;
        Loosening = binding.Loosening;
        Transfer = binding.Transfer;

        Log.i(TAG,"KKKKKKKKKKKKKKKKKK"+id);
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users");
        ref2.child(firebaseAuth.getUid()).child("User_plant").child(id).child("Last_care")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            if(dataSnapshot.exists())
                            {
                                last_day_of_loosening = String.valueOf(dataSnapshot.child("last_day_of_loosening").getValue());
                                Log.i(TAG,"AAAAAAAAFFFFFFF"+last_day_of_loosening);
                                last_day_of_transport = String.valueOf(dataSnapshot.child("last_day_of_transport").getValue());
                                last_day_of_watering = String.valueOf(dataSnapshot.child("last_day_of_watering").getValue());

                                binding.WaterP.setText(last_day_of_watering.toString());
                                Log.i(TAG,"AAAAAAAAFFFFFFF"+binding.WaterP);
                                binding.LoosP.setText(last_day_of_loosening.toString());
                                binding.TransportP.setText(last_day_of_transport.toString());

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.Redact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserPlantRedPlActivity.class);

                intent.putExtra("idPlU",id);
                intent.putExtra("descriptionPlU",description);
                intent.putExtra("namePlU",name);
                intent.putExtra("picturePl",image);
                intent.putExtra("plant_idPlU",plant_id);
                intent.putExtra("plant_sizePlU",hight);
                intent.putExtra("plant_widthPlU",width);
                intent.putExtra("sunPlU",sun);
                Log.d(TAG, "Пиздец"+name);

                startActivity(intent);
            }
        });

        binding.WLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLast_care();
            }
        });
        binding.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_YEAR);

                DatePickerDialog dialog = new DatePickerDialog(UserPlantDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int i1, int i2) {
                        if(i2 < 10){
                            if (i1 < 10){
                                i1=i1+1;
                                binding.WaterP.setText("0"+i2 + "/" + "0"+i1 + "/" + i);
                                watka=binding.WaterP.getText().toString();
                            }
                            else {
                                i1=i1+1;
                                binding.WaterP.setText("0"+i2 + "/" + i1 + "/" + i);
                                watka=binding.WaterP.getText().toString();
                            }
                        }
                        else if(i1+1 < 10){
                            i1=i1+1;
                            binding.WaterP.setText(i2 + "/" + "0"+i1 + "/" + i);
                            watka=binding.WaterP.getText().toString();
                        }
                        else {
                            i1=i1+1;
                            binding.WaterP.setText(i2 + "/" + i1 + "/" + i);
                            watka=binding.WaterP.getText().toString();
                        }

                    }
                }, year,month-2,day);
                dialog.show();
            }
        });
        binding.imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_YEAR);

                DatePickerDialog dialog = new DatePickerDialog(UserPlantDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int i1, int i2) {
                        if(i2 < 10){
                            if (i1 < 10){
                                i1=i1+1;
                                binding.LoosP.setText("0"+i2 + "/" + "0"+i1 + "/" + i);
                            }
                            else {
                                i1=i1+1;
                                binding.LoosP.setText("0"+i2 + "/" + i1 + "/" + i);
                            }
                        }
                        else if(i1+1 < 10){
                            i1=i1+1;
                            binding.LoosP.setText(i2 + "/" + "0"+i1 + "/" + i);
                        }
                        else {
                            i1=i1+1;
                            binding.LoosP.setText(i2 + "/" + i1 + "/" + i);
                        }

                    }
                }, year,month-2,day);
                dialog.show();
            }
        });
        binding.imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_YEAR);

                Log.i(TAG, "vaaaaayyyyyy"+binding.TransportP.getText().toString());

                DatePickerDialog dialog = new DatePickerDialog(UserPlantDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int i1, int i2) {

                        if(i2 < 10){
                            if (i1 < 10){
                                i1=i1+1;
                                binding.TransportP.setText("0"+i2 + "/" + "0"+i1 + "/" + i);
                            }
                            else {
                                i1=i1+1;
                                binding.TransportP.setText("0"+i2 + "/" + i1 + "/" + i);
                            }
                        }
                        else if(i1+1 < 10){
                            i1=i1+1;
                            binding.TransportP.setText(i2 + "/" + "0"+i1 + "/" + i);
                        }
                        else {
                            i1=i1+1;
                            binding.TransportP.setText(i2 + "/" + i1 + "/" + i);
                        }
                        //binding.TransportP.setText(MessageFormat.format("{0}/{1}/{2}", String.valueOf(i2), String.valueOf(i1+1),String.valueOf(i)));

                    }
                }, year,month-2,day);
                dialog.show();
            }
        });

        Log.i(TAG, "AAAAAAAAAAAAAAAAAAAAA"+plant_id);

        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Plant").child(plant_id);
        ref1.child("Optimal_condition").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Water = snapshot.child("watering_time").getValue().toString();
                    Loos = snapshot.child("loosening_time").getValue().toString();
                    Transf = snapshot.child("transfer_time").getValue().toString();

                    Log.i(TAG, "Uriririri"+Water.toString());
                    Log.i(TAG, "Uririririddddddddddd"+Loos.toString());
                    Log.i(TAG, "Uririririaaaaaaaaaa"+Transf.toString());

                    abundance_of_watering=snapshot.child("abundance_of_watering").getValue().toString();
                    air_hamidity_id = snapshot.child("air_hamidity_id").getValue().toString();
                    fertilizer_id=snapshot.child("fertilizer_id").getValue().toString();
                    optimal_temperature=snapshot.child("optimal_temperature").getValue().toString();
                    soil_type_id=snapshot.child("soil_type_id").getValue().toString();
                    binding.abundanceOfWatering.setText(abundance_of_watering);
                    binding.airHumidityId.setText(air_hamidity_id);
                    binding.fertilizerId.setText(fertilizer_id);
                    binding.optimalTemperature.setText(optimal_temperature);
                    binding.soilTypeId.setText(soil_type_id);
                    loadW();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateLast_care() {
        Bundle arguments = getIntent().getExtras();
        String id = arguments.get("idPlU").toString();
        loser = binding.LoosP.getText().toString();
        trans = binding.TransportP.getText().toString();
        watka = binding.WaterP.getText().toString();
        Log.i(TAG, "wotkaaaaaaaaa "+watka);


        //setup data to save
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("id", ""+id);
        hashMap.put("last_day_of_loosening", ""+loser);
        hashMap.put("last_day_of_transport", ""+trans);
        hashMap.put("last_day_of_watering", ""+watka);


        //save to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("User_plant").child(id).child("Last_care").child(id)
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UserPlantDetailActivity.this, "Последняя дата ухода изменена", Toast.LENGTH_SHORT).show();
                        loadW();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }
    private void loadW() {

        int w = Integer.parseInt(Water);
        int l = Integer.parseInt(Loos);
        int t = Integer.parseInt(Transf);
        watText.setText(Water+" дней");
        losText.setText(Loos+" дней");
        traText.setText(Transf+" дней");
        Log.i(TAG, "hhhhhhhhhhhhhhhhhhhhhh tttt "+t);

        String loser = binding.LoosP.getText().toString();
        String trans = binding.TransportP.getText().toString();
        String watka = binding.WaterP.getText().toString();
        Log.i(TAG, "hhhhhhhhhhhhhhhhhhhhhh watka fffff "+binding.WaterP.getText().toString());
        Log.i(TAG, "hhhhhhhhhhhhhhhhhhhhhh watka "+watka);
        Log.i(TAG, "hhhhhhhhhhhhhhhhhhhhhh trans "+trans);
        Log.i(TAG, "hhhhhhhhhhhhhhhhhhhhhh loser "+loser);


        DateTimeFormatter df = new DateTimeFormatterBuilder()
                // case insensitive to parse JAN and FEB
                .parseCaseInsensitive()
                // add pattern
                .appendPattern("dd/MM/yyyy")
                // create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH);
        LocalDate d = LocalDate.parse(loser,df);
        Log.i(TAG, "vjvjvjvkcvlx;s" +d);

        long millisecondsSinceEpoch = LocalDate.parse(loser, df)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();

        //Log.i(TAG, "vjvjvjvkcvlx;s" +millisecondsSinceEpoch);

        DateTimeFormatter df1 = new DateTimeFormatterBuilder()
                // case insensitive to parse JAN and FEB
                .parseCaseInsensitive()
                // add pattern
                .appendPattern("dd/MM/yyyy")
                // create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH);
        LocalDate d1 = LocalDate.parse(watka,df1);
        Log.i(TAG, "vjvjvjvkcvlx;s" +d1);

        long millisecondsSinceEpoch1 = LocalDate.parse(watka, df1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();

        DateTimeFormatter df2 = new DateTimeFormatterBuilder()
                // case insensitive to parse JAN and FEB
                .parseCaseInsensitive()
                // add pattern
                .appendPattern("dd/MM/yyyy")
                // create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH);
        LocalDate d2 = LocalDate.parse(trans,df2);
        Log.i(TAG, "vjvjvjvkcvlx;s" +d2);

        long millisecondsSinceEpoch2 = LocalDate.parse(trans, df2)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();


        //Date r = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());

        /*String date = "04/11/1972";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate d = LocalDate.parse(loser,formatter);
        Log.i(TAG, "vjvjvjvkcvlx;s" +d);
        //System.out.println(formatter.format(formatter.toString(d));

        Date r = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());*/



        long timestamp = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(millisecondsSinceEpoch1);
        cal.add(Calendar.DAY_OF_YEAR, w);
        String dateW = DateFormat.format("dd/MM/yyyy", cal).toString();
        Watering.setText(dateW);

        Calendar cal1 = Calendar.getInstance(Locale.ENGLISH);
        cal1.setTimeInMillis(millisecondsSinceEpoch);
        cal1.add(Calendar.DAY_OF_YEAR, l);
        String dateL = DateFormat.format("dd/MM/yyyy", cal1).toString();
        Loosening.setText(dateL);

        Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
        cal2.setTimeInMillis(millisecondsSinceEpoch2);
        cal2.add(Calendar.DAY_OF_YEAR, t);
        String dateT = DateFormat.format("dd/MM/yyyy", cal2).toString();
        Transfer.setText(dateT);

    }
}