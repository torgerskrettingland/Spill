package projectTest;
	
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.Board;
	
public class BoardTest {
		
		private Board board;
		
		@BeforeEach
		public void setup() {
			board = new Board(9);
		}
		
		@Test
		public void testConstructor() {
			assertThrows(IllegalArgumentException.class, () -> {
				new Board(4);
			}, "Board size less than 5 should have thrown exception");
			assertEquals(9, board.getSize());
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					assertTrue(board.getSquare(x, y).getType() == '#' | board.getSquare(x, y).getType() == ' ' | board.getSquare(x, y).getType() ==  'c');
				}
			}
			// Sjekker spesielt rutene der player og enemies skal spawne
			assertTrue(board.getSquare(4, 4).getType() == ' ' || board.getSquare(4, 4).getType() == 'c');
			assertTrue(board.getSquare(0, 0).getType() == ' ' || board.getSquare(0, 0).getType() == 'c');
			assertTrue(board.getSquare(0, 8).getType() == ' ' || board.getSquare(0, 8).getType() == 'c');
			assertTrue(board.getSquare(8, 0).getType() == ' ' || board.getSquare(8, 0).getType() == 'c');
			assertTrue(board.getSquare(8, 8).getType() == ' ' || board.getSquare(8, 8).getType() == 'c');
		}
		
		@Test
		public void testGetSquare() {
			assertNull(board.getSquare(-1, 1));
			assertNull(board.getSquare(1, -1));
			assertNull(board.getSquare(9, 1));
			assertNull(board.getSquare(1, 9));
			assertTrue(board.getSquare(0, 0).getType() == ' ' || board.getSquare(0, 0).getType() == 'c');
		}
		
		public void testGetCoinList() {
			assertEquals(10, board.getCoinList().size());
			for (int i = 0; i < board.getCoinList().size(); i++) {
				assertEquals('c', board.getCoinList().get(i).getType());
			}
		}

		@Test
		public void testGetSize() {
			assertEquals(9, board.getSize());
		}
		
		@Test
		public void testCreateCoins() {
			assertEquals(10, board.getCoinList().size());
			board.createCoins();
			assertEquals(10, board.getCoinList().size());
			board.getCoinList().remove(0);
			board.createCoins();
			assertEquals(10, board.getCoinList().size());
		}

}
