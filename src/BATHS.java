package wars;

import java.io.*;
/**
 * This interface specifies the behaviour expected from the BATHS
 system as required for 5COM2007 Cwk1B BATHS  - Feb 2025
 * 
 * @author A.A.Marczyk 
 * @version 05/02/25 
 */
 
public interface BATHS extends Serializable 
{    
 //**************** BATHS **************************  
    
    /**Returns a String representation of the state of the game,
     * including the name of the admiral, state of the warChest,
     * whether defeated or not, and the ships currently in the 
     * squadron even if resting,(or, "No ships" if squadron is empty), 
     * ships in the reserve fleet and ships sunk
     * @return a String representation of the state of the game,
     * including the name of the admiral, state of the warChest,
     * whether defeated or not, and the ships currently in the 
     * squadron even if resting,(or, "No ships" if squadron is empty), 
     * ships in the reserve fleet and ships sunk
     **/
    public String toString();
    
    
    /** returns true if War Chest <=0 and the admiral's fleet has no 
     * ships which can be decommissioned. 
     * @returns true if War Chest <=0 and the admiral's fleet has no 
     * ships which can be decommissioned,else false. 
     */
    public boolean isDefeated();
    
    /** returns the amount of money in the War Chest
     * @returns the amount of money in the War Chest
     */
    public double getWarChest();
    
    
    /**Returns a String representation of all ships in the reserve fleet
     * @return a String representation of all ships in the reserve fleet
     **/
    public String getReserveFleet();
    
    /**Returns a String representation of the ships in the admiral's squadron
     * or the message "No ships commissioned"
     * @return a String representation of the ships in the admiral's fleet
     **/
    public String getSquadron();
    
    /**Returns a String representation of the ships sunk (or "no ships sunk yet")
     * @return a String representation of the ships sunk
     **/
    public String getSunkShips();    

    /**Returns a String representation of the all ships in the game
     * including reserve, active, resting and sunk ships with their state
     * @return a String representation of the ships in the game
     **/
    public String getAllShips();
    
    
    /** Returns details of the ship with the given name
     * @return details of the ship with the given name
     **/
    public String getShipDetails(String nme);
    
    
 // ***************** Resreve Fleet Ships ************************   
    /** Allows a ship to be commissioned into the admiral's squadron, if there 
     * is enough money in the War Chest for the commission fee.The ship's 
     * state is set to "active"
     * @param nme represents the name of the ship
     * @return "Ship commissioned" if ship is commissioned, "Not found" 
     * if ship not found, "Not available" if ship is not in the reserve fleet, 
     * "Not enough money" if not enough money in the warChest
     **/        
    public String commissionShip(String nme);
    
        
    /** Returns true if the ship with the name is in 
     * the admiral's squadron, false otherwise.
     * @param nme is the name of the ship
     * @return returns true if the ship with the name
     * is in the admiral's fleet, false otherwise.
     **/
    public boolean isInSquadron(String nme);
    
    
    /** Removes a ship from the squadron to the fleet,if they are in the squadron
     * pre-condition: isInAdmiralsSquadron(nme)
     * @param nme is the name of the ship
     * @return true if ship decommissioned, else false
     **/
    public boolean decommissionShip(String nme);
               
    
    /**Restores a ship from resting to the squadron by setting its state to ACTIVE 
     * @param nme the name of the ship to be recommissioned
     */
    public void restoreShip(String nme);
    
    
//**********************Encounters************************* 
    /** returns true if the number represents a encounter
     * @param num is the number of the encounter
     * @return true if the number represents a encounter, else false
     **/
     public boolean isEncounter(int num);
     
          
/** Retrieves the encounter represented by the encounter 
      * number.Finds a ship from the fleet which can fight the 
      * encounter.The results of fighting an encounter will be 
      * one of the following: 
      * 0-Encounter won by...(ship reference and name)-add prize money to War 
      * Chest and set ship's state to RESTING,  
      * 1-Encounter lost as no ship available - deduct prize from the War Chest,
      * 2-Encounter lost on battle skill and (ship name) sunk" - deduct prize 
      * from War Chest and set ship state to SUNK.
      * If an encounter is lost and admiral is completely defeated because there 
      * are no ships to decommission,add "You have been defeated " to message, 
      * -1 No such encounter
      * Ensure that the state of the war chest is also included in the return message.
      * @param encNo is the number of the encounter
      * @return a String showing the result of fighting the encounter
      */ 
    public String fightEncounter(int encNo);
    

    /** Provides a String representation of an encounter given by 
     * the encounter number
     * @param num the number of the encounter
     * @return returns a String representation of a encounter given by 
     * the encounter number
     **/
    public String getEncounter(int num);
    
    
    /** Provides a String representation of all encounters 
     * @return returns a String representation of all encounters
     **/
    public String getAllEncounters();
 
     // ***************   file write/read  *********************
     /** Writes whole game to the specified file
      * @param fname name of file storing requests
      */
     public void saveGame(String fname);
     
     /** reads all information about the game from the specified file 
      * and returns an SeaBattles object
      * @param fname name of file storing the game
      * @return the game (as a SeaBattles object)
      */
     public SeaBattles loadGame(String fname);
}
