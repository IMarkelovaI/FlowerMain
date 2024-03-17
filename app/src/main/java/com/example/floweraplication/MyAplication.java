package com.example.floweraplication;

import android.app.Application;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.Context;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.zip.DataFormatException;

public class MyAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static final String formatTimestamp(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }
    /*public static void addToFlUser(Context context, String plantid){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()==null){
            Toast.makeText(context, "Вы не авторизованы",Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id",""+id);
            hashMap.put("timestamp",""+timestamp);

        }
    }*/
}
