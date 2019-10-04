package info.aydinoglu.baris.kalah.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import info.aydinoglu.baris.kalah.model.Game;
import info.aydinoglu.baris.kalah.model.exception.GameNotFoundException;

@Component
public class GameRepository {

    Map<String, Game> repository = new HashMap<>();

    public Game find(final String id) {
        final Game game = this.repository.get(id);
        if (game == null) {
            throw new GameNotFoundException(id);
        }
        return game;
    }

    public Game save(final Game game) {
        this.repository.put(game.getId(), game);
        return this.find(game.getId());
    }

}
