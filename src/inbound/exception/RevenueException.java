package inbound.exception;

public class RevenueException extends RuntimeException{



    public RevenueException(String message) {
        super(message);
    }

    public RevenueException(String message, Throwable cause) {
        super(message, cause);
    }
}
