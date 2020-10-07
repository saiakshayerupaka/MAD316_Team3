package com.cegep.lanbow.models;

public class Item {

    public Item() {
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public Item(String itemUrl, String itemName, String itemDes) {
        this.itemUrl = itemUrl;
        this.itemName = itemName;
        this.itemDes = itemDes;
    }

    private String itemUrl;
    private String itemName;
    private String itemDes;
}