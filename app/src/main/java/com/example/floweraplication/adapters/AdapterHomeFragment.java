package com.example.floweraplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.floweraplication.UserPlantDetailActivity;
import com.example.floweraplication.models.ModelUserFlow;
import com.example.floweraplication.databinding.RowUserBinding;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;


public class AdapterHomeFragment extends RecyclerView.Adapter<AdapterHomeFragment.HolderPngUser> {
    private Context context;
    public ArrayList<ModelUserFlow> pngArrayList;
    private RowUserBinding binding;
    private RelativeLayout pngRl;
    private static final String TAG ="ADAPTER_PNG_USER_TAG";

    public AdapterHomeFragment(Context context, ArrayList<ModelUserFlow> pngArrayList) {
        this.context = context;
        this.pngArrayList = pngArrayList;
    }


    @Override
    public HolderPngUser onCreateViewHolder(@org.checkerframework.checker.nullness.qual.NonNull ViewGroup parent, int viewType) {
        binding = RowUserBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderPngUser(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomeFragment.HolderPngUser holder, int position) {

        ModelUserFlow model = pngArrayList.get(position);
        Glide.with(context).load(model.getPicture()).into(holder.pngView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserPlantDetailActivity.class);

                intent.putExtra("idPlU",model.getId());
                intent.putExtra("descriptionPlU", model.getDescription());
                intent.putExtra("namePlU",model.getName());
                intent.putExtra("picturePl",model.getPicture());
                intent.putExtra("plant_idPlU",model.getPlant_id());
                intent.putExtra("plant_sizePlU",model.getPlant_size());
                intent.putExtra("plant_widthPlU",model.getPlant_width());
                intent.putExtra("sunPlU",model.getSun());



                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pngArrayList.size();
    }

    class HolderPngUser extends RecyclerView.ViewHolder{
        ImageView pngView;

        public HolderPngUser(View itemView) {
            super(itemView);
            pngView = binding.pngView;

        }
    }
}
