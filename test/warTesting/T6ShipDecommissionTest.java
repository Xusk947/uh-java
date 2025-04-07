package warTesting;

import wars.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author comqaam
 */
public class T6ShipDecommissionTest {
    BATHS game;
    public T6ShipDecommissionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        game = new SeaBattles("Ola");
        game.commissionShip("Victory");
        game.commissionShip("Surprise");
        game.commissionShip("Arrow");
        // WarChest 1000 - 500 - 100 - 150 = 250
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void decommissionOKShipGetMoney(){
        double expected = 500;
        game.decommissionShip("Victory");
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    }
    
    @Test
    public void decommissionOKShipBackInReserve(){
        game.decommissionShip("Victory");
        String actual = game.getReserveFleet();
        assertTrue(actual.contains("Victory"));
    }
    
    @Test
    public void decommissionOKShipNotInSquadron(){
        game.decommissionShip("Victory");
        assertFalse(game.isInSquadron("Victory"));
    }
    
    @Test
    public void cantDecommissionSunkShipFalse() {
        game.fightEncounter(5); //Victory sunk
        boolean actual = game.decommissionShip("Victory"); // no change to War Chest
        assertFalse(actual);
    }
    
    @Test
    public void cantDecommissionSunkShip() {
        double expected = 250 - 90;
        game.fightEncounter(5); //ship sunk
        game.decommissionShip("Victory"); // no change to War Chest
        double actual = game.getWarChest();
        assertEquals(expected,actual,0.5);
    }

    
    @Test
    public void decommissionShipNotInSquadronMoney(){
        double expected = 250;
        game.decommissionShip("Paris");
        double actual = game.getWarChest();
        assertEquals(expected, actual, 0.5);
    }
    
    @Test
    public void decommissionShipNotInSquadronFalse(){
        game.decommissionShip("Paris");
        boolean actual = game.decommissionShip("Paris");;
        assertFalse(actual);
    }    
     
     
   
    
    @Test
    public void decommissionNoSuchShip(){
        boolean actual = game.decommissionShip("Boggle");
        assertFalse(actual);
    }
   
}
