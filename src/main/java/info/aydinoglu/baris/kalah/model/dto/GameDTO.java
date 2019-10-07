package info.aydinoglu.baris.kalah.model.dto;

import java.util.Map;

public class GameDTO {

  private String id;
  private String uri;
  private Map<Integer, String> status;

  public GameDTO(String id, String uri) {
    this(id, uri, null);
  }

  public GameDTO(String id, String uri, Map<Integer, String> status) {
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
