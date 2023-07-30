package com.otto.game.util;

import com.otto.game.dto.HandSign;
import org.springframework.stereotype.Component;

@Component
public class GameUtil {
    public HandSign getRandomSign() {
        HandSign[] values = HandSign.values();
        int randomIndex = (int) (Math.random() * values.length);
        return values[randomIndex];
    }
}
