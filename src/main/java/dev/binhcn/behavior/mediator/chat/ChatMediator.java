package dev.binhcn.behavior.mediator.chat;

public interface ChatMediator {
 
    void sendMessage(String msg, User user);
 
    void addUser(User user);
}