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

player-2|0|6|6|6|6|6|6
-|-|-|-|-|-|-|-
player-1|6|6|6|6|6|6|0

2. Step two: Now we make a move. pick the index that has been chosen, take the number of its stones, make it empty and from the next index we add one until the number of remaining stones are zero. so this would be the result if player one choose index 0 of its board.

player-2|0|6|6|6|6|6|6
-|-|-|-|-|-|-|-
player-1|0|7|7|7|7|7|1

Note: if player one playes, the loop of adding will jump index 13 which is Playe 2 Kalah and if player 2 makes a move, the loop will jump index 6 which is the player 1 Kalah.

3. Step 3: who is next? now that we made the moves, we will call `whoseNext()` to check a few things.

	i. if the last stone fall in the player's Kalah, we set the next person to move as the same player
	
		 if (lastMoveFlag == 6 && player == 1) {
			playerTurn.add(1);
		} else if (lastMoveFlag == 13 && player == 2) {
			playerTurn.add(2);
		}

	ii.If the last stone seed lands in an empty house owned by the player, and the opposite house contains seeds, both the last seed 	and the opposite seeds are captured and placed into the player's store.
	
	iii. and at lat if none of the rules on top applies, i will just switch the player.
	
4. Step 4: check for the game status by calling `getStatus()`. we have three statuses (0,1,2). in this method i will add the pits stones of each player. if the sum is 0 that means that player is the winner. 

	i. if player 1 wins the status will be 1
	
	ii. if player 2 wins the status will be 2
	
	iii. if non of them wins the status wil be 0 and the game will continue. 


<h3>Test the Application</h3>
As the neccessity of any web application, I have created a JUnit test which will test the first move if the player one click on index 0.
The result of the test is this:

player-2|0|6|6|6|6|6|6
-|-|-|-|-|-|-|-
player-1|6|6|6|6|6|6|0


player-2|0|6|6|6|6|6|6
-|-|-|-|-|-|-|-
player-1|0|7|7|7|7|7|1
