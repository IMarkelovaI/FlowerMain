package com.example.floweraplication.Activity;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.floweraplication.ProfileUser;
import com.example.floweraplication.R;
import com.example.floweraplication.UserPlantRedPlActivity;
import com.example.floweraplication.databinding.ActivityUserPlantDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


public class UserPlantDetailActivity extends AppCompatActivity {

    private ActivityUserPlantDetailBinding binding;
    private FirebaseAuth firebaseAuth;

    TextView PlName,Sun,Height,Width,Description,Watering,Loosening,Transfer, watText, losText, traText, SunT;
    ImageView PlImage;
    ImageButton Redact;

    String Water,Loos,Transf;
    Button last;

    TextView WaterPlant,LoosPlant,TransfPlant;

    String last_day_of_watering, last_day_of_transport,last_day_of_loosening, LightU;
    String loser, watka,trans;

    String abundance_of_watering="", air_hamidity_id = "",fertilizer_id="", optimal_temperature="",soil_type_id="";

    private static final String TAG = "ADD_PLANT_TAG";

    AlarmManager alarmManager;

    private PendingIntent alarmIntent;

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
        SunT=binding.SunText;


        TypedValue typedValue1 = new TypedValue();
        Resources.Theme theme = this.getTheme();
        theme.resolveAttribute(com.google.android.material.R.attr.colorError, typedValue1, true);
        @ColorInt int colorRed = typedValue1.data;

        TypedValue typedValue2 = new TypedValue();
        theme.resolveAttribute(com.google.android.material.R.attr.colorPrimary, typedValue2, true);
        @ColorInt int colorGreen = typedValue2.data;

        Drawable d = ContextCompat.getDrawable(this,R.drawable.tertitoryred);
        Drawable g = ContextCompat.getDrawable(this,R.drawable.tertitory);


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

        //следующее действие
        Watering = binding.Watering;
        Loosening = binding.Loosening;
        Transfer = binding.Transfer;
        Log.e(TAG, "Watering начало on create тупо биндинг "+Watering);
        Log.e(TAG, "Loosening начало on create тупо биндинг "+Loosening);
        Log.e(TAG, "Transfer начало on create тупо биндинг "+Transfer);

        /*if (android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(UserPlantDetailActivity.this, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(UserPlantDetailActivity.this, 0, intent, PendingIntent.FLAG_MUTABLE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 12);
            long timeToStart = calendar.getTimeInMillis();
            if (System.currentTimeMillis() == timeToStart) {
                Log.e(TAG, "Ты кто такой? АААААААААААААААААААААААА Симба");
            }
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeToStart, AlarmManager.INTERVAL_DAY, alarmIntent);
            Log.e(TAG, "Время умирать!!! АААААААААААААААААААААААА");
        }*/

