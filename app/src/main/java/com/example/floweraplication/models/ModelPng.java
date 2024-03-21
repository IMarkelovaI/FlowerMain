package com.example.floweraplication.models;

import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Collector;

public class ModelPng implements Serializable {
    String name,image, purpose_id, id, degree_of_toxicity, description,endurance,habitat,size,type_id;
    public ModelPng(){}

    public ModelPng(String name, String image, String purpose_id,String id, String degree_of_toxicity, String description,String endurance,String habitat,String size,String type_id) {
        this.name = name;
        this.image = image;
        this.purpose_id = purpose_id;
        this.id = id;
        this.degree_of_toxicity = degree_of_toxicity;
        this.description = description;
        this.endurance = endurance;
        this.habitat = habitat;
        this.size = size;
        this.type_id = type_id;

    }

    public static final Comparator<ModelPng> BY_NAME_ALPHABETICAL = new Comparator<ModelPng>() {
        @Override
        public int compare(ModelPng modelPng1, ModelPng modelPng2) {
            return modelPng1.name.compareTo(modelPng2.name);
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDegree_of_toxicity() {
        return degree_of_toxicity;
    }

    public void setDegree_of_toxicity(String degree_of_toxicity) {
        this.degree_of_toxicity = degree_of_toxicity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndurance() {
        return endurance;
    }

    public void setEndurance(String endurance) {
        this.endurance = endurance;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPurpose_id() {
        return purpose_id;
    }

    public void setPurpose_id(String purpose_id) {
        this.purpose_id = purpose_id;
    }
}