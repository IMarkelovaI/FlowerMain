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
import com.example.floweraplication.models.ModelCategory;

import java.util.ArrayList;

public class AdapterDirectoryDesiase extends RecyclerView.Adapter<AdapterDirectoryDesiase.HolderDesiase> {

    private Context context;
    private ArrayList<ModelDesiase> desiasesArrayList;


    private RowTypeDirBinding binding;
    public  AdapterDirectoryDesiase(Context context, ArrayList<ModelDesiase> desiasesArrayList){
        this.context = context;
        this.desiasesArrayList = desiasesArrayList;
    }

    @NonNull
    @Override
    public HolderDesiase onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowTypeDirBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderDesiase(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDirectoryDesiase.HolderDesiase holder, int position) {
        ModelDesiase model = desiasesArrayList.get(position);
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
        return desiasesArrayList.size();
    }

    public class HolderDesiase extends RecyclerView.ViewHolder {
        TextView categoryTv;

        public HolderDesiase(@NonNull View itemView) {
            super(itemView);
            categoryTv = binding.categoryTv;
        }
    }
}
