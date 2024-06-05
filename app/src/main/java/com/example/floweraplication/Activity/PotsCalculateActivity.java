package com.example.floweraplication.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.floweraplication.databinding.ActivityPotsCalculateBinding;
import com.example.floweraplication.databinding.ActivityUserPlantDetailBinding;

public class PotsCalculateActivity extends AppCompatActivity {
    private ActivityPotsCalculateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPotsCalculateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
