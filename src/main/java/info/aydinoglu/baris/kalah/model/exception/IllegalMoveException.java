package info.aydinoglu.baris.kalah.model.exception;

public class IllegalMoveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IllegalMoveException(final String message) {
        super("Illegal move: " + message);
    }
}
