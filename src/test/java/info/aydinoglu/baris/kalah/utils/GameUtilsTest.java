package info.aydinoglu.baris.kalah.utils;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import info.aydinoglu.baris.kalah.model.Game;
import info.aydinoglu.baris.kalah.model.Player;
import info.aydinoglu.baris.kalah.model.exception.IllegalMoveException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameUtilsTest {

    private Game gameInitial;
    private Game gameFinishedWinnerSouth;
    private Game gameFinishedWinnerNorth;
    private Game gameNorthMovedFirst;
    private Game gameSouthMovedFirst;
    private Game gameTurnNorth;
    private Game gameTurnSouth;

    @Before
    public void setUpGames() throws Exception {
        this.gameInitial = new Game();
        this.gameFinishedWinnerSouth = new Game();
        this.gameFinishedWinnerNorth = new Game();
        this.gameNorthMovedFirst = new Game();
        this.gameSouthMovedFirst = new Game();
        this.gameTurnNorth = new Game();
        this.gameTurnSouth = new Game();

        GameUtils.resetBoard(this.gameFinishedWinnerSouth);
        this.gameFinishedWinnerSouth.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex()).setStoneCount(10);
        this.gameFinishedWinnerSouth.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex()).setStoneCount(62);
        GameUtils.resetBoard(this.gameFinishedWinnerNorth);
        this.gameFinishedWinnerNorth.getBoard().getPit(1).setStoneCount(1);
        this.gameFinishedWinnerNorth.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex()).setStoneCount(39);
        this.gameFinishedWinnerNorth.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex()).setStoneCount(32);

        GameUtils.distributeStones(this.gameNorthMovedFirst, 1);
        GameUtils.distributeStones(this.gameSouthMovedFirst, 10);
        GameUtils.distributeStones(this.gameTurnNorth, 1);
        GameUtils.distributeStones(this.gameTurnSouth, 8);
    }

    @Test
    public void testCheckGameOver() {
        GameUtils.checkGameOver(this.gameInitial);
        GameUtils.checkGameOver(this.gameFinishedWinnerSouth);
        GameUtils.checkGameOver(this.gameFinishedWinnerNorth);
        this.testDetermineWinner();

        Assert.assertEquals(36, this.gameInitial.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(36, this.gameInitial.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
        Assert.assertEquals(10, this.gameFinishedWinnerSouth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(62, this.gameFinishedWinnerSouth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
        Assert.assertEquals(40, this.gameFinishedWinnerNorth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(32, this.gameFinishedWinnerNorth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
    }

    @Test
    public void testDecidePlayerTurn() {
        Assert.assertNull(this.gameInitial.getTurn());
        Assert.assertEquals(Player.PLAYER_NORTH, this.gameNorthMovedFirst.getTurn());
        Assert.assertEquals(Player.PLAYER_NORTH, this.gameSouthMovedFirst.getTurn());
    }

    @Test
    public void testDetermineWinner() {
        GameUtils.determineWinner(this.gameInitial);
        GameUtils.determineWinner(this.gameFinishedWinnerSouth);
        GameUtils.determineWinner(this.gameFinishedWinnerNorth);

        Assert.assertNull(this.gameInitial.getWinner());
        Assert.assertEquals(Player.PLAYER_SOUTH, this.gameFinishedWinnerSouth.getWinner());
        Assert.assertEquals(Player.PLAYER_NORTH, this.gameFinishedWinnerNorth.getWinner());
    }

    @Test(expected = IllegalMoveException.class)
    public void testInvalidMovePlayerNorthTurn() {
        GameUtils.validateMove(this.gameTurnNorth, 12);
    }

    @Test(expected = IllegalMoveException.class)
    public void testInvalidMovePlayerSouthTurn() {
        GameUtils.validateMove(this.gameTurnSouth, 1);
    }

    @Test(expected = IllegalMoveException.class)
    public void testInvalidMoveStartFromEmptyPit() {
        GameUtils.validateMove(this.gameTurnNorth, 1);
    }

    @Test(expected = IllegalMoveException.class)
    public void testInvalidMoveStartFromHouse() {
        GameUtils.validateMove(this.gameInitial, Player.PLAYER_NORTH.getHouseIndex());
    }

    @Test
    public void testLastEmptyPit() {
        GameUtils.resetBoard(this.gameTurnNorth);
        this.gameTurnNorth.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex()).setStoneCount(0);
        this.gameTurnNorth.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex()).setStoneCount(0);
        this.gameTurnNorth.getBoard().getPit(4).setStoneCount(1);
        this.gameTurnNorth.getBoard().getPit(10).setStoneCount(6);

        GameUtils.lastEmptyPit(this.gameTurnNorth, 4);

        Assert.assertEquals(7, this.gameTurnNorth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(0, this.gameTurnNorth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
    }

    @Test
    public void testResetBoard() {
        GameUtils.resetBoard(this.gameInitial);
        GameUtils.resetBoard(this.gameFinishedWinnerSouth);
        GameUtils.resetBoard(this.gameFinishedWinnerNorth);

        Assert.assertEquals(0, this.gameInitial.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(0, this.gameInitial.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
        Assert.assertEquals(10, this.gameFinishedWinnerSouth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(62, this.gameFinishedWinnerSouth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
        Assert.assertEquals(39, this.gameFinishedWinnerNorth.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
        Assert.assertEquals(32, this.gameFinishedWinnerNorth.getBoard().getStoneCount(Player.PLAYER_SOUTH, true));
    }

    @Test
    public void testValidMovePlayerNorth() {
        GameUtils.validateMove(this.gameInitial, 1);
        Assert.assertEquals(Player.PLAYER_NORTH, this.gameInitial.getTurn());
    }

    @Test
    public void testValidMovePlayerSouth() {
        GameUtils.validateMove(this.gameInitial, 13);
        Assert.assertEquals(Player.PLAYER_SOUTH, this.gameInitial.getTurn());
    }

}
