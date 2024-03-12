package com.example.floweraplication.models;

public class ModelPng {
    String id,name,type_id,description,habitat,size,purpose_id,degree_of_toxicity,endurance,picture;

    public ModelPng() {

    }

    public ModelPng(String id, String name, String type_id, String description, String habitat, String size, String purpose_id, String degree_of_toxicity, String endurance, String picture) {
        this.id = id;
        this.name = name;
        this.type_id = type_id;
        this.description = description;
        this.habitat = habitat;
        this.size = size;
        this.purpose_id = purpose_id;
        this.degree_of_toxicity = degree_of_toxicity;
        this.endurance = endurance;
        this.picture = picture;

    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

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

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPurpose_id() {
        return purpose_id;
    }

    public void setPurpose_id(String purpose_id) {
        this.purpose_id = purpose_id;
    }

    public String getDegree_of_toxicity() {
        return degree_of_toxicity;
    }

    public void setDegree_of_toxicity(String degree_of_toxicity) {
        this.degree_of_toxicity = degree_of_toxicity;
    }

    public String getEndurance() {
        return endurance;
    }

    public void setEndurance(String endurance) {
        this.endurance = endurance;
    }
}
