package com.example.floweraplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floweraplication.databinding.RowTypeDirBinding;

import java.util.ArrayList;

public class AdapterDirectoryPots extends RecyclerView.Adapter<AdapterDirectoryPots.HolderPots> {

    private Context context;
    private ArrayList<ModelDesiasePots> potsArrayList;
    private RowTypeDirBinding binding;

    public  AdapterDirectoryPots(Context context, ArrayList<ModelDesiasePots> potsArrayList){
        this.context = context;
        this.potsArrayList = potsArrayList;
    }
    @NonNull
    @Override
    public AdapterDirectoryPots.HolderPots onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowTypeDirBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderPots(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDirectoryPots.HolderPots holder, int position) {
        ModelDesiasePots model = potsArrayList.get(position);
        String id =model.getId();
        String name = model.getPot_type();
        holder.categoryTv.setText(name);
    }

    @Override
    public int getItemCount() {
        return potsArrayList.size();
    }

    public class HolderPots extends RecyclerView.ViewHolder {
        TextView categoryTv;

        public HolderPots(@NonNull View itemView) {
            super(itemView);
            categoryTv = binding.categoryTv;
        }
    }
}
