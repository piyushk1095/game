# game



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


# output


            http://localhost:8080/play

            {
            "player1Wins": 1,
            "player2Wins": 0,
            "ties": 2,
            "gameResult": "Player 1 wins"
            }