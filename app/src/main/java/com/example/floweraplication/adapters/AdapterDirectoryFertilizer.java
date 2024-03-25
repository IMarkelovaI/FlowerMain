package com.example.floweraplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floweraplication.DetailDirectoryActivity;
import com.example.floweraplication.databinding.RowTypeDirBinding;
import com.example.floweraplication.models.ModelFertilizer;

import java.util.ArrayList;

public class AdapterDirectoryFertilizer extends RecyclerView.Adapter<AdapterDirectoryFertilizer.HolderFertilizer> {

    private Context context;
    private ArrayList<ModelFertilizer> fertilizerArrayList;


    private RowTypeDirBinding binding;
    public  AdapterDirectoryFertilizer(Context context, ArrayList<ModelFertilizer> fertilizerArrayList){
        this.context = context;
        this.fertilizerArrayList = fertilizerArrayList;
    }

    @NonNull
    @Override
    public HolderFertilizer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowTypeDirBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderFertilizer(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDirectoryFertilizer.HolderFertilizer holder, int position) {
        ModelFertilizer model = fertilizerArrayList.get(position);
        String id =model.getId();
        String name = model.getName();
        String description = model.getDescription();
        holder.categoryTv.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDirectoryActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("description",description);
                String picture = "https://firebasestorage.googleapis.com/v0/b/floweraplication.appspot.com/o/images%2F2024_03_24_04_03_25.png?alt=media&token=572e21d3-66f5-4ed4-96ab-5b4ecc619e6a";
                intent.putExtra("picture", picture);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fertilizerArrayList.size();
    }

    public class HolderFertilizer extends RecyclerView.ViewHolder {
        TextView categoryTv;

        public HolderFertilizer(@NonNull View itemView) {
            super(itemView);
            categoryTv = binding.categoryTv;
        }
    }
}
