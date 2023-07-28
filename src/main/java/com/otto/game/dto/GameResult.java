package com.otto.game.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class GameResult {
    private int player1Wins;
    private int player2Wins;
    private int ties;
    private String gameResult;

    public GameResult(int player1Wins, int player2Wins, int ties, String gameResult) {
        this.player1Wins = player1Wins;
        this.player2Wins = player2Wins;
        this.ties = ties;
        this.gameResult = gameResult;
    }
}
