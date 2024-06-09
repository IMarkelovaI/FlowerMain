package com.example.floweraplication.filter;

import android.widget.Filter;

import com.example.floweraplication.adapters.AdapterHomeFragmentWater;
import com.example.floweraplication.models.ModelUserFlowWater;

import java.util.ArrayList;

public class FilterPngUserWater extends Filter {
    ArrayList<ModelUserFlowWater> filterList;
    AdapterHomeFragmentWater adapterHomeFragmentWater;

    public FilterPngUserWater(ArrayList<ModelUserFlowWater> filterList, AdapterHomeFragmentWater adapterHomeFragmentWater){
        this.filterList = filterList;
        this.adapterHomeFragmentWater = adapterHomeFragmentWater;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelUserFlowWater> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size(); i++){
                if (filterList.get(i).getNext_day_of_watering().toUpperCase().contains(constraint)){
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
        adapterHomeFragmentWater.pngArrayList = (ArrayList<ModelUserFlowWater>)results.values;
        adapterHomeFragmentWater.notifyDataSetChanged();
    }
}
