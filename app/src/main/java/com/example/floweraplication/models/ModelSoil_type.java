package com.example.floweraplication.models;

import java.util.Comparator;

public class ModelSoil_type {
    String id, name, description;
    public ModelSoil_type(){

    }
    public ModelSoil_type(String id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public static final Comparator<ModelSoil_type> BY_NAME_ALPHABETICAL = new Comparator<ModelSoil_type>() {
        @Override
        public int compare(ModelSoil_type modelPng1, ModelSoil_type modelPng2) {
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
        this.description = description;
    }
}
