package info.aydinoglu.baris.kalah.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pit {

    private final int id;
    private int stoneCount;

    @JsonCreator
    public Pit(@JsonProperty("id") final int id) {
        this.id = id;
        if (!this.isHouse()) {
            this.setStoneCount(6);
        }
    }

    public int getId() {
        return this.id;
    }

    public Player getOwner() {
        if (this.getId() <= Player.PLAYER_NORTH.getHouseIndex()) {
            return Player.PLAYER_NORTH;
        } else {
            return Player.PLAYER_SOUTH;
        }
    }

    public int getStoneCount() {
        return this.stoneCount;
    }

    public void setStoneCount(final int stoneCount) {
        this.stoneCount = stoneCount;
    }

    public boolean isDistributable(final Player turn) {
        return (!turn.equals(Player.PLAYER_NORTH)
                || (this.getId() != Player.PLAYER_SOUTH.getHouseIndex()))
               && (!turn.equals(Player.PLAYER_SOUTH)
                   || (this.getId() != Player.PLAYER_NORTH.getHouseIndex()));
    }

    public boolean isHouse() {
        return (this.getId() == Player.PLAYER_NORTH.getHouseIndex())
               || (this.getId() == Player.PLAYER_SOUTH.getHouseIndex());
    }
}
