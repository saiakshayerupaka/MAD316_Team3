package com.cegep.lenbow.models;

import java.io.Serializable;

/**
 * Item model class
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */

public class Item implements Serializable {

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

    public Item(String itemUrl, String itemName, String itemDes,String itemType,long updatedOn) {
        this.itemUrl = itemUrl;
        this.itemName = itemName;
        this.itemDes = itemDes;
        this.itemType = itemType;
        this.updatedOn = updatedOn;
    }

    /**
     * Image url of the item
     */
    private String itemUrl;

    /**
     * Item name
     */
    private String itemName;
    /**
     * Item Description
     */
    private String itemDes;

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * Item update on
     */
    private long updatedOn;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /**
     * Item Type
     */
    private String itemType;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Item Id
     */
    private String itemId;
}
