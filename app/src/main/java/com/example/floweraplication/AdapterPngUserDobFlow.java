package com.example.floweraplication;

import android.content.Context;
import android.content.Intent;
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
import com.example.floweraplication.Activity.PlantDetailActivity;
import com.example.floweraplication.databinding.RowPngUserBinding;
import com.example.floweraplication.databinding.RowPngUserDobFlowBinding;
import com.example.floweraplication.filter.FilterPngUser;
import com.example.floweraplication.models.ModelPng;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class AdapterPngUserDobFlow extends RecyclerView.Adapter<AdapterPngUserDobFlow.HolderPngUser> implements Filterable {
    private Context context;
    public ArrayList<ModelPng> pngArrayList, filterList;
    private RowPngUserDobFlowBinding binding;
    private RelativeLayout pngRl;
    private static final String TAG ="ADAPTER_PNG_USER_TAG";
    private FilterPngUserDobFlow filter;

    public AdapterPngUserDobFlow(Context context, ArrayList<ModelPng> pngArrayList) {
        this.context = context;
        this.pngArrayList = pngArrayList;
        this.filterList = pngArrayList;
    }


    @Override
    public HolderPngUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowPngUserDobFlowBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderPngUser(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPngUserDobFlow.HolderPngUser holder, int position) {

        ModelPng model = pngArrayList.get(position);
        String title = model.getName();
        holder.titleTv.setText(title);
        Glide.with(context).load(model.getImage()).into(holder.pngView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DobUserPlantActivity.class);

                intent.putExtra("pngView", model.getImage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pngArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
        {
            filter = new FilterPngUserDobFlow(filterList, this);
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

        }
    }
}
