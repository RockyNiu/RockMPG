package com.rockyniu.calculatempg.model;

/**
 * Created by Lei on 2015/2/15.
 */
public class Record extends BaseData {
    private String carId = "";
    private String homeId = "";
    private float miles;
    private float gas;
    private long recordMilesTime;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public float getMiles() {
        return miles;
    }

    public void setMiles(float miles) {
        this.miles = miles;
    }

    public float getGas() {
        return gas;
    }

    public void setGas(float gas) {
        this.gas = gas;
    }

    public long getRecordMilesTime() {
        return recordMilesTime;
    }

    public void setRecordMilesTime(long recordMilesTime) {
        this.recordMilesTime = recordMilesTime;
    }

}
