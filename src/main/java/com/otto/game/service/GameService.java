package com.otto.game.service;

import com.otto.game.dto.GameResult;
import com.otto.game.dto.HandSign;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    public GameResult playThreeRounds() {
        int player1Wins = 0;
        int player2Wins = 0;
        int ties = 0;

        for (int round = 1; round <= 3; round++) {
            HandSign player1Sign = getRandomSign();
            HandSign player2Sign = getRandomSign();

            // Determine the winner of the round
            int roundResult = determineRoundWinner(player1Sign, player2Sign);

            // Update the counts
            if (roundResult == 1) {
                player1Wins++;
            } else if (roundResult == 2) {
                player2Wins++;
            } else {
                ties++;
            }
        }

        String gameResult;
        if (player1Wins > player2Wins) {
            gameResult = "Player 1 wins";
        } else if (player2Wins > player1Wins) {
            gameResult = "Player 2 wins";
        } else {
            gameResult = "It's a tie";
        }

        return new GameResult(player1Wins, player2Wins, ties, gameResult);
    }

    public HandSign getRandomSign() {
        HandSign[] values = HandSign.values();
        int randomIndex = (int) (Math.random() * values.length);
        return values[randomIndex];
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
