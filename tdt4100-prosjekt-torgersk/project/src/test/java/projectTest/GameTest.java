package projectTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.model.Game;


public class GameTest {
	
	private Game game;
	
	@BeforeEach
	public void setup() {
		game = new Game(9);
	}
	
	@Test
	@DisplayName("Konstruktøren")
	public void testConstructor() {
		assertEquals(9, game.getBoard().getSize());
		
		assertEquals(4, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
		
		assertEquals(0, game.getEnemies().get(0).getX());
		assertEquals(0, game.getEnemies().get(0).getY());
		
		assertEquals(8, game.getEnemies().get(1).getX());
		assertEquals(0, game.getEnemies().get(1).getY());
		
		assertEquals(0, game.getEnemies().get(2).getX());
		assertEquals(8, game.getEnemies().get(2).getY());
		
		assertEquals(8, game.getEnemies().get(3).getX());
		assertEquals(8, game.getEnemies().get(3).getY());
		
		// board.generateLevel() sørger for at disse rutene alltid vil være gyldige å spawne i
		// At disse rutene faktisk er gyldige å spawne i, er også sjekket i BoardTest
	}
	

	@Test
	@DisplayName("Kan ikke flytte player etter at spillet er over")
	public void testMovePlayerOnGameOver() {
		game.getBoard().getSquare(4, 3).setPath();
		game.getBoard().getSquare(4, 5).setPath();
		game.getPlayer().setGameOver(true);
		
		game.movePlayerLeft();
		assertEquals(4, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
		game.movePlayerRight();
		assertEquals(4, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
		game.movePlayerUp();
		assertEquals(4, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
		game.movePlayerDown();
		assertEquals(4, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
	}

	@Test
	@DisplayName("Kan ikke flytte player til en ugyldig destinasjon")
	public void testMovePlayerToIllegalDestination() {
		game.getBoard().getSquare(4, 3).setBrick();
		game.movePlayerUp();
		assertEquals(4, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
		game.getPlayer().setX(8);
		game.getPlayer().setY(4);
		game.movePlayerRight();
		assertEquals(8, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
	}

	@Test
	@DisplayName("Flytte player til en gyldig destinasjon")
	public void testMovePlayerToLegalDestination() {
		game.getBoard().getSquare(5, 4).setPath();
		game.movePlayerRight();
		assertEquals(5, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
		game.getBoard().getSquare(6, 4).setCoin();
		game.movePlayerRight();
		assertEquals(6, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
	}

	@Test
	@DisplayName("Oppdaterer brettet etter at player har gjort et trekk")
	public void testUpdateBoardAfterPlayerMove() {
		game.getBoard().getSquare(3, 4).setPath();
		game.movePlayerLeft();
		assertTrue(game.getBoard().getSquare(4, 4).getType() == ' ' | game.getBoard().getSquare(4, 4).getType() == 'c');
		assertEquals('P', game.getBoard().getSquare(3, 4).getType());
	}

	@Test
	@DisplayName("Player går på en coin")
	public void testPlayerCollectCoin() {
		game.getBoard().getSquare(3, 4).setCoin();
		assertEquals(10, game.getBoard().getCoinList().size());
		game.movePlayerLeft();
		assertEquals(1, game.getPlayer().getScore());
		assertTrue(game.getBoard().getSquare(4, 4).getType() == ' ' | game.getBoard().getSquare(4, 4).getType() == 'c');
		assertEquals(3, game.getPlayer().getX());
		assertEquals(4, game.getPlayer().getY());
		assertEquals(10, game.getBoard().getCoinList().size());
	}

	@Test
	@DisplayName("Player går på en enemy")
	public void testPlayerMoveToEnemy() {
		game.getBoard().getSquare(3, 4).setEnemy();
		game.movePlayerLeft();
		assertTrue(game.getPlayer().isGameOver());
	}
	
	@Test
	@DisplayName("Oppdaterer brettet etter at enemy har gjort et trekk")
	public void testUpdateBoardAfterEnemyMove() {
		game.getBoard().getSquare(1, 0).setBrick();
		game.moveEnemy(game.getEnemies().get(0));
		assertTrue(game.getBoard().getSquare(0, 0).getType() == ' ' | game.getBoard().getSquare(0, 0).getType() == 'c');
		assertEquals('E', game.getBoard().getSquare(0, 1).getType());
	}

	@Test
	@DisplayName("Enemy fortsetter rett frem dersom den er på en rett vei")
	public void testMoveEnemyWhenStraightPath() {
		game.getBoard().getSquare(1, 0).setBrick();
		game.moveEnemy(game.getEnemies().get(0));
		game.moveEnemy(game.getEnemies().get(0));
		game.moveEnemy(game.getEnemies().get(0));
		assertEquals(0, game.getEnemies().get(0).getX());
		assertEquals(3, game.getEnemies().get(0).getY());
	}	
	
	@Test
	@DisplayName("Enemy fortsetter ikke rett frem dersom den rette veien er perpendikulær på fartsretningen")
	public void testStraightPathPerpendicularOnDirection() {
		game.getBoard().getSquare(4, 0).setBrick();
		game.getEnemies().get(0).setX(4);
		game.getEnemies().get(0).setY(1);
		game.moveEnemy(game.getEnemies().get(0));
		
		game.getBoard().getSquare(4, 1).setEnemy();
		game.moveEnemy(game.getEnemies().get(0));
		
		assertTrue(game.getEnemies().get(0).getX() == 3 || game.getEnemies().get(0).getX() == 5);
		assertEquals(2, game.getEnemies().get(0).getY());
	}	

	@Test
	@DisplayName("Enemy venter dersom den er innesperret av andre enemies")
	public void testEnemyConfinedByEnemies() {
		game.getBoard().getSquare(0,  1).setEnemy();
		game.getBoard().getSquare(1,  0).setEnemy();
		game.moveEnemy(game.getEnemies().get(0));
		assertEquals(0, game.getEnemies().get(0).getX());
		assertEquals(0, game.getEnemies().get(0).getY());
	}

	@Test
	@DisplayName("Oppdaterer enemy sin retning etter et veikryss")
	public void testUpdateEnemyDirectionAfterPathIntersection() {
		game.getEnemies().get(0).setX(2);
		game.getEnemies().get(0).setY(4);
		game.moveEnemy(game.getEnemies().get(0));
		assertEquals(game.getEnemies().get(0).getX() - 2, game.getEnemies().get(0).getDeltaX());
		assertEquals(game.getEnemies().get(0).getY() - 4, game.getEnemies().get(0).getDeltaY());	
	}


	@Test
	@DisplayName("Enemy går på player")
	public void testEnemyMoveToPlayer() {
		game.getBoard().getSquare(0, 1).setPlayer();
		game.getBoard().getSquare(1, 0).setBrick();
		game.moveEnemy(game.getEnemies().get(0));
		assertTrue(game.getPlayer().isGameOver());
	}

	@Test
	@DisplayName("Enemy går på en coin")
	public void testEnemyCollectCoin() {
		game.getBoard().getSquare(1, 0).setBrick();
		game.getBoard().getSquare(0, 1).setCoin();
		assertEquals(10, game.getBoard().getCoinList().size());
		game.moveEnemy(game.getEnemies().get(0));
		assertTrue(game.getBoard().getSquare(0, 0).getType() == ' ' | game.getBoard().getSquare(0, 0).getType() == 'c');
		assertEquals(0, game.getEnemies().get(0).getX());
		assertEquals(1, game.getEnemies().get(0).getY());
		assertEquals(10, game.getBoard().getCoinList().size());
	}

}
