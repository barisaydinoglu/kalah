package info.aydinoglu.baris.kalah.service;

import info.aydinoglu.baris.kalah.model.Game;

public interface GameService {

    Game createGame();

    Game play(String gameId, Integer pitId);

}
