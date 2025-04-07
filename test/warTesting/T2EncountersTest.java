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
 * @author aam
 */
public class T2EncountersTest {
    BATHS game;
    
    public T2EncountersTest() {
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

     /** Testing Strings is not pleasant. These are not very good tests as the 
     * take no account of capital/lower case, spaces etc.You could use trim() 
     * & toLowerCase().They also don't check the order of String components 
     * (do we care?). But they are enough
     */
     
     
    //just a local method to check a String for contents
    private boolean containsText(String text, String[] s) {
        boolean check = true;
        for(int i=0; i < s.length; i++)
        check = check && text.contains(s[i]);
        return check;
    }
    
    
    @Test
    public void encounterBattleDisplayed() {
        boolean actual = false;
        String result = game.getAllEncounters();
        String[] xx = {"1","Battle","Trafalgar","3","300"};
        actual = containsText(result, xx);
        assertTrue(actual);
    }
    
    @Test
    public void encounterSkirmishDisplayed() {
        boolean actual = false;
        String result = game.getAllEncounters();
        String[] xx = {"2", "Skirmish", "Belle Isle", "3","120"};
        actual = containsText(result,xx );
        assertTrue(actual);
    }
    
    @Test
    public void encounterBlockadeDisplayed() {
        boolean actual = false;
        String result = game.getAllEncounters();
        String[] xx = {"3", "Blockade", "Brest", "3","150"};
        actual = containsText(result, xx);
        assertTrue(actual);
    }

    // You can add more but is it worth it ?
   
    @Test
    public void isEncounterTestMidRange(){
        boolean result = game.isEncounter(3);
        assertTrue(result);
    }
    
    @Test
    public void isEncounterTestStartRange(){
        boolean result = game.isEncounter(1);
        assertTrue(result); 
    }
    
    @Test
    public void isEncounterTestEndRange()
    {
        boolean result = game.isEncounter(10);
        assertTrue(result); 
    }
    
    @Test
    public void isEncounterTestOverEndRange(){
        boolean result = game.isEncounter(14);
        assertFalse(result); 
    }
    
    @Test
    public void isEncounterTestUnderStartRange(){
        boolean result = game.isEncounter(0);
        assertFalse(result); 
    }
    @Test
    public void getEncounterTest(){
        String result = game.getEncounter(3);
        boolean actual = result.contains("Blockade")&& result.contains("Brest");
        assertTrue(actual); 
    }

}
