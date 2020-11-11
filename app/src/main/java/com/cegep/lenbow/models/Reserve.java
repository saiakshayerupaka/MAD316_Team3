package com.cegep.lenbow.models;

import java.io.Serializable;
import java.util.List;

/**
 * Reseve Model class
 * @author dipmal lakhani
 */


public class Reserve implements Serializable {


    public Reserve() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<Long> getSelectedDates() {
        return selectedDates;
    }

    public void setSelectedDates(List<Long> selectedDates) {
        this.selectedDates = selectedDates;
    }

    public Long getAddon() {
        return addon;
    }

    public void setAddon(Long addon) {
        this.addon = addon;
    }

    public Long getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Long borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Long getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Long returnDate) {
        this.returnDate = returnDate;
    }

    public Reserve(String itemName,String userId, String itemId, List<Long> selectedDates, Long addon, Long borrowDate, Long returnDate) {
        this.userId = userId;
        this.itemName = itemName;
        this.itemId = itemId;
        this.selectedDates = selectedDates;
        this.addon = addon;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    private String userId;
    private String itemId;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    private String itemName;
    private List<Long> selectedDates;
    private Long addon;
    private Long borrowDate;
    private Long returnDate;

    public String getReserveId() {
        return reserveId;
    }

    public void setReserveId(String reserveId) {
        this.reserveId = reserveId;
    }

    private String reserveId;
}
