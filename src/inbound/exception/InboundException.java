package inbound.exception;



public class InboundException extends RuntimeException {
    public InboundException(String message) {
        super(message);
    }

    public InboundException(String message, Throwable cause) {
        super(message, cause);
    }
}

