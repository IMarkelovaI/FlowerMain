package com.example.floweraplication.adapters;

import static com.example.floweraplication.Constants.MAX_BYTES_PNG;

import android.content.Context;
import android.media.MediaSync;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.floweraplication.databinding.RowPngAdminBinding;
import com.example.floweraplication.filter.FilterPngAdmin;
import com.example.floweraplication.models.ModelPng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class AdapterPngAdmin extends RecyclerView.Adapter<AdapterPngAdmin.HolderPngAdmin> implements Filterable {
    private Context context;
    public ArrayList<ModelPng> pngArrayList, filterList;
    private RowPngAdminBinding binding;

    private FilterPngAdmin filter;
    private StorageReference storageReference;

    private static final String TAG = "PNG_ADAPTER_TAG";

    public AdapterPngAdmin (Context context, ArrayList<ModelPng> pngArrayList){
        this.context = context;
        this.pngArrayList = pngArrayList;
        this.filterList = pngArrayList;
    }

    @NonNull
    @Override
    public HolderPngAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = RowPngAdminBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderPngAdmin(binding.getRoot());
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterPngAdmin.HolderPngAdmin holder, int position) {

        ModelPng model = pngArrayList.get(position);
        String title = model.getName();

        holder.titleTv.setText(title);
        Glide.with(holder.pngView.getContext()).load(model.getPicture()).into(holder.pngView);
        //loadPng(model, holder);
        //holder.pngView.setImageResource(Integer.parseInt(picture));

    }

    private void loadPng(ModelPng model, HolderPngAdmin holder) {

        String picture = model.getPicture();
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(picture);
        ref.getBytes(MAX_BYTES_PNG)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Log.d(TAG, "onSuccess:"+model.getPicture()+"successful got the file");

                        // Use the bytes to display the image
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        // Handle any errors
                    }
                });
        //Log.d(TAG, "onSuccess:"+model.getPicture()+"successful got the file");
        //Log.d(TAG, "onFailure: failed getting file from url due to"+e.getMessage());
    }

    @Override
    public int getItemCount() {
        return pngArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FilterPngAdmin(filterList,this);
        }
        return filter;
    }

    class HolderPngAdmin extends RecyclerView.ViewHolder{

        ImageView pngView;
        ProgressBar progressBar;
        TextView titleTv;
        ImageButton moreBtn;


        public HolderPngAdmin(@NonNull View itemView) {
            super(itemView);
            pngView = binding.pngView;
            progressBar = binding.progressBar;
            titleTv = binding.titleTv;
            moreBtn = binding.moreBtn;

        }
    }


}
