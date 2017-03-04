package com.github.turoj55.generator;

class StateObject {
     private boolean canWork;

    StateObject(){
        canWork = true;
    }

    synchronized void changeState(){
        canWork = false;
    }

    synchronized boolean getState(){
        return canWork;
    }

    synchronized void reset(){
        canWork = true;
    }
}
