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
public class T3ShipsTest {
    BATHS game;
    
    public T3ShipsTest() {
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
    }
    
    @After
    public void tearDown() {
    }

     
    //a local method to check a String for contents - not a test as no @test
    private boolean containsText(String text, String[] str) {
        boolean result = true;
        for (String temp : str) {
            result = result && (text.toLowerCase()).contains(temp.toLowerCase());
        }
        return result;
    }
    
    @Test
    public void isShipTest()
    {
        boolean actual = game.isInSquadron("Victory");
        assertFalse(actual);
    }
    
    @Test
    public void getShipDetailsSophieTest(){
        String details = game.getShipDetails("Sophie");
        String[] xx = {"Sophie","Ben Baggins","8","160"};
        boolean actual = containsText(details,xx );
        assertTrue(actual);
    }
    
    @Test
    public void shipInReserveEndeavourDisplayed() {
        String result = game.getReserveFleet();
        String[] xx = {"Endeavour","Col Cannon", "4", "500", "2","20" };
        boolean actual = containsText(result,xx );
        assertTrue(actual);
    }
    
    @Test
    public void ShipInReserveArrowDisplayed() {
        String result = game.getReserveFleet();
        String[] xx = {"Arrow", "Dan Dare", "5", "150"};
        boolean actual = containsText(result,xx );
        assertTrue(actual);
    }
    
    @Test
    public void ShipInReserveSurpriseDisplayed() {
        String result = game.getReserveFleet();
        String[] xx = {"Surprise", "Fred Fox", "6","100", "10","Frigate"};
        boolean actual = containsText(result,xx );
        assertTrue(actual);
    }    
    
    @Test
    public void ShipInReserveJupiterDisplayed() {
        String result = game.getReserveFleet();
        String[] xx = {"Jupiter", "Gil Gamage", "7","200", "false", "Frigate"};
        boolean actual = containsText(result,xx );
        assertTrue(actual);
    }
    
    @Test
    public void ShipInReserveParisDisplayed() {
        String result = game.getReserveFleet();
        String[] xx = {"Paris", "Hal Henry","7", "200","true","Sloop"};
        boolean actual = containsText(result,xx );
        assertTrue(actual);
    }
    
    @Test
    public void ShipInReserveBelerophonDisplayed() {
        String result = game.getReserveFleet();
        String[] xx = {"Belerophon","Ed Evans", "8", "500", "50","ManOWar"};
        boolean actual = containsText(result,xx );
        assertTrue(actual);
    }

    @Test
    public void ShipInReserveBeastDisplayed() {
        String result = game.getReserveFleet();
        String[] xx = {"Beast", "false"};
        boolean actual = containsText(result,xx );
        assertTrue(actual);
    }
    
    //Why were those chosen? You can add more but is it worth it ?

         

}
