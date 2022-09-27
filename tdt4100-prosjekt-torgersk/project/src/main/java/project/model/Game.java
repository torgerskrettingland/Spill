package project.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	// Felter
	private Board board;
	private Player player;
	private List<Enemy> enemies;
	
	// Konstruktor
	public Game(int boardSize) {
		
		this.board = new Board(boardSize);
		spawnPlayerInMidSquare();
		spawnEnemyInEachCorner();
	}
		
	
	// Gettere
	public Board getBoard() {
		return board;
	}
	
	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	public Player getPlayer() {
		return player;
	}
		
	
	// Metoder for spawns
	private void spawnPlayerInMidSquare() {
		this.player = new Player(board.getSize()/2, board.getSize()/2);
		if (!isLegalSpawn(player.getX(), player.getY())) {
			throw new IllegalArgumentException("Player must spawn on an empty square.");
		}
		board.getSquare(player.getX(), player.getY()).setPlayer();
	}
	
	private void spawnEnemyInEachCorner() {
		this.enemies = new ArrayList<>();		
		enemies.add(new Enemy(0, 0, 0, 1));
		enemies.add(new Enemy(board.getSize()-1, 0, 0, 1));
		enemies.add(new Enemy(0, board.getSize()-1, 0, 1));
		enemies.add(new Enemy(board.getSize()-1, board.getSize()-1, 0, 1));
		
		for (int i = 0; i < enemies.size(); i++) {
			int x = enemies.get(i).getX();
			int y = enemies.get(i).getY();
			if (!isLegalSpawn(x, y)) {
				throw new IllegalArgumentException("Enemy must spawn on an empty square.");
			}
			board.getSquare(x, y).setEnemy();
		}
	}
	
	// Validering av spawns
	private boolean isLegalSpawn(int x, int y) {
		if (board.getSquare(x, y) == null) {
			throw new IllegalArgumentException("The requested square does not exist");
		}
		return board.getSquare(x, y).getType() == ' ' || board.getSquare(x, y).getType() == 'c';
	}	

// ------------------
// Flytting av player
// ------------------
		public void movePlayerUp() {
			if (!player.isGameOver()) {
				// Sjekker om destinasjonen er gyldig, og flytter dersom den er er det. 
				if (isLegalPlayerMove(player.getX(), player.getY() - 1)) {
					board.getSquare(player.getX(), player.getY()).setPath();
					player.setY(player.getY() - 1);
				}
				// Oppdaterer brettet
				executePlayersCurrentSquare();
			}
		}
		
		public void movePlayerDown() {
			if (!player.isGameOver()) {
				if (isLegalPlayerMove(player.getX(), player.getY() + 1)) {
					board.getSquare(player.getX(), player.getY()).setPath();
					player.setY(player.getY() + 1);
				}
				executePlayersCurrentSquare();
			}
		}

		public void movePlayerLeft() {
			if (!player.isGameOver()) {
				if (isLegalPlayerMove(player.getX() - 1, player.getY())) {
					board.getSquare(player.getX(), player.getY()).setPath();
					player.setX(player.getX() - 1);
				}
				executePlayersCurrentSquare();
			}
		}

		public void movePlayerRight() {
			if (!player.isGameOver()) {
				if (isLegalPlayerMove(player.getX() + 1, player.getY())) {
					board.getSquare(player.getX(), player.getY()).setPath();
					player.setX(player.getX() + 1);
				}
				executePlayersCurrentSquare();
			}
		}
		
		private void executePlayersCurrentSquare() {
			Square currentSquare = board.getSquare(player.getX(), player.getY());
			
			if (currentSquare.getType() == 'c') {
				player.setScore(player.getScore() + 1);
				currentSquare.setPlayer();
				board.getCoinList().remove(currentSquare);
				board.createCoins();
			}
			if (currentSquare.getType() == 'E') {
				player.setGameOver(true);
			}
			if (currentSquare.getType() == ' ' || currentSquare.getType() == 'P') {
				currentSquare.setPlayer();
			}
			System.out.println(this);
		}
		
		// Validering av flytting av player
		private boolean isLegalPlayerMove(int xDest, int yDest) {
			return board.getSquare(xDest, yDest) != null && Player.VALID_DESTINATIONS.contains(board.getSquare(xDest, yDest).getType());
		}

		
	
