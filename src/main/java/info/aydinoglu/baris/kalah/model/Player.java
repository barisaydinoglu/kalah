package info.aydinoglu.baris.kalah.model;

public enum Player {
    PLAYER_NORTH(Board.PIT_END_INDEX / 2),
    PLAYER_SOUTH(Board.PIT_END_INDEX);

    private int houseIndex;

    Player(final int houseIndex) {
        this.houseIndex = houseIndex;
    }

    public int getHouseIndex() {
        return this.houseIndex;
    }
}
