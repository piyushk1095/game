package com.otto.game.controller;


import com.otto.game.Model.Game;
import com.otto.game.dto.GameResult;
import com.otto.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @Autowired
    GameService gameService;

/*
    @GetMapping("/play")
    public ResponseEntity<GameResult> playGame() {
     //   GameResult gameResult = gameService.playThreeRounds();
        return new ResponseEntity<>(gameResult, HttpStatus.OK);
    }
*/

    @PostMapping("/play")
    public GameResult playRockScissorPaper(@RequestBody Game game) {
        return gameService.playThreeRounds(game);
    }
}


