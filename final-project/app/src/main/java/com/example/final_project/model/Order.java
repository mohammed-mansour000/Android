package com.example.final_project.model;

public class Order {

    private int pid;
    private int user_id;
    private int order_id;
    private String name;
    private double price;
    private String category;
    private String image;

    public Order(){

    }


    public Order(int pid, int user_id, int order_id, String name, double price, String category, String image) {
        this.pid = pid;
        this.user_id = user_id;
        this.order_id = order_id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
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

    public int getPid() {
        return pid;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getOrder_id() {
        return order_id;
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

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
