package com.example.floweraplication.models;

import java.util.Comparator;

public class ModelType {
    String id, name, description;
    public ModelType(){

    }
    public ModelType(String id, String name){
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public static final Comparator<ModelType> BY_NAME_ALPHABETICAL = new Comparator<ModelType>() {
        @Override
        public int compare(ModelType modelPng1, ModelType modelPng2) {
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
