package info.aydinoglu.baris.kalah.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PitTest {

  @Test
  public void testDistributable() {
    final Pit givenPit1 = new Pit(1);
    final Pit givenPit2 = new Pit(14);
    final Pit givenPit3 = new Pit(7);

    Assert.assertTrue(givenPit1.isDistributable(Player.PLAYER_NORTH));
    Assert.assertTrue(givenPit2.isDistributable(Player.PLAYER_SOUTH));
    Assert.assertFalse(givenPit2.isDistributable(Player.PLAYER_NORTH));
    Assert.assertFalse(givenPit3.isDistributable(Player.PLAYER_SOUTH));
  }

  @Test
  public void testHouse() {
    final Pit givenPit1 = new Pit(7);
    final Pit givenPit2 = new Pit(14);
    final Pit givenPit3 = new Pit(3);
    final Pit givenPit4 = new Pit(9);

    Assert.assertTrue(givenPit1.isHouse());
    Assert.assertTrue(givenPit2.isHouse());
    Assert.assertFalse(givenPit3.isHouse());
    Assert.assertFalse(givenPit4.isHouse());
  }

  @Test
  public void testInitialization() {
    final Pit givenPit1 = new Pit(1);
    final Pit givenPit2 = new Pit(14);
    final Pit givenPit3 = new Pit(7);

    Assert.assertEquals(1, givenPit1.getId());
    Assert.assertEquals(14, givenPit2.getId());
    Assert.assertEquals(7, givenPit3.getId());
  }

  @Test
  public void testInitialStoneCount() {
    final Pit givenPit1 = new Pit(1);
    final Pit givenPit2 = new Pit(7);

    Assert.assertEquals(6, givenPit1.getStoneCount());
    Assert.assertEquals(0, givenPit2.getStoneCount());
  }

  @Test
  public void testOwner() {
    final Pit givenPit1 = new Pit(4);
    final Pit givenPit2 = new Pit(7);
    final Pit givenPit3 = new Pit(10);
    final Pit givenPit4 = new Pit(14);

    Assert.assertEquals(Player.PLAYER_NORTH, givenPit1.getOwner());
    Assert.assertEquals(Player.PLAYER_NORTH, givenPit2.getOwner());
    Assert.assertEquals(Player.PLAYER_SOUTH, givenPit3.getOwner());
    Assert.assertEquals(Player.PLAYER_SOUTH, givenPit4.getOwner());
  }

  @Test
  public void testStoneCountSet() {
    final Pit givenPit = new Pit(1);
    givenPit.setStoneCount(7);

    Assert.assertEquals(7, givenPit.getStoneCount());
  }
}
