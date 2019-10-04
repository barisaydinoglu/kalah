package info.aydinoglu.baris.kalah.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameTest {

    @Test
    public void testInitialization() {
        final Game givenGame = new Game();

        Assert.assertNotNull(givenGame.getBoard());
        Assert.assertNull(givenGame.getTurn());
        Assert.assertNull(givenGame.getWinner());
    }
}
