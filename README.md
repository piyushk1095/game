# Game



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
###Request Body:-


####Game Round 1
```jsonResponse
The API accepts a POST request with the following JSON structure as the request body:

    {
      "playerOneHandSign": "PAPER",
      "playerTwoHandSign": "ROCK"
    }
```

####Output
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

####Game Round 2 & 3
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
####Output
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
    
###finalWinner
    For the final winner response request for the fourth time with same id 

    {   
        "gameId": 1,
        "playerOneHandSign" : "ROCK",
        "playerTwoHandSign"  :"ROCK"
    }


```jsonResponse
    {
        "gameId": 4,
        "gameResults": [
            "tie",
            "Player2",
            "tie"
        ],
        "remainingAttempts": 0,
        "finalWinner": "Player2"
    }
```
###Hand Sign Game Rules
    The hand sign game follows the conventional rules:

        - "ROCK" beats "SCISSORS"
        - "PAPER" beats "ROCK"
        - "SCISSORS" beats "PAPER"
        - If both players choose the same hand sign, the game results in a tie.
