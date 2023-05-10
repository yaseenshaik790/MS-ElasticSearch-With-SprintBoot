package com.sky.elasticsearch.document;

public class Vehicle {

    private String id;

    private String number;

    public Vehicle(String id, String number) {
        this.id = id;
        this.number = number;
    }

    public Vehicle() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
