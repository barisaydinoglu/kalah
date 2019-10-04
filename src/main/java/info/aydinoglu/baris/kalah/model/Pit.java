package info.aydinoglu.baris.kalah.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pit {

    private final int index;
    private int stoneCount;

    @JsonCreator
    public Pit(@JsonProperty("index") final int index) {
        this.index = index;
        if (!this.isHouse()) {
            this.setStoneCount(6);
        }
    }

    public int getIndex() {
        return this.index;
    }

    public Player getOwner() {
        if (this.getIndex() <= Player.PLAYER_NORTH.getHouseIndex()) {
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

    public Boolean isDistributable(final Player turn) {
        return (!turn.equals(Player.PLAYER_NORTH) || (this.getIndex() != Player.PLAYER_SOUTH.getHouseIndex()))
               && (!turn.equals(Player.PLAYER_SOUTH) || (this.getIndex() != Player.PLAYER_NORTH.getHouseIndex()));
    }

    public boolean isHouse() {
        return (this.getIndex() == Player.PLAYER_NORTH.getHouseIndex())
               || (this.getIndex() == Player.PLAYER_SOUTH.getHouseIndex());
    }
}
