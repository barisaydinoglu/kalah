package info.aydinoglu.baris.kalah.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;
import info.aydinoglu.baris.kalah.model.Game;
import info.aydinoglu.baris.kalah.model.Player;
import info.aydinoglu.baris.kalah.model.exception.IllegalMoveException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

  @Autowired
  private GameService service;

  private Game gameInitial;
  private Game gameFinishedWinnerSouth;
  private Game gameFinishedWinnerNorth;
  private Game gameNorthMovedFirst;
  private Game gameSouthMovedFirst;
  private Game gameTurnNorth;
  private Game gameTurnSouth;

  @Before
  public void init()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    this.gameInitial = new Game();
    this.gameFinishedWinnerSouth = new Game();
    this.gameFinishedWinnerNorth = new Game();
    this.gameNorthMovedFirst = new Game();
    this.gameSouthMovedFirst = new Game();
    this.gameTurnNorth = new Game();
    this.gameTurnSouth = new Game();

    final Method resetBoard =
        openMethodForTest(service.getClass().getDeclaredMethod("resetBoard", Game.class));
    final Method distributeStones = openMethodForTest(
        service.getClass().getDeclaredMethod("distributeStones", Game.class, int.class));

    resetBoard.invoke(this.service, this.gameFinishedWinnerSouth);
    this.gameFinishedWinnerSouth.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex())
        .setStoneCount(10);
    this.gameFinishedWinnerSouth.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex())
        .setStoneCount(62);
    resetBoard.invoke(this.service, this.gameFinishedWinnerNorth);
    this.gameFinishedWinnerNorth.getBoard().getPit(1).setStoneCount(1);
    this.gameFinishedWinnerNorth.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex())
        .setStoneCount(39);
    this.gameFinishedWinnerNorth.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex())
        .setStoneCount(32);

    distributeStones.invoke(this.service, this.gameNorthMovedFirst, 1);
    distributeStones.invoke(this.service, this.gameSouthMovedFirst, 10);
    distributeStones.invoke(this.service, this.gameTurnNorth, 1);
    distributeStones.invoke(this.service, this.gameTurnSouth, 8);
  }

  @Test
  @DirtiesContext
  public void testCreateGame() {
    final Game game = this.service.createGame();

    Assert.assertNotNull(game);
  }

  @Test
  @DirtiesContext
  public void testPlay() {
    final Game game = this.service.createGame();
    this.service.play(game.getId(), 6);

    Assert.assertEquals(Player.PLAYER_SOUTH, game.getTurn());
    Assert.assertNull(game.getWinner());
    Assert.assertEquals(31, game.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
  }

  @Test
  public void testDecidePlayerTurn() {
    Assert.assertNull(this.gameInitial.getTurn());
    Assert.assertEquals(Player.PLAYER_NORTH, this.gameNorthMovedFirst.getTurn());
    Assert.assertEquals(Player.PLAYER_NORTH, this.gameSouthMovedFirst.getTurn());
  }

  @Test
  public void testDetermineWinner()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    final Method determineWinner =
        openMethodForTest(service.getClass().getDeclaredMethod("determineWinner", Game.class));
    determineWinner.invoke(this.service, this.gameInitial);
    determineWinner.invoke(this.service, this.gameFinishedWinnerSouth);
    determineWinner.invoke(this.service, this.gameFinishedWinnerNorth);

    Assert.assertNull(this.gameInitial.getWinner());
    Assert.assertEquals(Player.PLAYER_SOUTH, this.gameFinishedWinnerSouth.getWinner());
    Assert.assertEquals(Player.PLAYER_NORTH, this.gameFinishedWinnerNorth.getWinner());
  }

  @Test
  public void testCheckGameOver()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    final Method checkGameOver =
        openMethodForTest(service.getClass().getDeclaredMethod("checkGameOver", Game.class));
    checkGameOver.invoke(this.service, this.gameInitial);
    checkGameOver.invoke(this.service, this.gameFinishedWinnerSouth);
    checkGameOver.invoke(this.service, this.gameFinishedWinnerNorth);

    Assert.assertEquals(36, this.gameInitial.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
    Assert.assertEquals(36, this.gameInitial.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
    Assert.assertEquals(10,
        this.gameFinishedWinnerSouth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
    Assert.assertEquals(62,

        this.gameFinishedWinnerSouth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
    Assert.assertEquals(40,
        this.gameFinishedWinnerNorth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
    Assert.assertEquals(32,
        this.gameFinishedWinnerNorth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
  }

  @Test(expected = IllegalMoveException.class)
  public void testInvalidMovePlayerNorthTurn() throws Throwable {
    try {
      final Method validateMove = openMethodForTest(
          service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
      validateMove.invoke(this.service, this.gameTurnNorth, 12);
    } catch (final Exception e) {
      if (e instanceof InvocationTargetException) {
        throw ((InvocationTargetException) e).getTargetException();
      } else {
        throw e;
      }
    }
  }

  @Test(expected = IllegalMoveException.class)
  public void testInvalidMovePlayerSouthTurn() throws Throwable {
    try {
      final Method validateMove = openMethodForTest(
          service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
      validateMove.invoke(this.service, this.gameTurnSouth, 1);
    } catch (final Exception e) {
      if (e instanceof InvocationTargetException) {
        throw ((InvocationTargetException) e).getTargetException();
      } else {
        throw e;
      }
    }
  }

  @Test(expected = IllegalMoveException.class)
  public void testInvalidMoveStartFromEmptyPit() throws Throwable {
    try {
      final Method validateMove = openMethodForTest(
          service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
      validateMove.invoke(this.service, this.gameTurnNorth, 1);
    } catch (final Exception e) {
      if (e instanceof InvocationTargetException) {
        throw ((InvocationTargetException) e).getTargetException();
      } else {
        throw e;
      }
    }
  }

  @Test(expected = IllegalMoveException.class)
  public void testInvalidMoveStartFromHouse() throws Throwable {
    try {
      final Method validateMove = openMethodForTest(
          service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
      validateMove.invoke(this.service, this.gameInitial, Player.PLAYER_NORTH.getHouseIndex());
    } catch (final Exception e) {
      if (e instanceof InvocationTargetException) {
        throw ((InvocationTargetException) e).getTargetException();
      } else {
        throw e;
      }
    }
  }

  @Test
  public void testLastEmptyPit()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    final Method resetBoard =
        openMethodForTest(service.getClass().getDeclaredMethod("resetBoard", Game.class));
    final Method lastEmptyPit = openMethodForTest(
        service.getClass().getDeclaredMethod("lastEmptyPit", Game.class, int.class));

    resetBoard.invoke(this.service, this.gameTurnNorth);
    this.gameTurnNorth.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex()).setStoneCount(0);
    this.gameTurnNorth.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex()).setStoneCount(0);
    this.gameTurnNorth.getBoard().getPit(4).setStoneCount(1);
    this.gameTurnNorth.getBoard().getPit(10).setStoneCount(6);

    lastEmptyPit.invoke(this.service, this.gameTurnNorth, 4);

    Assert.assertEquals(7, this.gameTurnNorth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
    Assert.assertEquals(0, this.gameTurnNorth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
  }

  @Test
  public void testValidMovePlayerNorth()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    final Method validateMove = openMethodForTest(
        service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
    validateMove.invoke(this.service, this.gameInitial, 1);
    Assert.assertEquals(Player.PLAYER_NORTH, this.gameInitial.getTurn());
  }

  @Test
  public void testValidMovePlayerSouth()
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    final Method validateMove = openMethodForTest(
        service.getClass().getDeclaredMethod("validateMove", Game.class, int.class));
    validateMove.invoke(this.service, this.gameInitial, 13);
    Assert.assertEquals(Player.PLAYER_SOUTH, this.gameInitial.getTurn());
  }

  private Method openMethodForTest(final Method method) {
    ReflectionUtils.makeAccessible(method);
    return method;
  }
}
