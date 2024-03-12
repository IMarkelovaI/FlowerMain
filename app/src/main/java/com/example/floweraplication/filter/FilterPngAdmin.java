package com.example.floweraplication.filter;

import android.widget.Filter;
import android.widget.Filterable;

import androidx.appcompat.view.menu.MenuBuilder;

import com.example.floweraplication.adapters.AdapterCategory;
import com.example.floweraplication.adapters.AdapterPngAdmin;
import com.example.floweraplication.models.ModelPng;

import java.util.ArrayList;

public class FilterPngAdmin extends Filter {
    ArrayList<ModelPng> filterList;
    AdapterPngAdmin adapterPngAdmin;

    public FilterPngAdmin (ArrayList<ModelPng> filterList, AdapterPngAdmin adapterPngAdmin){
        this.filterList = filterList;
        this.adapterPngAdmin = adapterPngAdmin;
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
            results.values = filterList;
        }
       else {
           results.count = filterList.size();
           results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapterPngAdmin.pngArrayList = (ArrayList<ModelPng>)results.values;
        adapterPngAdmin.notifyDataSetChanged();
    }
}
