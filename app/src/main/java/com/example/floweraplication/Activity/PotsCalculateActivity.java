package com.example.floweraplication.Activity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.floweraplication.R;
import com.example.floweraplication.databinding.ActivityPotsCalculateBinding;
import com.example.floweraplication.databinding.ActivityUserPlantDetailBinding;

public class PotsCalculateActivity extends AppCompatActivity {
    private ActivityPotsCalculateBinding binding;
    Toolbar toolbar;

    String hP = "", Lp = "", lp = "", Dp ="",dp ="", Bp= "", bp = "";

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPotsCalculateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.Height.setVisibility(View.GONE);
        binding.UserPlantHeight.setVisibility(View.GONE);
        binding.Diameter.setVisibility(View.GONE);
        binding.UserPlantDiameter.setVisibility(View.GONE);
        binding.Width.setVisibility(View.GONE);
        binding.UserPlantWidth.setVisibility(View.GONE);
        binding.DiameterSmall.setVisibility(View.GONE);
        binding.UserPlantDiameterSmall.setVisibility(View.GONE);
        binding.WidthSmall.setVisibility(View.GONE);
        binding.UserPlantWidthSmall.setVisibility(View.GONE);
        binding.constraintLayout9.setVisibility(View.GONE);
        binding.PotText.setVisibility(View.GONE);
        binding.Length.setVisibility(View.GONE);
        binding.UserPlantLength.setVisibility(View.GONE);
        binding.LengthSmall.setVisibility(View.GONE);
        binding.UserPlantLengthSmall.setVisibility(View.GONE);
        binding.calculate.setVisibility(View.GONE);

        binding.cylinder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    //цилиндр
                    Log.d(TAG, "Цилиндр ");
                    binding.rectangle.setChecked(false);
                    binding.quadrilateral.setChecked(false);
                    binding.cone.setChecked(false);
                    binding.Width.setVisibility(View.GONE);
                    binding.UserPlantWidth.setVisibility(View.GONE);
                    binding.DiameterSmall.setVisibility(View.GONE);
                    binding.UserPlantDiameterSmall.setVisibility(View.GONE);
                    binding.WidthSmall.setVisibility(View.GONE);
                    binding.UserPlantWidthSmall.setVisibility(View.GONE);
                    binding.Length.setVisibility(View.GONE);
                    binding.UserPlantLength.setVisibility(View.GONE);
                    binding.LengthSmall.setVisibility(View.GONE);
                    binding.UserPlantLengthSmall.setVisibility(View.GONE);

                    binding.constraintLayout9.setVisibility(View.VISIBLE);
                    binding.PotText.setVisibility(View.VISIBLE);
                    binding.calculate.setVisibility(View.VISIBLE);
                    binding.Height.setVisibility(View.VISIBLE);
                    binding.UserPlantHeight.setVisibility(View.VISIBLE);
                    binding.Diameter.setVisibility(View.VISIBLE);
                    binding.UserPlantDiameter.setVisibility(View.VISIBLE);

