package com.oreo.DogMate.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the customer
 */
public class Customer extends User {
    List<Baker> favorites;

    public Customer(String email, String full_name, String phone, Address address, String userID, ArrayList<Baker> favorites) {
        super(email, full_name, phone, address, userID);
        this.favorites = favorites;
    }

    public Customer() {

    }

    public List<Baker> getFavorites() {
        if(favorites==null){
            favorites = new ArrayList<Baker>();
        }
        return favorites;
    }

    public void addBaker(Baker baker) {
        if(favorites==null){
            favorites = new ArrayList<Baker>();
        }
        if(favorites.contains(baker)==false) {
            favorites.add(baker);
        }
    }
}
