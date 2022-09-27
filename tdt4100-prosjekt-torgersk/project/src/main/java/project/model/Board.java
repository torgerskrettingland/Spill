package project.model;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	// Felter
	private int size;
	private Square[][] squares;
	private List<Square> coinList = new ArrayList<>();
	
	// Konstruktor
	public Board(int size) {
		if (!isValidSize(size)) {
			throw new IllegalArgumentException("Board size can not be less than 5, 9 is optimal");
		}
		this.size = size;	
		this.squares = new Square[size][size];
		makeSquares();
		generateLevel();
	}
	
	
	// Gettere
	public Square getSquare(int x, int y) {
		if (!isSquare(x, y)) {
			return null;
		}
		return squares[x][y];
	}
		
	public int getSize() {
		return size;
	}
	
	public List<Square> getCoinList() {
		return coinList;
	}
	
	// Valideringsmetoder
	private boolean isSquare(int x, int y) {
		return x >= 0 && x < getSize() && y >= 0 && y < getSize();
	}
	
	private boolean isValidSize(int size) {
		return size >= 5;
	}
	
	
	// Hjelpemetoder
	
	private void makeSquares() {
		// Oppretter et square-objekt i hver koordinat på brettet, default type er brick
		for (int x = 0; x < getSize(); x++) {
			for (int y = 0; y < getSize(); y++) {
				squares[x][y] = new Square(x, y);
			}
		}	
	}
	
	private void generateLevel() {
		// Endrer noen squares til path og coin
		for (int x = 0; x < getSize(); x++) {
			getSquare(x, 0).setPath();
			getSquare(x, getSize()-1).setPath();
			getSquare(x, getSize()/2).setPath();
		}
		for (int y = 0; y < getSize(); y++) {
			getSquare(0, y).setPath();
			getSquare(getSize()-1, y).setPath();
		}
		for (int x = 2; x < getSize()-2; x++) {
			getSquare(x, 2).setPath();
			getSquare(x, getSize()-3).setPath();
		}
		for (int y = 2; y < getSize()-2; y++) {
			getSquare(2, y).setPath();
			getSquare(getSize()-3, y).setPath();
		}
		getSquare(getSize()/2, 1).setPath();
		getSquare(getSize()/2, getSize()-2).setPath();
		
		createCoins();
	}	
	
	public void createCoins() {
		// Det skal til en hver tid være 10 mynter på brettet, ev. så mange det er plass til under 10
		while (coinList.size() < 10) {
			int randIntX = random(0, getSize());
			int randIntY = random(0, getSize());
			Square chosenSquare = getSquare(randIntX, randIntY);
			if (chosenSquare != null && chosenSquare.getType() == ' ') {
				coinList.add(chosenSquare);
				getSquare(randIntX, randIntY).setCoin();	
			}
		}
	}
	
	private int random(int min, int max) {
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
	}

}