// -----------------
// Flytting av enemy
// -----------------
//	
	public void moveEnemy(Enemy enemy) {
		int x = enemy.getX();
		int y = enemy.getY();
		int deltaX = enemy.getDeltaX();
		int deltaY = enemy.getDeltaY();
		
		if (isStraightPath(x, y, deltaX, deltaY)) {
			// Hvis veien går rett frem skal enemy fortsette i samme retning som før
			enemy.setX(enemy.getX() + enemy.getDeltaX());
			enemy.setY(enemy.getY() + enemy.getDeltaY());
			board.getSquare(x, y).setPath();
		} 
		else if (enemyCanMove(x, y)) {
			// Velger tilfeldig en av de rutene det er mulig å flytte til, dersom det finnes noen
			Square destination = ajacentOpenSquares(x, y).get(random(0, ajacentOpenSquares(x, y).size()-1));
			enemy.setX(destination.getX());
			enemy.setY(destination.getY());
			// Setter enemy sin retning til å samsvare med dens siste trekk
			enemy.setDirection(enemy.getX() - x, enemy.getY() - y);
			board.getSquare(x, y).setPath();
		} 
		// Ellers er den for øyeblikket innesperret og skal ikke bevege seg
		
		// Oppdaterer brettet
		excecuteEnemysCurrentSquare(enemy);
	}
	
	private void excecuteEnemysCurrentSquare(Enemy enemy) {
		Square currentSquare = board.getSquare(enemy.getX(), enemy.getY());
		if (currentSquare.getType() == 'c') {
			currentSquare.setEnemy();
			board.getCoinList().remove(currentSquare);
			board.createCoins();
		} else if (currentSquare.getType() == 'P') {
			player.setGameOver(true);
			currentSquare.setEnemy();
		} else {
			currentSquare.setEnemy();
		}
		System.out.println(this);
	}
	
	
	// Hjelpemetoder til flytting av enemy	
	private int random(int min, int max) {
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
	}
	
	
	private Square squareAbove(int x, int y) {
		return board.getSquare(x, y-1);
	}
	private Square squareBelow(int x, int y) {
		return board.getSquare(x, y+1);
	}
	private Square squareRight(int x, int y) {
		return board.getSquare(x+1, y);
	}
	private Square squareLeft(int x, int y) {
		return board.getSquare(x-1, y);
	}
	
	
	private List<Square> ajacentOpenSquares(int x, int y) {
		// AdjacentSquares er de naborutene det er mulig å flytte til
		List<Square> ajacentOpenSquares = new ArrayList<>();
	
		if (squareAbove(x, y) != null && isLegalEnemyMove(x, y-1)) {
			ajacentOpenSquares.add(squareAbove(x, y));
		}
		if (squareBelow(x, y) != null && isLegalEnemyMove(x, y+1)) {
			ajacentOpenSquares.add(squareBelow(x, y));
		}
		if (squareRight(x, y) != null && isLegalEnemyMove(x+1, y)) {
			ajacentOpenSquares.add(squareRight(x, y));
		}
		if (squareLeft(x, y) != null && isLegalEnemyMove(x-1, y)) {
			ajacentOpenSquares.add(squareLeft(x, y));
		}
		return ajacentOpenSquares;
	}
	
	private boolean isPathIntersection(int x, int y) {
		// Sjekker om man befinner seg i et veikryss/sving
		return (ajacentOpenSquares(x, y).contains(squareAbove(x, y)) || 
				ajacentOpenSquares(x, y).contains(squareBelow(x, y)))
				&& 
			   (ajacentOpenSquares(x, y).contains(squareRight(x, y)) || 
				ajacentOpenSquares(x, y).contains(squareLeft(x, y)));
	}
	
	private boolean isStraightPath(int x, int y, int deltaX, int deltaY) {
		// Sjekker om man befinner seg på en rett vei (som går med fartsretningen)
		return ajacentOpenSquares(x, y).size() == 2 && !isPathIntersection(x, y) 
				&& (ajacentOpenSquares(x, y).contains(board.getSquare(x + deltaX, y)) 
				|| ajacentOpenSquares(x, y).contains(board.getSquare(x, y + deltaY)));
	}
	
	
	private boolean enemyCanMove(int x, int y) {
		return ajacentOpenSquares(x, y).size() > 0;
	}
	
	
	// Validering av flytting av enemy
	private boolean isLegalEnemyMove(int xDest, int yDest) {
		return board.getSquare(xDest, yDest) != null && Enemy.VALID_DESTINATIONS.contains(board.getSquare(xDest, yDest).getType());
	}
	

	
//----------
// toString
//----------
	@Override
	public String toString() {
		String gameString = "";
		for (int y = 0; y < board.getSize(); y++) {
			gameString += '\n';
			for (int x = 0; x < board.getSize(); x++) {
				gameString += " " + Character.toString(board.getSquare(x, y).getType());
			}
		}
		if (player.isGameOver()) {
			return "\n\nGame over \nScore: " + player.getScore();
		}
		return gameString;
	}
}
