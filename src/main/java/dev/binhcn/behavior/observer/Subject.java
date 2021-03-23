package dev.binhcn.behavior.observer;

public interface Subject {
 
    void attach(Observer observer);
 
    void detach(Observer observer);
 
    void notifyAllObserver();
}