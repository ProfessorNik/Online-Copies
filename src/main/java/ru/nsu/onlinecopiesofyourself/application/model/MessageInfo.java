package ru.nsu.onlinecopiesofyourself.application.model;

public class MessageInfo {
    private String content;
    private String senderId;

    public MessageInfo(String content, String senderId){
        this.content = content;
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}


