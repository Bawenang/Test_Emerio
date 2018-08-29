package com.bawe.test_emerio.Models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by poing on 8/28/18.
 */

public class DokterModel {
    private int id;
    private String name;
    private String area;
    private String speciality;
    private String currency;
    private String price;
    private String photoUrl;
    private Drawable photoDrawable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Drawable getPhotoDrawable() {
        return photoDrawable;
    }

    public void setPhotoDrawable(Drawable photoDrawable) {
        this.photoDrawable = photoDrawable;
    }

    public String getCurrencyPrice() {
        return getCurrency()+" " + getPrice();
    }
}
