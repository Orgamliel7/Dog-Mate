package com.oreo.DogMate.Objects;

import java.io.Serializable;

public class Preference implements Serializable {

    private Age age;
    private Size size;
    private Gender gender;
    private Region region;
    private boolean isNeedsEducated;
    private boolean isHypoallergenic;
    private boolean isKidsFriendly;
    private boolean isCatsFriendly;
    private boolean isDogsFriendly;
    private boolean suitsToApartment;
    private boolean suitsToPrivateHouse;
    private boolean isEnergetic;


    public Preference(Age a, Size s, Gender g, Region r, boolean ne, boolean ih, boolean kf, boolean cf, boolean df, boolean ap, boolean ph, boolean ie) {
        this.age = a;
        this.size = s;
        this.gender = g;
        this.region = r;
        this.isNeedsEducated = ne;
        this.isHypoallergenic = ih;
        this.isKidsFriendly = kf;
        this.isCatsFriendly = cf;
        this.isDogsFriendly = df;
        this.suitsToApartment = ap;
        this.suitsToPrivateHouse = ph;
        this.isEnergetic = ie;
    }

    public Preference() {

    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public boolean isNeedsEducated() {
        return isNeedsEducated;
    }

    public void setNeedsEducated(boolean needsEducated) {
        isNeedsEducated = needsEducated;
    }

    public boolean isHypoallergenic() {
        return isHypoallergenic;
    }

    public void setHypoallergenic(boolean hypoallergenic) {
        isHypoallergenic = hypoallergenic;
    }

    public boolean isKidsFriendly() {
        return isKidsFriendly;
    }

    public void setKidsFriendly(boolean kidsFriendly) {
        isKidsFriendly = kidsFriendly;
    }

    public boolean isCatsFriendly() {
        return isCatsFriendly;
    }

    public void setCatsFriendly(boolean catsFriendly) {
        isCatsFriendly = catsFriendly;
    }

    public boolean isDogsFriendly() {
        return isDogsFriendly;
    }

    public void setDogsFriendly(boolean dogsFriendly) {
        isDogsFriendly = dogsFriendly;
    }

    public boolean isSuitsToApartment() {
        return suitsToApartment;
    }

    public void setSuitsToApartment(boolean suitsToApartment) {
        this.suitsToApartment = suitsToApartment;
    }

    public boolean isSuitsToPrivateHouse() {
        return suitsToPrivateHouse;
    }

    public void setSuitsToPrivateHouse(boolean suitsToPrivateHouse) {
        this.suitsToPrivateHouse = suitsToPrivateHouse;
    }

    public boolean isEnergetic() {
        return isEnergetic;
    }

    public void setEnergetic(boolean energetic) {
        isEnergetic = energetic;
    }
}
