package info.aydinoglu.baris.kalah.controller;

import javax.annotation.PostConstruct;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import info.aydinoglu.baris.kalah.model.Game;
import info.aydinoglu.baris.kalah.service.IGameService;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GameControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private IGameService service;

    private MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DirtiesContext
    public void testNewGame() throws Exception {
        final MockHttpServletRequestBuilder playGameRequest = MockMvcRequestBuilders.post("/games");
        this.mockMvc.perform(playGameRequest).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(14)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[4].index").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[4].stoneCount").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[13].stoneCount").value(0)).andReturn();
    }

    @Test
    @DirtiesContext
    public void testPlay() throws Exception {
        final Game game = this.service.newGame();
        final MockHttpServletRequestBuilder playGameRequest = MockMvcRequestBuilders
                .put("/games/" + game.getId() + "/pits/1");
        this.mockMvc.perform(playGameRequest).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.turn").value("PLAYER_NORTH"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(14)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[0].stoneCount").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[1].stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[2].stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[3].stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[4].stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[5].stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[6].stoneCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[7].stoneCount").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[8].stoneCount").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[9].stoneCount").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[10].stoneCount").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[11].stoneCount").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[12].stoneCount").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pits[13].stoneCount").value(0)).andReturn();
    }

}
