package com.oreo.DogMate.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the adopter
 */
public class Adopter extends User {
    List<Advertiser> favorites;

    public Adopter(String email, String full_name, String phone, Address address, String userID, ArrayList<Advertiser> favorites) {
        super(email, full_name, phone, address, userID);
        this.favorites = favorites;
    }

    public Adopter() {

    }

    public List<Advertiser> getFavorites() {
        if(favorites==null){
            favorites = new ArrayList<Advertiser>();
        }
        return favorites;
    }

    public void addAdvertiser(Advertiser advertiser) {
        if(favorites==null){
            favorites = new ArrayList<Advertiser>();
        }
        if(favorites.contains(advertiser)==false) {
            favorites.add(advertiser);
        }
    }
}
