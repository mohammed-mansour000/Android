package com.example.final_project.model;

public class Category {
    private int cid;
    private String name;

    public Category(int cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public int getCid() {
        return cid;
    }

    @Override
    public String toString() {
        return name;
    }
}

