package info.aydinoglu.baris.kalah.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerTest {

  @Test
  public void testHouseIndex() {
    final Player givenPlayer1 = Player.PLAYER_NORTH;
    final Player givenPlayer2 = Player.PLAYER_SOUTH;

    Assert.assertEquals(7, givenPlayer1.getHouseIndex());
    Assert.assertEquals(14, givenPlayer2.getHouseIndex());
  }
}
