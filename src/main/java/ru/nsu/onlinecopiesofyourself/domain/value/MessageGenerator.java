package ru.nsu.onlinecopiesofyourself.domain.value;

import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class MessageGenerator {
    public static final String phrase = "Hello!";
    public static final UUID appId = UUID.randomUUID();
    public static final String ip;

    static {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject generateJSON(){
        return new JSONObject()
                .put(MessageField.PHRASE, phrase)
                .put(MessageField.APP_ID, appId.toString())
                .put(MessageField.MESSAGE_ID, UUID.randomUUID())
                .put(MessageField.IP, ip);
    }

    public String generateMessage() {
        return generateJSON().toString();
    }
}
