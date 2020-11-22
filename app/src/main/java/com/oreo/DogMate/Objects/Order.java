package com.oreo.DogMate.Objects;

import java.io.Serializable;

/**
 * Represents the order of a pastry for both customer and baker.
 * includes the customer and the baker that connected in this order,
 * the date and more..
 */
public class Order implements Serializable {
    Customer customer;
    Baker baker;
    Pastry pastry;

    String date;
    String comments;

    boolean card;// true means pay by card, false means pay by cash
    boolean delivery;//true means delivery from the baker, false means self-delivery

    public Order(Customer customer, Baker baker, Pastry pastry, String date,
                 String comments, boolean card, boolean delivery) {
        this.customer = customer;
        this.baker = baker;
        this.pastry = pastry;
        this.date = date;
        this.comments = comments;
        this.card = card;
        this.delivery = delivery;
    }

    public Order(){

    }

    public Customer getCustomer() {
        return customer;
    }

    public Baker getBaker() {
        return baker;
    }

    public Pastry getPastry() {
        return pastry;
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
