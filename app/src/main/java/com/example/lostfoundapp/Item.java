package com.example.lostfoundapp;

public class Item {
    private int item_id;
    private String itemName;
    private String phoneNumber;
    private String description;
    private String type;
    private String date;
    private String location;

    public Item() {
    }

    public Item(String itemName, String phoneNumber, String description, String type, String date, String location) {

        this.itemName = itemName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.type = type;
        this.date = date;
        this.location = location;
    }

    public Item(int item_id, String itemName, String phoneNumber, String description, String type, String date, String location) {
        this.item_id = item_id;
        this.itemName = itemName;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.type = type;
        this.date = date;
        this.location = location;
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

}
