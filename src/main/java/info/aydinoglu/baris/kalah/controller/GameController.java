package info.aydinoglu.baris.kalah.controller;

import java.net.InetAddress;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import info.aydinoglu.baris.kalah.model.Game;
import info.aydinoglu.baris.kalah.model.Pit;
import info.aydinoglu.baris.kalah.model.dto.GameResponse;
import info.aydinoglu.baris.kalah.service.GameService;

@RestController
public class GameController {

  private final GameService service;
  private final Environment environment;

  @Autowired
  public GameController(final GameService service, final Environment environment) {
    this.service = service;
    this.environment = environment;
  }

  @PostMapping("/games")
  public ResponseEntity<GameResponse> createGame() {
    final Game game = this.service.createGame();
    final GameResponse gameResponse = new GameResponse(game.getId(), getUrl(game.getId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(gameResponse);
  }

  @PutMapping("/games/{gameId}/pits/{pitId}")
  public ResponseEntity<GameResponse> playGame(@PathVariable final String gameId,
      @PathVariable final Integer pitId) {
    final Game game = this.service.play(gameId, pitId);
    final Map<Integer, String> status = game.getBoard().getPits().stream()
        .collect(Collectors.toMap(Pit::getId, value -> Integer.toString(value.getStoneCount())));
    final GameResponse gameResponse = new GameResponse(game.getId(), getUrl(game.getId()), status);
    return ResponseEntity.status(HttpStatus.OK).body(gameResponse);
  }

  private String getUrl(final String gameId) {
    final int port = environment.getProperty("server.port", Integer.class, 8080);
    return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(),
        port, gameId);
  }

}
