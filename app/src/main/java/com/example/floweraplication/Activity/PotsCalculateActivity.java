package com.example.floweraplication.Activity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.floweraplication.R;
import com.example.floweraplication.databinding.ActivityPotsCalculateBinding;
import com.example.floweraplication.databinding.ActivityUserPlantDetailBinding;

public class PotsCalculateActivity extends AppCompatActivity {
    private ActivityPotsCalculateBinding binding;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPotsCalculateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
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
                    binding.Height.setVisibility(View.VISIBLE);
                    binding.Diameter.setVisibility(View.VISIBLE);
                    binding.UserPlantDiameter.setVisibility(View.VISIBLE);
                    binding.UserPlantHeight.setVisibility(View.VISIBLE);
                    try {
                        Bundle arguments = getIntent().getExtras();
                        String WidhtP = arguments.get("WidhtP").toString();
                        String HeightP = arguments.get("HeightP").toString();

                        int Widht = Integer.parseInt(WidhtP.trim());
                        int Height = Integer.parseInt(HeightP.trim());
                        int sizehpots = Height * 1/3;

                        String si = String.valueOf(sizehpots);
                        binding.Height.setText(si);
                        double d = Widht * 2.5;
                        int diametr = (int)d;

                        String di = String.valueOf(diametr);
                        binding.Diameter.setText(di);
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
                    binding.Height.setVisibility(View.VISIBLE);
                    binding.UserPlantHeight.setVisibility(View.VISIBLE);
                    binding.Width.setVisibility(View.VISIBLE);
                    binding.UserPlantWidth.setVisibility(View.VISIBLE);

                    try {
                        Bundle arguments = getIntent().getExtras();
                        String WidhtP = arguments.get("WidhtP").toString();
                        String HeightP = arguments.get("HeightP").toString();

                        int Widht = Integer.parseInt(WidhtP.trim());
                        int Height = Integer.parseInt(HeightP.trim());
                        int sizehpots = Height * 1/3;

                        String si = String.valueOf(sizehpots);
                        binding.Height.setText(si);
                        double d = Widht * 2.5;
                        int diametr = (int)d;

                        String di = String.valueOf(diametr);
                        binding.Width.setText(di);

                        double gr = diametr*diametr*sizehpots;
                        binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + sizehpots + " см. Максимальная ширина контейнера должена быть " + diametr +". Приблизительный объем грунта на прямоугольный горшок составляет " + gr + " л. ");

                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println("NumberFormatException: " + nfe.getMessage());
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

                    binding.constraintLayout9.setVisibility(View.VISIBLE);
                    binding.PotText.setVisibility(View.VISIBLE);
                    binding.Diameter.setVisibility(View.VISIBLE);
                    binding.UserPlantDiameter.setVisibility(View.VISIBLE);
                    binding.Height.setVisibility(View.VISIBLE);
                    binding.UserPlantHeight.setVisibility(View.VISIBLE);
                    binding.DiameterSmall.setVisibility(View.VISIBLE);
                    binding.UserPlantDiameterSmall.setVisibility(View.VISIBLE);


                    //binding.cylinder.setSelected(false);
                    binding.cylinder.setChecked(false);
                    try {
                        Bundle arguments = getIntent().getExtras();
                        String WidhtP = arguments.get("WidhtP").toString();
                        String HeightP = arguments.get("HeightP").toString();

                        int Widht = Integer.parseInt(WidhtP.trim());
                        int Height = Integer.parseInt(HeightP.trim());
                        int sizehpots = Height * 1/3;

                        String si = String.valueOf(sizehpots);
                        binding.Height.setText(si);
                        double d = Widht * 2.5;
                        double ds = d*2/3;
                        int diametr = (int)d;
                        int diametrSm = (int)ds;
                        String di = String.valueOf(diametr);
                        String dis = String.valueOf(diametrSm);
                        binding.Diameter.setText(di);
                        binding.DiameterSmall.setText(dis);

                        double gr = Math.PI/3*(Math.pow(diametr,2) + Math.pow(diametrSm,2) + (Math.sqrt(Math.pow(diametr,2) * Math.pow(diametrSm,2))))*sizehpots;
                        binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + sizehpots + " см. Максимальный диаметр верхнего основания контейнера должена быть " + diametr +".Минимальный диаметр нижнего основания контейнера должена быть " + diametrSm +". Приблизительный объем грунта на конусообразный горшок составляет " + gr + " л. ");
                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println("NumberFormatException: " + nfe.getMessage());
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

                    binding.constraintLayout9.setVisibility(View.VISIBLE);
                    binding.PotText.setVisibility(View.VISIBLE);
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

                    //binding.cylinder.setSelected(false);
                    binding.cylinder.setChecked(false);
                    try {
                        Bundle arguments = getIntent().getExtras();
                        String WidhtP = arguments.get("WidhtP").toString();
                        String HeightP = arguments.get("HeightP").toString();

                        int Widht = Integer.parseInt(WidhtP.trim());
                        int Height = Integer.parseInt(HeightP.trim());
                        int sizehpots = Height * 1/3;

                        String si = String.valueOf(sizehpots);
                        binding.Height.setText(si);
                        double d = Widht * 2.5;
                        double ds = d*2/3;
                        int diametr = (int)d;
                        int diametrSm = (int)ds;
                        String di = String.valueOf(diametr);
                        String dis = String.valueOf(diametrSm);
                        binding.Width.setText(di);
                        binding.WidthSmall.setText(dis);
                        double l = diametr + diametr/4;
                        double ls = diametrSm + diametrSm/4;
                        int length = (int)l;
                        int lengthSm = (int)ls;
                        String li = String.valueOf(length);
                        String lis = String.valueOf(lengthSm);
                        binding.Length.setText(li);
                        binding.LengthSmall.setText(lis);
                        Double S1 = d*l;
                        Double S2 = ds*ls;
                        double gr = sizehpots/6*(S1 + S2 + (Math.sqrt(S1 * S2)));
                        binding.PotText.setText("Оптимальная высота горшка для вашего растения должна быть не менее " + sizehpots + " см. Максимальная ширина верхнего основания контейнера должена быть " + diametr +".Минимальная ширина нижнего основания контейнера должена быть " + diametrSm +".Максимальная длина верхнего основания контейнера должена быть " + length +".Минимальная длина нижнего основания контейнера должена быть " + lengthSm +". Приблизительный объем грунта на четырехугольный горшок составляет " + gr + " л. ");

                    }
                    catch (NumberFormatException nfe)
                    {
                        System.out.println("NumberFormatException: " + nfe.getMessage());
                    }
                }
            }
        });
        //тут расчет при нажатии на кнопку расчета
        //не работает :(
        //ебучие радиобатоны, их создатель хотел видеть слезы разработчиков и наслаждаться проклятиями с их стороны!!!!
        //бляяяяяяяяять
        //сука!!!
        binding.calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.cylinder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if(isChecked){
                            //цилиндр
                            binding.rectangle.setChecked(false);
                            binding.quadrilateral.setChecked(false);
                            binding.cone.setChecked(false);
                            Log.d(TAG, "Цилиндр ");
                            try {
                                String Height = binding.Height.getText().toString();
                                int sizehpots = Integer.parseInt (Height);
                                String Dia = binding.Diameter.getText().toString();
                                int diametr = Integer.parseInt (Dia);

                                double rad = diametr/2;
                                double rd2 = (Math.pow(rad,2));
                                double grunt = (Math.PI*rd2*sizehpots)/1000;
                                double gr = Math.round(grunt * 100.0) / 100.0;
                                binding.PotText.setText("Приблизительный объем грунта на цилиндрический горшок составляет " + gr + " л. ");
                            }
                            catch (NumberFormatException nfe)
                            {
                                System.out.println("NumberFormatException: " + nfe.getMessage());
                            }
                        }
                    }
                });

                binding.rectangle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if(isChecked){
                            //Прямоугольник
                            binding.cylinder.setChecked(false);
                            binding.quadrilateral.setChecked(false);
                            binding.cone.setChecked(false);
                            Log.d(TAG, "Прямоугольник ");
                            try {
                                String Height = binding.Height.getText().toString();
                                int sizehpots = Integer.parseInt (Height);
                                String Dia = binding.Width.getText().toString();
                                int diametr = Integer.parseInt (Dia);

                                double gr = diametr*diametr*sizehpots;
                                binding.PotText.setText("Приблизительный объем грунта на прямоугольный горшок составляет " + gr + " л. ");

                            }
                            catch (NumberFormatException nfe)
                            {
                                System.out.println("NumberFormatException: " + nfe.getMessage());
                            }
                        }
                    }
                });

                binding.cone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                        if(isChecked){
                            //Конус
                            binding.cylinder.setChecked(false);
                            binding.rectangle.setChecked(false);
                            binding.quadrilateral.setChecked(false);
                            Log.d(TAG, "Конус ");
                            binding.cylinder.setChecked(false);
                            try {
                                String Height = binding.Height.getText().toString();
                                int sizehpots = Integer.parseInt (Height);

                                String Dia = binding.Diameter.getText().toString();
                                int diametr = Integer.parseInt (Dia);

                                String DiamS = binding.DiameterSmall.getText().toString();
                                int diametrSm = Integer.parseInt (DiamS);

                                double gr = Math.PI/3*(Math.pow(diametr,2) + Math.pow(diametrSm,2) + (Math.sqrt(Math.pow(diametr,2) * Math.pow(diametrSm,2))))*sizehpots;
                                binding.PotText.setText("Приблизительный объем грунта на конусообразный горшок составляет " + gr + " л. ");
                            }
                            catch (NumberFormatException nfe)
                            {
                                System.out.println("NumberFormatException: " + nfe.getMessage());
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
                            binding.cylinder.setChecked(false);
                            try {
                                String Height = binding.Height.getText().toString();
                                int sizehpots = Integer.parseInt (Height);

                                String Dia = binding.Width.getText().toString();
                                int Widht = Integer.parseInt (Dia);
                                double d = Widht * 2.5;
                                int diametr = (int)d;

                                String DiamS = binding.WidthSmall.getText().toString();
                                int diametrSm = Integer.parseInt (DiamS);

                                String Le = binding.Length.getText().toString();
                                int length = Integer.parseInt (Le);

                                String LeS = binding.LengthSmall.getText().toString();
                                int lengthSm = Integer.parseInt (LeS);

                                int S1 = diametr*length;
                                int S2 = diametrSm*lengthSm;
                                double gr = sizehpots/6*(S1 + S2 + (Math.sqrt(S1 * S2)));
                                binding.PotText.setText("Приблизительный объем грунта на четырехугольный горшок составляет " + gr + " л. ");

                            }
                            catch (NumberFormatException nfe)
                            {
                                System.out.println("NumberFormatException: " + nfe.getMessage());
                            }
                        }
                    }
                });
            }
        });
    }
}
