package com.example.floweraplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floweraplication.databinding.RowPurposeBinding;
import com.example.floweraplication.databinding.RowTypeBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterPurpose extends RecyclerView.Adapter<AdapterPurpose.HolderPurpose>{

    private Context context;
    private ArrayList<ModelPurpose> purposeArrayList;

    private RowPurposeBinding binding;
    public  AdapterPurpose(Context context, ArrayList<ModelPurpose> purposeArrayList){
        this.context = context;
        this.purposeArrayList = purposeArrayList;
    }
    @NonNull
    @Override
    public AdapterPurpose.HolderPurpose onCreateViewHolder(@NonNull ViewGroup parent, int viewPurpose) {
        binding = RowPurposeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new AdapterPurpose.HolderPurpose(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPurpose.HolderPurpose holder, int position) {
        ModelPurpose model = purposeArrayList.get(position);
        String id = model.getId();
        String name = model.getName();
        holder.PurposeTv.setText(name);

        holder.deltaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Удалить")
                        .setMessage("Точно хотите удалить предназначение?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Удаляется", Toast.LENGTH_SHORT).show();
                                deletePurpose(model, holder);
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
    private void deletePurpose(ModelPurpose model, AdapterPurpose.HolderPurpose holder) {
        String id = model.getId();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Purpose");
        ref.child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Данные успешно удалены", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return purposeArrayList.size();
    }

    class HolderPurpose extends RecyclerView.ViewHolder{
        TextView PurposeTv;
        ImageButton deltaBtn;

        public HolderPurpose(@NonNull View itemView) {
            super(itemView);

            PurposeTv = binding.PurposeTv;
            deltaBtn = binding.deltaBtn;
        }
    }
}
