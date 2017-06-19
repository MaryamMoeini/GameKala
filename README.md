# GameKala

<h2> About the game</h2>
The game provides a Kalah board and a number of seeds or counters. The board has 12 small pits, called houses, on each side.
each player gets one or more moves depending on the result of the previouse move. here are a few rules: <br>

 * Rule No1: if last stone drops in Mancala, the player gets one more move <br>
 
 * Rule No2: if the last stone seed lands in an empty house owned by the player, and the opposite 
house contains seeds, both the last seed and the opposite seeds are captured and placed into the player's store <br>

 * Rule No3:  game is over when all the pits on the players side are empty <br>

<h2>API Documentation</h2>
This is a single page application constucted using sprint boot. The interation between the view (index.jsp) and the controller (GameLogicController.java) is via Ajax calls which everytime, construct a json object and pass it to the controller.
 
<h3>GameLogicController</h3>
In this controller, only two mapping is provided: <br>

 * initGame : The purpose of this methode is loading the init page and setting the game to start point. in this methode we are setting the init model for player one and player two.<br>
    int starterStones1[] = { 6, 6, 6, 6, 6, 6, 0 };<br>
		int starterStones2[] = { 0, 6, 6, 6, 6, 6, 6 };<br>
    
Note: I needed to use my imagination and revers the player 2, becasue in the game logic the full board starts from player 1, position 1, and more counterclockwise towards the player two. 

* move method :Basically this methode will be called everytime a player make a move. this method recieved a few key parameters:
  * two array which hold the number of stones in each pit for both players.
  * player : the player who performed the action
  * position : the position that player chose to make a move from

<h4>What happens in move method</h4>

1. Step one: combine both players and create a full Board. so lets say for the first move we gonna have something like this:

0|6|6|6|6|6|6
-|-|-|-|-|-|-
6|6|6|6|6|6|0
2. Step two: 
3. Step 3:
