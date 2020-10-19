package com.cegep.lanbow.models;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Reserve implements Serializable {

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

    public List<Date> getSelectedDates() {
        return selectedDates;
    }

    public void setSelectedDates(List<Date> selectedDates) {
        this.selectedDates = selectedDates;
    }

    public Date getAddon() {
        return addon;
    }

    public void setAddon(Date addon) {
        this.addon = addon;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Reserve(String userId, String itemId, List<Date> selectedDates, Date addon, Date borrowDate, Date returnDate) {
        this.userId = userId;
        this.itemId = itemId;
        this.selectedDates = selectedDates;
        this.addon = addon;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    private String userId;
    private String itemId;
    private List<Date> selectedDates;
    private Date addon;
    private Date borrowDate;
    private Date returnDate;
}
