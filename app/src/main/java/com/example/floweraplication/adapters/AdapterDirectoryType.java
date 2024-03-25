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
import com.example.floweraplication.models.ModelType;

import java.util.ArrayList;

public class AdapterDirectoryType extends RecyclerView.Adapter<AdapterDirectoryType.HolderType> {

    private Context context;
    private ArrayList<ModelType> categoryArrayList;

    private RowTypeDirBinding binding;
    public  AdapterDirectoryType(Context context, ArrayList<ModelType> categoryArrayList){
        this.context = context;
        this.categoryArrayList = categoryArrayList;
    }

    @NonNull
    @Override
    public HolderType onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RowTypeDirBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HolderType(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDirectoryType.HolderType holder, int position) {
        ModelType model = categoryArrayList.get(position);
        String id =model.getId();
        String name = model.getName();
        String description = model.getDescription();
        holder.categoryTv.setText(name);
        String s;
        switch (name){
            case ("Вьюнковые"):
                s="https://cdn.botanichka.ru/wp-content/uploads/2015/11/Convolvulus_sabatius_07.jpg";
                break;
            case("Орхидные"):
                s="https://jenciklopedija-roz.ru/wp-content/uploads/2019/05/orhidei-897x600.jpg";
                break;
            case("Амариллисовые"):
                s="https://flori-da.ru/wp-content/uploads/2013/08/%D0%B3%D0%B8%D0%BF%D0%BF%D0%B5%D0%B0%D1%81%D1%82%D1%80%D1%83%D0%BC-12.jpg";
                break;
            case("Шиповник"):
                s="https://chelseagarden.ru/wp-content/uploads/2020/03/foto-roza-rygoza-chelsea-garden-foto.jpg";
                break;
            case("Бромелиевые"):
                s="https://images.floristics.info/images/stati_photo3/bromelia/bromelia3a_guzmania.jpg";
                break;
            default:
                s="https://dacha.avgust.com/upload/iblock/51d/51d46aa0661159801b6a097e2ee91de8.jpg";
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
        return categoryArrayList.size();
    }

    public class HolderType extends RecyclerView.ViewHolder {
            TextView categoryTv;

            public HolderType(@NonNull View itemView) {
                super(itemView);
                categoryTv = binding.categoryTv;
            }
    }
}
