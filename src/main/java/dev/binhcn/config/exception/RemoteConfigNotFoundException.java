package dev.binhcn.config.exception;

/**
 * @author thainq
 */
public class RemoteConfigNotFoundException extends RuntimeException {

    public RemoteConfigNotFoundException(String message) {
        super(message);
    }
}
