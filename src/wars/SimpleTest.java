package testingwars;

import wars.*;

/**
 * Simple test class for SeaBattles that doesn't require JUnit
 * This class can be used when JUnit libraries are not available.
 * 
 * @author Team CS90
 * @version 26/03/2025
 */
public class SimpleTest {
    
    private static SeaBattles game;
    
    public static void main(String[] args) {
        System.out.println("Starting BATHS Simple Test Suite");
        System.out.println("================================\n");
        
        // Setup
        game = new SeaBattles("Admiral Test");
        
        // Run tests
        testInitialState();
        testCommissionShip();
        testDecommissionShip();
        testFightEncounterWin();
        testFightEncounterLose();
        testRestoreShip();
        
        System.out.println("\nAll tests completed!");
    }
    
    private static void testInitialState() {
        System.out.println("Test: Initial Game State");
        
        boolean notDefeated = !game.isDefeated();
        double warChest = game.getWarChest();
        
        System.out.println("- War chest value: " + warChest);
        System.out.println("- Not defeated: " + notDefeated);
        
        if (notDefeated && Math.abs(warChest - 1000.0) < 0.01) {
            System.out.println("✓ Initial state test passed\n");
        } else {
            System.out.println("✗ Initial state test failed\n");
        }
    }
    
    private static void testCommissionShip() {
        System.out.println("Test: Commission Ship");
        
        String result = game.commissionShip("Victory");
        boolean inSquadron = game.isInSquadron("Victory");
        double warChest = game.getWarChest();
        
        System.out.println("- Commission result: " + result);
        System.out.println("- Ship in squadron: " + inSquadron);
        System.out.println("- War chest after commission: " + warChest);
        
        if (result.equals("Ship commissioned") && inSquadron && warChest < 1000.0) {
            System.out.println("✓ Commission ship test passed\n");
        } else {
            System.out.println("✗ Commission ship test failed\n");
        }
    }
    
    private static void testDecommissionShip() {
        System.out.println("Test: Decommission Ship");
        
        // First ensure we have a ship to decommission
        if (!game.isInSquadron("Victory")) {
            game.commissionShip("Victory");
        }
        
        double beforeWarChest = game.getWarChest();
        boolean result = game.decommissionShip("Victory");
        boolean notInSquadron = !game.isInSquadron("Victory");
        double afterWarChest = game.getWarChest();
        
        System.out.println("- Decommission result: " + result);
        System.out.println("- Ship no longer in squadron: " + notInSquadron);
        System.out.println("- War chest increased by: " + (afterWarChest - beforeWarChest));
        
        if (result && notInSquadron && afterWarChest > beforeWarChest) {
            System.out.println("✓ Decommission ship test passed\n");
        } else {
            System.out.println("✗ Decommission ship test failed\n");
        }
        
        // Recommission the ship for subsequent tests
        game.commissionShip("Victory");
    }
    
    private static void testFightEncounterWin() {
        System.out.println("Test: Fight Encounter (Win)");
        
        // Ensure we have a ship that can win (Victory has skill 3, encounter 10 requires skill 1)
        if (!game.isInSquadron("Victory")) {
            game.commissionShip("Victory");
        }
        
        double beforeWarChest = game.getWarChest();
        String result = game.fightEncounter(10);
        double afterWarChest = game.getWarChest();
        
        System.out.println("- Fight result: " + result);
        System.out.println("- War chest change: " + (afterWarChest - beforeWarChest));
        
        if (result.contains("Encounter won by Victory") && afterWarChest > beforeWarChest) {
            System.out.println("✓ Fight encounter (win) test passed\n");
        } else {
            System.out.println("✗ Fight encounter (win) test failed\n");
        }
    }
    
    private static void testFightEncounterLose() {
        System.out.println("Test: Fight Encounter (Lose)");
        
        // Restore Victory to ACTIVE state if it's RESTING
        game.restoreShip("Victory");
        
        // Commission another ship for testing a loss
        game.commissionShip("Victory");
        
        double beforeWarChest = game.getWarChest();
        // Fight encounter 4 which has reqSkill 9, Victory has skill 3
        String result = game.fightEncounter(4);
        double afterWarChest = game.getWarChest();
        
        System.out.println("- Fight result: " + result);
        System.out.println("- War chest change: " + (afterWarChest - beforeWarChest));
        
        if (result.contains("sunk") && afterWarChest < beforeWarChest) {
            System.out.println("✓ Fight encounter (lose) test passed\n");
        } else {
            System.out.println("✗ Fight encounter (lose) test failed\n");
        }
    }
    
    private static void testRestoreShip() {
        System.out.println("Test: Restore Ship");
        
        // Commission a ship and get it into RESTING state
        game.commissionShip("Endeavour");
        String fightResult = game.fightEncounter(10); // Will win and go to RESTING
        
        // Now restore it
        game.restoreShip("Endeavour");
        
        // Test by trying to fight again
        String secondFightResult = game.fightEncounter(3);
        
        System.out.println("- First fight result: " + fightResult);
        System.out.println("- After restore, second fight result: " + secondFightResult);
        
        if (fightResult.contains("Encounter won by") && 
            secondFightResult.contains("Encounter")) {
            System.out.println("✓ Restore ship test passed\n");
        } else {
            System.out.println("✗ Restore ship test failed\n");
        }
    }
} 