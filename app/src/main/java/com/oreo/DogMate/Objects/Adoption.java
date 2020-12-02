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
    String comments;

    boolean card;// true means pay by card, false means pay by cash
    boolean delivery;//true means delivery from the advertiser, false means self-delivery

    public Adoption(Adopter adopter, Advertiser advertiser, Dog dog, String date,
                    String comments, boolean card, boolean delivery) {
        this.adopter = adopter;
        this.advertiser = advertiser;
        this.dog = dog;
        this.date = date;
        this.comments = comments;
        this.card = card;
        this.delivery = delivery;
    }

    public Adoption(){

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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isCard() {
        return card;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
