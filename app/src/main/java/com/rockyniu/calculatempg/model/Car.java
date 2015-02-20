package com.rockyniu.calculatempg.model;

import java.util.Calendar;

/**
 * Created by Lei on 2015/2/16.
 */
public class Car extends BaseData {
    private String maker = "";
    private String model = "";
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private String style = "";
    private String color = "";

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
