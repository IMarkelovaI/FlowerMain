package com.example.floweraplication.models;

import java.io.Serializable;

public class ModelPng implements Serializable {
    String name,image, purpose_id;
    public ModelPng(){}



    public ModelPng(String name, String image, String purpose_id) {
        this.name = name;
        this.image = image;
        this.purpose_id = purpose_id;

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