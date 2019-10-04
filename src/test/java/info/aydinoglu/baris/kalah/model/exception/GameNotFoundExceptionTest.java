package info.aydinoglu.baris.kalah.model.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameNotFoundExceptionTest {

    @Test
    public void testInitialization() {
        final GameNotFoundException givenGameNotFoundException = new GameNotFoundException("game1");

        Assert.assertEquals("Could not find game game1", givenGameNotFoundException.getMessage());
    }
}
