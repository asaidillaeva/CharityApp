package com.example.charityapp.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
public class Needy  implements Serializable {

    private String needyId;
    private String fullName;
    private String address;
    private String image;
    private String need;
    private Integer phone;
    @ServerTimestamp
    private Timestamp timestamp;

    public String getUrlToImage() {
        return image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setUrlToImage(String image) {
        this.image = image;
    }

    public void setNeedyId(String needyId) {
        this.needyId = needyId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setNeed(String need) {
        this.need = need;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getNeedyId() {
        return needyId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }


    public String getNeed() {
        return need;
    }

    public Integer getPhone() {
        return phone;
    }
}
