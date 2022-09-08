package ru.nsu.onlinecopiesofyourself.application.model;

import ru.nsu.onlinecopiesofyourself.domain.value.Message;

public class MessageInfo {
    private Message content;
    private String senderId;

    public MessageInfo(Message content, String senderId){
        this.content = content;
        this.senderId = senderId;
    }

    public Message getContent() {
        return content;
    }

    public void setContent(Message content) {
        this.content = content;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}


