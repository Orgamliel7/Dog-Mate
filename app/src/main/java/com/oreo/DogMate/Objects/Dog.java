package com.oreo.DogMate.Objects;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represent a Dog
 */
public class Dog implements Serializable {
    private Age age;
    private String name;
    private Gender gender;
    private Region region;
    private Size size;
    private boolean isNeedsEducated;
    private boolean isHypoallergenic;
    private boolean isKidsFriendly;
    private boolean isCatsFriendly;
    private boolean isDogsFriendly;
    private boolean suitsToApartment;
    private boolean suitsToPrivateHouse;
    private boolean isEnergetic;


    private String allerganics;
    private String description;
    private String dogID;
    ArrayList<Upload> images;
    private String imagesID;
    private String advertiserID;

    public Dog() {
        // no args constructor needed
    }

    public Dog(Age age, String name, Size size, Gender gender, Region region, boolean ne, boolean iH, boolean kf, boolean cf,
               boolean df, boolean ap, boolean ph, boolean ie, String allerganics, String description, String advertiserID) {
        this.age = age;
        this.name = name;
        this.gender = gender;
        this.region = region;
        this.size = size;
        this.isNeedsEducated = ne;
        this.isHypoallergenic = iH;
        this.isKidsFriendly = kf;
        this.isCatsFriendly = cf;
        this.isDogsFriendly = df;
        this.suitsToApartment = ap;
        this.suitsToPrivateHouse = ph;
        this.isEnergetic = ie;
        this.allerganics = allerganics;
        this.description = description;
        images = new ArrayList<Upload>();
        imagesID = "";
        this.advertiserID = advertiserID;
    }

    public void addImage(Upload upload) {
        if (images == null) {
            images = new ArrayList<Upload>();
        }
        images.add(upload);
    }

    public void setImages(ArrayList<Upload> images) {
        this.images = images;
    }

    public String getadvertiserID() {
        return advertiserID;
    }

    public void setadvertiserID(String advertiserID) {
        this.advertiserID = advertiserID;
    }

    public ArrayList<Upload> getImages() {
        if (images == null) images = new ArrayList<>();
        return images;
    }

    public String getImagesID() {
        return imagesID;
    }

    public void setImagesID(String imagesID) {
        this.imagesID = imagesID;
    }

    public String getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public String getAllerganics() {
        return allerganics;
    }

    public String getDescription() {
        return description;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public void setAllerganics(String allerganics) {
        this.allerganics = allerganics;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDogID(String dogID) {
        this.dogID = dogID;
    }

    public String getDogID() {
        return dogID;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
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
