package com.otto.game.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResult {
    private int gameId;
    private List<String> gameResults;
    private int remainingAttempts;
    private String finalWinner;
}
