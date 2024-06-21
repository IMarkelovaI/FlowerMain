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
import com.example.floweraplication.models.ModelSoil_type;

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
        String s;
        switch (name){
            case ("Верховный торф"):
                s="https://goldwins.ru/upload/medialibrary/cab/2_min.jpg";
                break;
            case("Низинный торф"):
                s="https://www.mirmulchi.ru/images/2019/12/20/nizinnyj-torf-2.jpg";
                break;
            case("Биогумус"):
                s="https://progress39.ru/uploads/product/160954700/160954717/biokompost_2020-01-29_16-30-42.jpg";
                break;
            case("Дерновая земля"):
                s="https://gardennews.ru/wp-content/uploads/2019/09/011-143-900x400.jpg";
                break;
            default:
                s="https://firebasestorage.googleapis.com/v0/b/floweraplication.appspot.com/o/images%2F2024_03_24_04_03_25.png?alt=media&token=572e21d3-66f5-4ed4-96ab-5b4ecc619e6a";
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailDirectoryActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("description",description);
                String picture = s;
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
