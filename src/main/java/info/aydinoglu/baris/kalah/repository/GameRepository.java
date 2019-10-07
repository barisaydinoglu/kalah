package info.aydinoglu.baris.kalah.repository;

import info.aydinoglu.baris.kalah.model.Game;

public interface GameRepository {

  Game find(final String id);

  Game save(final Game game);

}
