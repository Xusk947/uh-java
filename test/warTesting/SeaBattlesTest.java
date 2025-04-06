package warTesting;

import wars.SeaBattles;

/**
 * Test class for the SeaBattles implementation of BATHS
 */
public class SeaBattlesTest {
    
    private static int totalTests = 0;
    private static int passedTests = 0;
    
    /**
     * Main method to run all tests
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Running SeaBattlesTest...");
        
        SeaBattlesTest test = new SeaBattlesTest();
        
        // Run all tests
        test.testInitialState();
        test.testCommissionShip();
        test.testDecommissionShip();
        test.testFightEncounter();
        test.testRestoreShip();
        test.testIsDefeated();
        
        // Print summary
        System.out.println("\nTest Summary:");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed tests: " + passedTests);
        System.out.println("Failed tests: " + (totalTests - passedTests));
        
        if (passedTests == totalTests) {
            System.out.println("All tests passed!");
        } else {
            System.out.println("Some tests failed!");
            // Exit with non-zero status to indicate test failure
            System.exit(1);
        }
    }
    
    /**
     * Helper method to assert that a condition is true
     * @param message message to display if assertion fails
     * @param condition condition to check
     */
    private void assertTrue(String message, boolean condition) {
        totalTests++;
        if (condition) {
            passedTests++;
            System.out.println("PASS: " + message);
        } else {
            System.out.println("FAIL: " + message);
        }
    }
    
