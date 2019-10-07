package info.aydinoglu.baris.kalah.model.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IllegalMoveExceptionTest {

  @Test
  public void testInitialization() {
    final IllegalMoveException givenIllegalMoveException = new IllegalMoveException("move1");

    Assert.assertEquals("Illegal move: move1", givenIllegalMoveException.getMessage());
  }
}
