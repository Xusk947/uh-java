/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warTesting;

import wars.*;
import java.util.*;
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
public class T1InitializationTest {
    BATHS game;
    
    public T1InitializationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        game = new SeaBattles("Olek");
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    //just a local method to check a String for contents
    private boolean containsText(String text, String[] s) {
        boolean check = true;
        for(int i=0; i < s.length; i++)
        check = check && text.contains(s[i]);
        return check;
    }
    
    @Test
    public void gameCorrectlyInitialised() {
        String result = game.toString();
        String[] xx = {"Olek","1000", "Is OK", "No ships"};
        boolean actual = containsText(result,xx );
        assertTrue(actual);
    }
    
    @Test
    public void treasuryTest() {
        double expected = 1000;
        double actual = game.getWarChest();
        assertEquals(expected,actual,0.5);
    }
    
    @Test
    public void defeatedTest() {
        boolean actual = game.isDefeated();
        assertFalse(actual);
    }
    
    @Test
    public void checkTeamEmptyAtStart() {
        boolean result = true;
        List<String> ships = new ArrayList<String>(Arrays.asList("Victory","Sophie",
                 "Endeavour","Arrow", "Belerophon", "Surprise","Jupiter", "Paris", "Beast", "Athena"));

        for (String chmp : ships) {
            result = result && !game.isInSquadron(chmp);
        }
        assertTrue(result);
    }
}
