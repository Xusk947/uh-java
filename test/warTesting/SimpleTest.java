package warTesting;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import wars.BATHS;
import wars.SeaBattles;

import static org.junit.Assert.*;

/**
 * Simple test class for SeaBattles that can be used with or without JUnit
 * 
 * @version 06/04/2025
 */
public class SimpleTest {
    
    private SeaBattles game;
    
    public SimpleTest() {
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
    
    /**
     * Tests the initial state of a newly created game.
     * Validates that:
     * - The game is not in a defeated state
     * - The war chest has the expected starting value of 1000 gold
     */
    @Test
    public void testInitialState() {
        // Test initial state of the game
        assertFalse("New game should not be in a defeated state", game.isDefeated());
        assertEquals("War chest should start with 1000 gold", 1000.0, game.getWarChest(), 0.01);
    }
    
    /**
     * Tests the ship commissioning functionality.
     * Validates that:
     * - A ship can be successfully added to the squadron
     * - The war chest is reduced after commissioning
     */
    @Test
    public void testCommissionShip() {
        // Commission a ship
        String result = game.commissionShip("Victory");
        
        // Verify the ship was commissioned successfully
        assertEquals("Ship should be commissioned successfully", "Ship commissioned", result);
        assertTrue("Ship should be in the squadron", game.isInSquadron("Victory"));
        
        // Verify the war chest was reduced
        assertTrue("War chest should be reduced after commissioning", game.getWarChest() < 1000.0);
    }
    
    /**
     * Tests the ship decommissioning functionality.
     * Validates that:
     * - A ship can be successfully removed from the squadron
     * - The war chest increases after decommissioning
     * - The ship is properly removed from the squadron
     */
    @Test
    public void testDecommissionShip() {
        // First ensure we have a ship to decommission
        game.commissionShip("Victory");
        
        double beforeWarChest = game.getWarChest();
        boolean result = game.decommissionShip("Victory");
        double afterWarChest = game.getWarChest();
        
        // Verify the ship was decommissioned successfully
        assertTrue("Ship should be decommissioned successfully", result);
        assertFalse("Ship should no longer be in the squadron", game.isInSquadron("Victory"));
        
        // Verify the war chest increased
        assertTrue("War chest should increase after decommissioning", afterWarChest > beforeWarChest);
    }
    
    /**
     * Tests winning a battle encounter.
     * Validates that:
     * - A ship with sufficient skill can win an encounter
     * - The war chest increases after winning
     */
    @Test
    public void testFightEncounterWin() {
        // Ensure we have a ship that can win (Victory has skill 3, encounter 10 requires skill 1)
        game.commissionShip("Victory");
        
        double beforeWarChest = game.getWarChest();
        String result = game.fightEncounter(10);
        double afterWarChest = game.getWarChest();
        
        // Verify the encounter was won
        String[] expectedTexts = {"Encounter won by Victory", "added to War Chest"};
        boolean containsExpected = containsText(result, expectedTexts);
        assertTrue("Victory should win this encounter", containsExpected);
        
        // Verify the war chest increased
        assertTrue("War chest should increase after winning", afterWarChest > beforeWarChest);
    }
    
    /**
     * Tests losing a battle encounter.
     * Validates that:
     * - A ship with insufficient skill loses the encounter
     * - The ship sinks when it loses
     * - The war chest decreases after losing
     */
    @Test
    public void testFightEncounterLose() {
        // Commission a ship for testing a loss
        game.commissionShip("Victory");
        
        // Restore the ship if it's in RESTING state
        game.restoreShip("Victory");
        
        double beforeWarChest = game.getWarChest();
        // Fight encounter 4 which has reqSkill 9, Victory has skill 3
        String result = game.fightEncounter(4);
        double afterWarChest = game.getWarChest();
        
        // Verify the encounter was lost and the ship sunk
        String[] expectedTexts = {"lost on battle skill", "Victory", "sunk"};
        boolean containsExpected = containsText(result, expectedTexts);
        assertTrue("Victory should lose this encounter and sink", containsExpected);
        
        // Verify the war chest decreased
        assertTrue("War chest should decrease after losing", afterWarChest < beforeWarChest);
    }
    
    /**
     * Tests the ship restoration functionality.
     * Validates that:
     * - A ship in RESTING state can be restored
     * - A restored ship can participate in new encounters
     */
    @Test
    public void testRestoreShip() {
        // Commission a ship and get it into RESTING state
        game.commissionShip("Endeavour");
        game.fightEncounter(10); // Will win and go to RESTING
        
        // Verify the ship is in the squadron but can't fight (RESTING state)
        assertTrue("Ship should be in the squadron", game.isInSquadron("Endeavour"));
        String resultBeforeRestore = game.fightEncounter(3);
        assertTrue("Ship should not be able to fight while RESTING", 
                resultBeforeRestore.contains("lost as no ship available"));
        
        // Now restore it
        game.restoreShip("Endeavour");
        
        // Test by trying to fight again
        String resultAfterRestore = game.fightEncounter(3);
        
        // Verify the ship can now fight
        assertFalse("Ship should be able to fight after restore", 
                resultAfterRestore.contains("lost as no ship available"));
    }
    
    // Для запуска без JUnit
    public static void main(String[] args) {
        System.out.println("Starting BATHS Simple Test Suite");
        System.out.println("================================\n");
        
        SimpleTest test = new SimpleTest();
        test.setUp();
        
        // Run tests
        runTest(test, "testInitialState");
        runTest(test, "testCommissionShip");
        runTest(test, "testDecommissionShip");
        runTest(test, "testFightEncounterWin");
        runTest(test, "testFightEncounterLose");
        runTest(test, "testRestoreShip");
        
        System.out.println("\nAll tests completed!");
    }
    
    private static void runTest(SimpleTest test, String testName) {
        System.out.println("Test: " + testName);
        try {
            switch(testName) {
                case "testInitialState": test.testInitialState(); break;
                case "testCommissionShip": test.testCommissionShip(); break;
                case "testDecommissionShip": test.testDecommissionShip(); break;
                case "testFightEncounterWin": test.testFightEncounterWin(); break;
                case "testFightEncounterLose": test.testFightEncounterLose(); break;
                case "testRestoreShip": test.testRestoreShip(); break;
            }
            System.out.println("✓ " + testName + " passed\n");
        } catch (AssertionError e) {
            System.out.println("✗ " + testName + " failed: " + e.getMessage() + "\n");
        }
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
    
    private static void assertTrue(String message, boolean condition) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
    
    private static void assertFalse(String message, boolean condition) {
        if (condition) {
            throw new AssertionError(message);
        }
    }
}
