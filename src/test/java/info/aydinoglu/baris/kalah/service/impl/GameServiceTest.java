package info.aydinoglu.baris.kalah.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import info.aydinoglu.baris.kalah.model.Game;
import info.aydinoglu.baris.kalah.model.Player;
import info.aydinoglu.baris.kalah.service.IGameService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

    @Autowired
    private IGameService service;

    @Test
    @DirtiesContext
    public void testNewGame() {
        final Game game = this.service.newGame();

        Assert.assertNotNull(game);
    }

    @Test
    @DirtiesContext
    public void testPlay() {
        final Game game = this.service.newGame();
        this.service.play(game.getId(), 6);

        Assert.assertEquals(Player.PLAYER_SOUTH, game.getTurn());
        Assert.assertNull(game.getWinner());
        Assert.assertEquals(31, game.getBoard().getStoneCount(Player.PLAYER_NORTH, true));
    }
}
