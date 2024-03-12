package com.example.floweraplication.adapters;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floweraplication.databinding.RowPngAdminBinding;
import com.example.floweraplication.filter.FilterPngAdmin;
import com.example.floweraplication.models.ModelPng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class AdapterPngAdmin extends RecyclerView.Adapter<AdapterPngAdmin.HolderPngAdmin> implements Filterable {
    private Context context;
    public ArrayList<ModelPng> pngArrayList, filterList;
    private RowPngAdminBinding binding;

    private FilterPngAdmin filter;

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
        String picture = model.getPicture();

        holder.titleTv.setText(title);
        holder.pngView.setImageResource(Integer.parseInt(picture));
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
