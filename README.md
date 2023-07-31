# Game


    Rock, Paper, Scissors is a simple game usually played between two people. The players count to three in unison and simultaneously throw one of three hand signs representing rock, paper, or scissors. The winner of the game is determined by the rules below:

        1. **Rock** crushes scissors (Rock wins) 
        2. **Scissors** cuts paper (Scissors wins)
        3. **Paper** covers rock (Paper wins)

# Requirements
         1. Java 17
         2. Maven

# Setup

    1. Build the project using the following command:
        mvn clean install
    2. Run the application using the following command:
        mvn spring-boot:run
    3. Open a web browser and navigate to http://localhost:8080/play
    4. To run the test case 
        mvn test


# Input

    The /play API endpoint is a web service that allows two players to play a hand-sign game against each other. 
    Players can submit their chosen hand signs,and the API will determine the winner based on the rules of the game

    - playerOneHandSign: The chosen hand sign of player one. It can be one of the following values: "ROCK", "PAPER", or "SCISSORS".
    - playerTwoHandSign: The chosen hand sign of player two. It can be one of the following values: "ROCK", "PAPER", or "SCISSORS".

```Bash
    http://localhost:8080/play
```    
### Request Body:-


### Game Round 1
```jsonResponse
The API accepts a POST request with the following JSON structure as the request body:

    {
      "playerOneHandSign": "PAPER",
      "playerTwoHandSign": "ROCK"
    }
```

#### Output

```jsonResponse
    {
        "gameId": 1,
            "gameResults": [
               "Player1"
            ],
        "remainingAttempts": 2,
        "finalWinner": null
    }
```

#### Game Round 2 & 3
```jsonResponse
It appears that you want to continue playing the game for Round 2. To do so, you should make a new request to the /play API endpoint with the updated information for Round 2 and Round 3. 
Here's how the request body for Round 2 would look like below.
The API accepts a POST request with the following JSON structure as the request body:

    {   
        "gameId": 1,
        "playerOneHandSign" : "ROCK",
        "playerTwoHandSign"  :"ROCK"
    }
```
#### Output
```jsonResponse
    {
        "gameId": 1,
        "gameResults": [
            "Player1",
            "tie"
        ],
        "remainingAttempts": 1,
        "finalWinner": null
    }
```
    1-gameId: A unique identifier for the current game session. Each game will have a different gameId.
    2-gameResults: An array containing the result(s) of the game. It may include one or more of the following values: "Player1", "Player2", or "Tie". In this example response, "Player1" indicates that player one (the submitter of the request) won the game.
    3-remainingAttempts: The number of remaining attempts to play the game after the current request. It can help to keep track of the number of times the game can be played.
    4-finalWinner: The ultimate winner of the game after all attempts are completed. It will be null until all rounds are played, and a winner is determined.
    
### finalWinner
    For the final winner response request for the fourth time with same id 

    {   
        "gameId": 1,
        "playerOneHandSign" : "ROCK",
        "playerTwoHandSign"  :"ROCK"
    }


```jsonResponse
    {
        "gameId": 1,
        "gameResults": [
            "tie",
            "Player2",
            "tie"
        ],
        "remainingAttempts": 0,
        "finalWinner": "Player2"
    }
```
### Hand Sign Game Rules
    The hand sign game follows the conventional rules:

        - "ROCK" beats "SCISSORS"
        - "PAPER" beats "ROCK"
        - "SCISSORS" beats "PAPER"
        - If both players choose the same hand sign, the game results in a tie.


----------------------------------------------------------

#  GameServiceImpl.java class Explanation

