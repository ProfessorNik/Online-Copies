package ru.nsu.onlinecopiesofyourself.domain.entity;

import ru.nsu.onlinecopiesofyourself.domain.exception.InvalidMessageException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CopyOfYourself {
    private final UUID id;
    private final String ip;
    private final List<UUID> prevMessageIds;
    private long lastMessageTime;

    public CopyOfYourself(UUID id, String ip, UUID messageId) {
        this.id = id;
        this.ip = ip;
        this.prevMessageIds = new ArrayList<>();
        lastMessageTime = System.currentTimeMillis();
        prevMessageIds.add(messageId);
    }

    public void addMessageId(UUID messageID){
        if(prevMessageIds.contains(messageID)){
            throw new InvalidMessageException("that UUID \"" + messageID + "\" already was");
        }
        prevMessageIds.add(messageID);
        lastMessageTime = System.currentTimeMillis();
    }

    public boolean hasTimeExpired(long expirationTime){
        return System.currentTimeMillis() - lastMessageTime >= expirationTime;
    }

    public UUID getId(){
        return id;
    }

    public String getIp() {
        return ip;
    }
}
