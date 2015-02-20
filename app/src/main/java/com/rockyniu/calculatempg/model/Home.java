package com.rockyniu.calculatempg.model;

/**
 * Created by Lei on 2015/2/16.
 */
public class Home extends BaseData {
    private String zipCode = "";
    private String address = "";

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
