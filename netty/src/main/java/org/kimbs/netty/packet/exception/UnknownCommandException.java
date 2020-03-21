package org.kimbs.netty.packet.exception;

public class UnknownCommandException extends RuntimeException {

    public UnknownCommandException(String message) {
        super(message);
    }
}
