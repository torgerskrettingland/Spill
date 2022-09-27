package project.model;

public class Square {
	
	// Felter
	private char type;
	private int x;
	private int y;
	
	// Konstruktor
	public Square(int x, int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("Index can not be negative");
		}
		this.x = x;
		this.y = y;
		this.type = '#';
	}
	
	// Gettere
	public char getType() {
		return type;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	// Settere
	public void setBrick() {
		type = '#';
	}
	
	public void setCoin() {
		type = 'c';
	}
	
	public void setPlayer() {
		type = 'P';
	}
	
	public void setEnemy() {
		type = 'E';
	}
	
	public void setPath() {
		type = ' ';
	}

}
