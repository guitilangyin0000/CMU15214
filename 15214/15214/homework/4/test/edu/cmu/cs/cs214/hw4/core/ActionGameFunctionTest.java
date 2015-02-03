package edu.cmu.cs.cs214.hw4.core;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.cs.cs214.hw4.core.specialTile.Boom;
import edu.cmu.cs.cs214.hw4.core.specialTile.NegativePoints;
import edu.cmu.cs.cs214.hw4.core.specialTile.OneMoreOrder;
import edu.cmu.cs.cs214.hw4.core.specialTile.ReversePlayerOrder;
import edu.cmu.cs.cs214.hw4.core.specialTile.SpecialTile;

public class ActionGameFunctionTest {

	private Game game;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		//game.initialGame();
	}

	/** Called after each test case method. */
	@After
	public void tearDown() throws Exception {
		// Don't need to do anything here.
	}

	/** Tests property of action */
	@Test
	public void testActionInitial() {
		game.setStartLocationToAction(17, 7);
		assertEquals(game.getAction().getStartLocation(), null);
		game.setStartLocationToAction(7, 7);
		assertEquals(7, game.getAction().getStartLocation().getX());
		assertEquals(7, game.getAction().getStartLocation().getY());
		game.setDirectionToAction(-2);
		assertEquals(game.getAction().getDirection(), -1);
		game.setDirectionToAction(1);
		assertEquals(game.getAction().getDirection(), 1);
	}

	@Test
	public void testGameTileBag() {
		game.addPlayer(new Player("cuiyang36"));
		game.addPlayer(new Player("xinli"));
		game.addPlayer(new Player("chaoli"));
		game.addPlayer(new Player("shuchang"));
		game.addPlayer(null);
		game.addPlayer(new Player("player should not added"));
		game.addPlayer(new Player("cuiyang36"));

		assertEquals(4, game.getPlayerNum());
		//game.showPlayerInventory();
		assertEquals(98, game.getTilePackage().getNum());
		game.initialPlayerInventory();
		for (Player player: game.getPlayers()){
			assertEquals(7, player.getTileNum());
			assertEquals(0, player.getScore());
		}
		ArrayList<Tile> tiles = game.getPlayerByName("cuiyang36").getTiles();
		game.exchangeTiles(tiles, game.getPlayerByName("cuiyang36"));
		//game.getPlayerByName("cuiyang36").showInventory();

	}
	
	@Test
	public void testActionAddTile() {
		game.setStartLocationToAction(7, 7);
		game.setDirectionToAction(1);
		game.addTileToAction(null, 7, 7);
		game.addTileToAction(new Tile('C', 3), 17, 7);
		game.addTileToAction(new Tile('C', 3), 7, 7);
		game.addTileToAction(new Tile('C', 3), 7, 7);
		game.addTileToAction(new Tile('A', 1), 7, 8);
		game.addTileToAction(new Tile('T', 1), 7, 9);
		ArrayList<Tile> tiles = game.getAction().getTiles();
		ArrayList<Location> locations = game.getAction().getLocations();
		assertEquals('C', tiles.get(0).getLetter());
		assertEquals('A', tiles.get(1).getLetter());
		assertEquals('T', tiles.get(2).getLetter());
		assertEquals(7, locations.get(0).getY());
		assertEquals(8, locations.get(1).getY());
		assertEquals(9, locations.get(2).getY());
	}

	@Test
	public void testActionBasic() {
		game.setStartLocationToAction(10, 7);
		game.setDirectionToAction(0);

		game.addTileToLocation(new Tile('K', 5), 6, 7);
		game.addTileToLocation(new Tile('K', 5), 16, 7);
		game.addTileToLocation(new Tile('K', 5), 6, 7);
		game.addTileToLocation(new Tile('N', 1), 7, 7);
		game.addTileToLocation(new Tile('O', 1), 8, 7);
		game.addTileToLocation(new Tile('W', 4), 9, 7);

		game.addTileToLocation(new Tile('E', 1), 14, 7);
		game.addTileToLocation(new Tile('D', 2), 14, 8);
		game.addTileToLocation(new Tile('G', 2), 14, 9);
		game.addTileToLocation(new Tile('E', 1), 14, 10);

		game.addTileToAction(new Tile('L', 1), 10, 7);
		game.addTileToAction(new Tile('E', 1), 11, 7);
		game.addTileToAction(new Tile('D', 2), 12, 7);
		game.addTileToAction(new Tile('G', 2), 13, 7);

		Word word = game.makeOneWord();
		assertEquals(true, game.getBoard().checkNearWord(game.getAction()));
		assertEquals("KNOWLEDGE", word.toString());
		ArrayList<Word> nearWords = game.getBoard().makeAdjacentWords(
				game.getAction());
		assertEquals(0, nearWords.size());
		word.calculateValue(game.getBoard());
		assertEquals(1, word.getTimer());
		assertEquals(19, word.getValue());
		assertEquals(true, game.getBoard().checkValidWord(game.getAction()));
	}

	@Test
	public void testActionMedium() {
		game.addPlayer(new Player("cuiyang36"));
		game.addPlayer(new Player("xinli"));
		game.addPlayer(new Player("chaoli"));
		//game.initialPlayerInventory();
		game.getPlayerByName("cuiyang36").addScore(10);
		System.out.println(game.getPlayerByName("cuiyang36").getScore());
		assertEquals(10, game.getPlayerByName("cuiyang36").getScore());
		assertEquals(3, game.getPlayerNum());
		assertEquals("cuiyang36", game.getCurrentPlayer().getName());
		System.out.println(game.getTilePackage().getNum());
		//System.out.println(game.getTilePackage().getNum());
		assertEquals(98, game.getTilePackage().getNum());
		ArrayList<Tile> inventory = new ArrayList<Tile>();
		inventory.add(new Tile('A', 1));
		inventory.add(new Tile('A', 1));
		inventory.add(new Tile('C', 3));
		inventory.add(new Tile('L', 1));
		inventory.add(new Tile('E', 1));
		inventory.add(new Tile('D', 2));
		inventory.add(new Tile('G', 2));
		game.giveTiles(inventory, game.getCurrentPlayer());
		assertEquals(7, game.getCurrentPlayer().getTileNum());
		
		SpecialTile testSpecialTile1 = new OneMoreOrder();
		testSpecialTile1.setOwner(game.getPlayerByName("xinli"));
		game.getBoard().setSpecialTileToLocation(testSpecialTile1, 4, 7);
		assertEquals("OneMoreOrder", game.getBoard().getLocation(4, 7)
				.getSpecialTile().getName());
		assertEquals("xinli", game.getBoard().getLocation(4, 7)
				.getSpecialTile().getOwner().getName());
		
		SpecialTile testSpecialTile2 = new NegativePoints();
		testSpecialTile2.setOwner(game.getPlayerByName("chaoli"));
		game.getBoard().setSpecialTileToLocation(testSpecialTile2, 5, 7);
		assertEquals("NegativePoints", game.getBoard().getLocation(5, 7)
				.getSpecialTile().getName());
		assertEquals("chaoli", game.getBoard().getLocation(5, 7)
				.getSpecialTile().getOwner().getName());
		
		SpecialTile testSpecialTile3 = new ReversePlayerOrder();
		testSpecialTile3.setOwner(game.getPlayerByName("chaoli"));
		game.getBoard().setSpecialTileToLocation(testSpecialTile3, 10, 7);
		assertEquals("ReversePlayerOrder", game.getBoard().getLocation(10, 7)
				.getSpecialTile().getName());
		assertEquals("chaoli", game.getBoard().getLocation(10, 7)
				.getSpecialTile().getOwner().getName());
		
		game.setStartLocationToAction(4, 7);
		game.setDirectionToAction(0);
		game.addTileToLocation(new Tile('K', 5), 6, 7);
		game.addTileToLocation(new Tile('N', 1), 7, 7);
		game.addTileToLocation(new Tile('O', 1), 8, 7);
		game.addTileToLocation(new Tile('W', 4), 9, 7);
		game.addTileToLocation(new Tile('E', 1), 14, 7);
		game.addTileToLocation(new Tile('D', 2), 14, 8);
		game.addTileToLocation(new Tile('G', 2), 14, 9);
		game.addTileToLocation(new Tile('E', 1), 14, 10);
		game.addTileToLocation(new Tile('A', 1), 10, 8);
		game.addTileToLocation(new Tile('T', 1), 10, 9);
		game.addTileToLocation(new Tile('E', 1), 10, 10);
		game.addTileToLocation(new Tile('N', 1), 11, 4);
		game.addTileToLocation(new Tile('A', 1), 11, 5);
		game.addTileToLocation(new Tile('M', 3), 11, 6);
		game.addTileToLocation(new Tile('B', 3), 12, 5);
		game.addTileToLocation(new Tile('A', 1), 12, 6);
		game.addTileToLocation(new Tile('O', 1), 13, 8);
		game.addTileToLocation(new Tile('O', 1), 13, 9);
		game.addTileToLocation(new Tile('D', 2), 13, 10);

		game.addTileToAction(new Tile('A', 1), 4, 7);
		game.addTileToAction(new Tile('C', 3), 5, 7);
		game.addTileToAction(new Tile('L', 1), 10, 7);
		game.addTileToAction(new Tile('E', 1), 11, 7);
		game.addTileToAction(new Tile('D', 2), 12, 7);
		game.addTileToAction(new Tile('G', 2), 13, 7);


		SpecialTile specialTile = game.buySpecialTile(game.getPlayerByName("cuiyang36"), "ReversePlayerOrder");
		assertEquals(10, game.getPlayerByName("cuiyang36").getScore());
		game.setSpecialTileToAction(specialTile);
		game.setSpecialTileLocationToAction(3, 7);

		game.showBoard();
		Word word = game.makeOneWord();
		assertEquals("ACKNOWLEDGE", word.toString());
		assertEquals(true, game.getBoard().checkNearWord(game.getAction()));
		assertEquals(true, game.getBoard().checkValidWord(game.getAction()));
		ArrayList<Word> adjacentWords = game.getBoard().makeAdjacentWords(
				game.getAction());
		assertEquals("LATE", adjacentWords.get(0).toString());
		assertEquals("NAME", adjacentWords.get(1).toString());
		assertEquals("BAD", adjacentWords.get(2).toString());
		assertEquals("GOOD", adjacentWords.get(3).toString());
		assertEquals(true, game.getBoard().checkAdjacentWords(game.getAction()));
		assertEquals(1, game.checkValidAction());
		assertEquals(46, game.getBoard().getActionScore(game.getAction()));
		game.makeAction();
		game.showBoard();
		assertEquals(7, game.getPlayerByName("cuiyang36").getTileNum());
		assertEquals(92, game.getTilePackage().getNum());
		assertEquals(-36, game.getPlayerByName("cuiyang36").getScore());
		assertEquals("xinli", game.getCurrentPlayer().getName());
	}
	
	@Test
	public void testActionActivateBoom() {
		game.addPlayer(new Player("cuiyang36"));
		game.addPlayer(new Player("xinli"));
		game.getPlayerByName("cuiyang36").addScore(10);
		assertEquals(10, game.getPlayerByName("cuiyang36").getScore());
		assertEquals(2, game.getPlayerNum());
		assertEquals("cuiyang36", game.getCurrentPlayer().getName());
		
		assertEquals(98, game.getTilePackage().getNum());
		ArrayList<Tile> inventory = new ArrayList<Tile>();
		inventory.add(new Tile('A', 1));
		inventory.add(new Tile('A', 1));
		inventory.add(new Tile('C', 3));
		inventory.add(new Tile('L', 1));
		inventory.add(new Tile('E', 1));
		inventory.add(new Tile('D', 2));
		inventory.add(new Tile('G', 2));
		game.giveTiles(inventory, game.getCurrentPlayer());
		assertEquals(7, game.getCurrentPlayer().getTileNum());
		
		SpecialTile testSpecialTile1 = new Boom();
		testSpecialTile1.setOwner(game.getPlayerByName("xinli"));
		game.getBoard().setSpecialTileToLocation(testSpecialTile1, 4, 7);
		assertEquals("Boom", game.getBoard().getLocation(4, 7)
				.getSpecialTile().getName());
		assertEquals("xinli", game.getBoard().getLocation(4, 7)
				.getSpecialTile().getOwner().getName());
		
		SpecialTile testSpecialTile2 = new Boom();
		testSpecialTile2.setOwner(game.getPlayerByName("xinli"));
		game.getBoard().setSpecialTileToLocation(testSpecialTile2, 5, 7);
		assertEquals("Boom", game.getBoard().getLocation(5, 7)
				.getSpecialTile().getName());
		assertEquals("xinli", game.getBoard().getLocation(5, 7)
				.getSpecialTile().getOwner().getName());
		
		
		game.setStartLocationToAction(4, 7);
		game.setDirectionToAction(0);
		game.addTileToLocation(new Tile('K', 5), 6, 7);
		game.addTileToLocation(new Tile('N', 1), 7, 7);
		game.addTileToLocation(new Tile('O', 1), 8, 7);
		game.addTileToLocation(new Tile('W', 4), 9, 7);
		game.addTileToLocation(new Tile('E', 1), 14, 7);
		game.addTileToLocation(new Tile('D', 2), 14, 8);
		game.addTileToLocation(new Tile('G', 2), 14, 9);
		game.addTileToLocation(new Tile('E', 1), 14, 10);
		game.addTileToLocation(new Tile('A', 1), 10, 8);
		game.addTileToLocation(new Tile('T', 1), 10, 9);
		game.addTileToLocation(new Tile('E', 1), 10, 10);
		game.addTileToLocation(new Tile('N', 1), 11, 4);
		game.addTileToLocation(new Tile('A', 1), 11, 5);
		game.addTileToLocation(new Tile('M', 3), 11, 6);
		game.addTileToLocation(new Tile('B', 3), 12, 5);
		game.addTileToLocation(new Tile('A', 1), 12, 6);
		game.addTileToLocation(new Tile('O', 1), 13, 8);
		game.addTileToLocation(new Tile('O', 1), 13, 9);
		game.addTileToLocation(new Tile('D', 2), 13, 10);

		game.addTileToAction(new Tile('A', 1), 4, 7);
		game.addTileToAction(new Tile('C', 3), 5, 7);
		game.addTileToAction(new Tile('L', 1), 10, 7);
		game.addTileToAction(new Tile('E', 1), 11, 7);
		game.addTileToAction(new Tile('D', 2), 12, 7);
		game.addTileToAction(new Tile('G', 2), 13, 7);


		SpecialTile specialTile = game.buySpecialTile(game.getPlayerByName("cuiyang36"), "ReversePlayerOrder");
		assertEquals(10, game.getPlayerByName("cuiyang36").getScore());
		game.setSpecialTileToAction(specialTile);
		game.setSpecialTileLocationToAction(3, 7);

		game.showBoard();
		Word word = game.makeOneWord();
		assertEquals("ACKNOWLEDGE", word.toString());
		assertEquals(true, game.getBoard().checkNearWord(game.getAction()));
		assertEquals(true, game.getBoard().checkValidWord(game.getAction()));
		ArrayList<Word> adjacentWords = game.getBoard().makeAdjacentWords(
				game.getAction());
		// System.out.println(adjacentWords.size());
		assertEquals("LATE", adjacentWords.get(0).toString());
		assertEquals("NAME", adjacentWords.get(1).toString());
		assertEquals("BAD", adjacentWords.get(2).toString());
		assertEquals("GOOD", adjacentWords.get(3).toString());
		assertEquals(true, game.getBoard().checkAdjacentWords(game.getAction()));
		assertEquals(1, game.checkValidAction());
		assertEquals(46, game.getBoard().getActionScore(game.getAction()));
		game.makeAction();
		game.showBoard();
		assertEquals(7, game.getPlayerByName("cuiyang36").getTileNum());
		assertEquals(92, game.getTilePackage().getNum());
		//System.out.println(game.getPlayerByName("cuiyang36").getScore());
		//System.out.println("this time: " + game.getPlayerByName("cuiyang36").getScore());
		assertEquals(46, game.getPlayerByName("cuiyang36").getScore());
		assertEquals("xinli", game.getCurrentPlayer().getName());
	}

}
