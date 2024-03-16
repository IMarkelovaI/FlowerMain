package com.example.floweraplication;

import android.widget.Filter;

import com.example.floweraplication.adapters.AdapterPngAdmin;
import com.example.floweraplication.adapters.AdapterPngUser;
import com.example.floweraplication.models.ModelPng;

import java.util.ArrayList;

public class FilterPngUser extends Filter {
    ArrayList<ModelPng> filterList;
    AdapterPngUser adapterPngUser;

    public FilterPngUser(ArrayList<ModelPng> filterList, AdapterPngUser adapterPngUser){
        this.filterList = filterList;
        this.adapterPngUser = adapterPngUser;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelPng> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size(); i++){
                if (filterList.get(i).getName().toUpperCase().contains(constraint)){
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count =filteredModels.size();
            results.values = filteredModels;
        }
       else {
           results.count = filterList.size();
           results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapterPngUser.pngArrayList = (ArrayList<ModelPng>)results.values;
        adapterPngUser.notifyDataSetChanged();
    }
}
