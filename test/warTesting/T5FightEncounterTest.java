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
 * @author aam
 */
public class T5FightEncounterTest {
    BATHS game;
    
    public T5FightEncounterTest() {
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
    
//ManOWar facing Encounters
    @Test
    public void manOWarFacingBlockadeWins() {
        game.commissionShip("Belerophon");
        String actual = game.fightEncounter(3);
        assertTrue(actual.contains("won"));
    }
 
    @Test
    public void manOWarFacingBlockadeWinsMoney() {
        double expected = 650;
        game.commissionShip("Belerophon"); //warchest= 500
        game.fightEncounter(3);
        double actual = game.getWarChest();
        assertEquals(expected,actual,0.5);
    }
    
    @Test
    public void manOWarFacingBlockadeEqualSkillsWins() {
        game.commissionShip("Victory");
        String actual = game.fightEncounter(3);
        assertTrue(actual.contains("won"));
    }
 
    @Test
    public void manOWarFacingBlockadequalSkillsWinsMoney() {
        double expected = 650;
        game.commissionShip("Victory"); //warchest= 500
        game.fightEncounter(3);
        double actual = game.getWarChest();
        assertEquals(actual,expected,0.5);
    }
    
    @Test
    public void manOWarFacingBlockadeLosesOnSkill() {

        game.commissionShip("Victory");
        String actual = game.fightEncounter(5);
        assertTrue(actual.contains("lost on battle skill"));
    }
    
  
    @Test
    public void manOWarFacingBlockadeLostOnSkillLosesMoney() {

        double expected = 410;
        game.commissionShip("Victory"); //warchest= 500
        game.fightEncounter(5); //loses
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    }
    
    
    @Test
    public void manOWarFacingBlockadeonSunkList() {
        game.commissionShip("Endeavour");
        game.fightEncounter(5);
        String actual= game.getSunkShips();
        assertTrue(actual.contains("Endeavour"));
    }
    
    
    @Test
    public void manOWar2deckFacingBlockadeWinsMoney() {
        double expected = 850;
        game.commissionShip("Endeavour");
        game.fightEncounter(3);
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    }
    
    //ManOwar facing Battle
    @Test
    public void manOWarFacingBattleWins() {
        game.commissionShip("Victory");
        String actual = game.fightEncounter(10);
        assertTrue(actual.contains("won"));
    }
    
  
    @Test
    public void manOWarFacingBattleWinsMoney() {
        double expected = 750;
        game.commissionShip("Victory"); //warchest= 500
        game.fightEncounter(10);
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    }
    
    @Test
    public void manOWarFacingBattleRestingNotInSquadron() {
        game.commissionShip("Victory");
        game.fightEncounter(10);  //wins and rests

        assertFalse(game.isInSquadron("Victory")); 
    }
    
    @Test
    public void manOWarFacingBattleRestingRestoredSquadron() {
        game.commissionShip("Victory");
        game.fightEncounter(10);  //wins and rests
        game.restoreShip("Victory");
        assertTrue(game.isInSquadron("Victory")); 
    }
    public void manOWarRestoredWhenNotInSquadron() {
        //Victory not inSquadron
        game.restoreShip("Victory");
        assertFalse(game.isInSquadron("Victory")); 
    }

    
    @Test
    public void manOWarFacingBattleEqualSkillsWins() {
        game.commissionShip("Victory");
        String actual = game.fightEncounter(1);
        assertTrue(actual.contains("won"));
    }
 
    @Test
    public void manOWarFacingBattleequalSkillsWinsMoney() {
        double expected = 800;
        game.commissionShip("Victory"); //warchest= 500
        game.fightEncounter(1);
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    }

    
    @Test
    public void manOWarFacingBattleLosesOnSkill() {

        game.commissionShip("Victory");
        String actual = game.fightEncounter(4);
        assertTrue(actual.contains("lost on battle skill"));
    }
    
    
    
    @Test
    public void manOWarFacingBattleLostSkillLosesMoney() {
        double expected = 300;
        game.commissionShip("Victory"); //warchest= 500
        game.fightEncounter(4);
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    }
    
    @Test
    public void manOWarFacingBattleisSunk() {
        game.commissionShip("Victory"); //warchest= 500
        game.fightEncounter(4);
        String actual = game.getSunkShips();
        assertTrue(actual.contains("Victory"));
    }
    
    @Test
    public void manOWarFacingBattleisSunkNotRestored() {
        game.commissionShip("Victory"); //warchest= 500
        game.fightEncounter(4);
        game.restoreShip("Victory"); // can't restore as sunk
        assertFalse(game.isInSquadron("Victory"));
    }   
    
    @Test
    public void manOWarFacingSkirmishCantWin() {

        double expected = 410;
        game.commissionShip("Belerophon"); //warchest= 500
        String actual = game.fightEncounter(2); //can't do
        assertTrue(actual.contains("no ship available"));
    }
    
    @Test
    public void manOWarFacingSkirmishCantWinMoney() {

        double expected = 380;
        game.commissionShip("Belerophon"); //warchest= 500
        game.fightEncounter(2); //can't do, lose 180
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    }
    
 
   
   
    @Test
    public void manOWarFacingNoSuchEncounter() {
        game.commissionShip("Victory");
        String actual = game.fightEncounter(20);
        assertTrue(actual.contains("No such"));
    } 
    
    @Test
    public void manOWarFacingNoSuchEncounterNoDeduction() {
        game.commissionShip("Victory"); //war chaest 5--
        game.fightEncounter(20);
        double expected = 500;
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    } 

    
    // Frigate facing Encounters
    @Test
    public void FrigateFacingBlockadewithPinaceWins() {
       game.commissionShip("Sophie");
       String actual = game.fightEncounter(3);
       assertTrue(actual.contains("won"));
    }
    
// Frigate facing Blockade
    @Test
    public void FrigateFacingBlockadewithPinaceWinsMoney() {
       game.commissionShip("Sophie");// war chext 840
       game.fightEncounter(3); //add 260
       double actual = game.getWarChest();
       assertEquals(990, actual,0.5);
    }
    
    @Test
    public void FrigateFacingBlockadeNoPinaceloses() {
       game.commissionShip("Surprise"); // warchest 900
       String actual = game.fightEncounter(3);
       assertTrue(actual.contains("no ship"));
    }
    
    //  Frigate facing Blockade
    @Test
    public void FrigateFacingBlockadeNoPinacelosesMoney() {
       game.commissionShip("Jupiter"); // warchest 800
       game.fightEncounter(3);
       double actual = game.getWarChest();
       assertEquals(650, actual,0.5);
    }
    
   @Test
    public void FridateFacingBattleWinsMoneyAdded() {
        int expected = 1000-160+300;  //960
        game.commissionShip("Sophie");
        game.fightEncounter(1);
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    } 
    
    @Test
    public void FridateFacingBattleLosesMoneyDeducted() {
        int expected = 1000-160-200;  //960
        game.commissionShip("Sophie");
        game.fightEncounter(4);
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    } 
    
    @Test
    public void FridateFacingSkirmishWinsMoneyAdded() {
        int expected = 1000-160+120;  //960
        game.commissionShip("Sophie");
        game.fightEncounter(2);
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    } 
    
     @Test
    public void FridateFacingSkirmishLosesMoneyDeducted() {
        int expected = 1000-200-45;  
        game.commissionShip("Jupiter");
        game.fightEncounter(6);
        double actual = game.getWarChest();
        assertEquals(expected, actual,0.5);
    } 
    
 
    
    
 
//    //Sloop - write your own tests
//    
  
    
    

}
