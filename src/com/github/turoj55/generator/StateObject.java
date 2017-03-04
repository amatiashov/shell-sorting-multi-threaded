package com.github.turoj55.generator;

public class StateObject {
     private boolean canWork;

    public StateObject(){
        canWork = true;
    }

    public synchronized void changeState(){
        canWork = false;
    }

    public synchronized boolean getState(){
        return canWork;
    }

    public synchronized void reset(){
        canWork = true;
    }
}
