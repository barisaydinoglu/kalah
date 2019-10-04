package info.aydinoglu.baris.kalah.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import info.aydinoglu.baris.kalah.model.Game;
import info.aydinoglu.baris.kalah.service.IGameService;

@RestController
public class GameController {

    @Autowired
    private IGameService service;

    @PostMapping("/games")
    public ResponseEntity<Game> newGame() {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.newGame());
    }

    @PutMapping("/games/{gameId}/pits/{pitIndex}")
    public ResponseEntity<Game> playGame(@PathVariable final String gameId, @PathVariable final Integer pitIndex) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.play(gameId, pitIndex));
    }
}
