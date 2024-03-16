package com.example.floweraplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.floweraplication.FilterPngUser;
import com.example.floweraplication.databinding.RowPngUserBinding;
import com.example.floweraplication.models.ModelPng;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class AdapterPngUser extends RecyclerView.Adapter<AdapterPngUser.HolderPngUser> implements Filterable {
    private Context context;
    public ArrayList<ModelPng> pngArrayList, filterList;
    private RowPngUserBinding binding;
    private RelativeLayout pngRl;
    private static final String TAG ="ADAPTER_PNG_USER_TAG";
    private FilterPngUser filter;

    public AdapterPngUser(Context context, ArrayList<ModelPng> pngArrayList) {
        this.context = context;
        this.pngArrayList = pngArrayList;
        this.filterList = pngArrayList;
    }


    @Override
    public HolderPngUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowPngUserBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderPngUser(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPngUser.HolderPngUser holder, int position) {

        ModelPng model = pngArrayList.get(position);
        String title = model.getName();
        holder.titleTv.setText(title);
        Glide.with(context).load(model.getImage()).into(holder.pngView);
    }

    @Override
    public int getItemCount() {
        return pngArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
        {
            filter = new FilterPngUser(filterList, this);
        }
        return filter;
    }

    class HolderPngUser extends RecyclerView.ViewHolder{
        ImageView pngView;
        TextView titleTv;


        public HolderPngUser(View itemView) {
            super(itemView);
            pngView = binding.pngView;
            titleTv = binding.titleTv;
            pngRl = binding.pngRl;
        }
    }
}
