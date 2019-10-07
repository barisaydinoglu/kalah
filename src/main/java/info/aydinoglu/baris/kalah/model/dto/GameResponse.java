package info.aydinoglu.baris.kalah.model.dto;

import java.util.Map;

public class GameResponse {

    private final String id;
    private final String uri;
    private final Map<Integer, String> status;

    public GameResponse(final String id, final String uri) {
        this(id, uri, null);
    }

    public GameResponse(final String id, final String uri, final Map<Integer, String> status) {
        this.id = id;
        this.uri = uri;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public Map<Integer, String> getStatus() {
        return status;
    }
}
