package info.aydinoglu.baris.kalah.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int PIT_START_INDEX = 1;
    public static final int PIT_END_INDEX = 14;

    private final List<Pit> pits;

    public Board() {
        this.pits = new ArrayList<>();
        for (int i = Board.PIT_START_INDEX; i <= Board.PIT_END_INDEX; i++) {
            this.pits.add(new Pit(i));
        }
    }

    public Pit getPit(final int index) {
        return this.pits.get((index - 1) % Board.PIT_END_INDEX);
    }

    public List<Pit> getPits() {
        return this.pits;
    }

    public int getStoneCount(final Player player, final boolean includeHouse) {
        int count = 0;
        for (final Pit pit : this.getPits()) {
            if (pit.getOwner().equals(player) && (includeHouse || !pit.isHouse())) {
                count += pit.getStoneCount();
            }
        }
        return count;
    }
}
