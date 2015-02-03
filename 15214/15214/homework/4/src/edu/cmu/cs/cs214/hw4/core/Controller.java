package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
	
	private ArrayList<Player> orders;
	private int orderCounter;
	private int playerNum;
	
	public Controller(){
		orders = new ArrayList<Player>();
		playerNum = 0;
		orderCounter = 0;
	}
	
	/* add the player to the controller system*/
	public void addPlayer(Player player){
		orders.add(player);
		playerNum += 1;
	}
	
	/* get the player number in the controller system*/
	public int getPlayerNum(){
		return playerNum;
	}
	
	/* return the current player*/
	public Player getCurrentPlayer(){
		if (playerNum == 0){
			System.out.println("There is no player in this game!");
			return null;
		}
		if (orderCounter > playerNum - 1){
			throw new IllegalArgumentException(
					"orderCounter should not be larger than the playerNum!");
		}
		return orders.get(orderCounter);
	}
	
	/* add the orderCounter by 1*/
	public void updateOrder(){
		if (playerNum == 0){
			System.out.println("There is no player in this game!");
			return;
		}
		orderCounter += 1;
		orderCounter %= playerNum;
	}
	
	/* minus the orderCounter by 1*/
	public void retriveOrder(){
		if (playerNum == 0){
			System.out.println("There is no player in this game!");
			return;
		}
		orderCounter -= 1;
		orderCounter %= playerNum;
	}
	
	/* skip this turn*/
	public void skipOrder(){
		updateOrder();
	}
	
	/* shuffle the order randomly*/
	public void shuffleOrder(){
		if (playerNum == 0){
			System.out.println("There is no player in this game!");
			return;
		}
		Random rand = new Random();
		int n = rand.nextInt(playerNum);
		orderCounter = n;
	}
	
	/* reverse the turn of the controller system*/
	public void reverseOrder(){
		if (playerNum == 0){
			System.out.println("There is no player in this game!");
			return;
		}
		ArrayList<Player> tmp = new ArrayList<Player>();
		for (int i = playerNum - 1; i >= 0; i--){
			tmp.add(orders.get(i));
		}
		
		orders = tmp;
		orderCounter = playerNum - 1 - orderCounter;
	}

}
