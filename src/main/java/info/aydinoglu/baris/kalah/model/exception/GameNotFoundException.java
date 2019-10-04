package info.aydinoglu.baris.kalah.model.exception;

public class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GameNotFoundException(final String id) {
        super("Could not find game " + id);
    }
}
