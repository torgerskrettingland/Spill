package project.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import project.model.Game;

public class GameController {
	
	private Image PLAYERIMAGE;
	private Image ENEMYIMAGE;
	private Image COINIMAGE;
	private Image PATHIMAGE;
	private Image BRICKIMAGE;
	
	private Game game;
	private final IScoreFileReading fileSupport = new ScoreFileSupport();
	private String filename = "scores";
	
	@FXML
	Pane board;
	
	@FXML
	private void initialize() {
		game = new Game(9);
		
		PLAYERIMAGE = new Image(getClass().getResourceAsStream("/player.png"));
		ENEMYIMAGE = new Image(getClass().getResourceAsStream("/enemy.png"));
		COINIMAGE = new Image(getClass().getResourceAsStream("/coin.png"));
		PATHIMAGE = new Image(getClass().getResourceAsStream("/path.png"));
		BRICKIMAGE = new Image(getClass().getResourceAsStream("/brick.png"));
		
		createBoard();
		drawBoard();
		moveEnemiesOnTimer();
	}
	
	private String getFilename() {
		return filename;
	}
	
	@FXML
	private void writeScore() {
	  	try {
	   		fileSupport.writeScore(game.getPlayer().getScore(), getFilename());
	   	} catch (IOException e) {
	   		System.out.println("Could not write score to file");
			e.printStackTrace();
		}
	}
	
	@FXML
	private List<String> readScores() {
		List<String> scores = new ArrayList<>();
		try {
			scores = fileSupport.readScores(getFilename());
		} catch (IOException e) {
			System.out.println("Could not read score from file");
			e.printStackTrace();
		}
	   	return scores;
	}
	
	private int getHighScore() {
		// Konverterer elementene i listen til Integer, og returnerer det høyeste
		List<Integer> scores = new ArrayList<>();
		try {
			for (String score : readScores()) {
				Integer scoresAsInt = Integer.valueOf(score);
				scores.add(scoresAsInt);
			}
		} catch (NumberFormatException e) {
			System.out.println("The string could not be converted to Integer");
			e.printStackTrace();
		}
		return Collections.max(scores);
	}
	
	
	private void createBoard() {
		board.getChildren().clear();
		for (int y = 0; y < game.getBoard().getSize(); y++) {
			for (int x = 0; x < game.getBoard().getSize(); x++) {
				ImageView square = new ImageView();
				square.setImage(PATHIMAGE);
				square.setTranslateX(x*70);
				square.setTranslateY(y*70);
				square.setFitHeight(70);
				square.setFitWidth(70);
				square.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
				board.getChildren().add(square);
			}
		}
	}

	
	private void drawBoard(){
		for (int y = 0; y < game.getBoard().getSize(); y++) {
			for (int x = 0; x < game.getBoard().getSize(); x++) {
				if (game.getBoard().getSquare(x, y).getType() == 'E') {
					((ImageView) board.getChildren().get(y*game.getBoard().getSize() + x)).setImage(ENEMYIMAGE);
				}
				if (game.getBoard().getSquare(x, y).getType() == 'P') {
					((ImageView) board.getChildren().get(y*game.getBoard().getSize() + x)).setImage(PLAYERIMAGE);
				}
				if (game.getBoard().getSquare(x, y).getType() == 'c') {
					((ImageView) board.getChildren().get(y*game.getBoard().getSize() + x)).setImage(COINIMAGE);
				}
				if (game.getBoard().getSquare(x, y).getType() == ' ') {
					((ImageView) board.getChildren().get(y*game.getBoard().getSize() + x)).setImage(PATHIMAGE);
				}
				if (game.getBoard().getSquare(x, y).getType() == '#') {
					((ImageView) board.getChildren().get(y*game.getBoard().getSize() + x)).setImage(BRICKIMAGE);
				}
			}
		}
	}	
	
	private void onGameOver() {
		// Skriver scoren til fil
		writeScore();
		// Gir et oppdrag til javaFX, men det blir utført ved neste frame, siden onGameOver() kalles utenfor javaFX-tråden
		Platform.runLater(new Runnable(){
			@Override public void run() {
				
				Text gameOverText = new Text();
				gameOverText.setText("Game over\n\n  Score: " + game.getPlayer().getScore() + "\n\nHighscore: " + getHighScore());
				gameOverText.setStyle("-fx-font-size: 50px");
				gameOverText.setFill(Color.BLACK);
				gameOverText.setTranslateX(185.0);
				gameOverText.setTranslateY(205.0);
				board.getChildren().add(gameOverText);
				
				Text restartText = new Text();
				restartText.setText("Press 'R' to restart");
				restartText.setStyle("-fx-font-size: 25px");
				restartText.setFill(Color.BLACK);
				restartText.setTranslateX(220.0);
				restartText.setTranslateY(600.0);
				board.getChildren().add(restartText);
            }
		});
	}
	
	
// Flytting av player
	
	@FXML
	private void handlePlayerInput(KeyEvent evt) {
		String keyName = evt.getCode().getName();
		System.out.println(keyName);
		if (keyName.equals("Up")) {
			game.movePlayerUp();
		}
		if (keyName.equals("Down")) {
			game.movePlayerDown();
		}
		if (keyName.equals("Right")) {
			game.movePlayerRight();
		}
		if (keyName.equals("Left")) {
			game.movePlayerLeft();
		}
		if (game.getPlayer().isGameOver() && keyName.equals("R")) {
			initialize();
		}
		drawBoard();
	}

// Flytting av enemy
	private void timerTask(int i, Timer timer) {
		if (game.getPlayer().isGameOver()) {
			timer.cancel();	
			onGameOver();	
		} else {
			game.moveEnemy(game.getEnemies().get(i));
			System.out.println(game);
		}
		drawBoard();
	}
	
	private void moveEnemiesOnTimer() {
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timerTask(0, timer);
				}
			}, 259, 259);
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timerTask(1, timer);
			}
		}, 503, 503);
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timerTask(2, timer);
			}
		}, 751, 751);
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				timerTask(3, timer);
			}
		}, 997, 997);
	}
}

