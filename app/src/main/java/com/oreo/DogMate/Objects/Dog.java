package com.oreo.DogMate.Objects;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represent a Dog
 */
public class Dog implements Serializable {
    private  String price;
    private String name;
    private String allerganics;
    private String description;
    private String docID;
    ArrayList<Upload> images;
    private String imagesID;
    private String advertiserID;

    public Dog(){
        // no args constructor needed
    }

    public Dog(String price, String name, String allerganics, String description, String advertiserID){
        this.price=price;
        this.name=name;
        this.allerganics=allerganics;
        this.description=description;
        images = new ArrayList<Upload>();
        imagesID = "";
        this.advertiserID=advertiserID;
    }
    public void addImage(Upload upload){
        if(images==null){
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
    public ArrayList<Upload> getImages(){
        if(images==null) images = new ArrayList<>();
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

    public String getPrice() {
        return price;
    }

    public String getAllerganics() {
        return allerganics;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getDocID() {
        return docID;
    }

}
