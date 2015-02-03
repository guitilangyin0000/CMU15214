package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TilePackage {

	private static final int[] TILENUM = { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4,
			2, 6, 8, 2, 1, 6, 4, 6, 2, 2, 4, 1, 2, 1 };
	private static final int[] TILESCORE = { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5,
			1, 3, 1, 1, 3, 10, 1, 1, 1, 4, 4, 1, 8, 4, 10 };
	private ArrayList<Tile> tiles;
	private int num;
	
    public TilePackage(){
    	tiles = new ArrayList<Tile>();
    	num = 0;
    }
    
	/* initial the tiles in tile package*/
    public void initial(){
    	for (char i = 65; i < 91; i++){
    		for (int j = 0; j < TILENUM[i - 65]; j++){
    			tiles.add(new Tile(i, TILESCORE[i - 65]));
    			num += 1;
    		}
    	}
    }
    
    public Boolean isEmpty(){
    	return num == 0;
    }
    
    public int getNum(){
    	return num;
    }
    
    public int getRandom(){
    	Random rand = new Random();
    	int n = rand.nextInt(num);
    	//System.out.println(n);
    	return n;
    }
    
    public void addTiles(ArrayList<Tile> tiles){
    	if (tiles == null){
    		System.out.println("The input tiles are null!");
    		return;
    	}
    	for (Tile tile: tiles){
    		addTile(tile);
    	}
    }
    
    public void addTile(Tile tile){
        if (tile == null){
        	throw new IllegalArgumentException(
					"The tile is null!");
        }
        
        tiles.add(tile);
        num += 1;
        
    }
    
    public int getValueByLetter(char letter){
    	for (char i = 65; i < 91; i++){
    		if (letter == i){
    			return TILESCORE[i - 65];
    		}
    	}
    	return -1;
    }
    
    public ArrayList<Tile> getTiles(int number){
    	if (number <= 0 || number > 7){
    		System.out.println("The request tile number is invalid!");
    		return new ArrayList();
    	}
    	
    	if (isEmpty()){
    		System.out.println("The Tile Package is empty!");
    		return new ArrayList(); // prevent returning null
    	}
    	
    	if (number > num){
    		return tiles;
    	}
    	
    	ArrayList<Tile> tmp = new ArrayList();
    	
    	for (int i = 0; i < number; i++){
    		int index = getRandom();
    		tmp.add(tiles.remove(index));
    		num -= 1;
    	}
    	return tmp;
    	
    }

}


