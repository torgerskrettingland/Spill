package project.model;

import java.util.Arrays;
import java.util.List;

public class Player {
	
	// Felter
	private int x;
	private int y;
	private int score;
	private boolean isGameOver;
	
	// Lister
	public static List<Character> VALID_DESTINATIONS = Arrays.asList(' ', 'c', 'E');

	// Konstruktor
	public Player(int x, int y) {
		// Validering av posisjon skjer ved oppretting i gameklassen, da det avhenger av brettstørrelsen
		this.x = x;
		this.y = y;
		this.score = 0;
		this.isGameOver = false;
	}
	
	
	// Gettere
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	
	
	// Settere
	
	public void setX(int x) {
		// Gameklassen tillater player å bevege seg kun dersom destinasjonen er gyldig. Validerer derfor ikke ny posisjon her
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
		
	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}
}
