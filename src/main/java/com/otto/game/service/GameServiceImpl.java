package com.otto.game.service;

import com.otto.game.Model.Game;
import com.otto.game.Model.GameResult;
import com.otto.game.Model.HandSign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService{

    @Value("${max.game.round}")
    int maxRound;
    int gameId = 0;
   public  Map<Integer, GameResult> gameMap = new ConcurrentHashMap<>();

    @Override
    public GameResult playGame(Game game) {
        if (!gameMap.containsKey(game.getGameId())) {
            // new player
            if(game.getGameId()==0) {
                game.setGameId(++gameId);
            }else {
                gameId=game.getGameId();
            }
            game.setRemainingAttempts(maxRound);
            return getGameResultPerRound(game, maxRound, true);
            // 2nd time or third
        } else if (gameMap.containsKey(game.getGameId()) && gameMap.get(game.getGameId()).getRemainingAttempts() > 0) {
            return getGameResultPerRound(game, gameMap.get(game.getGameId()).getRemainingAttempts(), false);
        } else {
            //Final Winner
            return getGameResultPerRound(game, gameMap.get(game.getGameId()).getRemainingAttempts(), false);
        }

    }

    public GameResult getGameResultPerRound(Game game, int remainingAttempts, boolean isFirst) {
        GameResult gameResult;
        if (isFirst) {
            gameResult = new GameResult();
            gameResult.setGameResults(new ArrayList<>());
        } else {
            gameResult = gameMap.get(game.getGameId());
        }

        if (remainingAttempts == 0) {
            List<String> resultList = gameMap.values()
                    .stream()
                    .filter(gameResultMap -> gameResultMap.getGameId() == game.getGameId())
                    .map(GameResult::getGameResults)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            gameResult.setFinalWinner(determineFinalWinner(game.getGameId(), gameMap));
            gameMap.put(game.getGameId(), gameResult);
            return new GameResult(game.getGameId(), resultList, remainingAttempts, determineFinalWinner(game.getGameId(), gameMap));
        } else {
            HandSign player1Sign = game.getPlayerOneHandSign();
            HandSign player2Sign = game.getPlayerTwoHandSign();
            int roundResult = determineRoundWinner(player1Sign, player2Sign);
            gameResult.setGameId(game.getGameId());
            gameResult.setRemainingAttempts(remainingAttempts - 1);
            if (roundResult == 1) {
                gameResult.getGameResults().add("Player1");
            } else if (roundResult == 2) {
                gameResult.getGameResults().add("Player2");
            } else {
                gameResult.getGameResults().add("tie");
            }
        }
        return gameResult;
    }

    private String determineFinalWinner(int gameId, Map<Integer, GameResult> gameMap) {
        long player1Wins=gameMap.values().stream()
                .map(GameResult::getGameResults)
                .flatMap(List::stream)
                .filter("Player1"::equals)
                .count();
        long player2Wins=gameMap.values().stream()
                .map(GameResult::getGameResults)
                .flatMap(List::stream)
                .filter("Player2"::equals)
                .count();
        String gameResult;
        if (player1Wins > player2Wins) {
            gameResult = "Player 1 wins";
        } else if (player2Wins > player1Wins) {
            gameResult = "Player 2 wins";
        } else {
            gameResult = "It's a tie";
        }
        return gameResult;
    }

    public int determineRoundWinner(HandSign player1Sign, HandSign player2Sign) {
        Map<HandSign, HandSign> winningCombinations = new HashMap<>();
        winningCombinations.put(HandSign.ROCK, HandSign.SCISSORS);
        winningCombinations.put(HandSign.SCISSORS, HandSign.PAPER);
        winningCombinations.put(HandSign.PAPER, HandSign.ROCK);

        if (player1Sign == player2Sign) {
            return 0; // Tie
        } else if (winningCombinations.get(player1Sign) == player2Sign) {
            return 1; // Player 1 wins
        } else {
            return 2; // Player 2 wins
        }
    }
}
