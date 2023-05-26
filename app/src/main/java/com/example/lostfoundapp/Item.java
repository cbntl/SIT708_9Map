package com.example.lostfoundapp;

public class Item {
    private int item_id;
    private String itemName;
    private String phoneNumber;
    private String description;
    private String type;
    private String date;
    private String location;
    private double lat;
    private double lon;

    public Item() {
    }

    public Item(String itemName, String phoneNumber, String description, String type, String date, String location, double lat, double lon) {

        this.itemName = itemName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.type = type;
        this.date = date;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
    }

    public Item(int item_id, String itemName, String phoneNumber, String description, String type, String date, String location,double lat, double lon) {
        this.item_id = item_id;
        this.itemName = itemName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.type = type;
        this.date = date;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
    }

    public int getItem_id() {

        return item_id;
    }


    public String getItemName() {

        return itemName;
    }


    public String getPhoneNumber() {

        return phoneNumber;
    }


    public String getDescription() {

        return description;
    }


    public String getType() {

        return type;
    }


    public String getDate() {

        return date;
    }


    public String getLocation() {

        return location;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

}
