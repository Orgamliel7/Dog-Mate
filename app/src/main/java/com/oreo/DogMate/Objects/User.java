package com.oreo.DogMate.Objects;
/**
 * Represent a General user with general personal details - name, address etc.
 */

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String full_name;
    private String phone;
    private String userID;
    private Address address;

    public User(){}
    public User(String email, String full_name, String phone, Address address, String userID) {
        this.email = email;
        this.full_name = full_name;
        this.phone = phone;
        this.address = address;
        this.userID = userID;

    }

    public String getEmail() {
        return email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUserID() {
        return userID;
    }

}
