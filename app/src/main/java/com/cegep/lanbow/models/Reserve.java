package com.cegep.lanbow.models;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    public Reserve(String userId, String itemId, List<Long> selectedDates, Long addon, Long borrowDate, Long returnDate) {
        this.userId = userId;
        this.itemId = itemId;
        this.selectedDates = selectedDates;
        this.addon = addon;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    private String userId;
    private String itemId;
    private List<Long> selectedDates;
    private Long addon;
    private Long borrowDate;
    private Long returnDate;
}
