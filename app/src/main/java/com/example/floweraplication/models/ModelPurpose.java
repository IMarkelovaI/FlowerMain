package com.example.floweraplication.models;

public class ModelPurpose {
    String id, name;
    public ModelPurpose(){
    }
    public ModelPurpose(String id, String name){
        this.id = id;
        this.name = name;
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
}