        /*NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            channel = new NotificationChannel(
                    "Test",
                    "Test descr",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(UserPlantDetailActivity.this, "Test")
                    .setContentTitle("Необходим уход за "+name)
                    .setContentText("Настало время ухода за вашим растением")
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .build();
            long temeatamp = System.currentTimeMillis();
            if (System.currentTimeMillis() == temeatamp) {
                Log.e(TAG, "Ты кто такой? АААААААААААААААААААААААА");
                notificationManager.notify(42, notification);
            }
            Log.e(TAG, "Время умирать!!! АААААААААААААААААААААААА");
        }*/

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
                                Log.e(TAG,"last_day_of_loosening загрузка из бд "+last_day_of_loosening);
                                last_day_of_transport = String.valueOf(dataSnapshot.child("last_day_of_transport").getValue());
                                Log.e(TAG,"last_day_of_transport загрузка из бд "+last_day_of_transport);
                                last_day_of_watering = String.valueOf(dataSnapshot.child("last_day_of_watering").getValue());
                                Log.e(TAG,"last_day_of_watering загрузка из бд "+last_day_of_watering);

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
                int day = calendar.get(Calendar.DAY_OF_MONTH);

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
                }, year,month,day);
                dialog.show();
            }
        });
        binding.imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

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
                }, year,month,day);
                dialog.show();
            }
        });
        binding.imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

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
                    }
                        //binding.TransportP.setText(MessageFormat.format("{0}/{1}/{2}", String.valueOf(i2), String.valueOf(i1+1),String.valueOf(i)));

                }, year,month,day);
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

                    LightU = snapshot.child("Light").getValue().toString();
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

                    try
                    {
                        // именно здесь String преобразуется в int
                        int PlantT = Integer.parseInt(LightU.trim());
                        int PlantU = Integer.parseInt(sun.trim());

                        // выведем на экран значение после конвертации
                        System.out.println("int PlantT = " + PlantT);
                        System.out.println("int PlantU = " + PlantU);


                        if ((PlantU<=PlantT+PlantT*0.2 && PlantU>=PlantT-PlantT*0.2)){
                            binding.SunText.setText("Ваше растение получает достаточно освещения");
                            binding.SunText.setTextColor(colorGreen);
                            binding.cl.setBackground(g);

                        }
                        else if (PlantU<PlantT+PlantT*0.2){
                            binding.SunText.setText("Ваше растение не получает достаточного освещения");
                            binding.SunText.setTextColor(colorRed);

                            binding.cl.setBackground(d);
                        }
                        else if (PlantU>PlantT+PlantT*0.2){
                            binding.SunText.setText("Ваше растение получает освещения в избытке");
                            binding.SunText.setTextColor(colorRed);
                            binding.cl.setBackground(d);

                        }

                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println("NumberFormatException: " + nfe.getMessage());
                    }

                    try {
                        int Widht = Integer.parseInt(width.trim());
                        int Height = Integer.parseInt(hight.trim());
                        int sizehpots = Height * 1/3;
                        double d = Widht * 2.5;
                        int diametr = (int)d ;
                        double rad = d/2;
                        double rd2 = (Math.pow(rad,2));
                        double grunt = (Math.PI*rd2*sizehpots)/1000;
                        double gr = Math.round(grunt * 100.0) / 100.0;
                        binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + sizehpots + " см. Максимальный диаметр контейнера должен быть " + diametr +". Приблизительный объем грунта на цилиндрический горшок составляет " + gr + " л. ");
                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println("NumberFormatException: " + nfe.getMessage());
                    }

                    loadW();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.PotsCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PotsCalculateActivity.class);

                int Widht = Integer.parseInt(width.trim());
                int Height = Integer.parseInt(hight.trim());

                intent.putExtra("WidhtP",Widht);
                intent.putExtra("HeightP",Height);

                startActivity(intent);

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
        Drawable ch = ContextCompat.getDrawable(this, R.drawable.datecheck);
        Drawable chok = ContextCompat.getDrawable(this, R.drawable.variantsurv);


        int w = Integer.parseInt(Water);
        int l = Integer.parseInt(Loos);
        int t = Integer.parseInt(Transf);

        Log.e(TAG, "w дни " + w);
        Log.e(TAG, "l дни " + l);
        Log.e(TAG, "t дни " + t);

        watText.setText(Water + " дней");
        losText.setText(Loos + " дней");
        traText.setText(Transf + " дней");

        String loser = binding.LoosP.getText().toString();
        String trans = binding.TransportP.getText().toString();
        String watka = binding.WaterP.getText().toString();
        Log.e(TAG, "Дата прошлого полива  " + binding.WaterP.getText().toString());
        Log.e(TAG, "Дата прошлого полива  " + watka);
        Log.e(TAG, "Дата прошлой пересадки " + trans);
        Log.e(TAG, "Дата прошлого рыхления " + loser);


        DateTimeFormatter df = new DateTimeFormatterBuilder()
                // case insensitive to parse JAN and FEB
                .parseCaseInsensitive()
                // add pattern
                .appendPattern("dd/MM/yyyy")
                // create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH);
        LocalDate d = LocalDate.parse(loser, df);
        Log.e(TAG, "loser " + d);

        long millisecondsSinceEpoch = LocalDate.parse(loser, df)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
        Log.e(TAG, "millisecondsSinceEpoch loser " + millisecondsSinceEpoch);

        DateTimeFormatter df1 = new DateTimeFormatterBuilder()
                // case insensitive to parse JAN and FEB
                .parseCaseInsensitive()
                // add pattern
                .appendPattern("dd/MM/yyyy")
                // create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH);
        LocalDate d1 = LocalDate.parse(watka, df1);
        Log.e(TAG, "watka " + d1);

        long millisecondsSinceEpoch1 = LocalDate.parse(watka, df1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
        Log.e(TAG, "millisecondsSinceEpoch1 watka" + millisecondsSinceEpoch1);

        DateTimeFormatter df2 = new DateTimeFormatterBuilder()
                // case insensitive to parse JAN and FEB
                .parseCaseInsensitive()
                // add pattern
                .appendPattern("dd/MM/yyyy")
                // create formatter (use English Locale to parse month names)
                .toFormatter(Locale.ENGLISH);
        LocalDate d2 = LocalDate.parse(trans, df2);
        Log.e(TAG, "trans " + d2);

        long millisecondsSinceEpoch2 = LocalDate.parse(trans, df2)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
        Log.e(TAG, "millisecondsSinceEpoch2 trans " + millisecondsSinceEpoch2);

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
        Log.e(TAG, "cal.add(Calendar.DAY_OF_YEAR, w) " + cal);
        String dateW = DateFormat.format("dd/MM/yyyy", cal).toString();
        Watering.setText(dateW);
        Log.e(TAG, "Watering следующее " + dateW);

        Calendar cal1 = Calendar.getInstance(Locale.ENGLISH);
        cal1.setTimeInMillis(millisecondsSinceEpoch);
        cal1.add(Calendar.DAY_OF_YEAR, l);
        Log.e(TAG, "cal.add(Calendar.DAY_OF_YEAR, l) " + cal1);
        String dateL = DateFormat.format("dd/MM/yyyy", cal1).toString();
        Loosening.setText(dateL);
        Log.e(TAG, "Loosening следующее " + dateL);

        Calendar cal2 = Calendar.getInstance(Locale.ENGLISH);
        cal2.setTimeInMillis(millisecondsSinceEpoch2);
        cal2.add(Calendar.DAY_OF_YEAR, t);
        Log.e(TAG, "cal.add(Calendar.DAY_OF_YEAR, t) " + cal2);
        String dateT = DateFormat.format("dd/MM/yyyy", cal2).toString();
        Transfer.setText(dateT);
        Log.e(TAG, "Transfer следующее " + dateT);


        long millisecondsSinceEpoch3 = LocalDate.parse(dateL, df)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
        Log.e(TAG, "millisecondsSinceEpoch3 dateL " + millisecondsSinceEpoch3);

        long millisecondsSinceEpoch4 = LocalDate.parse(dateW, df)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
        Log.e(TAG, "millisecondsSinceEpoch4 dateW " + millisecondsSinceEpoch4);

        long millisecondsSinceEpoch5 = LocalDate.parse(dateT, df)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli();
        Log.e(TAG, "millisecondsSinceEpoch5 dateT " + millisecondsSinceEpoch5);


        if ((System.currentTimeMillis() - (60000 * 24 * 60)) > millisecondsSinceEpoch3) {
            Log.e(TAG, "Вы проебали дату рыхления АХАХАХА.Ты попался на кликбейт, олух, олух!!! ");
//Нынешнее время минус сутки (так надо, иначе не работает корректно) - (System.currentTimeMillis() - (60000*24*60))
//millisecondsSinceEpoch3 - запланированная дата рыхления
            binding.cnstcard1.setBackground(ch);
            long LELE = (System.currentTimeMillis() - (60000 * 24 * 60));
            long Lulu = LELE - millisecondsSinceEpoch3;
            Log.e(TAG, "что это такое LELE " + LELE);
            Log.e(TAG, "что это такое Lulu " + Lulu);
            long FFF = Lulu / (60000 * 24 * 60);
            FFF = FFF + 1;
            String wff = Long.toString(FFF);
            String cool = "";
            String prop = "";
            int lastCharacter = (int) FFF % 10;

            if (lastCharacter == 1 && (int) FFF != 11) {
                cool = "день";
                prop = "ропущен";
            } else if (lastCharacter == 2 || lastCharacter == 3 || lastCharacter == 4) {
                cool = "дня";
                prop = "ропущены";
            } else {
                cool = "дней";
                prop = "ропущено";
            }

            Log.e(TAG, " FFFFFFFFFFF " + FFF);
// layout = binding.cnstcard;
//ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) layout.getLayoutParams();
//params.height = 130;
//params.width = 115;
//binding.cnstcard.setLayoutParams(params);
            binding.cnstcardshape1.setVisibility(View.VISIBLE);
            binding.prf.setText("П" + prop + " " + wff + " " + cool + "");
            if (binding.cnstcardshape.getVisibility() == View.VISIBLE || binding.cnstcardshape1.getVisibility() == View.VISIBLE || binding.cnstcardshape2.getVisibility() == View.VISIBLE) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.constraintLayout7.getLayoutParams();
                params.topMargin = 48;
                binding.constraintLayout7.setLayoutParams(params);
            }

        } else {
            binding.cnstcard1.setBackground(chok);
            binding.cnstcardshape1.setVisibility(View.INVISIBLE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.constraintLayout7.getLayoutParams();
            params.topMargin = 0;
            binding.constraintLayout7.setLayoutParams(params);
        }
        if ((System.currentTimeMillis() - (60000 * 24 * 60)) > millisecondsSinceEpoch5) {
            Log.e(TAG, "Вы проебали дату пересадки АХАХАХА. Ты попался на кликбейт, олух, олух!!! ");
            binding.cnstcard2.setBackground(ch);
            long LELE = (System.currentTimeMillis() - (60000 * 24 * 60));
            long Lulu = LELE - millisecondsSinceEpoch5;
            Log.e(TAG, "что это такое LELE " + LELE);
            Log.e(TAG, "что это такое Lulu " + Lulu);
            long FFF = Lulu / (60000 * 24 * 60);
            FFF = FFF + 1;
            String wff = Long.toString(FFF);
            String cool = "";
            String prop = "";
            int lastCharacter = (int) FFF % 10;

            if (lastCharacter == 1 && (int) FFF != 11) {
                cool = "день";
                prop = "ропущен";
            } else if (lastCharacter == 2 || lastCharacter == 3 || lastCharacter == 4) {
                cool = "дня";
                prop = "ропущены";
            } else {
                cool = "дней";
                prop = "ропущено";
            }

            Log.e(TAG, " FFFFFFFFFFF " + FFF);
// layout = binding.cnstcard;
//ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) layout.getLayoutParams();
//params.height = 130;
//params.width = 115;
//binding.cnstcard.setLayoutParams(params);
            binding.cnstcardshape2.setVisibility(View.VISIBLE);
            binding.prt.setText("П" + prop + " " + wff + " " + cool + "");
            if (binding.cnstcardshape.getVisibility() == View.VISIBLE || binding.cnstcardshape1.getVisibility() == View.VISIBLE || binding.cnstcardshape2.getVisibility() == View.VISIBLE) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.constraintLayout7.getLayoutParams();
                params.topMargin = 48;
                binding.constraintLayout7.setLayoutParams(params);
            }
        } else {
            binding.cnstcard2.setBackground(chok);
            binding.cnstcardshape2.setVisibility(View.INVISIBLE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.constraintLayout7.getLayoutParams();
            params.topMargin = 0;
            binding.constraintLayout7.setLayoutParams(params);
        }
        if ((System.currentTimeMillis() - (60000 * 24 * 60)) > millisecondsSinceEpoch4) {
            Log.e(TAG, "Вы проебали дату полива АХАХАХА. Ты попался на кликбейт, олух, олух!!! ");

            binding.cnstcard.setBackground(ch);

            Log.i(TAG, "System.currentTimeMillis()" + (System.currentTimeMillis() -
                    (27000 * 24 * 60 * 60)));
            Log.i(TAG, "System.currentTimeMillis() cimba" + System.currentTimeMillis());
            Log.i(TAG, "System.currentTimeMillis() fffffff " + millisecondsSinceEpoch4);
//- (60000*24*60)
            long LELE = (System.currentTimeMillis() - (60000 * 24 * 60));
            long Lulu = LELE - millisecondsSinceEpoch4;
            Log.e(TAG, "что это такое LELE " + LELE);
            Log.e(TAG, "что это такое Lulu " + Lulu);
            long FFF = Lulu / (60000 * 24 * 60);
            FFF = FFF + 1;
            String wff = Long.toString(FFF);
            String cool = "";
            String prop = "";
            int lastCharacter = (int) FFF % 10;

            if (lastCharacter == 1 && (int) FFF != 11) {
                cool = "день";
                prop = "ропущен";
            } else if (lastCharacter == 2 || lastCharacter == 3 || lastCharacter == 4) {
                cool = "дня";
                prop = "ропущены";
            } else {
                cool = "дней";
                prop = "ропущено";
            }

            Log.e(TAG, " FFFFFFFFFFF " + FFF);
// layout = binding.cnstcard;
//ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) layout.getLayoutParams();
//params.height = 130;
//params.width = 115;
//binding.cnstcard.setLayoutParams(params);
            binding.cnstcardshape.setVisibility(View.VISIBLE);
            binding.prw.setText("П" + prop + " " + wff + " " + cool + "");
            if (binding.cnstcardshape.getVisibility() == View.VISIBLE || binding.cnstcardshape1.getVisibility() == View.VISIBLE || binding.cnstcardshape2.getVisibility() == View.VISIBLE) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.constraintLayout7.getLayoutParams();
                params.topMargin = 48;
                binding.constraintLayout7.setLayoutParams(params);
            }

//с его помощью я понимала диапазон времени, это можешь удалить
/*NotificationChannel channel = null;
if (android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
channel = new NotificationChannel(
"Test",
"Test descr",
NotificationManager.IMPORTANCE_DEFAULT);
NotificationManager notificationManager = getSystemService(NotificationManager.class);
notificationManager.createNotificationChannel(channel);

Notification notification = new NotificationCompat.Builder(UserPlantDetailActivity.this, "Test")
.setContentTitle("Вы проебали дату полива АХАХАХА ")
.setContentText("Ты попался на кликбейт, олух, олух!!!")
.setSmallIcon(R.drawable.baseline_notifications_24)
.build();
Log.e(TAG, "Ты кто такой? АААААААААААААААААААААААА");
notificationManager.notify(42, notification);
Log.e(TAG, "Время умирать!!! АААААААААААААААААААААААА");*/
        } else {
            binding.cnstcard.setBackground(chok);
            binding.cnstcardshape.setVisibility(View.INVISIBLE);
            if (binding.cnstcardshape1.getVisibility() == View.VISIBLE || binding.cnstcardshape2.getVisibility() == View.VISIBLE) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.constraintLayout7.getLayoutParams();
                params.topMargin = 48;
                binding.constraintLayout7.setLayoutParams(params);
            } else {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.constraintLayout7.getLayoutParams();
                params.topMargin = 0;
                binding.constraintLayout7.setLayoutParams(params);
            }

        }
    }
}