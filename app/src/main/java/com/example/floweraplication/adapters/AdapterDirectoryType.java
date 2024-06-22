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
            case("Ароидные"):
                s="https://cvetomagia.ru/wp-content/uploads/2021/02/aroidn-3.jpg";
                break;
            case("Астроцветные"):
                s="https://upload.wikimedia.org/wikipedia/commons/0/07/Aster_Gawedka.jpg";
                break;
            case("Верескоцветные"):
                s="https://img-fotki.yandex.ru/get/61164/87288678.cf/0_b8343_a45d4a53_L.jpg";
                break;
            case("Горечавкоцветные"):
                s="https://t3.ftcdn.net/jpg/00/83/11/16/360_F_83111696_P1D7CmDnjqOU0eSOUF7cCyQUbpFa2iYI.jpg";
                break;
            case("Кактусовые"):
                s="https://my-plants.com/uploads/articles/2020/09/9edb606a6c036a03bf93353a27767dbf.jpg";
                break;
            case("Мальпигиецветные"):
                s="https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Passiflora_c.jpg/1200px-Passiflora_c.jpg";
                break;
            case("Папоротниковидные"):
                s="https://media.izi.travel/767a01dd-79c0-42ee-99fc-bacd302e64bb/bd668a95-0dac-4d8f-be42-6847ffcc62ae_800x600.jpg";
                break;
            case("Паслёноцветные"):
                s="https://abekker.ru/uploads/files/27.01/ru_viushchiesia_01.jpg";
                break;
            case("Сосудистые"):
                s="https://ibss-ras.ru/about-ibss/structure-ibss/unique-scientific-facilities/usf-karadag-nature-reserve/Plants_17.jpg";
                break;
            case("Суккулент"):
                s="https://my-plants.com/uploads/articles/2021/05/b2bf9a25bc013e580d1b4cf4ae961efb.jpg";
                break;
            case("Тутовые"):
                s="https://flori-da.ru/wp-content/uploads/2016/12/%D0%A4%D0%B8%D0%BA%D1%83%D1%812.jpg";
                break;
            case("Лютикоцветные"):
                s="https://shans-group.com/upload/iblock/05d/%D0%9B%D1%8E%D1%82%D0%B8%D0%BA%20%D0%BF%D0%BE%D0%BB%D0%B7%D1%83%D1%87%D0%B8%D0%B8%CC%86.jpg";
                break;
            case("Кутровые"):
                s="https://flori-da.ru/wp-content/uploads/2014/10/%D1%81%D1%82%D0%B5%D1%84%D0%B0%D0%BD%D0%BE%D1%82%D0%B8%D1%81.jpg";
                break;
            case("Первоцветные"):
                s="https://blumgarden.ru/wp-content/uploads/2019/11/primula-bulleya-3.jpg";
                break;
            case("Асфоделовые"):
                s="https://img.freepik.com/free-photo/spring-macro-outdoors-closeup-plant_1122-1569.jpg";
                break;
            case("Толстянковые"):
                s="https://blumgarden.ru/wp-content/uploads/2020/10/ochitok-vidnyj-sedum-brilliant.jpg";
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