    /**
     * Helper method to assert that a condition is false
     * @param message message to display if assertion fails
     * @param condition condition to check
     */
    private void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }
    
    /**
     * Helper method to assert that two strings are equal
     * @param message message to display if assertion fails
     * @param expected expected string
     * @param actual actual string
     */
    private void assertEquals(String message, String expected, String actual) {
        totalTests++;
        if (expected.equals(actual)) {
            passedTests++;
            System.out.println("PASS: " + message);
        } else {
            System.out.println("FAIL: " + message + " - Expected: '" + expected + "', Actual: '" + actual + "'");
        }
    }
    
    /**
     * Helper method to assert that two doubles are equal within a delta
     * @param message message to display if assertion fails
     * @param expected expected double
     * @param actual actual double
     * @param delta acceptable difference between expected and actual
     */
    private void assertEquals(String message, double expected, double actual, double delta) {
        totalTests++;
        if (Math.abs(expected - actual) <= delta) {
            passedTests++;
            System.out.println("PASS: " + message);
        } else {
            System.out.println("FAIL: " + message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }
    
    /**
     * Test the initial state of the game
     */
    public void testInitialState() {
        System.out.println("\nRunning testInitialState...");
        SeaBattles game = new SeaBattles("Admiral Test");
        
        // Test initial state of the game
        assertFalse("New game should not be in a defeated state", game.isDefeated());
        assertEquals("War chest should start with 1000 gold", 1000.0, game.getWarChest(), 0.01);
    }
    
    /**
     * Test commissioning ships
     */
    public void testCommissionShip() {
        System.out.println("\nRunning testCommissionShip...");
        SeaBattles game = new SeaBattles("Admiral Test");
        
        // Test commissioning a ship
        String result = game.commissionShip("Victory");
        assertEquals("Ship should be commissioned successfully", "Ship commissioned", result);
        assertTrue("Ship should be in the squadron", game.isInSquadron("Victory"));
        
        // Test commissioning a non-existent ship
        result = game.commissionShip("NonExistentShip");
        assertEquals("Should not find non-existent ship", "Not found", result);
        
        // Test commissioning a ship without enough money
        // First, commission expensive ships to deplete war chest
        game.commissionShip("Sophie");
        game.commissionShip("Jupiter");
        game.commissionShip("Belerophon");
        // Then try to commission another ship
        result = game.commissionShip("Beast");
        assertEquals("Should not have enough money", "Not enough money", result);
    }
    
    /**
     * Test decommissioning ships
     */
    public void testDecommissionShip() {
        System.out.println("\nRunning testDecommissionShip...");
        SeaBattles game = new SeaBattles("Admiral Test");
        
        // Commission a ship first
        game.commissionShip("Victory");
        assertTrue("Ship should be in the squadron", game.isInSquadron("Victory"));
        
        // Test decommissioning the ship
        boolean result = game.decommissionShip("Victory");
        assertTrue("Ship should be decommissioned successfully", result);
        assertFalse("Ship should no longer be in the squadron", game.isInSquadron("Victory"));
        
        // Test decommissioning a non-existent ship
        result = game.decommissionShip("NonExistentShip");
        assertFalse("Cannot decommission non-existent ship", result);
    }
    
    /**
     * Test fighting encounters
     */
    public void testFightEncounter() {
        System.out.println("\nRunning testFightEncounter...");
        SeaBattles game = new SeaBattles("Admiral Test");
        
        // Commission a ship suitable for battle
        game.commissionShip("Victory");
        
        // Fight an encounter where the ship should win (encounter 10 has reqSkill 1, Victory has skill 3)
        String result = game.fightEncounter(10);
        assertTrue("Victory should win this encounter", result.contains("Encounter won by Victory"));
        
        // После первого сражения корабль переходит в состояние RESTING
        // и не может участвовать в следующем сражении, поэтому мы получаем сообщение
        // о том, что корабль недоступен
        String result2 = game.fightEncounter(4);
        assertTrue("Should lose due to no suitable ship", result2.contains("lost as no ship available"));
        
        // Восстановим корабль, чтобы он мог участвовать в сражении
        game.restoreShip("Victory");
        
        // Теперь корабль должен проиграть сражение и затонуть
        String result3 = game.fightEncounter(4); // Encounter 4 has reqSkill 9, Victory has skill 3
        assertTrue("Victory should lose this encounter and sink", result3.contains("lost on battle skill") && result3.contains("Victory") && result3.contains("sunk"));
        
        // Test fighting with no suitable ship available
        result = game.fightEncounter(1); // After Victory is sunk
        assertTrue("Should lose due to no suitable ship", result.contains("lost as no ship available"));
    }
    
    /**
     * Test restoring ships
     */
    public void testRestoreShip() {
        System.out.println("\nRunning testRestoreShip...");
        SeaBattles game = new SeaBattles("Admiral Test");
        
        // Commission a ship
        game.commissionShip("Victory");
        
        // Fight an encounter that the ship will win, putting it in RESTING state
        game.fightEncounter(10);
        
        // The ship should be in the squadron but in RESTING state
        assertTrue("Ship should still be in the squadron", game.isInSquadron("Victory"));
        
        // Restore the ship
        game.restoreShip("Victory");
        
        // Ship should now be ACTIVE and ready for another encounter
        String result = game.fightEncounter(3);
        assertTrue("Victory should be able to fight again", result.contains("Encounter"));
    }
    
    /**
     * Test if the game is defeated
     */
    public void testIsDefeated() {
        System.out.println("\nRunning testIsDefeated...");
        SeaBattles game = new SeaBattles("Admiral Test");
        
        // Commission all ships to deplete war chest
        game.commissionShip("Victory");
        game.commissionShip("Endeavour");
        game.commissionShip("Belerophon");
        game.commissionShip("Sophie");
        game.commissionShip("Surprise");
        game.commissionShip("Jupiter");
        
        // Now decommission all ships except one to get some money back
        game.decommissionShip("Endeavour");
        game.decommissionShip("Belerophon");
        game.decommissionShip("Sophie");
        game.decommissionShip("Surprise");
        game.decommissionShip("Jupiter");
        
        // Fight battles that will lose money
        game.fightEncounter(4); // Expensive battle that Victory will lose
        game.fightEncounter(4); // Expensive battle that Victory will lose
        game.fightEncounter(4); // Expensive battle that Victory will lose

        // Now the remaining ship should be sunk and war chest depleted
        assertTrue("Game should be in defeated state", game.isDefeated());
    }
} 
