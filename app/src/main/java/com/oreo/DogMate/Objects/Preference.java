package com.oreo.DogMate.Objects;

import java.io.Serializable;

public class Preference implements Serializable {

    private Age age;
    private Size size;
    private Gender gender;
    private Region region;
    private boolean isNeedsEducated;


    public Preference(Age a, Size s, Gender g, Region r, boolean ne) {
        this.age = a;
        this.size = s;
        this.gender = g;
        this.region = r;
        this.isNeedsEducated = ne;
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
}
