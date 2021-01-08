package com.example.final_project.model;

public class Product {
    private int pid;
    private String name;
    private double price;
    private String category;
    private String image;

    public Product(){

    }

    public Product(int pid, String name, double price, String category, String image) {
        this.pid = pid;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
