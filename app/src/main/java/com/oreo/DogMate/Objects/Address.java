package com.oreo.DogMate.Objects;

import java.io.Serializable;

/**
 * Represent a user's Adress - city, street, number etc.
 */
public class Address implements Serializable {
    private String city;
    private String streetName;
    private String BuildingNumber;
    private String floor;
    private String appartmentNumber;

    public Address(String city, String streetName, String buildingNumber, String floor, String appartmentNumber) {
        this.city = city;
        this.streetName = streetName;
        BuildingNumber = buildingNumber;
        this.floor = floor;
        this.appartmentNumber = appartmentNumber;
    }

    public Address() {
        //need one for fireBase
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingNumber() {
        return BuildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        BuildingNumber = buildingNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getAppartmentNumber() {
        return appartmentNumber;
    }

    public void setAppartmentNumber(String appartmentNumber) {
        this.appartmentNumber = appartmentNumber;
    }
}
