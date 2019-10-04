package info.aydinoglu.baris.kalah.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import info.aydinoglu.baris.kalah.model.Game;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository repository;

    @Test
    @DirtiesContext
    public void testFind() {
        final Game given = this.repository.save(new Game());
        final Game when = this.repository.find(given.getId());

        Assert.assertNotNull(given);
        Assert.assertEquals(given, when);
    }

    @Test
    @DirtiesContext
    public void testSave() {
        final Game game = this.repository.save(new Game());

        Assert.assertNotNull(game);
    }
}
