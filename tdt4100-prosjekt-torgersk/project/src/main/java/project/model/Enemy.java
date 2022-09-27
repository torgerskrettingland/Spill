package project.model;

import java.util.Arrays;
import java.util.List;
import java.lang.Math;

public class Enemy {
	
	private int x;
	private int y;
	private int deltaX;
	private int deltaY;
	
	// Lister
	public static List<Character> VALID_DESTINATIONS = Arrays.asList(' ', 'c', 'P');
	
	// Konstruktor
	public Enemy(int x, int y, int deltaX, int deltaY) {
		// Validering av posisjon skjer ved oppretting i gameklassen, da det avhenger av brettstørrelsen
		this.x = x;
		this.y = y;
		
		if (!isLegalDirection(deltaX, deltaY)) {
			throw new IllegalArgumentException("(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
		}
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
	
	
	// Gettere
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDeltaX() {
		return deltaX;
	}
	
	public int getDeltaY() {
		return deltaY;
	}
	
	
	// Settere
	public void setX(int x) {
		// Gameklassen tillater enemy kun å bevege seg til gyldige destinasjoner. Validerer derfor ikke ny posisjon her
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setDirection(int deltaX, int deltaY) {
		if (!isLegalDirection(deltaX, deltaY)) {
			throw new IllegalArgumentException("(deltaX, deltaY) must be (0, +-1) or (+-1, 0)");
		}
		this.deltaX = deltaX;
		this.deltaY = deltaY;
	}
	
	
	// Valideringsmetoder
		
	private boolean isLegalDirection(int deltaX, int deltaY) {
		return (Math.abs(deltaX) == 1 && deltaY == 0) || (deltaX == 0 && Math.abs(deltaY) == 1);
	}
}
	

