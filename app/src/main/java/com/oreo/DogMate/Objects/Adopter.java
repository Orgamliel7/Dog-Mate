package com.oreo.DogMate.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the adopter
 */
public class Adopter extends User {
    List<Dog> favorites;

    public Adopter(String email, String full_name, String phone, Address address, String userID, ArrayList<Dog> favorites) {
        super(email, full_name, phone, address, userID);
        this.favorites = favorites;
    }

    public Adopter() {

    }

    public List<Dog> getFavorites() {
        if(favorites==null){
            favorites = new ArrayList<Dog>();
        }
        return favorites;
    }

    public void addAdvertiser(Dog dog) {
        if(favorites==null){
            favorites = new ArrayList<Dog>();
        }
        if(favorites.contains(dog)==false) {
            favorites.add(dog);
        }
    }
}
