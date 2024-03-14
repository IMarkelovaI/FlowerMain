package com.example.floweraplication.models;

public class ModelPng {
    String id, name,image;

    public ModelPng() {

    }

    public ModelPng(String name,  String image) {
        this.name = name;
        this.image = image;
        this.id = id;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
