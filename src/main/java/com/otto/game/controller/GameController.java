package com.otto.game.controller;


import com.otto.game.Model.Game;
import com.otto.game.Model.GameResult;
import com.otto.game.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GameController {
    @Autowired
    GameServiceImpl gameService;

    @PostMapping("/play")
    public ResponseEntity<?> playRockScissorPaper(@RequestBody Game game) {
        if (game.getPlayerOneHandSign() != null && game.getPlayerTwoHandSign() != null) {
            GameResult result = gameService.playGame(game);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            String errorMessage = "Both playerOneHandSign and playerTwoHandSign must be provided.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("winner/{gameId}")
    public ResponseEntity<?>  getGameResultById(@PathVariable int gameId) {
        if (gameService.gameMap.containsKey(gameId)) {
            return new ResponseEntity<>(gameService.gameMap.get(gameId), HttpStatus.OK);
        } else {
            String errorMessage = "Invalid ID please provide the valid gameid otherwise play again";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}


