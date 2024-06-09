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
import com.example.floweraplication.databinding.RowPngUserDobFlowWaterBinding;
import com.example.floweraplication.filter.FilterPngUserWater;
import com.example.floweraplication.models.ModelUserFlowWater;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;


public class AdapterHomeFragmentWater extends RecyclerView.Adapter<AdapterHomeFragmentWater.HolderPngUser> implements Filterable {
    private Context context;
    public ArrayList<ModelUserFlowWater> pngArrayList, filterList;
    private RowPngUserDobFlowWaterBinding binding;
    private RelativeLayout pngRl;
    private static final String TAG ="ADAPTER_PNG_USER_TAG";
    private FilterPngUserWater filter;

    public AdapterHomeFragmentWater(Context context, ArrayList<ModelUserFlowWater> pngArrayList) {
        this.context = context;
        this.pngArrayList = pngArrayList;
        this.filterList = pngArrayList;
    }


    @Override
    public HolderPngUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowPngUserDobFlowWaterBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderPngUser(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomeFragmentWater.HolderPngUser holder, int position) {

        ModelUserFlowWater model = pngArrayList.get(position);
        String title = model.getNext_day_of_watering();
        holder.titleTv.setText(title);
        Glide.with(context).load(model.getPicture()).into(holder.pngView);
    }

    @Override
    public int getItemCount() {
        return pngArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
        {
            filter = new FilterPngUserWater(filterList, this);
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
