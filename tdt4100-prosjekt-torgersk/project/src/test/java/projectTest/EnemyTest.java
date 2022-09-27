package projectTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.Enemy;	

public class EnemyTest {
	
	private Enemy enemy;
		
		@BeforeEach
		public void setup() {
			enemy = new Enemy(4, 5, 1, 0);
		}
		
		@Test
		public void testConstructor() {
			assertEquals(4, enemy.getX());
			assertEquals(5, enemy.getY());
			assertEquals(1, enemy.getDeltaX());
			assertEquals(0, enemy.getDeltaY());
			
			assertThrows(IllegalArgumentException.class, () -> {
				new Enemy(4, 5, 0, 0);
			}, "(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
			assertThrows(IllegalArgumentException.class, () -> {
				new Enemy(4, 5, 1, -1);
			}, "(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
			assertThrows(IllegalArgumentException.class, () -> {
				new Enemy(4, 5, 2, 0);
			}, "(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
			assertThrows(IllegalArgumentException.class, () -> {
				new Enemy(4, 5, 0, -2);
			}, "(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
		}
		
		@Test
		public void testGetX() {
			assertEquals(4, enemy.getX());
		}
		
		@Test
		public void testGetY() {
			assertEquals(5, enemy.getY());
		}
		
		@Test
		public void testGetDeltaX() {
			assertEquals(1, enemy.getDeltaX());
		}
		
		@Test
		public void testGetDeltaY() {
			assertEquals(0, enemy.getDeltaY());
		}
		
		@Test
		public void testSetX() {
			enemy.setX(2);
			assertEquals(2, enemy.getX());
		}
		
		@Test
		public void testSetY() {
			enemy.setY(6);
			assertEquals(6, enemy.getY());
		}
		
		@Test
		public void testSetDirection() {
			enemy.setDirection(-1, 0);
			assertEquals(-1, enemy.getDeltaX());
			assertEquals(0, enemy.getDeltaY());
			
			assertThrows(IllegalArgumentException.class, () -> {
				enemy.setDirection(2, 0);
			}, "(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
			assertThrows(IllegalArgumentException.class, () -> {
				enemy.setDirection(0, 0);
			}, "(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
			assertThrows(IllegalArgumentException.class, () -> {
				enemy.setDirection(0, -2);
			}, "(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
			assertThrows(IllegalArgumentException.class, () -> {
				enemy.setDirection(1, 1);
			}, "(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
		}

		
		
}
