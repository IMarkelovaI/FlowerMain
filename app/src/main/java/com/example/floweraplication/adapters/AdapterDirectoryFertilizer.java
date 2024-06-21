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

        String s;
        switch (name){
            case ("Медный купорос"):
                s="https://trade-him.ru/files/content/2020/07/1596114873.jpg";
                break;
            case("Аммофос"):
                s="https://agrochemcenter.ru/upload/iblock/21b/zi9bm0fu8y528k5x9vptdp0qria1pztb.jpg";
                break;
            case("Нитроаммофоска"):
                s="https://www.ogorod.ru/images/cache/1200x628/crop_0_54_1200_682/images%7Ccms-image-000088155.jpg";
                break;
            case("Монофосфат калия"):
                s="https://antonovsad.ru/media/cache/article_photo_full/uploads/articles/photos/63d8cc1cd869c.jpg";
                break;
            case("Кальциевая селитра"):
                s="https://sadogoroda.net/wp-content/uploads/2018/05/nitrat-kaltsiya-ili-kaltsievaya-selitra-primenenie-kak-udobreniya-na-ogorode.jpg";
                break;
            case("Жидкий биогумус"):
                s="https://www.sotki.ru/public/wysiwyg/images/%D0%9F%D0%BE%D0%B4%D0%BA%D0%BE%D1%80%D0%B8%D0%BC%D0%BA%D0%B0%20%D0%92%D0%9C(1).jpg";
                break;
            case("Древесная зола"):
                s="https://storage01.sb.by/iblock/b70/b7058a92914eabb780a46b860cf2986e.jpg";
                break;
            case("Дрожжи"):
                s="https://static.insales-cdn.com/files/1/4999/16659335/original/%D0%9F%D0%B5%D0%BA%D0%B0%D1%80%D1%81%D0%BA%D0%B8%D0%B5_%D0%B4%D1%80%D0%BE%D0%B6%D0%B6%D0%B8_%D1%81%D1%83%D1%85%D0%B8%D0%B5_%D0%B8_%D0%B6%D0%B8%D0%B2%D1%8B%D0%B5.jpg";
                break;
            case("Янтарная кислота"):
                s="https://temaonline.ru/wp-content/uploads/2023/12/depositphotos_1558727_original-1024x683.jpg";
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

