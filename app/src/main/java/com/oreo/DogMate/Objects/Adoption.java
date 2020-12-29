package com.oreo.DogMate.Objects;

import java.io.Serializable;

/**
 * Represents the order of a dog for both adopter and advertiser.
 * includes the adopter and the advertiser that connected in this order,
 * the date and more..
 */
public class Adoption implements Serializable {
    Adopter adopter;
    Advertiser advertiser;
    Dog dog;

    String date;
    String request;

    public Adoption(Adopter adopter, Advertiser advertiser, Dog dog, String date,
                    String request) {
        this.adopter = adopter;
        this.advertiser = advertiser;
        this.dog = dog;
        this.date = date;
        this.request = request;

    }

    public Adoption() {

    }

    public Adopter getAdopter() {
        return adopter;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public Dog getDog() {
        return dog;
    }

    public String getRequest() {
        return request;
    }

    public void setComments(String request) {
        this.request = request;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
