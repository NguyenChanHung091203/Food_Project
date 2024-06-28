package com.example.food_project.Domain;

public class Products {
    private String name;
    private String price;
    private String type;
    private String description;
    private String imageUrl;

    public Products() {
        // Default constructor required for Firebase Database operations
    }

    public Products(String name, String price, String type, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

