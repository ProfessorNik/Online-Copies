package ru.nsu.onlinecopiesofyourself.application.util;

import java.util.ArrayList;

public class Observable {
    private final ArrayList<Observer> observers = new ArrayList<>();

    public void addListener(Observer observer){
        observers.add(observer);
    }

    public void deleteListener(Observer observer){
        observers.remove(observer);
    }

    public void notifyListeners(){
        observers.forEach(Observer::update);
    }
}
