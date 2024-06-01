package com.example.floweraplication;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floweraplication.ml.Predictdisease;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class photopredictor extends AppCompatActivity {

    FloatingActionButton camera, folder, add;
    Animation open,close,forward,backword;
    boolean isOpen = false;
    TextView TextDisease, TextDiseaseAcc, caption;
    ImageView imagePredictDisease;

    ImageButton arrowPredict;

    private static final String TAG = "ADD_PLANT_TAG";

    ConstraintLayout seccnt;


    byte[] bytes;
    Uri uri;
    Uri pa;
    Bitmap bitmap;

    int imageSize = 224;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopredictor);


        Toolbar toolbar = findViewById(R.id.toolbar_predict);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextDisease = findViewById(R.id.TextDisease);
        TextDiseaseAcc = findViewById(R.id.TextDiseaseAcc);
        imagePredictDisease = findViewById(R.id.imagePredictDisease);
        caption = findViewById(R.id.captionPredictDisease);
        arrowPredict = findViewById(R.id.arrowpredict);

        seccnt = findViewById(R.id.seccnt);
        add = findViewById(R.id.add);
        camera = findViewById(R.id.camera);
        folder = findViewById(R.id.folder);
        open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        close = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        forward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        backword = AnimationUtils.loadAnimation(this, R.anim.rotate_backword);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateAdd();
            }
        });

        imagePredictDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    //Request camera permission if we don't have it.
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
//здесь вылетает при выборе из галереи
        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent,1);
            }
        });

        arrowPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(photopredictor.this, DizActivity.class);

                i.putExtra("Bitmap", bytes);
                i.putExtra("PlName",TextDisease.getText().toString());
                i.putExtra("PictureP",imagePredictDisease.toString());
                Log.i(TAG, "Пиздец aaaaaaaa "+uri.toString());
                i.putExtra("Pa", uri.toString());
                startActivity(i);
                Log.i(TAG, "Пиздец jjjjjjj "+uri.toString());
            }
        });
    }

    public void classifyImage(Bitmap image){
        try {
            Predictdisease model = Predictdisease.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4*imageSize*imageSize*3);
            byteBuffer.order(ByteOrder.nativeOrder());
            int [] intValues = new int[imageSize*imageSize];
            image.getPixels(intValues, 0, image.getWidth(),0,0,image.getWidth(),image.getHeight());
            int pixel =0;
            for(int i = 0; i<imageSize;i++){
                for(int j=0;j<imageSize;j++){
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val>>16)&0xFF)*(1.f/255.f));
                    byteBuffer.putFloat(((val>>8)&0xFF)*(1.f/255.f));
                    byteBuffer.putFloat((val & 0xFF)*(1.f/255.f));
                }
            }


            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Predictdisease.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos =0;
            float maxConfidence = 0;
            for(int i=0; i<confidences.length;i++){
                if (confidences[i]>maxConfidence){
                    maxConfidence=confidences[i];
                    maxPos=i;
                }
            }
            //вот здесь захват типа растения
            String[] classes = {"Здоровое растение","Ржавчина","Мучнистая роса","Парша","Фитофтороз","Бактериальное заболевание",
                    "Вирусное заболевание","Грибковое заболевание"};
            TextDisease.setText(classes[maxPos]);

            String s = "";
            for (int i=0;i<classes.length;i++) {
                s += String.format("%s: %.1f%%\n", classes[i],confidences[i]*100);
            }

            TextDiseaseAcc.setText(s);




            if(TextDisease.getText()!="Здоровое растение"){
                seccnt.setBackgroundResource(R.drawable.error_container);
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = this.getTheme();
                theme.resolveAttribute(com.google.android.material.R.attr.colorError, typedValue, true);
                @ColorInt int color = typedValue.data;
                TextDisease.setTextColor(color);
                Drawable drawable = ContextCompat.getDrawable(this,R.drawable.exclamation_mark);
               drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.6),
                        (int) (drawable.getIntrinsicHeight() * 0.6));
               ScaleDrawable sd = new ScaleDrawable(drawable, 0, 40, 40);


                TextDisease.setCompoundDrawables(null, null, sd.getDrawable(), null);
                arrowPredict.setVisibility(View.VISIBLE);
                arrowPredict.setColorFilter(color);

            }
            else{
               seccnt.setBackgroundResource(R.drawable.secondary_container);
                arrowPredict.setVisibility(View.GONE);
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = this.getTheme();
                theme.resolveAttribute(com.google.android.material.R.attr.colorOnPrimaryContainer, typedValue, true);
                @ColorInt int color = typedValue.data;
                TextDisease.setTextColor(color);
            }
            caption.setVisibility(View.GONE);


            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == 3) {
                //uri = data.getData();
                Bitmap image = (Bitmap) data.getExtras().get("data");

                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imagePredictDisease.setImageBitmap(image);

                //assert image != null;
                //bitmap = Bitmap.createScaledBitmap(image, 359,359,false);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap = image;
                bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), image,"IMG_" + System.currentTimeMillis(),null);
                Uri pa = Uri.parse(path);
                uri = pa;
                Log.w(TAG, "Ты не пройдешь "+uri);
                bytes = stream.toByteArray();
                imagePredictDisease.setAlpha(1f);
                imagePredictDisease.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String picture = imagePredictDisease.toString();
                Log.d(TAG, "ALLLLLPP "+uri);


                Log.w(TAG, "Жопа осла"+data.getData());
            }
            else {
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imagePredictDisease.setImageBitmap(image);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap = image;
                bitmap.compress(Bitmap.CompressFormat.PNG,100, stream);
                bytes = stream.toByteArray();
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap,"IMG_" + System.currentTimeMillis(),null);
                Uri pa = Uri.parse(path);
                uri = pa;
                //assert image != null;
                //bitmap = Bitmap.createScaledBitmap(image, 359,359,false);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);

                imagePredictDisease.setAlpha(1f);
                imagePredictDisease.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String picture = imagePredictDisease.toString();

                Log.i(TAG, "Пиздец"+dat);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        animateAdd();
    }



    private void animateAdd(){
        if (isOpen){
            add.startAnimation(forward);
            camera.startAnimation(close);
            folder.startAnimation(close);
            camera.setClickable(false);
            folder.setClickable(false);
            isOpen=false;
        }
        else{
            add.startAnimation(backword);
            camera.startAnimation(open);
            folder.startAnimation(open);
            camera.setClickable(true);
            folder.setClickable(true);
            isOpen=true;
        }
    }
}
