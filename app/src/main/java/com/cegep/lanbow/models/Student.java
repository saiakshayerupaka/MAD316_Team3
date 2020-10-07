package com.cegep.lanbow.models;

import java.io.Serializable;

public class Student implements Serializable {
    public Student() {
    }

    public Student(String name, String email, String studentId, String phonenumber, String address,String profileStatus) {
        this.name = name;
        this.email = email;
        this.studentId = studentId;
        this.phonenumber = phonenumber;
        this.address = address;
        this.profileStatus = profileStatus;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String name;
    public String email;
    public String studentId;
    public String phonenumber;
    public String address;

    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    public String profileStatus;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String Key;
}
