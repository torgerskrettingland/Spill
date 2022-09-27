package projectTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.Square;

public class SquareTest {
	
	private Square square;
	
	@BeforeEach
	public void setup() {
		square = new Square(4, 5);
	}
	
	@Test
	public void testConstructor() {
		Assertions.assertEquals(4, square.getX());
		Assertions.assertEquals(5, square.getY());
		Assertions.assertEquals('#', square.getType());
		
		assertThrows(IllegalArgumentException.class, () -> {
			new Square(-1, 1);
		}, "Negativ x-verdi skal utløse unntak");
		assertThrows(IllegalArgumentException.class, () -> {
			new Square(1, -1);
		}, "Negativ y-verdi skal utløse unntak");
		assertThrows(IllegalArgumentException.class, () -> {
			new Square(-1, -1);
		}, "Negativ x- og y-verdi skal utløse unntak");
	}

	@Test
	public void testGetType() {
		Assertions.assertEquals('#',square.getType());
	}

	@Test
	public void testGetX() {
		Assertions.assertEquals(4, square.getX());
	}

	@Test
	public void testGetY() {
		Assertions.assertEquals(5, square.getY());
	}

	@Test
	public void testSetters() {
		square.setCoin();
		Assertions.assertEquals('c',square.getType());
		
		square.setPlayer();
		Assertions.assertEquals('P',square.getType());
		
		square.setEnemy();
		Assertions.assertEquals('E',square.getType());
		
		square.setPath();
		Assertions.assertEquals(' ',square.getType());
		
		square.setBrick();
		Assertions.assertEquals('#',square.getType());
	}
	
	

}
