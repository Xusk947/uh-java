package testingwars;

import wars.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the SeaBattles implementation of BATHS
 * 
 * @author Team 42
 * @version 26/03/2025
 */
public class SeaBattlesTest {
    
    private SeaBattles game;
    
    @Before
    public void setUp() {
        game = new SeaBattles("Admiral Test");
    }
    
    /**
     * Tests the initial state of a newly created game.
     * Verifies that:
     * - The game is not in a defeated state
     * - The war chest starts with the expected 1000 gold
     */
    @Test
    public void testInitialState() {
        // Test initial state of the game
        assertFalse("New game should not be in a defeated state", game.isDefeated());
        assertEquals("War chest should start with 1000 gold", 1000.0, game.getWarChest(), 0.01);
    }
    
    /**
     * Tests the ship commissioning functionality.
     * Verifies that:
     * - Valid ships can be commissioned successfully
     * - Non-existent ships cannot be commissioned
     * - Ships cannot be commissioned when there's insufficient funds
     */
    @Test
    public void testCommissionShip() {
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
     * Tests the ship decommissioning functionality.
     * Verifies that:
     * - Ships in the squadron can be decommissioned
     * - Attempting to decommission non-existent ships fails
     */
    @Test
    public void testDecommissionShip() {
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
     * Tests the encounter battle system.
     * Verifies that:
     * - Ships with sufficient skill can win encounters
     * - Ships with insufficient skill lose encounters and sink
     * - Game appropriately handles having no suitable ships for an encounter
     */
    @Test
    public void testFightEncounter() {
        // Commission a ship suitable for battle
        game.commissionShip("Victory");
        
        // Fight an encounter where the ship should win (encounter 10 has reqSkill 1, Victory has skill 3)
        String result = game.fightEncounter(10);
        assertTrue("Victory should win this encounter", result.contains("Encounter won by Victory"));
        
        // Fight an encounter where the ship should lose and sink
        // Encounter 4 has reqSkill 9, Victory has skill 3
        result = game.fightEncounter(4);
        assertTrue("Victory should lose this encounter and sink", result.contains("sunk"));
        
        // Test fighting with no suitable ship available
        result = game.fightEncounter(1); // After Victory is sunk
        assertTrue("Should lose due to no suitable ship", result.contains("lost as no ship available"));
    }
    
    /**
     * Tests the ship restoration functionality.
     * Verifies that:
     * - Ships in RESTING state can be restored
     * - Restored ships can participate in new encounters
     */
    @Test
    public void testRestoreShip() {
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
     * Tests the game defeat condition.
     * Verifies that:
     * - Game enters defeated state when no ships remain and war chest is depleted
     */
    @Test
    public void testIsDefeated() {
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
        
        // Now the remaining ship should be sunk and war chest depleted
        assertTrue("Game should be in defeated state", game.isDefeated());
    }
} 