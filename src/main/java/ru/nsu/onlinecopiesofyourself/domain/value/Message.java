package ru.nsu.onlinecopiesofyourself.domain.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public final class Message {
    @JsonProperty("phrase")
    private  String phrase;
    @JsonProperty("appId")
    private  String appId;
    @JsonProperty("messageId")
    private  String messageId;


    public Message(){}
    public Message(String phrase, String appId, String messageId) {
        this.phrase = phrase;
        this.appId = appId;
        this.messageId = messageId;
    }

    public String getPhrase() {
        return phrase;
    }

    public UUID getAppId() {
        return UUID.fromString(appId);
    }

    public UUID getMessageId() {
        return UUID.fromString(messageId);
    }

    public static Message fromUUIDs(String phrase, UUID appId, UUID messageId){
        return new Message(phrase, appId.toString(), messageId.toString());
    }
}
