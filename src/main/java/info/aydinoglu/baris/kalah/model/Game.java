package info.aydinoglu.baris.kalah.model;

import java.util.UUID;

public class Game {

  private final String id;
  private final Board board;
  private Player winner;
  private Player turn;

  public Game() {
    this.id = UUID.randomUUID().toString();
    this.board = new Board();
  }

  public Board getBoard() {
    return this.board;
  }

  public String getId() {
    return this.id;
  }

  public Player getTurn() {
    return this.turn;
  }

  public void setTurn(final Player turn) {
    this.turn = turn;
  }

  public Player getWinner() {
    return this.winner;
  }

  public void setWinner(final Player winner) {
    this.winner = winner;
  }
}
