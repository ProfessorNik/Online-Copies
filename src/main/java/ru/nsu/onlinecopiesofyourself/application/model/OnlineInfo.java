package ru.nsu.onlinecopiesofyourself.application.model;

import java.util.List;

public class OnlineInfo {
    private int online;
    private List<String> ips;

    public OnlineInfo(int online, List<String> ips) {
        this.online = online;
        this.ips = ips;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }
}
