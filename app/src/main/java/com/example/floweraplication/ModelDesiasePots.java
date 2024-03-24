package com.example.floweraplication;

public class ModelDesiasePots {
    String id,Pot_type,height,volume,width;
    public ModelDesiasePots(){

    }
    public ModelDesiasePots(String id, String Pot_type,String height,String volume,String width){
        this.id = id;
        this.Pot_type =Pot_type;
        this.height =height;
        this.volume =volume;
        this.width =width;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPot_type() {
        return Pot_type;
    }

    public void setPot_type(String pot_type) {
        Pot_type = pot_type;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
