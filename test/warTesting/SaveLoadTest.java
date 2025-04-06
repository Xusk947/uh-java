package warTesting;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import wars.BATHS;
import wars.SeaBattles;

import static org.junit.Assert.*;

/**
 * Unit test for validating the save and load functionality of the SeaBattles game.
 * 
 * This test performs the following operations:
 * 1. Creates a new game instance with a test admiral
 * 2. Makes changes to the game state (commissions a ship, fights an encounter)
 * 3. Saves the game state to a file
 * 4. Creates a new game instance with different data
 * 5. Loads the saved game data
 * 6. Verifies that the loaded game correctly restores the original state
 * 
 * @version 06/04/2025
 */
public class SaveLoadTest {
    
    private SeaBattles game;
    private final String TEST_SAVE_FILE = "test_save.dat";
    
    public SaveLoadTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        game = new SeaBattles("Admiral Test");
    }
    
    @After
    public void tearDown() {
        // Clean up test save file if it exists
        File saveFile = new File(TEST_SAVE_FILE);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }
    
    /**
     * Helper method to check if a string contains all required substrings
     * @param text the text to check
     * @param s array of strings that should be contained in the text
     * @return true if all strings are found in the text
     */
    private boolean containsText(String text, String[] s) {
        boolean check = true;
        for(int i=0; i < s.length; i++)
            check = check && text.contains(s[i]);
        return check;
    }
    
    @Test
    public void testSaveAndLoad() {
        // Make some changes to the game
        game.commissionShip("Victory");
        game.fightEncounter(10);
        
        // Capture state after changes
        String originalAdmiral = game.toString().split("\n")[0];
        double originalWarChest = game.getWarChest();
        
        // Save the game
        game.saveGame(TEST_SAVE_FILE);
        
        // Create a new game object with different data
        SeaBattles game2 = new SeaBattles("Different Admiral");
        
        // Verify the new game has different state
        assertFalse("New game should have different admiral", 
                game2.toString().split("\n")[0].equals(originalAdmiral));
        
        // Load the saved game
        SeaBattles loadedGame = game2.loadGame(TEST_SAVE_FILE);
        
        // Verify the loaded game is not null
        assertNotNull("Loaded game should not be null", loadedGame);
        
        // Verify that the loaded game data matches the original
        assertEquals("Admiral name should match", 
                originalAdmiral, loadedGame.toString().split("\n")[0]);
        assertEquals("War chest should match", 
                originalWarChest, loadedGame.getWarChest(), 0.01);
    }
    
    @Test
    public void testLoadNonExistentFile() {
        // Try to load a non-existent file
        SeaBattles loadedGame = game.loadGame("non_existent_file.dat");
        
        // Verify the result is null
        assertNull("Loading non-existent file should return null", loadedGame);
    }
    
    // Для запуска без JUnit
    public static void main(String[] args) { 
        System.out.println("*** SAVE/LOAD TEST ***");
        
        SaveLoadTest test = new SaveLoadTest();
        test.setUp();
        
        try {
            test.testSaveAndLoad();
            System.out.println("Save/Load test passed!");
        } catch (AssertionError e) {
            System.out.println("Save/Load test failed: " + e.getMessage());
        }
        
        try {
            test.testLoadNonExistentFile();
            System.out.println("Load non-existent file test passed!");
        } catch (AssertionError e) {
            System.out.println("Load non-existent file test failed: " + e.getMessage());
        }
        
        test.tearDown();
    }
    
    // Методы-заглушки для случая отсутствия JUnit
    private static void assertEquals(String message, String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: '" + expected + "', Actual: '" + actual + "'");
        }
    }
    
    private static void assertEquals(String message, double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }
    
    private static void assertNotNull(String message, Object obj) {
        if (obj == null) {
            throw new AssertionError(message);
        }
    }
    
    private static void assertNull(String message, Object obj) {
        if (obj != null) {
            throw new AssertionError(message);
        }
    }
    
    private static void assertFalse(String message, boolean condition) {
        if (condition) {
            throw new AssertionError(message);
        }
    }
}
