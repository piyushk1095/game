package com.otto.game.controller;


import com.otto.game.Model.Game;
import com.otto.game.Model.GameResult;
import com.otto.game.service.GameServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @Autowired
    GameServiceImpl gameService;

    @PostMapping("/play")
    public ResponseEntity<?> playRockScissorPaper(@RequestBody Game game) {
        if (game.getPlayerOneHandSign() != null && game.getPlayerTwoHandSign() != null) {
            GameResult result = gameService.playThreeRounds(game);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            String errorMessage = "Both playerOneHandSign and playerTwoHandSign must be provided.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}


