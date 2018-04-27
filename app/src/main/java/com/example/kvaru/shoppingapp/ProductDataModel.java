package com.example.kvaru.shoppingapp;

/**
 * Created by kvaru on 2/20/2018.
 */

public class ProductDataModel {

    private String Description,Name,Rating,Price,ImageUrl;
    private int Key;

    public ProductDataModel(){}

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getKey() {
        return Key;
    }

    public void setKey(int key) {
        this.Key = key;
    }
}
