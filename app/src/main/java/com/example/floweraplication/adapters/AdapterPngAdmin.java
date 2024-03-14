package com.example.floweraplication.adapters;

import static com.example.floweraplication.Constants.MAX_BYTES_PNG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaSync;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.floweraplication.databinding.RowPngAdminBinding;
import com.example.floweraplication.filter.FilterPngAdmin;
import com.example.floweraplication.models.ModelPng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

public class AdapterPngAdmin extends RecyclerView.Adapter<AdapterPngAdmin.HolderPngAdmin> {
    private Context context;
    public ArrayList<ModelPng> pngArrayList;
    private RowPngAdminBinding binding;

    private StorageReference storageReference;
    private RelativeLayout pngRl;

    private static final String TAG = "PNG_ADAPTER_TAG";

    //private ProgressDialog progressDialog;

    public AdapterPngAdmin (Context context, ArrayList<ModelPng> pngArrayList){
        this.context = context;
        this.pngArrayList = pngArrayList;

    }

    @NonNull
    @Override
    public HolderPngAdmin onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = RowPngAdminBinding.inflate(LayoutInflater.from(context),parent,false);
        return new HolderPngAdmin(binding.getRoot());
    }
    @Override
    public void onBindViewHolder(HolderPngAdmin holder, final int position) {

        ModelPng model = pngArrayList.get(position);
        String title = model.getName();
        holder.titleTv.setText(title);
        Glide.with(context).load(model.getImage()).into(holder.pngView);



        /*holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreOptionsDialog(model, holder);
            }
        });*/
    }
    @Override
    public int getItemCount() {
        return pngArrayList.size();
    }


    public class HolderPngAdmin extends RecyclerView.ViewHolder{
        ImageView pngView;
        ProgressBar progressBar;
        TextView titleTv;
        ImageButton moreBtn;


        public HolderPngAdmin(View itemView) {
            super(itemView);
            pngView = binding.pngView;
            progressBar = binding.progressBar;
            titleTv = binding.titleTv;
            moreBtn = binding.moreBtn;
            pngRl = binding.pngRl;
        }
    }
    /*private void moreOptionsDialog(ModelPng model, HolderPngAdmin holder) {
        String[] options = {"Редактировать","Удалить"};
        AlertDialog.Builder bilder = new AlertDialog.Builder(context);
        bilder.setTitle("Выбрать опцию")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0)
                        {

                        }
                        else if (which==1)
                        {
                            deleatPlant(model,holder);
                        }
                    }
                })
                .show();
    }*/

    /*private void deleatPlant(ModelPng model, HolderPngAdmin holder) {
        String id = model.getId();
        String name = model.getName();

        Log.d(TAG, "deletePlant: Deleting");
        progressDialog.setMessage("Удаляется: "+name);
        progressDialog.show();

        Log.d(TAG, "deletePlant: Deleting from storage");
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(id);
        storageReference.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Deleted from storage");

                        Log.d(TAG, "onSuccess: Now deleting into from db");
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Plant");
                        reference.child(id)
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "onSuccess: Deleted from db too");
                                        progressDialog.dismiss();
                                        Toast.makeText(context, "Растение удалено", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Failed to delete from db due to"+e.getMessage());
                                        progressDialog.dismiss();
                                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to delete from storage"+e.getMessage());
                        progressDialog.dismiss();
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
}
