package com.otto.game.service;

import com.otto.game.Model.Game;
import com.otto.game.dto.GameResult;
import com.otto.game.dto.HandSign;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GameService {
    // TODO : make AtomicInteger
    private int maxRound = 3;
    int gameId = 0;
    Map<Integer, GameResult> gameMap = new ConcurrentHashMap<>();

    public GameResult playThreeRounds(Game game) {
        if (!gameMap.containsKey(game.getGameId())) {
            // new player
            game.setGameId(gameId++);
            game.setRemainingAttempts(maxRound);
            return getGameResultPerRound(game, maxRound, true);
            // 2nd time or third
        } else if (gameMap.containsKey(game.getGameId()) && gameMap.get(game.getGameId()).getRemainingAttempts() > 0) {
            return getGameResultPerRound(game, gameMap.get(game.getGameId()).getRemainingAttempts(), false);
        } else {
            // end of the game
            System.out.println("Attempt exceed");
            return getGameResultPerRound(game, gameMap.get(game.getGameId()).getRemainingAttempts(), false);
        }

    }

    public GameResult getGameResultPerRound(Game game, int remainingAttempts, boolean isFirst) {
        GameResult gameResult;
        if (isFirst) {
            gameResult = new GameResult();
            gameResult.setGameResults(new ArrayList<>());
        } else {
            gameResult = gameMap.get(gameId);
        }

        if (remainingAttempts == 0) {
            List<String> resultList = gameMap.values()
                    .stream()
                    .filter(gameResultMap -> gameResultMap.getGameId() == game.getGameId())
                    .map(GameResult::getGameResults)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            return new GameResult(game.getGameId(), resultList, remainingAttempts, getResult(game.getGameId(), gameMap));
        } else {
            HandSign player1Sign = game.getPlayerOneHandSign();
            HandSign player2Sign = game.getPlayerTwoHandSign();
            int roundResult = determineRoundWinner(player1Sign, player2Sign);
            gameResult.setGameId(gameId);
            gameResult.setRemainingAttempts(remainingAttempts - 1);
            if (roundResult == 1) {
                gameResult.getGameResults().add("Player1");
            } else if (roundResult == 2) {
                gameResult.getGameResults().add("Player2");
            } else {
                gameResult.getGameResults().add("tie");
            }
            gameMap.put(gameId, gameResult);
        }
        return gameResult;
    }

    private String getResult(int gameId, Map<Integer, GameResult> gameMap) {
        return gameMap.get(gameId).getGameResults().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public int determineRoundWinner(HandSign player1Sign, HandSign player2Sign) {
        if (player1Sign == player2Sign) {
            return 0; // Tie
        } else if (player1Sign == HandSign.ROCK && player2Sign == HandSign.SCISSORS) {
            return 1; // Player 1 wins
        } else if (player1Sign == HandSign.SCISSORS && player2Sign == HandSign.PAPER) {
            return 1; // Player 1 wins
        } else if (player1Sign == HandSign.PAPER && player2Sign == HandSign.ROCK) {
            return 1; // Player 1 wins
        } else {
            return 2; // Player 2 wins
        }
    }
}