                    if (binding.UserPlantDiameter.getVisibility() == View.VISIBLE ) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.calculate.getLayoutParams();
                        params.topMargin = 264;
                        binding.calculate.setLayoutParams(params);
                    }

                    try {
                        Bundle arguments = getIntent().getExtras();
                        String WidhtP = arguments.get("WidhtP").toString();
                        String HeightP = arguments.get("HeightP").toString();

                        double Widht = Double.parseDouble(WidhtP.trim());
                        double Height = Double.parseDouble(HeightP.trim());
                        double sizehpots = Height * 1/3;
                        double s = Math.round(sizehpots * 100.0) / 100.0;
                        String si = String.valueOf(s);
                        binding.Height.setText(si);
                        double d = Widht * 2.5;
                        double diametr = (double)d;
                        double dia = Math.round(diametr * 100.0) / 100.0;
                        String di = String.valueOf(dia);

                        binding.Diameter.setText(di);
                        double rad = d/2;
                        double rd2 = (Math.pow(rad,2));
                        double grunt = (Math.PI*rd2*sizehpots)/1000;
                        double gr = Math.round(grunt * 100.0) / 100.0;
                        binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + s + " см. Максимальный диаметр контейнера должен быть " + dia +" см. Приблизительный объем грунта на цилиндрический горшок составляет " + gr + " л. ");
                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println("Формат ошибки: " + nfe.getMessage());
                    }
                }
            }
        });

        binding.rectangle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    //Прямоугольник
                    Log.d(TAG, "Прямоугольник ");
                    //binding.cylinder.setSelected(false);
                    binding.cylinder.setChecked(false);
                    binding.quadrilateral.setChecked(false);
                    binding.cone.setChecked(false);
                    binding.Diameter.setVisibility(View.GONE);
                    binding.UserPlantDiameter.setVisibility(View.GONE);
                    binding.DiameterSmall.setVisibility(View.GONE);
                    binding.UserPlantDiameterSmall.setVisibility(View.GONE);
                    binding.WidthSmall.setVisibility(View.GONE);
                    binding.UserPlantWidthSmall.setVisibility(View.GONE);
                    binding.Length.setVisibility(View.GONE);
                    binding.UserPlantLength.setVisibility(View.GONE);
                    binding.LengthSmall.setVisibility(View.GONE);
                    binding.UserPlantLengthSmall.setVisibility(View.GONE);

                    binding.constraintLayout9.setVisibility(View.VISIBLE);
                    binding.PotText.setVisibility(View.VISIBLE);
                    binding.calculate.setVisibility(View.VISIBLE);
                    binding.Height.setVisibility(View.VISIBLE);
                    binding.UserPlantHeight.setVisibility(View.VISIBLE);
                    binding.Width.setVisibility(View.VISIBLE);
                    binding.UserPlantWidth.setVisibility(View.VISIBLE);

                    if (binding.UserPlantWidth.getVisibility() == View.VISIBLE ) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.calculate.getLayoutParams();
                        params.topMargin = 16;
                        binding.calculate.setLayoutParams(params);
                    }

                    try {
                        Bundle arguments = getIntent().getExtras();
                        String WidhtP = arguments.get("WidhtP").toString();
                        String HeightP = arguments.get("HeightP").toString();

                        double Widht = Double.parseDouble(WidhtP.trim());
                        double Height = Double.parseDouble(HeightP.trim());
                        double sizehpots = Height * 1/3;
                        double s = Math.round(sizehpots * 100.0) / 100.0;
                        String si = String.valueOf(s);
                        binding.Height.setText(si);
                        double d = Widht * 2.5;
                        double diametr = (double) d;
                        double dia = Math.round(diametr * 100.0) / 100.0;
                        String di = String.valueOf(dia);
                        binding.Width.setText(di);
                        Log.e(TAG, "diametr " +diametr);
                        Log.e(TAG, "sizehpots " +sizehpots);
                        double grunt = (diametr*diametr*sizehpots);
                        grunt = grunt/1000;
                        Log.e(TAG, "grunt" +grunt);
                        double gr = Math.round(grunt *100.0) / 100.0;
                        Log.e(TAG, "gr" +gr);
                        binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + s + " см. Максимальная ширина контейнера должна быть " + dia +" см. Приблизительный объем грунта на прямоугольный горшок составляет " + gr + " л. ");
                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println("Формат ошибки: " + nfe.getMessage());
                    }
                }
            }
        });

        binding.cone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    //Конус
                    Log.d(TAG, "Конус ");
                    binding.cylinder.setChecked(false);
                    binding.rectangle.setChecked(false);
                    binding.quadrilateral.setChecked(false);
                    binding.Width.setVisibility(View.GONE);
                    binding.UserPlantWidth.setVisibility(View.GONE);
                    binding.WidthSmall.setVisibility(View.GONE);
                    binding.UserPlantWidthSmall.setVisibility(View.GONE);
                    binding.Length.setVisibility(View.GONE);
                    binding.UserPlantLength.setVisibility(View.GONE);
                    binding.LengthSmall.setVisibility(View.GONE);
                    binding.UserPlantLengthSmall.setVisibility(View.GONE);

                    binding.constraintLayout9.setVisibility(View.GONE);
                    binding.PotText.setVisibility(View.GONE);
                    binding.calculate.setVisibility(View.VISIBLE);
                    binding.Diameter.setVisibility(View.VISIBLE);
                    binding.UserPlantDiameter.setVisibility(View.VISIBLE);
                    binding.Height.setVisibility(View.VISIBLE);
                    binding.UserPlantHeight.setVisibility(View.VISIBLE);
                    binding.DiameterSmall.setVisibility(View.VISIBLE);
                    binding.UserPlantDiameterSmall.setVisibility(View.VISIBLE);

                    if (binding.UserPlantDiameterSmall.getVisibility() == View.VISIBLE ) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.calculate.getLayoutParams();
                        params.topMargin = 528;
                        binding.calculate.setLayoutParams(params);
                    }

                    try {
                        Bundle arguments = getIntent().getExtras();
                        String WidhtP = arguments.get("WidhtP").toString();
                        String HeightP = arguments.get("HeightP").toString();

                        double Widht = Double.parseDouble(WidhtP.trim());
                        double Height = Double.parseDouble(HeightP.trim());
                        double sizehpots = Height * 1/3;
                        double s = Math.round(sizehpots * 100.0) / 100.0;
                        String si = String.valueOf(s);
                        binding.Height.setText(si);
                        double d = Widht * 2.5;
                        double diametr = (double)d;
                        double dia = Math.round(diametr * 100.0) / 100.0;
                        String di = String.valueOf(dia);
                        binding.Diameter.setText(di);
                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println("Формат ошибки: " + nfe.getMessage());
                    }
                }
            }
        });
        binding.quadrilateral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    //Четырёхугольник
                    Log.d(TAG, "Четырёхугольник ");
                    binding.cylinder.setChecked(false);
                    binding.rectangle.setChecked(false);
                    binding.cone.setChecked(false);
                    binding.Diameter.setVisibility(View.GONE);
                    binding.UserPlantDiameter.setVisibility(View.GONE);
                    binding.DiameterSmall.setVisibility(View.GONE);
                    binding.UserPlantDiameterSmall.setVisibility(View.GONE);

                    binding.constraintLayout9.setVisibility(View.GONE);
                    binding.PotText.setVisibility(View.GONE);
                    binding.calculate.setVisibility(View.VISIBLE);
                    binding.Height.setVisibility(View.VISIBLE);
                    binding.UserPlantHeight.setVisibility(View.VISIBLE);
                    binding.Width.setVisibility(View.VISIBLE);
                    binding.UserPlantWidth.setVisibility(View.VISIBLE);
                    binding.WidthSmall.setVisibility(View.VISIBLE);
                    binding.UserPlantWidthSmall.setVisibility(View.VISIBLE);
                    binding.Length.setVisibility(View.VISIBLE);
                    binding.UserPlantLength.setVisibility(View.VISIBLE);
                    binding.LengthSmall.setVisibility(View.VISIBLE);
                    binding.UserPlantLengthSmall.setVisibility(View.VISIBLE);

                    if (binding.UserPlantLengthSmall.getVisibility() == View.VISIBLE ) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.calculate.getLayoutParams();
                        params.topMargin = 16;
                        binding.calculate.setLayoutParams(params);
                    }

                    try {
                        Bundle arguments = getIntent().getExtras();
                        String WidhtP = arguments.get("WidhtP").toString();
                        String HeightP = arguments.get("HeightP").toString();

                        double Widht = Double.parseDouble(WidhtP.trim());
                        double Height = Double.parseDouble(HeightP.trim());
                        double sizehpots = Height * 1/3;
                        double s = Math.round(sizehpots * 100.0) / 100.0;
                        String si = String.valueOf(s);
                        binding.Height.setText(si);
                        double d = Widht * 2.5;
                        double diametr = (double)d;
                        double dia = Math.round(diametr * 100.0) / 100.0;
                        String di = String.valueOf(dia);
                        binding.Width.setText(di);

                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println("Формат ошибки: " + nfe.getMessage());
                    }
                }
            }
        });

        binding.calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(binding.cylinder.isChecked()){
                            //цилиндр
                            Log.d(TAG, "Цилиндр ");
                            binding.rectangle.setChecked(false);
                            binding.quadrilateral.setChecked(false);
                            binding.cone.setChecked(false);
                            binding.Width.setVisibility(View.GONE);
                            binding.UserPlantWidth.setVisibility(View.GONE);
                            binding.DiameterSmall.setVisibility(View.GONE);
                            binding.UserPlantDiameterSmall.setVisibility(View.GONE);
                            binding.WidthSmall.setVisibility(View.GONE);
                            binding.UserPlantWidthSmall.setVisibility(View.GONE);
                            binding.Length.setVisibility(View.GONE);
                            binding.UserPlantLength.setVisibility(View.GONE);
                            binding.LengthSmall.setVisibility(View.GONE);
                            binding.UserPlantLengthSmall.setVisibility(View.GONE);

                            binding.constraintLayout9.setVisibility(View.VISIBLE);
                            binding.PotText.setVisibility(View.VISIBLE);
                            binding.calculate.setVisibility(View.VISIBLE);
                            binding.Height.setVisibility(View.VISIBLE);
                            binding.UserPlantHeight.setVisibility(View.VISIBLE);
                            binding.Diameter.setVisibility(View.VISIBLE);
                            binding.UserPlantDiameter.setVisibility(View.VISIBLE);

                             hP = binding.Height.getText().toString().trim();
                             Dp = binding.Diameter.getText().toString().trim();

                            if (TextUtils.isEmpty(hP)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите высоту горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else if (TextUtils.isEmpty(Dp)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите диаметр горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else {
                                try {
                                    String Height = binding.Height.getText().toString();
                                    double sizehpots = Double.parseDouble (Height);
                                    double s = Math.round(sizehpots * 100.0) / 100.0;
                                    Log.d(TAG, "sizehpots " + sizehpots);
                                    String Dia = binding.Diameter.getText().toString();
                                    double diametr = Double.parseDouble (Dia);
                                    double dia = Math.round(diametr * 100.0) / 100.0;
                                    Log.d(TAG, "diametr " + diametr);
                                    double rad = diametr/2;
                                    double rd2 = (Math.pow(rad,2));
                                    double grunt = (Math.PI*rd2*sizehpots)/1000;
                                    Log.e(TAG, "grunt " + grunt);
                                    double gr = Math.round(grunt * 100.0) / 100.0;
                                    Log.e(TAG, "gr " + gr);
                                    binding.constraintLayout9.setVisibility(View.VISIBLE);
                                    binding.PotText.setVisibility(View.VISIBLE);
                                    binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + s + " см. Максимальный диаметр контейнера должен быть " + dia +" см. Приблизительный объем грунта на цилиндрический горшок составляет " + gr + " л. ");
                                }
                                catch (NumberFormatException nfe)
                                {
                                    System.out.println("Формат ошибки: " + nfe.getMessage());
                                }
                            }
                        }
                        if(binding.rectangle.isChecked()){
                            //Прямоугольник
                            Log.d(TAG, "Прямоугольник ");
                            binding.cylinder.setChecked(false);
                            binding.quadrilateral.setChecked(false);
                            binding.cone.setChecked(false);
                            binding.Diameter.setVisibility(View.GONE);
                            binding.UserPlantDiameter.setVisibility(View.GONE);
                            binding.DiameterSmall.setVisibility(View.GONE);
                            binding.UserPlantDiameterSmall.setVisibility(View.GONE);
                            binding.WidthSmall.setVisibility(View.GONE);
                            binding.UserPlantWidthSmall.setVisibility(View.GONE);
                            binding.Length.setVisibility(View.GONE);
                            binding.UserPlantLength.setVisibility(View.GONE);
                            binding.LengthSmall.setVisibility(View.GONE);
                            binding.UserPlantLengthSmall.setVisibility(View.GONE);

                            binding.constraintLayout9.setVisibility(View.VISIBLE);
                            binding.PotText.setVisibility(View.VISIBLE);
                            binding.calculate.setVisibility(View.VISIBLE);
                            binding.Height.setVisibility(View.VISIBLE);
                            binding.UserPlantHeight.setVisibility(View.VISIBLE);
                            binding.Width.setVisibility(View.VISIBLE);
                            binding.UserPlantWidth.setVisibility(View.VISIBLE);

                            hP = binding.Height.getText().toString().trim();
                            Bp = binding.Width.getText().toString().trim();


                            if (TextUtils.isEmpty(hP)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите высоту горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else if (TextUtils.isEmpty(Bp)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите ширину горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else {
                                try {
                                    String Height = binding.Height.getText().toString();
                                    double sizehpots = Double.parseDouble (Height);
                                    double s = Math.round(sizehpots * 100.0) / 100.0;
                                    Log.e(TAG, "sizehpots " + sizehpots);
                                    String Dia = binding.Width.getText().toString();
                                    double diametr = Double.parseDouble (Dia);
                                    double dia = Math.round(diametr * 100.0) / 100.0;
                                    Log.e(TAG, "diametr " + diametr);

                                    double grunt = (diametr*diametr*sizehpots);
                                    grunt = grunt/1000;
                                    double gr = Math.round(grunt *100.0) / 100.0;
                                    Log.e(TAG, "gr " + gr);
                                    binding.constraintLayout9.setVisibility(View.VISIBLE);
                                    binding.PotText.setVisibility(View.VISIBLE);
                                    binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + s + " см. Максимальная ширина контейнера должна быть " + dia +" см. Приблизительный объем грунта на прямоугольный горшок составляет " + gr + " л. ");

                                }
                                catch (NumberFormatException nfe)
                                {
                                    System.out.println("Формат ошибки: " + nfe.getMessage());
                                }
                            }
                        }
                        if(binding.cone.isChecked()){
                            //Конус
                            Log.d(TAG, "Конус");
                            binding.cylinder.setChecked(false);
                            binding.rectangle.setChecked(false);
                            binding.quadrilateral.setChecked(false);
                            binding.Width.setVisibility(View.GONE);
                            binding.UserPlantWidth.setVisibility(View.GONE);
                            binding.WidthSmall.setVisibility(View.GONE);
                            binding.UserPlantWidthSmall.setVisibility(View.GONE);
                            binding.Length.setVisibility(View.GONE);
                            binding.UserPlantLength.setVisibility(View.GONE);
                            binding.LengthSmall.setVisibility(View.GONE);
                            binding.UserPlantLengthSmall.setVisibility(View.GONE);

                            binding.constraintLayout9.setVisibility(View.VISIBLE);
                            binding.PotText.setVisibility(View.VISIBLE);
                            binding.calculate.setVisibility(View.VISIBLE);
                            binding.Diameter.setVisibility(View.VISIBLE);
                            binding.UserPlantDiameter.setVisibility(View.VISIBLE);
                            binding.Height.setVisibility(View.VISIBLE);
                            binding.UserPlantHeight.setVisibility(View.VISIBLE);
                            binding.DiameterSmall.setVisibility(View.VISIBLE);
                            binding.UserPlantDiameterSmall.setVisibility(View.VISIBLE);

                            hP = binding.Height.getText().toString().trim();
                            Dp = binding.Diameter.getText().toString().trim();
                            dp = binding.DiameterSmall.getText().toString().trim();


                            if (TextUtils.isEmpty(hP)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите высоту горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else if (TextUtils.isEmpty(Dp)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите верхний диаметр горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else if (TextUtils.isEmpty(dp)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите нижний диаметр горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else {
                                try {
                                    String Height = binding.Height.getText().toString();
                                    double sizehpots = Double.parseDouble (Height);
                                    double s = Math.round(sizehpots * 100.0) / 100.0;
                                    Log.e(TAG, "sizehpots "+ sizehpots);

                                    String Dia = binding.Diameter.getText().toString();
                                    double diametr = Double.parseDouble (Dia);
                                    double dia = Math.round(diametr * 100.0) / 100.0;
                                    double diametr2 = (Math.pow(diametr,2));

                                    String DiamS = binding.DiameterSmall.getText().toString();
                                    double diametrSm = Double.parseDouble (DiamS);
                                    double diam = Math.round(diametrSm * 100.0) / 100.0;
                                    double diametrSm2 = (Math.pow(diametrSm,2));

                                    double rad1 = diametr/2;
                                    double rad2 = (Math.pow(rad1,2));
                                    Log.e(TAG, "rd2 "+ rad2);
                                    double radSm1 = diametrSm/2;
                                    double radSm2 = (Math.pow(radSm1,2));
                                    Log.e(TAG, "rdSm2 "+ radSm2);

                                    double grunt = (Math.PI*sizehpots*(rad2 + rad1 * radSm1 + radSm2))/3000;
                                    Log.e(TAG, "grunt " + grunt);
                                    double gr = Math.round(grunt *100.0) / 100.0;
                                    Log.e(TAG, "gr "+ gr);
                                    binding.constraintLayout9.setVisibility(View.VISIBLE);
                                    binding.PotText.setVisibility(View.VISIBLE);
                                    binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + s + " см. Максимальный верхний диаметр контейнера должен быть " + dia +" см., малый диаметр  "+diam+" см. Приблизительный объем грунта на конусообразный горшок составляет " + gr + " л. ");
                                }
                                catch (NumberFormatException nfe)
                                {
                                    System.out.println("Формат ошибки: " + nfe.getMessage());
                                }
                            }
                        }
                        if(binding.quadrilateral.isChecked()){
                            //Четырёхугольник
                            Log.d(TAG, "Четырёхугольник ");
                            binding.cylinder.setChecked(false);
                            binding.rectangle.setChecked(false);
                            binding.cone.setChecked(false);
                            binding.Diameter.setVisibility(View.GONE);
                            binding.UserPlantDiameter.setVisibility(View.GONE);
                            binding.DiameterSmall.setVisibility(View.GONE);
                            binding.UserPlantDiameterSmall.setVisibility(View.GONE);

                            binding.constraintLayout9.setVisibility(View.VISIBLE);
                            binding.PotText.setVisibility(View.VISIBLE);
                            binding.calculate.setVisibility(View.VISIBLE);
                            binding.Height.setVisibility(View.VISIBLE);
                            binding.UserPlantHeight.setVisibility(View.VISIBLE);
                            binding.Width.setVisibility(View.VISIBLE);
                            binding.UserPlantWidth.setVisibility(View.VISIBLE);
                            binding.WidthSmall.setVisibility(View.VISIBLE);
                            binding.UserPlantWidthSmall.setVisibility(View.VISIBLE);
                            binding.Length.setVisibility(View.VISIBLE);
                            binding.UserPlantLength.setVisibility(View.VISIBLE);
                            binding.LengthSmall.setVisibility(View.VISIBLE);
                            binding.UserPlantLengthSmall.setVisibility(View.VISIBLE);

                            hP = binding.Height.getText().toString().trim();
                            Bp = binding.Width.getText().toString().trim();
                            bp = binding.WidthSmall.getText().toString().trim();
                            Lp = binding.Length.getText().toString().trim();
                            lp = binding.LengthSmall.getText().toString().trim();


                            if (TextUtils.isEmpty(hP)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите высоту горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else if (TextUtils.isEmpty(Bp)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите верхнюю ширину горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else if (TextUtils.isEmpty(bp)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите нижнюю ширину горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else if (TextUtils.isEmpty(Lp)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите верхнюю длину горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else if (TextUtils.isEmpty(lp)){
                                Toast.makeText(PotsCalculateActivity.this,  "Введите нижнюю длину горшка", Toast.LENGTH_SHORT).show();
                                binding.constraintLayout9.setVisibility(View.GONE);
                                binding.PotText.setVisibility(View.GONE);
                            }
                            else {
                                try {
                                    String Height = binding.Height.getText().toString();
                                    double sizehpots = Double.parseDouble (Height);
                                    double s = Math.round(sizehpots * 100.0) / 100.0;

                                    String Dia = binding.Width.getText().toString();
                                    double diametr = Double.parseDouble (Dia);
                                    double dia = Math.round(diametr * 100.0) / 100.0;

                                    String DiamS = binding.WidthSmall.getText().toString();
                                    double diametrSm = Double.parseDouble (DiamS);
                                    double diam = Math.round(diametrSm * 100.0) / 100.0;

                                    String Le = binding.Length.getText().toString();
                                    double length = Double.parseDouble (Le);
                                    double le = Math.round(length * 100.0) / 100.0;

                                    String LeS = binding.LengthSmall.getText().toString();
                                    double lengthSm = Double.parseDouble (LeS);
                                    double len = Math.round(lengthSm * 100.0) / 100.0;

                                    double S = ((diametr*length)+(diametrSm*lengthSm))/2;
                                    double grunt = (S*sizehpots)/1000;
                                    Log.e(TAG, "grunt " + grunt);
                                    double gr = Math.round(grunt *100.0) / 100.0;
                                    Log.e(TAG, "gr " + gr);
                                    binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + s + " см. Максимальная ширина верхней стороны контейнера " +
                                            "должна быть " + dia +" см., малая ширина "+diam+" см. Максимальная длина верхней стороны горшка должна быть " + le +" см., малая длина "+len+" см. Приблизительный объем грунта на черырехугольного горшка составляет " + gr + " л. ");

                                }
                                catch (NumberFormatException nfe)
                                {
                                    System.out.println("Формат ошибки: " + nfe.getMessage());
                                }
                            }
                        }
            }
        });
    }
}
