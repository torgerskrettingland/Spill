package project.fxui;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface IScoreFileReading {
	
	List<String> readScores(InputStream input);
	
	List<String> readScores(String filename) throws IOException;
	
	void writeScore(List<String> scores, OutputStream output);
	
	void writeScore(int score, String filename) throws IOException;

}
