package com.game.kalah;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Controller
public class GameLogicController {

	@RequestMapping("/")
	public String initGame(Model model, HttpServletResponse response) {
		int starterStones1[] = { 6, 6, 6, 6, 6, 6, 0 };
		int starterStones2[] = { 0, 6, 6, 6, 6, 6, 6 };
		model.addAttribute("stones1", starterStones1);
		model.addAttribute("stones2", starterStones2);
		return "index";
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, value = "/move")
	@ResponseBody
	public List<List<Integer>> move(@RequestParam(value = "player1[]") List<Integer> player1,
			@RequestParam(value = "player2[]") List<Integer> player2, @RequestParam("player") String player,
			@RequestParam("position") int position) {

		// we need to know how many stones we have in the clicked position
		int moveNumbers = 0;

		List<Integer> fullBoard = new LinkedList<Integer>(player1);
		Collections.reverse(player2);
		fullBoard.addAll(player2); // Now we have a big list which is the combination of player 1 + player 2
		moveNumbers = fullBoard.get(position); //we find the number of stones in player ones List
		
		// Time to move the stones
		int i;
		int lastMoveFlag = 0; // you will know the purpose of this flag in
								// whoseNext method
		
		fullBoard.set(position, 0); //empty the pit 
		int startPoint = position+1; //thats where we start adding
		while (moveNumbers > 0) {
			for (i = startPoint; i < fullBoard.size(); i++) {
				if (moveNumbers == 0) {
					break;
				}
				
				//skip point
				boolean pl1 = (i == 13 && player.equals("pl1"));
				boolean pl2 = (i == 6 && player.equals("pl2"));
				if(!pl1 && !pl2){
					fullBoard.set(i, fullBoard.get(i) + 1);
					moveNumbers--;
				}
				lastMoveFlag = i;
			}
			i = 0;
			startPoint = 0;
		}

		// we are done moving the stones on the board, time to split the players
		// again
		System.out.println(fullBoard);
		List<List<Integer>> split = Lists.partition(fullBoard, 7);
		List<Integer> pl1_afterMove = split.get(0);
		List<Integer> pl2_afterMove = split.get(1);
		System.out.println(pl2_afterMove);
		System.out.println(pl1_afterMove);

		String whoseWinner = getStatus(pl1_afterMove, pl2_afterMove);
		List<List<Integer>> nextMove = new ArrayList<List<Integer>>();
		if (whoseWinner.equals("continue")) {
			nextMove = whoseNext(pl1_afterMove, pl2_afterMove, player, lastMoveFlag);
		}

		return nextMove;

	}

	private String getStatus(List<Integer> player1, List<Integer> player2) {
		String status;
		int sum1 = 0;
		int sum2 = 0;
		for (int i = 0; i < 7; i++) {
			sum1 =sum1 + player1.get(i);
		}
		for (int i = 1; i < 7; i++) {
			sum2 =sum2 + player2.get(i);
		}

		if (sum1 == 0) {
			status = "pl1";
		} else if (sum2 == 0) {
			status = "pl2";
		} else {
			status = "continue";
		}

		return status;
	}

	private List<List<Integer>> whoseNext(List<Integer> pl1_afterMove, List<Integer> pl2_afterMove, String player,
			int lastMoveFlag) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		List<Integer> playerTurn = new ArrayList<Integer>();
		// Rule No1 : if last stone drops in Mancala, the player gets one more
		// move
		if (lastMoveFlag == 6 && player.equals("pl1")) {
			playerTurn.add(1);

		} else if (lastMoveFlag == 13 && player.equals("pl2")) {
			playerTurn.add(2);
		}

		/*
		 * Rule No2:If the last stone seed lands in an empty house owned by the
		 * player, and the opposite house contains seeds, both the last seed and
		 * the opposite seeds are captured and placed into the player's store
		 */
		if (playerTurn.isEmpty()) {
			if (lastMoveFlag < 6 && player.equals("pl1")) {
				int nomberOfStones = pl1_afterMove.get(lastMoveFlag);
				if (nomberOfStones == 1) {
					Collections.reverse(pl2_afterMove);
					pl1_afterMove.set(6, pl1_afterMove.get(6)+(pl2_afterMove.get(lastMoveFlag) + 1)); // add the stones if opposite pits and the players one stone to the Mancala
					pl2_afterMove.set(lastMoveFlag, 0); // empty the opposite
														// pit
					pl1_afterMove.set(lastMoveFlag, 0); // empty the players pit
					Collections.reverse(pl2_afterMove);
				}
				playerTurn.add(2);
			} else if (lastMoveFlag < 13 && lastMoveFlag > 6 && player.equals("pl2")) {

				int nomberOfStones = pl2_afterMove.get(lastMoveFlag-7);
				if (nomberOfStones == 1) {
					pl2_afterMove.set(6,pl2_afterMove.get(6)+(pl1_afterMove.get(lastMoveFlag-7)));
					pl1_afterMove.set(lastMoveFlag, 0); // empty the opposite pit
					pl2_afterMove.set(lastMoveFlag, 0); // empty the players pit
					Collections.reverse(pl2_afterMove);
				}
				playerTurn.add(1);
			}

		}
		
		if(playerTurn.isEmpty()){
			if (player.equals("pl1") ) {
				playerTurn.add(2);
			} else {
				playerTurn.add(1);
			}
		}
		
		Collections.reverse(pl2_afterMove);
		result.add(pl1_afterMove);
		result.add(pl2_afterMove);
		result.add(playerTurn);
		return result;

	}

}
