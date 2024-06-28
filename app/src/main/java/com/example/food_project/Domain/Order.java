package com.example.food_project.Domain;

public class Order {
    private String id;
    private String name;
    private Double total;
    private String phoneNumber;
    private String diaChi;

    public Order() {
    }

    public Order(String id, String name, Double total, String phoneNumber, String diaChi) {
        this.id = id;
        this.name = name;
        this.total = total;
        this.phoneNumber = phoneNumber;
        this.diaChi = diaChi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
