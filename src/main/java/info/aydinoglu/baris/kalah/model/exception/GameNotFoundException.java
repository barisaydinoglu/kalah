package info.aydinoglu.baris.kalah.model.exception;

public class GameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2576221153177453295L;

    public GameNotFoundException(final String id) {
        super("Could not find game " + id);
    }
}
