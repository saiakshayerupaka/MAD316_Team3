package com.cegep.lenbow.models;

import java.io.Serializable;
/**
 * Student model class
 * @author dipmal lakhani
 * @author Sai Akshay
 * @author Gopichand
 * @author HarshaVardhan
 * @author Vinay
 * @author prashant
 * @author Amandeep singh
 */

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

    /**
     * name of the student
     */
    public String name;
    /**
     * email of the student
     */
    public String email;
    /**
     * unique student id
     */
    public String studentId;
    /**
     * phone number of the student
     */
    public String phonenumber;
    /**
     * address of the student
     */
    public String address;

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String profilepic;

    public String getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }

    /**
     * user profile status : active| block
     */

    public String profileStatus;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String key;
}
