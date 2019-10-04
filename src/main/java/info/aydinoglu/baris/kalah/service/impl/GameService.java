package info.aydinoglu.baris.kalah.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.aydinoglu.baris.kalah.model.Game;
import info.aydinoglu.baris.kalah.repository.GameRepository;
import info.aydinoglu.baris.kalah.service.IGameService;
import info.aydinoglu.baris.kalah.utils.GameUtils;

@Service
public class GameService implements IGameService {

    @Autowired
    private GameRepository repository;

    @Override
    public Game newGame() {
        return this.repository.save(new Game());
    }

    @Override
    public Game play(final String gameId, final Integer pitIndex) {
        final Game game = this.repository.find(gameId);
        GameUtils.distributeStones(game, pitIndex);
        GameUtils.checkGameOver(game);
        return game;
    }
}
