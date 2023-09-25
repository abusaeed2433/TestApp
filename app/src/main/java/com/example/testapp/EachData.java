package com.example.testapp;

import androidx.annotation.NonNull;

public class EachData {
    private String heart_rate, sys, dys,photo;

    public EachData() {
    }

    public EachData(String heart_rate, String sys, String dys, String photo) {
        this.heart_rate = heart_rate;
        this.sys = sys;
        this.dys = dys;
        this.photo = photo;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getDys() {
        return dys;
    }

    public void setDys(String dys) {
        this.dys = dys;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @NonNull
    @Override
    public String toString() {

        return heart_rate+" "+sys+" "+dys;
    }
}
