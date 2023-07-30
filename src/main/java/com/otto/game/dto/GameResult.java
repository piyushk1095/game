package com.otto.game.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResult {
    // TODO : CleanUp above
    private int gameId;
    private List<String> gameResults;
    private int remainingAttempts;
    private String finalWinner;

}
