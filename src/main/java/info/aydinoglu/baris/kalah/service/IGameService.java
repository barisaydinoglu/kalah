package info.aydinoglu.baris.kalah.service;

import info.aydinoglu.baris.kalah.model.Game;

public interface IGameService {

    Game newGame();

    Game play(String gameId, Integer pitIndex);

}
