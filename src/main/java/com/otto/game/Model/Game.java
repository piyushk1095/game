package com.otto.game.Model;

import com.otto.game.dto.HandSign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private int gameId;
    private HandSign playerOneHandSign;
    private HandSign playerTwoHandSign;
    private int remainingAttempts;
}
