package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class WordFunctionTest {
	
	private Board board;
	private Dictionary dic;
	private Word word, word_1;
	
	
	@Before
	public void setUp() throws Exception {
		dic = new Dictionary();
		dic.initial("assets/words.txt");
		board = new Board(dic);
		board.intialLocations();
		word = new Word(board.getLocation(7, 7), 1);
		word_1 = new Word(board.getLocation(12, 2), 1);
		
		
	}

	/** Called after each test case method. */
	@After
	public void tearDown() throws Exception {
		// Don't need to do anything here.
	}

	/** Tests property of word  */
	@Test
	public void testAddTileToWord() {
		word.addTile(new Tile('C', 3));
		word.addTile(new Tile('A', 1));
		word.addTile(new Tile('T', 1));
		try{
			word.addTile(null);
		}catch (IllegalArgumentException e){
			System.out.println("Exception catches in testAddTileToWord!");
		}
		ArrayList<Tile> tiles = word.getTiles();
		assertEquals('C', tiles.get(0).getLetter());
		assertEquals('A', tiles.get(1).getLetter());
		assertEquals('T', tiles.get(2).getLetter());
		assertEquals(3, tiles.get(0).getValue());
		assertEquals(1, tiles.get(1).getValue());
		assertEquals(1, tiles.get(2).getValue());
		assertEquals(3, word.getLength());
		assertEquals(7, word.getStartLocation().getX());
		assertEquals(7, word.getStartLocation().getY());
		assertEquals("CAT", word.toString());
	}
	
	@Test
	public void testScoreCalculationCase1() {
		word.addTile(new Tile('C', 3)); // 2WS
		word.addTile(new Tile('A', 1));
		word.addTile(new Tile('T', 1));
		assertEquals(0, word.getValue());
		assertEquals(1, word.getTimer());
		word.calculateValue(board);
		assertEquals(2, word.getTimer());
		assertEquals(10, word.getValue());
	}
	
	@Test
	public void testScoreCalculationCase2() {
		word_1.addTile(new Tile('C', 3)); // 2WS
		word_1.addTile(new Tile('A', 1));
		word_1.addTile(new Tile('T', 1));
		word_1.addTile(new Tile('E', 1));
		word_1.addTile(new Tile('R', 1)); // 2LS
		assertEquals(0, word_1.getValue());
		assertEquals(1, word_1.getTimer());
		word_1.calculateValue(board);
		assertEquals(2, word_1.getTimer());
		assertEquals(16, word_1.getValue());
	}
	
	@Test
	public void testScoreCalculationCase3() {
		board.getLocation(12, 2).addTile(new Tile('C', 3));
		word_1.addTile(new Tile('C', 3)); // 2WS, but belongs to former word
		word_1.addTile(new Tile('A', 1));
		word_1.addTile(new Tile('T', 1));
		word_1.addTile(new Tile('E', 1));
		word_1.addTile(new Tile('R', 1)); // 2LS
		assertEquals(0, word_1.getValue());
		assertEquals(1, word_1.getTimer());
		try{
			word_1.calculateValue(null);
		}catch (IllegalArgumentException e){
			System.out.println("Exception catches in testScoreCalculationCase3!");
		}
		word_1.calculateValue(board);
		assertEquals(1, word_1.getTimer());
		assertEquals(8, word_1.getValue());
	}

}
