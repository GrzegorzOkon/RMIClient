package rmi.server.output;

public class NotEnoughItemsException extends Exception {
    private static final long serialVersionUID = 1L;

    public NotEnoughItemsException(String message) {
        super(message);
    }
}
