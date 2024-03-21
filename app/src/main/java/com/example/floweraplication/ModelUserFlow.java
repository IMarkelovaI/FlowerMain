package com.example.floweraplication;

import java.io.Serializable;

public class ModelUserFlow implements Serializable {

    String description,id,name,picture,plant_id,plant_size,plant_width,sun;
    public ModelUserFlow(){}

    public ModelUserFlow(String description, String id, String name, String picture, String plant_id, String plant_size, String plant_width, String sun) {
        this.description = description;
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.plant_id = plant_id;
        this.plant_size = plant_size;
        this.plant_width = plant_width;
        this.sun = sun;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(String plant_id) {
        this.plant_id = plant_id;
    }

    public String getPlant_size() {
        return plant_size;
    }

    public void setPlant_size(String plant_size) {
        this.plant_size = plant_size;
    }

    public String getPlant_width() {
        return plant_width;
    }

    public void setPlant_width(String plant_width) {
        this.plant_width = plant_width;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }
}
