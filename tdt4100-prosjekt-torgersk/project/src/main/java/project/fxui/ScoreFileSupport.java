package project.fxui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScoreFileSupport implements IScoreFileReading {
	
	private Path getFilePath(String filename) {
		return Path.of(System.getProperty("user.home"), "tdt4100", "game", filename + ".txt");
	}
	
	private void ensureThatFileExists(String filename) {
		try {						
			File scoreFile = new File(getFilePath(filename).toString());					
			scoreFile.createNewFile();
		} catch (Exception e) {
			System.out.println("Could not create file");
			e.printStackTrace();
		}
	}

    public List<String> readScores(String filename) throws IOException {
    	ensureThatFileExists(filename);
    	Path filePath = getFilePath(filename);
        try (InputStream input = new FileInputStream(filePath.toFile())) {
            return readScores(input);
        }
    }
    
    public List<String> readScores(InputStream input) {
		List<String> scores = new ArrayList<>();
		
		try (Scanner scanner = new Scanner(input)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().stripTrailing();
                scores.add(line);
            }
        } 
		return scores;
    }
    
    public void writeScore(int score, String filename) throws IOException {
    	List<String> scores = readScores(filename);   	
    	String scoreAsString = String.valueOf(score);
    	scores.add(scoreAsString);
    	
    	Path filePath = getFilePath(filename);
       	try (OutputStream output = new FileOutputStream(filePath.toFile())) {
            writeScore(scores, output);
       	}
    }
        
    public void writeScore(List<String> scores, OutputStream output) {
    	try (PrintWriter writer = new PrintWriter(output)) {
    		for (String score : scores) {
    			writer.println(score);
    		}
    	}
    }
}