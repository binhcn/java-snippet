package dev.binhcn.config.exception;

public class RemoteConfigNotFoundException extends RuntimeException {

    public RemoteConfigNotFoundException(String message) {
        super(message);
    }
}
