package com.example.floweraplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floweraplication.DetailDirectoryActivity;
import com.example.floweraplication.databinding.RowTypeDirBinding;
import com.example.floweraplication.models.ModelDesiase;

import java.util.ArrayList;


public class AdapterDirectoryDesiase extends RecyclerView.Adapter<AdapterDirectoryDesiase.HolderDesiase> {

    private Context context;
    private ArrayList<ModelDesiase> desiasesArrayList;
    private static final String TAG = "ADD_PLANT_TAG";

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
        String s;
        switch (name){
            case ("Парса"):
                s="https://markik.ru/800/600/http/tk-ast.ru/wp-content/uploads/6a9b1788195c63130ee3a1d25310d9ce.jpg";
                break;
            case("Просса"):
                s="https://static.tildacdn.com/tild3935-6363-4330-a234-626336663366/1-6-700x599.jpg";
                break;
            default:
                s="https://dacha.avgust.com/upload/iblock/51d/51d46aa0661159801b6a097e2ee91de8.jpg";
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "image"+s);
                Intent intent = new Intent(context, DetailDirectoryActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("description",description);
                Log.i(TAG, "жопа"+name);

                String picture = s;
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
