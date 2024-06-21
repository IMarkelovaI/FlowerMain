package com.example.floweraplication.models;

import java.util.Comparator;

public class ModelFertilizer {
    String id, name, description;
    public ModelFertilizer(){

    }
    public ModelFertilizer(String id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public static final Comparator<ModelFertilizer> BY_NAME_ALPHABETICAL = new Comparator<ModelFertilizer>() {
        @Override
        public int compare(ModelFertilizer modelPng1, ModelFertilizer modelPng2) {
            return modelPng1.name.compareTo(modelPng2.name);
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.replace("_b","\n");
    }
}
