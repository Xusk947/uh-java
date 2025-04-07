package warTesting;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import wars.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jp14adn
 */
public class T4ShipCommissionedTest {
    BATHS game;
    
    public T4ShipCommissionedTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        game = new SeaBattles("Olenka");
    }
    
    @After
    public void tearDown() {
    }

    
    // TODO add test methods here.
   
    @Test
    public void shipNotCommissionedNotFound() {
        String result = game.commissionShip("Fred");
        boolean expected = result.contains("Not found");
        assertTrue(expected);
    }

    @Test
    public void shipNotCommissionedNotFoundNoDeduction() {
        String result = game.commissionShip("Fred");
        double actual = game.getWarChest();
        assertEquals(actual, 1000,0.5);
    }    
    
    
    @Test
    public void oneShipCommissioned() {
        String result = game.commissionShip("Victory");
        boolean expected = result.contains("Ship commissioned");
        assertTrue(expected);
    }
    
    @Test
    public void oneShipCommissionedTreasuryDeducted() {
        double expected = 840;
        game.commissionShip("Sophie"); //don't store return
        double actual= game.getWarChest();
        assertEquals(expected, actual,0.5);
    }
    
    @Test
    public void oneShipCommissionedInSquadron() {
        game.commissionShip("Sophie"); 
        boolean actual= game.isInSquadron("Sophie");
        assertTrue(actual);
    }
    
    @Test
    public void oneShipNotInReserve() {
        game.commissionShip("Sophie");
        String reserve = game.getReserveFleet();
        boolean result = reserve.contains("Sophie");
        assertFalse(result);
    }
    
    @Test
    public void oneShipNotCommissionedNotAvailable() {
        game.commissionShip("Sophie"); 
        String result = game.commissionShip("Sophie"); //try to commission again
        boolean actual= result.contains("Not available");
        assertTrue(actual);
    }
    
   @Test
    public void oneShipNotCommissionedNotAvailableNoDeduction() {
        game.commissionShip("Sophie"); //war chest 840
        game.commissionShip("Sophie"); //try to commission again
        double actual= game.getWarChest();
        assertEquals(actual,840,0.5);
    } 

    @Test
    public void notEnoughWarChestNoDeduction() {
        double expected = 340;
        game.commissionShip("Victory");
        game.commissionShip("Sophie"); //war chest 340
        game.commissionShip("Beast"); // not enough money
        assertEquals(expected, game.getWarChest(),0.5);
    }
    
    @Test
    public void notEnoughWarChestResult() {
        game.commissionShip("Victory");
        game.commissionShip("Sophie"); //war chest 340
        String actual = game.commissionShip("Beast");
        assertTrue(actual.contains("Not enough money"));
    }
    
    @Test
    public void notEnoughWarChestSoNotInSquadron() {
        game.commissionShip("Victory");
        game.commissionShip("Sophie"); //war chest 340
        game.commissionShip("Beast"); // not enough money
        boolean actual = game.isInSquadron("Beast");
        assertFalse(actual);
    }
    
    @Test
    public void notEnoughWarChestSoStaysInReserve() {
        game.commissionShip("Victory");
        game.commissionShip("Sophie"); //war chest 340
        game.commissionShip("Beast"); // not enough money
        boolean actual = (game.getReserveFleet()).contains("Beast");
        assertTrue(actual);
    }
      
}
