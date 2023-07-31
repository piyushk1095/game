package com.otto.game.service;


import com.otto.game.Model.Game;
import com.otto.game.Model.GameResult;
import com.otto.game.Model.HandSign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GameServiceTest {
    private GameServiceImpl gameService;

    @BeforeEach
    public void setup() {
        gameService = new GameServiceImpl();
        gameService.maxRound = 3;// Set maxRound for testing purposes
        gameService.gameId = 0;
    }

    @Test
    public void testPlayThreeRoundsNewPlayer() {
        // Given
        Game game = new Game();
        game.setPlayerOneHandSign(HandSign.ROCK);
        game.setPlayerTwoHandSign(HandSign.SCISSORS);

        // When
        GameResult gameResult = gameService.playGame(game);

        // Then
        assertNotNull(gameResult);
        assertEquals(1, gameResult.getGameId());
        assertEquals(2, gameResult.getRemainingAttempts());
        assertEquals(1, gameResult.getGameResults().size());
        assertEquals("Player1", gameResult.getGameResults().get(0));
    }

    @Test
    public void testPlayThreeRoundsExistingPlayer() {
        // Given
        Game game = new Game();

        game.setGameId(1); // An existing game ID
        game.setPlayerOneHandSign(HandSign.SCISSORS);
        game.setPlayerTwoHandSign(HandSign.ROCK);
        gameService.gameId = 1;
        gameService.gameMap.put(1, new GameResult(1, new ArrayList<>(), 2, null)); // Add existing game result
        // When
        GameResult gameResult = gameService.playGame(game);

        // Then
        assertNotNull(gameResult);
        assertEquals(1, gameResult.getGameId());
        assertEquals(1, gameResult.getRemainingAttempts());
        assertEquals(1, gameResult.getGameResults().size());
        assertEquals("Player2", gameResult.getGameResults().get(0));
    }


    @Test
    public void testPlayTie() {
        // Given
        Game game = new Game();

        game.setGameId(1); // An existing game ID
        game.setPlayerOneHandSign(HandSign.ROCK);
        game.setPlayerTwoHandSign(HandSign.ROCK);
        gameService.gameId = 1;
        gameService.gameMap.put(1, new GameResult(1, new ArrayList<>(), 2, null)); // Add existing game result
        // When
        GameResult gameResult = gameService.playGame(game);

        // Then
        assertNotNull(gameResult);
        assertEquals(1, gameResult.getGameId());
        assertEquals(1, gameResult.getRemainingAttempts());
        assertEquals(1, gameResult.getGameResults().size());
        assertEquals("tie", gameResult.getGameResults().get(0));
    }

    @Test
    public void testPlayThreeRoundsFinalWinner() {
        // Given
        Game game = new Game();
        game.setGameId(2); // An existing game ID
        game.setPlayerOneHandSign(HandSign.SCISSORS);
        game.setPlayerTwoHandSign(HandSign.SCISSORS);
        gameService.gameMap.put(2, new GameResult(2, List.of("Player1", "Player2", "tie"), 0, null)); // Add existing game result

        // When
        GameResult gameResult = gameService.playGame(game);

        // Then
        assertNotNull(gameResult);
        assertEquals(2, gameResult.getGameId());
        assertEquals(0, gameResult.getRemainingAttempts());
        assertEquals(3, gameResult.getGameResults().size());
        assertEquals("Player1", gameResult.getGameResults().get(0));
        assertEquals("Player2", gameResult.getGameResults().get(1));
        assertEquals("tie", gameResult.getGameResults().get(2));
    }


    @Test
    public void testDetermineRoundWinner() {
        // Given
        HandSign rock = HandSign.ROCK;
        HandSign paper = HandSign.PAPER;
        HandSign scissors = HandSign.SCISSORS;

        // When & Then
        assertEquals(0, gameService.determineRoundWinner(rock, rock));
        assertEquals(1, gameService.determineRoundWinner(rock, scissors));
        assertEquals(1, gameService.determineRoundWinner(scissors, paper));
        assertEquals(2, gameService.determineRoundWinner(paper, scissors));
    }
}