```Method

    Method
    
    GameResult playGame(Game game)
    
           The `playGame` method simulates a game of Rock-Paper-Scissors for three rounds based on the given `Game` object and keeps track of the game results using the `gameMap`, which is a `Map` where the key is the `gameId`, and the value is a `GameResult` object.
        
        Here's a short explanation of the method:
        
        1. First, it checks if the `gameMap` does not contain the `gameId` (i.e., `!gameMap.containsKey(game.getGameId())`). If this is true, it means it's a new player, and it sets a new unique `gameId` for the game, initializes the remaining attempts to the maximum number of rounds (`maxRound`), and then calls the `getGameResultPerRound` method with `true` as the last parameter, indicating it's the first round.
        
        2. If the `gameMap` already contains the `gameId`, it means it's either the second or third round. In this case, it checks if there are remaining attempts left (`gameMap.get(game.getGameId()).getRemainingAttempts() > 0`). If there are remaining attempts, it calls the `getGameResultPerRound` method with `false` as the last parameter, indicating it's not the first round.
        
        3. If there are no remaining attempts (i.e., `gameMap.get(game.getGameId()).getRemainingAttempts() <= 0`), it means the game is over. It calls the `getGameResultPerRound` method with `false` as the last parameter, indicating it's not the first round, and returns the result. This will be the final game result for the player.
        
        In summary, the `playThreeRounds` method handles three scenarios: for a new player, it initializes the game and starts the first round. For the second and third rounds, it continues the game, updating the remaining attempts. Finally, for the fourth round onwards (when `maxRound` rounds are played), it returns the final game result. The actual game result calculation and updates are done in the `getGameResultPerRound` method.

    Method 
    
    GameResult getGameResultPerRound(Game game, int remainingAttempts, boolean isFirst)
    
           The `getGameResultPerRound` method is used to calculate the game result for a specific round in a Rock-Paper-Scissors game. It takes a `Game` object, `remainingAttempts`, and a boolean `isFirst` as parameters.
        
        1. The method first checks if it's the first round (`isFirst`). If it's the first round, it initializes a new `GameResult` object and an empty list to store the game results for this game (`gameResult.setGameResults(new ArrayList<>())`). Otherwise, it retrieves the `GameResult` object associated with the `gameId` from the `gameMap`.
        
        2. If there are no `remainingAttempts` left (i.e., `remainingAttempts == 0`), it means the game is over. In this case, it collects the game results of all rounds for the given `gameId` and calculates the overall game result using the `getResult` method. The method then returns a new `GameResult` object with the complete game results and the overall game result.
        
        3. If there are `remainingAttempts`, it proceeds with the current round. It gets the hand signs of both players (`player1Sign` and `player2Sign`) from the `game` object and determines the round winner using the `determineRoundWinner` method.
        
        4. The `gameResult` object is updated with the current round information. It sets the `gameId`, decreases the `remainingAttempts`, and adds the result of the current round ("Player1," "Player2," or "tie") to the `gameResults` list.
        
        5. After updating the `gameResult` object, it puts the updated `gameResult` back into the `gameMap` with the corresponding `gameId`.
        
        Overall, this method is used to calculate the game result for each round and keeps track of the overall game progress by updating the `gameResult` object and storing it in the `gameMap` for each round.
            
    Method -
    
         private String determineFinalWinner(int gameId, Map<Integer, GameResult> gameMap)
    
            The `determineFinalWinner` method takes two parameters: `gameId` and `gameMap`. It aims to determine the overall result of a game, which contains multiple rounds, based on the number of times each player (Player 1 and Player 2) wins in the `gameResults` lists of the `GameResult` objects associated with the given `gameId` in the `gameMap`.
        
        1. Based on the number of wins, the method then determines the overall result of the game. If `player1Wins` is greater than `player2Wins`, it sets `gameResult` to "Player 1 wins." If `player2Wins` is greater than `player1Wins`, it sets `gameResult` to "Player 2 wins." Otherwise, if both have the same number of wins, it sets `gameResult` to "It's a tie."
        
        2. Finally, the method returns the `gameResult`, indicating the overall result of the game. It will be either "Player 1 wins," "Player 2 wins," or "It's a tie" based on the number of wins accumulated for each player in the game results.


    Method -
         public int determineRoundWinner(HandSign player1Sign, HandSign player2Sign)
        
            The `determineRoundWinner` method takes two parameters of type `HandSign`, `player1Sign`, and `player2Sign`, representing the hand signs chosen by two players in a game (e.g., Rock, Paper, or Scissors).
        
        1. It creates a `Map` called `winningCombinations`, where the keys represent the hand signs that can win a round, and the corresponding values represent the hand signs that they can defeat. In a classic Rock-Paper-Scissors game, Rock crushes Scissors, Scissors cuts Paper, and Paper covers Rock.
        
        2. The method then checks if both players' hand signs are the same (`player1Sign == player2Sign`). If they are the same, it returns `0`, indicating a tie.
        
        3. If the hand signs are different, the method checks if `player1Sign` wins over `player2Sign` according to the `winningCombinations` map (`winningCombinations.get(player1Sign) == player2Sign`). If so, it returns `1`, indicating that Player 1 wins the round.
        
        4. If neither of the above conditions is met, it means `player2Sign` wins over `player1Sign`, so the method returns `2`, indicating that Player 2 wins the round.
        
        In summary, this method determines the winner of a round in a Rock-Paper-Scissors game based on the hand signs chosen by two players. It returns `0` for a tie, `1` if Player 1 wins, and `2` if Player 2 wins.


```
