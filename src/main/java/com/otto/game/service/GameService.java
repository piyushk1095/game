package com.otto.game.service;

import com.otto.game.Model.Game;
import com.otto.game.Model.GameResult;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface GameService {
   public GameResult playGame(Game game);
}
