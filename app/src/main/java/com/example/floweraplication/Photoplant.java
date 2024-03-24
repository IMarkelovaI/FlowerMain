package com.example.floweraplication;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.floweraplication.Activity.DobUserPlantActivity;
import com.example.floweraplication.ml.ModelPlant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Photoplant extends AppCompatActivity {

    FloatingActionButton camera, folder, add;
    Animation open,close,forward,backword;
    boolean isOpen = false;
    TextView TextDisease, TextDiseaseAcc, caption, textView;
    ImageView imagePredictDisease;

    byte[] bytes;
    ImageButton arrowPredict;
    Uri uri;
    Uri dat;

    Bitmap bitmap;
    Button AddPlant;
    public static  String IMAGE_RES_ID_KEY = "IMAGE_RES_ID_KEY";

    private static final String TAG = "ADD_PLANT_TAG";

    ConstraintLayout seccnt;


    Uri pa;

    int imageSize = 224;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoplant);



        Toolbar toolbar = findViewById(R.id.toolbar_predict);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextDisease = findViewById(R.id.TextDisease);
        AddPlant = findViewById(R.id.AddPlant);
        textView = findViewById(R.id.textView2);

        imagePredictDisease = findViewById(R.id.imagePredictDisease);
        caption = findViewById(R.id.captionPredictDisease);


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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlant.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                TextDisease.setText("");
                caption.setVisibility(View.VISIBLE);
                imagePredictDisease.setImageResource(R.drawable.ic_camera_grin);
                imagePredictDisease.setAlpha(0.3f);
            }
        });
        AddPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(getApplicationContext(), ActivityPhotoplantDob.class);


                //BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                //Bitmap bitmap = BitmapFactory.decodeFile();
                //imagePredictDisease.buildDrawingCache();
                //Bitmap bitmap = imagePredictDisease.getDrawingCache();

                Intent i = new Intent(Photoplant.this, ActivityPhotoplantDob.class);

                //i.putExtra("ImageUri",dat);
                //BitmapDrawable bitmapDrawable = ((BitmapDrawable) imagePredictDisease.getDrawable());
                //Bitmap b = bitmapDrawable.getBitmap();
                //Bundle extras = new Bundle();
                //extras.putParcelable("Image",b);

                i.putExtra("Bitmap", bytes);
                i.putExtra("PlName",TextDisease.getText().toString());
                i.putExtra("PictureP",imagePredictDisease.toString());
                Log.i(TAG, "Пиздец aaaaaaaa "+uri.toString());
                i.putExtra("Pa", uri.toString());
                startActivity(i);
                Log.i(TAG, "Пиздец jjjjjjj "+uri.toString());
                //ByteArra
                //Log.d(TAG, "Пиздец"+bitmap);
                //startActivity(intent);


            }
        });
    }

    /*public Uri getUri (Bitmap image, Context context){
        File Images = new File(context.getCodeCacheDir(), "images");
        Uri uri = null;
        try {
            Images.mkdirs();
            File file = new File(Images,"capha.jpg");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return uri;
    }*/

    public void classifyImage(Bitmap image){


        try {
            ModelPlant model = ModelPlant.newInstance(getApplicationContext());

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
            ModelPlant.Outputs outputs = model.process(inputFeature0);
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
            String[] classes = {"Орхидея","Драцена","Замиокулькас","Сансервиерия","Каладиум","Антуриум",
                    "Фаленопсис приятный (Орхидея альбинос)","Тилландсия","Гусмания язычковая","Пафиопедилум (Венерин башмачок)", "Зантедеския эфиопская (Калла)",
                    "Амараллис", "Адениум", "Ипомея", "Гербера", "Клематис (Ломонос)", "Молочай красивейший", "Сурфиния", "Цикламен", "Плюмерия", "Фиалка",
                    "Эхинопсис (Кактус)", "Мирсина африканская", "Хавортия", "Роза", "Эхеверия Жемчужина Нюрнберга", "Седум Буррито (Очиток ослика)", "Алоэ",
                    "Маммиллярия перистая (Кактус)", "Маммиллярия парноколючковая (Кактус)"};
            TextDisease.setText(classes[maxPos]);

            caption.setVisibility(View.GONE);
            AddPlant.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);


            //Тут TextDisease хранит название растения

            String name = textView.toString();


            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Intent i = new Intent(Photoplant.this, ActivityPhotoplantDob.class);
        if (resultCode==RESULT_OK) {
            if (requestCode == 3) {
                uri = data.getData();
                Bitmap image = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap = image;
                bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap,"val",null);
                Uri pa = Uri.parse(path);
                uri = pa;
                Log.w(TAG, "Ты не пройдешь "+uri);
                bytes = stream.toByteArray();
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imagePredictDisease.setImageBitmap(image);

                //assert image != null;
                //bitmap = Bitmap.createScaledBitmap(image, 359,359,false);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
                imagePredictDisease.setAlpha(1f);
                imagePredictDisease.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String picture = imagePredictDisease.toString();
                Log.d(TAG, "ALLLLLPP "+uri);

                i.putExtra("Picture",uri);
                i.putExtra("PlName",TextDisease.getText().toString());
                Log.w(TAG, "Жопа осла"+data.getData());

            }
            else {
                Uri dat = data.getData();
                Log.d(TAG,"dat fffffffffffff"+dat);
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                imagePredictDisease.setImageBitmap(image);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap = image;
                bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
                bytes = stream.toByteArray();
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap,"val",null);
                Uri pa = Uri.parse(path);
                uri = pa;
                //assert image != null;
                //bitmap = Bitmap.createScaledBitmap(image, 359,359,false);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);

                imagePredictDisease.setAlpha(1f);
                imagePredictDisease.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String picture = imagePredictDisease.toString();
                i.putExtra("Picture",dat);
                i.putExtra("PlName",TextDisease.getText().toString());
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