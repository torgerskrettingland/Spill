package projectTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.Player;

public class PlayerTest {
	
	private Player player;
	
	@BeforeEach
	public void setup() {
		player = new Player(4, 5);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(4, player.getX());
		assertEquals(5, player.getY());
		assertEquals(0, player.getScore());
		assertFalse(player.isGameOver());
	}
	
	@Test
	public void testGetX() {
		assertEquals(4, player.getX());
	}
	
	@Test
	public void testGetY() {
		assertEquals(5, player.getY());
	}

	@Test
	public void testGetScore() {
		assertEquals(0, player.getScore());
	}

	@Test
	public void testIsGameOver() {
		assertFalse(player.isGameOver());
		
	}

	@Test
	public void testSetX() {
		player.setX(2);
		assertEquals(2, player.getX());
	}

	@Test
	public void testSetY() {
		player.setY(6);
		assertEquals(6, player.getY());
	}
	
	public void testSetScore() {
		player.setScore(2);
		assertEquals(2, player.getScore());
	}

	@Test
	public void testSetGameOver() {
		player.setGameOver(true);
		assertTrue(player.isGameOver());
	}

	
}
