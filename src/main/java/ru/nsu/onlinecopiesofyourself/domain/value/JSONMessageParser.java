package ru.nsu.onlinecopiesofyourself.domain.value;

import org.json.JSONObject;

import java.util.UUID;

public class JSONMessageParser {
    private JSONObject json;

    public JSONMessageParser(JSONObject json){
        this.json = json;
    }

    public String getPhrase(){
        return json.getString(MessageField.PHRASE);
    }

    public String getIp(){return json.getString(MessageField.IP);}

    public UUID getMessageId(){
        return UUID.fromString(json.get(MessageField.MESSAGE_ID).toString());
    }

    public UUID getAppId(){
        return UUID.fromString(json.get(MessageField.APP_ID).toString());
    }

    public static JSONMessageParser from(String message){
        return new JSONMessageParser(new JSONObject(message));
    }
}
