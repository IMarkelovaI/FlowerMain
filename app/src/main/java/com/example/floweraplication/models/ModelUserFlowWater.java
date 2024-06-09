package com.example.floweraplication.models;

import java.io.Serializable;
import java.util.Comparator;

public class ModelUserFlowWater implements Serializable {

    String next_day_of_watering;
    String id,picture;
    public ModelUserFlowWater(){}
    //String id, String picture,

    public ModelUserFlowWater(String id, String picture, String next_day_of_watering) {
        this.id = id;
        this.picture = picture;
        this.next_day_of_watering = next_day_of_watering;
    }

    public static final Comparator<ModelUserFlowWater> BY_NAME_ALPHABETICAL = new Comparator<ModelUserFlowWater>() {
        @Override
        public int compare(ModelUserFlowWater modelUserFlowWater1, ModelUserFlowWater modelUserFlowWater2) {
            return modelUserFlowWater1.next_day_of_watering.compareTo(modelUserFlowWater2.next_day_of_watering);
        }
    };

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNext_day_of_watering() {
        return next_day_of_watering;
    }

    public void setNext_day_of_watering(String next_day_of_watering) {
        this.next_day_of_watering = next_day_of_watering;
    }
}
