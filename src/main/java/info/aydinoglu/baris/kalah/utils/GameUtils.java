package info.aydinoglu.baris.kalah.utils;

import info.aydinoglu.baris.kalah.model.*;
import info.aydinoglu.baris.kalah.model.exception.IllegalMoveException;

public class GameUtils {

    private GameUtils() {

    }

    public static void checkGameOver(final Game game) {
        final int playerNorthPitStoneCount = game.getBoard().getStoneCount(Player.PLAYER_NORTH, false);
        final int playerSouthPitStoneCount = game.getBoard().getStoneCount(Player.PLAYER_SOUTH, false);
        if ((playerNorthPitStoneCount == 0) || (playerSouthPitStoneCount == 0)) {
            final Pit houseNorth = game.getBoard().getPit(Player.PLAYER_NORTH.getHouseIndex());
            final Pit houseSouth = game.getBoard().getPit(Player.PLAYER_SOUTH.getHouseIndex());
            houseNorth.setStoneCount(houseNorth.getStoneCount() + playerNorthPitStoneCount);
            houseSouth.setStoneCount(houseSouth.getStoneCount() + playerSouthPitStoneCount);
            GameUtils.determineWinner(game);
            GameUtils.resetBoard(game);
        }
    }

    public static void decidePlayerTurn(final Game game, final int pitIndex) {
        final Pit pit = game.getBoard().getPit(pitIndex);
        if (pit.isHouse() && Player.PLAYER_NORTH.equals(pit.getOwner()) && Player.PLAYER_NORTH.equals(game.getTurn())) {
            game.setTurn(Player.PLAYER_NORTH);
        } else if (pit.isHouse() && Player.PLAYER_SOUTH.equals(pit.getOwner())
                   && Player.PLAYER_SOUTH.equals(game.getTurn())) {
            game.setTurn(Player.PLAYER_SOUTH);
        } else {
            if (Player.PLAYER_NORTH.equals(game.getTurn())) {
                game.setTurn(Player.PLAYER_SOUTH);
            } else {
                game.setTurn(Player.PLAYER_NORTH);
            }
        }
    }

    public static void determineWinner(final Game game) {
        final int houseNorthStoneCount = game.getBoard().getStoneCount(Player.PLAYER_NORTH, true);
        final int houseSouthStoneCount = game.getBoard().getStoneCount(Player.PLAYER_SOUTH, true);
        if (houseNorthStoneCount > houseSouthStoneCount) {
            game.setWinner(Player.PLAYER_NORTH);
        } else if (houseNorthStoneCount < houseSouthStoneCount) {
            game.setWinner(Player.PLAYER_SOUTH);
        }
    }

    public static void distributeStones(final Game game, int pitIndex) {
        Pit pit = game.getBoard().getPit(pitIndex);
        GameUtils.validateMove(game, pitIndex);
        int stoneToDistribute = pit.getStoneCount();
        pit.setStoneCount(0);
        while (stoneToDistribute > 0) {
            pit = game.getBoard().getPit(++pitIndex);
            if (pit.isDistributable(game.getTurn())) {
                pit.setStoneCount(pit.getStoneCount() + 1);
                stoneToDistribute--;
            }
        }
        GameUtils.lastEmptyPit(game, pitIndex);
        GameUtils.decidePlayerTurn(game, pitIndex);
    }

    public static void lastEmptyPit(final Game game, final int endPitIndex) {
        final Pit endPit = game.getBoard().getPit(endPitIndex);
        if (!endPit.isHouse() && endPit.getOwner().equals(game.getTurn()) && (endPit.getStoneCount() == 1)) {
            final Pit oppositePit = game.getBoard().getPit(Board.PIT_END_INDEX - endPit.getIndex());
            if (oppositePit.getStoneCount() > 0) {
                final Pit house = game.getBoard().getPit(endPit.getOwner().getHouseIndex());
                house.setStoneCount((house.getStoneCount() + oppositePit.getStoneCount()) + endPit.getStoneCount());
                oppositePit.setStoneCount(0);
                endPit.setStoneCount(0);
            }
        }
    }

    public static void resetBoard(final Game game) {
        for (final Pit pit : game.getBoard().getPits()) {
            if ((Player.PLAYER_NORTH.getHouseIndex() == pit.getIndex())
                || (Player.PLAYER_SOUTH.getHouseIndex() == pit.getIndex())) {
                continue;
            }
            pit.setStoneCount(0);
        }
    }

    public static void validateMove(final Game game, final int startPitIndex) {
        final Pit startPit = game.getBoard().getPit(startPitIndex);
        if (startPit.isHouse()) {
            throw new IllegalMoveException("Can not start from house");
        }
        if (Player.PLAYER_NORTH.equals(game.getTurn()) && !Player.PLAYER_NORTH.equals(startPit.getOwner())) {
            throw new IllegalMoveException("It's Player North's turn");
        }
        if (Player.PLAYER_SOUTH.equals(game.getTurn()) && !Player.PLAYER_SOUTH.equals(startPit.getOwner())) {
            throw new IllegalMoveException("It's Player South's turn");
        }
        if (startPit.getStoneCount() == 0) {
            throw new IllegalMoveException("Can not start from empty pit");
        }
        if (game.getTurn() == null) {
            if (Player.PLAYER_NORTH.equals(startPit.getOwner())) {
                game.setTurn(Player.PLAYER_NORTH);
            } else {
                game.setTurn(Player.PLAYER_SOUTH);
            }
        }
    }

}
