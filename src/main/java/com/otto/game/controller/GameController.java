package com.otto.game.controller;


import com.otto.game.dto.GameResult;
import com.otto.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @Autowired
    GameService gameService;

    @GetMapping("/play")
    public ResponseEntity<GameResult> playGame() {
        GameResult gameResult = gameService.playThreeRounds();

        System.out.print("qqqq"+gameResult.toString());
        return new ResponseEntity<>(gameResult, HttpStatus.OK);
    }
}


