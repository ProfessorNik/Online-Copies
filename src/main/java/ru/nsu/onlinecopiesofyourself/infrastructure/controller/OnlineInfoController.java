package ru.nsu.onlinecopiesofyourself.infrastructure.controller;

import ru.nsu.onlinecopiesofyourself.application.model.OnlineInfo;
import ru.nsu.onlinecopiesofyourself.application.usecase.GetOnlineInfo;

public class OnlineInfoController {
    private final GetOnlineInfo getOnlineInfo;


    public OnlineInfoController(GetOnlineInfo getOnlineInfo) {
        this.getOnlineInfo = getOnlineInfo;
    }

    public OnlineInfo getInfo(){
        return getOnlineInfo.execute();
    }
}
