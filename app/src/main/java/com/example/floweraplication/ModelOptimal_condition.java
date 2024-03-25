package com.example.floweraplication;

public class ModelOptimal_condition {
    String abundance_of_watering,air_hamidity_id,fertilizer_id,id,loosening_time,optimal_temperature,soil_type_id,transfer_time,watering_time;

    public ModelOptimal_condition(){}

    public ModelOptimal_condition(String abundance_of_watering, String air_hamidity_id, String fertilizer_id, String id, String loosening_time,
                                  String optimal_temperature, String soil_type_id,
                                  String transfer_time, String watering_time) {
        this.abundance_of_watering = abundance_of_watering;
        this.air_hamidity_id = air_hamidity_id;
        this.fertilizer_id = fertilizer_id;
        this.id = id;
        this.loosening_time = loosening_time;
        this.optimal_temperature = optimal_temperature;
        this.soil_type_id = soil_type_id;
        this.transfer_time = transfer_time;
        this.watering_time = watering_time;
    }

    public String getAbundance_of_watering() {
        return abundance_of_watering;
    }

    public void setAbundance_of_watering(String abundance_of_watering) {
        this.abundance_of_watering = abundance_of_watering;
    }

    public String getAir_hamidity_id() {
        return air_hamidity_id;
    }

    public void setAir_hamidity_id(String air_hamidity_id) {
        this.air_hamidity_id = air_hamidity_id;
    }

    public String getFertilizer_id() {
        return fertilizer_id;
    }

    public void setFertilizer_id(String fertilizer_id) {
        this.fertilizer_id = fertilizer_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoosening_time() {
        return loosening_time;
    }

    public void setLoosening_time(String loosening_time) {
        this.loosening_time = loosening_time;
    }

    public String getOptimal_temperature() {
        return optimal_temperature;
    }

    public void setOptimal_temperature(String optimal_temperature) {
        this.optimal_temperature = optimal_temperature;
    }

    public String getSoil_type_id() {
        return soil_type_id;
    }

    public void setSoil_type_id(String soil_type_id) {
        this.soil_type_id = soil_type_id;
    }

    public String getTransfer_time() {
        return transfer_time;
    }

    public void setTransfer_time(String transfer_time) {
        this.transfer_time = transfer_time;
    }

    public String getWatering_time() {
        return watering_time;
    }

    public void setWatering_time(String watering_time) {
        this.watering_time = watering_time;
    }
}
