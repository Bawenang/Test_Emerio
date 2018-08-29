package com.bawe.test_emerio.Models;

import java.util.Objects;

/**
 * Created by poing on 8/28/18.
 */

public class LocationModel {

    private String area;
    private String city;

    public LocationModel(String area, String city) {
        this.area = area;
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String value) {
        this.area = value;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String value) {
        this.city = value;
    }

    public String combine() {
        return area + ", " + city;
    }
}
