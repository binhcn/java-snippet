package dev.binhcn.behavior.state;

public class SubmittedState implements State {
 
    @Override
    public void handleRequest() {
        System.out.println("Submitted");
    }
}