package com.example.floweraplication.models;

import java.io.Serializable;

public class ModelUser implements Serializable {

    String id, email, profileImage,name;
    public ModelUser(){}
    public ModelUser(String id, String email,String profileImage,String name) {
        this.id = id;
        this.email = email;
        this.name=name;
        this.profileImage=profileImage;

    }
    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
