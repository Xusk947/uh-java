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
 * @author comqaam
 */
public class T7ChallengeSequences {
    BATHS game;
    public T7ChallengeSequences() {
    }
    
     @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        game = new SeaBattles("Jean");
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    //Action sequences
    @Test
    public void manOWarSunkUsedAgain() {
        game.commissionShip("Victory");
        game.fightEncounter(5);  //should be sunk
        String actual = game.fightEncounter(5); //re-used ?
        assertTrue(actual.contains("no ship"));
    }
    
    @Test
    public void manOWarSunkUsedAgainMoney() {
        double expected = 1000 - 500 - 90 -90;
        game.commissionShip("Victory");
        game.fightEncounter(5);  //should be sunk
        game.fightEncounter(5); //re-used ?
        double actual = game.getWarChest();
        assertEquals(expected,actual,0.5);
    }

@Test
    public void frigateWithdrawnBeforeBattleMoney() {
        double expected = 1000 - 160 + 80 - 300;
        game.commissionShip("Sophie");
        game.decommissionShip("Sophie");
        game.fightEncounter(1);
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    }
    
    @Test
    public void frigateWithdrawnBeforeBattle() {
        game.commissionShip("Sophie");
        game.decommissionShip("Sophie");
        String actual =game.fightEncounter(1);
        
        assertTrue(actual.contains("no ship"));
    }
 
    
    @Test
    public void defeatAchieved() {
        //double expected = 1000 -500-400-130-45 //-75
        game.commissionShip("Victory");
        game.commissionShip("Beast");
        game.fightEncounter(7);  //lose
        String actual = game.fightEncounter(6);   //lose , all sunk
        assertTrue(actual.contains("defeated"));
    }
    
    @Test
    public void defeatWithDecommissionMoney() {
        double expected = 1000-500-400-130+200-200;
        game.commissionShip("Victory");
        game.commissionShip("Beast");
        game.fightEncounter(7);  //lose
        game.decommissionShip("Beast");
        game.fightEncounter(9);  //no one left
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    }
    
    @Test
    public void defeatNotAchieved() {
        //double expected = 1000-500-400-130-200;
        game.commissionShip("Victory");
        game.commissionShip("Beast");
        game.fightEncounter(7);  //lose Victory
        String actual = game.fightEncounter(7);   //lose as no one available but Beast in Squadron
        assertFalse(actual.contains("defeated"));
    }
   
    @Test
    public void defeatNotAchievedMoney() {
        double expected = 1000-500-400-130-130;
        game.commissionShip("Victory");
        game.commissionShip("Beast");
        game.fightEncounter(7);  //lose Victory
        game.fightEncounter(7); //lose as no one available but Beast in Squadron
        double actual =  game.getWarChest();
        assertEquals(expected,actual, 0.5);
    }


    
// Add your own tests    

}
