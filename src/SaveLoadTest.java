package testingwars;

import wars.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Test class for save and load functionality.
 * Basic implementation added.
 * 
 * @author Farrukh Kasimkhodja
 * @version 01/04/2025 (evening)
 */
public class SaveLoadTest {
    private static final String SAVE_FILE = "baths_save.dat";
    private static final String TEST_SAVE_FILE = "test_save.dat";
    private static BATHS game;
    
    /**
     * Set up the test environment
     */
    private static void setup() {
        game = new SeaBattles();
        
        // Add some test ships
        game.addShip(new Frigate("HMS Victory", 120, 7, 220, 32));
        game.addShip(new ManOWar("HMS Sovereign", 180, 5, 450, 100));
        game.addShip(new Sloop("HMS Scout", 80, 9, 120, 18));
        
        // Conduct some test battles to populate battle history
        Ship attacker = game.getShipByName("HMS Sovereign");
        Ship defender = game.getShipByName("HMS Scout");
        
        if (attacker != null && defender != null) {
            ((SeaBattles)game).battleWithType(attacker, defender, EncounterType.SKIRMISH);
        }
    }
    
    /**
     * Test saving game state
     */
    private static void testSave() {
        System.out.println("Testing save functionality...");
        
        // Test the BATHS interface save method
        boolean saveResult = game.saveGame(SAVE_FILE);
        System.out.println("Save result using BATHS interface: " + saveResult);
        
        // Also test manual serialization
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEST_SAVE_FILE))) {
            oos.writeObject(game);
            System.out.println("Manual save to " + TEST_SAVE_FILE + " successful");
        } catch (IOException e) {
            System.out.println("Error during manual save: " + e.getMessage());
        }
        
        // Check if the save files exist
        File saveFile = new File(SAVE_FILE);
        File testSaveFile = new File(TEST_SAVE_FILE);
        
        System.out.println(SAVE_FILE + " exists: " + saveFile.exists());
        System.out.println(TEST_SAVE_FILE + " exists: " + testSaveFile.exists());
        
        System.out.println("Save test complete.\n");
    }
    
    /**
     * Test loading game state
     */
    private static void testLoad() {
        System.out.println("Testing load functionality...");
        
        // Create a new game instance to load into
        SeaBattles newGame = new SeaBattles();
        
        // Test the BATHS interface load method
        boolean loadResult = newGame.loadGame(SAVE_FILE);
        System.out.println("Load result using BATHS interface: " + loadResult);
        
        if (loadResult) {
            // Check the loaded ships
            List<Ship> ships = newGame.getAllShips();
            System.out.println("Loaded " + ships.size() + " ships:");
            for (Ship ship : ships) {
                System.out.println("- " + ship.toString());
            }
            
            // Check battle history if available
            List<BATHS.BattleResult> battleHistory = newGame.getBattleHistory();
            if (battleHistory != null) {
                System.out.println("\nLoaded " + battleHistory.size() + " battle records");
            }
        }
        
        // Also test manual deserialization
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEST_SAVE_FILE))) {
            SeaBattles manualLoad = (SeaBattles) ois.readObject();
            System.out.println("\nManual load from " + TEST_SAVE_FILE + " successful");
            System.out.println("Loaded " + manualLoad.getAllShips().size() + " ships");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error during manual load: " + e.getMessage());
        }
        
        System.out.println("Load test complete.\n");
    }
    
    /**
     * Test modifying and saving game state
     */
    private static void testModifyAndSave() {
        System.out.println("Testing game modification and re-save...");
        
        // Load the game
        SeaBattles loadedGame = new SeaBattles();
        boolean loaded = loadedGame.loadGame(SAVE_FILE);
        
        if (loaded) {
            // Add a new ship
            Ship newShip = new Frigate("HMS Surprise", 100, 8, 210, 28);
            loadedGame.addShip(newShip);
            System.out.println("Added new ship: " + newShip.toString());
            
            // Damage an existing ship
            Ship existingShip = loadedGame.getShipByName("HMS Victory");
            if (existingShip != null) {
                existingShip.takeDamage(40);
                System.out.println("Damaged HMS Victory: " + existingShip.toString());
            }
            
            // Save the modified game
            boolean saved = loadedGame.saveGame(TEST_SAVE_FILE);
            System.out.println("Saved modified game: " + saved);
            
            // Load it again to verify
            SeaBattles verifyGame = new SeaBattles();
            boolean verified = verifyGame.loadGame(TEST_SAVE_FILE);
            
            if (verified) {
                System.out.println("\nVerified modified game contents:");
                for (Ship ship : verifyGame.getAllShips()) {
                    System.out.println("- " + ship.toString());
                }
            }
        } else {
            System.out.println("Could not load game for modification test");
        }
        
        System.out.println("Modify and save test complete.\n");
    }
    
    /**
     * Main method to run the tests
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting SaveLoad tests...\n");
        
        setup();
        testSave();
        testLoad();
        testModifyAndSave();
        
        System.out.println("All SaveLoad tests completed.");
    }
} 