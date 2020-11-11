package com.cegep.lenbow.models;

import java.io.Serializable;
/**
 * Message model class
 * @author dipmal lakhani
 */


public class Message implements Serializable {

    public Message() {
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageBy() {
        return messageBy;
    }

    public void setMessageBy(String messageBy) {
        this.messageBy = messageBy;
    }

    public Message(String messageTitle, String messageType, String message, String messageStatus, String messageBy,long createdOn) {
        this.messageTitle = messageTitle;
        this.messageType = messageType;
        this.message = message;
        this.messageStatus = messageStatus;
        this.messageBy = messageBy;
        this.createdOn = createdOn;
    }

    private String messageTitle;
    private String messageType;
    private String message;
    private String messageStatus;
    private String messageBy;

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    private long createdOn;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    private String messageId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    private String studentId;

}
