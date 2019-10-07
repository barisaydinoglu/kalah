package info.aydinoglu.baris.kalah.controller;

import java.net.InetAddress;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import info.aydinoglu.baris.kalah.model.Game;
import info.aydinoglu.baris.kalah.model.Pit;
import info.aydinoglu.baris.kalah.model.dto.GameDTO;
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
  public ResponseEntity<GameDTO> createGame() {
    final Game game = this.service.createGame();
    final GameDTO gameDTO = new GameDTO(game.getId(), getUrl(game.getId()));
    return ResponseEntity.status(HttpStatus.CREATED).body(gameDTO);
  }

  @PutMapping("/games/{gameId}/pits/{pitId}")
  public ResponseEntity<GameDTO> playGame(@PathVariable final String gameId,
      @PathVariable final Integer pitId) {
    final Game game = this.service.play(gameId, pitId);
    final Map<Integer, String> status = game.getBoard().getPits().stream()
        .collect(Collectors.toMap(Pit::getId, value -> Integer.toString(value.getStoneCount())));
    final GameDTO gameDTO = new GameDTO(game.getId(), getUrl(game.getId()), status);
    return ResponseEntity.status(HttpStatus.OK).body(gameDTO);
  }

  private String getUrl(final String gameId) {
    final int port = environment.getProperty("server.port", Integer.class, 8080);
    return String.format("http://%s:%s/games/%s", InetAddress.getLoopbackAddress().getHostName(),
        port, gameId);
  }

}
