package com.example.floweraplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floweraplication.databinding.RowTypeDirBinding;

import java.util.ArrayList;

public class AdapterDirectorySoil_type extends RecyclerView.Adapter<AdapterDirectorySoil_type.HolderSoil_type> {

    private Context context;
    private ArrayList<ModelSoil_type> soil_typeArrayList;


    private RowTypeDirBinding binding;
    public  AdapterDirectorySoil_type(Context context, ArrayList<ModelSoil_type> soil_typeArrayList){
        this.context = context;
        this.soil_typeArrayList = soil_typeArrayList;
    }

    @NonNull
    @Override
    public HolderSoil_type onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowTypeDirBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderSoil_type(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDirectorySoil_type.HolderSoil_type holder, int position) {
        ModelSoil_type model = soil_typeArrayList.get(position);
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
        return soil_typeArrayList.size();
    }

    public class HolderSoil_type extends RecyclerView.ViewHolder {
        TextView categoryTv;

        public HolderSoil_type(@NonNull View itemView) {
            super(itemView);
            categoryTv = binding.categoryTv;
        }
    }
}
