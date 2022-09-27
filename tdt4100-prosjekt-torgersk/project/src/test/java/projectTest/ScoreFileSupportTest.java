package projectTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import project.fxui.ScoreFileSupport;

public class ScoreFileSupportTest {
	
	private static String testFilename;
	private static Path testFilePath;
	private List<String> actualScoresInTestFile;
	private ScoreFileSupport fileSupport;
	
	@BeforeEach
	public void setup() {
		fileSupport = new ScoreFileSupport();
		testFilename = "test_file";
		testFilePath = Path.of(System.getProperty("user.home"), "tdt4100", "game", testFilename + ".txt");
		actualScoresInTestFile = new ArrayList<>();		
		
		File testFile = new File(testFilePath.toString());
		testFile.delete();
		try {
			testFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testWriteAndRead() {
		try {
			fileSupport.writeScore(5, testFilename);
			fileSupport.writeScore(44, testFilename);
			fileSupport.writeScore(150, testFilename);
		} catch (IOException e) {
			fail("Could not write to file");
		}
		actualScoresInTestFile.add("5");
		actualScoresInTestFile.add("44");
		actualScoresInTestFile.add("150");
		
		List<String> readScores;
		try {
			readScores = fileSupport.readScores(testFilename);
		} catch (IOException e) {
			fail("Could not read file");
			return;
		}
		assertEquals(actualScoresInTestFile, readScores);
	}
		
	
	@Test
	public void testFileNotExist() {
		List<String> readScores;
		try {
			fileSupport.writeScore(5, "test_create_new_file");
			readScores = fileSupport.readScores("test_create_new_file");
		} catch (IOException e) {
			fail("Could not create file");
			return;
		}
		List<String> expectedScores = new ArrayList<>();
		expectedScores.add("5");
		assertEquals(expectedScores, readScores);
		
		try {
			fileSupport.writeScore(6, "test_create_new_file");
			readScores = fileSupport.readScores("test_create_new_file");
		} catch (IOException e) {
			fail("Could not create file");
			return;
		}
		expectedScores.add("6");
		assertEquals(expectedScores, readScores);
		

	}	
	@Test
	public void testWriteToSeperateFiles() {
		try {
			fileSupport.writeScore(15, testFilename);
			fileSupport.writeScore(10, "excessive_file");
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		List<String> readScores;
		try {
			readScores = fileSupport.readScores(testFilename);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		actualScoresInTestFile.add("15");
		assertEquals(actualScoresInTestFile, readScores);
	}
	
	
	@AfterAll
	public static void tearDown() {
		Path createdFilePath = Path.of(System.getProperty("user.home"), "tdt4100", "game", "test_create_new_file.txt");
		File createdFile = new File(createdFilePath.toString());
		createdFile.delete();
		
		Path excessiveFilePath = Path.of(System.getProperty("user.home"), "tdt4100", "game", "excessive_file.txt");
		File excessiveFile = new File(excessiveFilePath.toString());
		excessiveFile.delete();
		
		File testFile = new File(testFilePath.toString());
		testFile.delete();
	}

}
