package wars;

import java.util.*;
import java.io.*;
/**
 * This class implements the behaviour expected from the BATHS
 system as required for 5COM2007 Cwk1B BATHS - Feb 2025
 * 
 * @author A.A.Marczyk 
 * @version 16/02/25
 */

public class SeaBattles implements BATHS 
{
    // Collections to store ships and encounters
    private Map<String, Ship> allShips; // All ships indexed by name
    private Map<Integer, Encounter> encounters; // All encounters indexed by number
    
    // Game state
    private String admiral;
    private double warChest;
    private boolean defeated;

//**************** BATHS ************************** 
    /** Constructor requires the name of the admiral
     * @param adm the name of the admiral
     */  
    public SeaBattles(String adm)
    {
        admiral = adm;
        warChest = 1000.0; // Initial war chest
        defeated = false;
        
        // Initialize collections
        allShips = new HashMap<>();
        encounters = new HashMap<>();
        
        // Setup initial ships and encounters
        setupShips();
        setupEncounters();
    }
    
    /** Constructor requires the name of the admiral and the
     * name of the file storing encounters
     * @param admir the name of the admiral
     * @param filename name of file storing encounters
     */  
    public SeaBattles(String admir, String filename)  //Task 3
    {
        admiral = admir;
        warChest = 1000.0; // Initial war chest
        defeated = false;
        
        // Initialize collections
        allShips = new HashMap<>();
        encounters = new HashMap<>();
        
        // Setup ships and read encounters from file
        setupShips();
        // setupEncounters(); // Not used in this constructor
        readEncounters(filename);
    }
    
    
    /**Returns a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     * @return a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     **/
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Admiral: ").append(admiral).append("\n");
        sb.append("War Chest: ").append(warChest).append(" pounds\n");
        sb.append("Status: ").append(defeated ? "Defeated" : "Active").append("\n\n");
        
        // Add squadron information
        sb.append("Squadron: ").append("\n");
        String squadron = getSquadron();
        sb.append(squadron).append("\n\n");
        
        // Add reserve fleet information
        sb.append("Reserve Fleet: ").append("\n");
        String reserveFleet = getReserveFleet();
        sb.append(reserveFleet).append("\n\n");
        
        // Add sunk ships information
        sb.append("Sunk Ships: ").append("\n");
        String sunkShips = getSunkShips();
        sb.append(sunkShips);
        
        return sb.toString();
    }
    
    
    /** returns true if War Chest <=0 and the admiral's squadron has no ships which 
     * can be retired. 
     * @returns true if War Chest <=0 and the admiral's fleet has no ships 
     * which can be retired. 
     */
    public boolean isDefeated()
    {
        // Check if war chest is empty or negative
        if (warChest <= 0) {
            // Check if there are any ships that can be decommissioned
            for (Ship ship : allShips.values()) {
                if (ship.getState() == ShipState.ACTIVE || ship.getState() == ShipState.RESTING) {
                    return false; // At least one ship can be decommissioned
                }
            }
            // No ships can be decommissioned and war chest is empty
            defeated = true;
            return true;
        }
        return false;
    }
    
    /** returns the amount of money in the War Chest
     * @returns the amount of money in the War Chest
     */
    public double getWarChest()
    {
        return warChest;
    }
    
    
    /**Returns a String representation of all ships in the reserve fleet
     * @return a String representation of all ships in the reserve fleet
     **/
    public String getReserveFleet()
    {   
        StringBuilder sb = new StringBuilder();
        boolean hasReserveShips = false;
        
        for (Ship ship : allShips.values()) {
            if (ship.getState() == ShipState.RESERVE) {
                sb.append(ship.toString()).append(" [Cost: ").append(ship.getCommissionFee()).append(" gold]").append("\n");
                hasReserveShips = true;
            }
        }
        
        if (!hasReserveShips) {
            return "No ships in reserve";
        }
        
        return sb.toString();
    }
    
    /**Returns a String representation of the ships in the admiral's squadron
     * or the message "No ships commissioned"
     * @return a String representation of the ships in the admiral's fleet
     **/
    public String getSquadron()
    {
        StringBuilder sb = new StringBuilder();
        boolean hasSquadronShips = false;
        
        for (Ship ship : allShips.values()) {
            if (ship.getState() == ShipState.ACTIVE || ship.getState() == ShipState.RESTING) {
                sb.append(ship.toString()).append("\n");
                hasSquadronShips = true;
            }
        }
        
        if (!hasSquadronShips) {
            return "No ships commissioned";
        }
        
        return sb.toString();
    }
    
    /**Returns a String representation of the ships sunk (or "no ships sunk yet")
     * @return a String representation of the ships sunk
     **/
    public String getSunkShips()
    {
        StringBuilder sb = new StringBuilder();
        boolean hasSunkShips = false;
        
        for (Ship ship : allShips.values()) {
            if (ship.getState() == ShipState.SUNK) {
                sb.append(ship.toString()).append("\n");
                hasSunkShips = true;
            }
        }
        
        if (!hasSunkShips) {
            return "No ships sunk yet";
        }
        
        return sb.toString();
    }
    
    /**Returns a String representation of the all ships in the game
     * including their status
     * @return a String representation of the ships in the game
     **/
    public String getAllShips()
    {
        if (allShips.isEmpty()) {
            return "No ships";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Ship ship : allShips.values()) {
            sb.append(ship.toString()).append("\n");
        }
        
        return sb.toString();
    }
    
    
    /** Returns details of any ship with the given name
     * @return details of any ship with the given name
     **/
    public String getShipDetails(String nme)
    {
        Ship ship = allShips.get(nme);
        
        if (ship == null) {
            return "\nNo such ship";
        }
        
        return ship.toString();
    }     
 
    // ***************** Fleet Ships ************************   
    /** Allows a ship to be commissioned to the admiral's squadron, if there 
     * is enough money in the War Chest for the commission fee.The ship's 
     * state is set to "active"
     * @param nme represents the name of the ship
     * @return "Ship commissioned" if ship is commissioned, "Not found" if 
     * ship not found, "Not available" if ship is not in the reserve fleet, "Not 
     * enough money" if not enough money in the warChest
     **/        
    public String commissionShip(String nme)
    {
        // Check if ship exists
        Ship ship = allShips.get(nme);
        if (ship == null) {
            return "Not found";
        }
        
        // Check if ship is in reserve
        if (ship.getState() != ShipState.RESERVE) {
            return "Not available";
        }
        
        // Check if enough money in war chest
        double fee = ship.getCommissionFee();
        if (warChest < fee) {
            return "Not enough money";
        }
        
        // Commission the ship
        ship.setState(ShipState.ACTIVE);
        warChest -= fee;
        
        return "Ship commissioned";
    }
        
    /** Returns true if the ship with the name is in the admiral's squadron, false otherwise.
     * @param nme is the name of the ship
     * @return returns true if the ship with the name is in the admiral's squadron, false otherwise.
     **/
    public boolean isInSquadron(String nme)
    {
        Ship ship = allShips.get(nme);
        
        if (ship == null) {
            return false;
        }
        
        return ship.getState() == ShipState.ACTIVE || ship.getState() == ShipState.RESTING;
    }
    
    /** Decommissions a ship from the squadron to the reserve fleet (if they are in the squadron)
     * pre-condition: isInSquadron(nme)
     * @param nme is the name of the ship
     * @return true if ship decommissioned, else false
     **/
    public boolean decommissionShip(String nme)
    {
        // Check if the ship is in the squadron
        if (!isInSquadron(nme)) {
            return false;
        }
        
        Ship ship = allShips.get(nme);
        
        // Return half the commission fee to the war chest
        warChest += ship.getCommissionFee() / 2;
        
        // Change ship state to reserve
        ship.setState(ShipState.RESERVE);
        
        return true;
    }
    
  
    /**Restores a ship to the squadron by setting their state to ACTIVE 
     * @param ref the name of the ship to be restored
     */
    public void restoreShip(String ref)
    {
        Ship ship = allShips.get(ref);
        
        if (ship != null && ship.getState() == ShipState.RESTING) {
            ship.setState(ShipState.ACTIVE);
        }
    }
    
//**********************Encounters************************* 
    /** returns true if the number represents a encounter
     * @param num is the reference number of the encounter
     * @returns true if the reference number represents a encounter, else false
     **/
     public boolean isEncounter(int num)
     {
         return encounters.containsKey(num);
     }
     
     
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
    public String fightEncounter(int encNo)
    {
        // Check if encounter exists
        if (!isEncounter(encNo)) {
            return "No such encounter";
        }
        
        Encounter encounter = encounters.get(encNo);
        EncounterType encounterType = encounter.getType();
        int requiredSkill = encounter.getRequiredSkill();
        double prizeMoney = encounter.getPrizeMoney();
        
        // Find the first suitable ship
        Ship selectedShip = null;
        
        for (Ship ship : allShips.values()) {
            if (ship.getState() == ShipState.ACTIVE && ship.canFight(encounterType)) {
                selectedShip = ship;
                break;
            }
        }
        
        // Check if a suitable ship was found
        if (selectedShip == null) {
            // No suitable ship
            warChest -= prizeMoney;
            
            String result = "Encounter lost as no ship available - " + prizeMoney + 
                           " pounds deducted from War Chest. War Chest now: " + warChest + " pounds";
            
            // Check if Admiral is defeated
            if (isDefeated()) {
                result += ". You have been defeated!";
            }
            
            return result;
        }
        
        // Compare battle skills
        if (selectedShip.getBattleSkill() >= requiredSkill) {
            // Ship wins the encounter
            warChest += prizeMoney;
            selectedShip.setState(ShipState.RESTING);
            
            return "Encounter won by " + selectedShip.getName() + " - " + prizeMoney + 
                   " pounds added to War Chest. War Chest now: " + warChest + " pounds";
        } else {
            // Ship loses the encounter
            warChest -= prizeMoney;
            selectedShip.setState(ShipState.SUNK);
            
            String result = "Encounter lost on battle skill and " + selectedShip.getName() + 
                           " sunk - " + prizeMoney + " pounds deducted from War Chest. War Chest now: " + 
                           warChest + " pounds";
            
            // Check if Admiral is defeated
            if (isDefeated()) {
                result += ". You have been defeated!";
            }
            
            return result;
        }
    }

    /** Provides a String representation of an encounter given by 
     * the encounter number
     * @param num the number of the encounter
     * @return returns a String representation of a encounter given by 
     * the encounter number
     **/
    public String getEncounter(int num)
    {
        Encounter encounter = encounters.get(num);
        
        if (encounter == null) {
            return "\nNo such encounter";
        }
        
        return encounter.toString();
    }
    
    /** Provides a String representation of all encounters 
     * @return returns a String representation of all encounters
     **/
    public String getAllEncounters()
    {
        if (encounters.isEmpty()) {
            return "No encounters";
        }
        
        StringBuilder sb = new StringBuilder();
        
        for (Encounter encounter : encounters.values()) {
            sb.append(encounter.toString()).append("\n");
        }
        
        return sb.toString();
    }
    

    //****************** private methods for Task 4 functionality*******************
    //*******************************************************************************
     private void setupShips()
     {
        // Set up the ships from Appendix A
        
        // Man-O-Wars
        allShips.put("Victory", new ManOWar("Victory", "Alan Aikin", 3, 3, 30));
        allShips.put("Endeavour", new ManOWar("Endeavour", "Col Cannon", 4, 2, 20));
        allShips.put("Belerophon", new ManOWar("Belerophon", "Ed Evans", 8, 3, 50));
        
        // Frigates
        allShips.put("Sophie", new Frigate("Sophie", "Ben Baggins", 8, 16, true));
        allShips.put("Surprise", new Frigate("Surprise", "Fred Fox", 6, 10, false));
        allShips.put("Jupiter", new Frigate("Jupiter", "Gil Gamage", 7, 20, false));
        
        // Sloops
        allShips.put("Arrow", new Sloop("Arrow", "Dan Dare", 150.0, true));
        allShips.put("Paris", new Sloop("Paris", "Hal Henry", 200.0, true));
        allShips.put("Beast", new Sloop("Beast", "Ian Idle", 400.0, false));
        allShips.put("Athena", new Sloop("Athena", "John Jones", 100.0, true));
     }
     
    private void setupEncounters()
    {
        // Set up encounters from Appendix A
        encounters.put(1, new Encounter(1, EncounterType.BATTLE, "Trafalgar", 3, 300.0));
        encounters.put(2, new Encounter(2, EncounterType.SKIRMISH, "Belle Isle", 3, 120.0));
        encounters.put(3, new Encounter(3, EncounterType.BLOCKADE, "Brest", 3, 150.0));
        encounters.put(4, new Encounter(4, EncounterType.BATTLE, "St Malo", 9, 200.0));
        encounters.put(5, new Encounter(5, EncounterType.BLOCKADE, "Dieppe", 7, 90.0));
        encounters.put(6, new Encounter(6, EncounterType.SKIRMISH, "Jersey", 8, 45.0));
        encounters.put(7, new Encounter(7, EncounterType.BLOCKADE, "Nantes", 6, 130.0));
        encounters.put(8, new Encounter(8, EncounterType.BATTLE, "Finisterre", 4, 100.0));
        encounters.put(9, new Encounter(9, EncounterType.SKIRMISH, "Biscay", 5, 200.0));
        encounters.put(10, new Encounter(10, EncounterType.BATTLE, "Cadiz", 1, 250.0));
    }
        
    //******************************** Task 3.5 **********************************
    /** reads data about encounters from a text file and stores in collection of 
     * encounters.Data in the file is editable
     * @param filename name of the file to be read
     */
    public void readEncounters(String filename)
    { 
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comment lines and empty lines
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }
                
                // Parse encounter data
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    int encounterNumber = Integer.parseInt(parts[0].trim());
                    
                    // Determine encounter type
                    EncounterType type;
                    String typeStr = parts[1].trim();
                    if (typeStr.equalsIgnoreCase("Battle")) {
                        type = EncounterType.BATTLE;
                    } else if (typeStr.equalsIgnoreCase("Skirmish")) {
                        type = EncounterType.SKIRMISH;
                    } else if (typeStr.equalsIgnoreCase("Blockade")) {
                        type = EncounterType.BLOCKADE;
                    } else {
                        type = EncounterType.INVALID;
                    }
                    
                    String location = parts[2].trim();
                    int requiredSkill = Integer.parseInt(parts[3].trim());
                    double prizeMoney = Double.parseDouble(parts[4].trim());
                    
                    // Create and store the encounter
                    Encounter encounter = new Encounter(encounterNumber, type, location, requiredSkill, prizeMoney);
                    encounters.put(encounterNumber, encounter);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading encounters from file: " + e.getMessage());
        }
    }   
 
    
    // ***************   file write/read  *********************
    /** Writes whole game to the specified file
     * @param fname name of file storing requests
     */
    public void saveGame(String fname)
    {   // uses object serialisation 
        System.out.println("Attempting to save game to: " + fname);
        System.out.println("Current admiral: " + this.admiral);
        System.out.println("Current war chest: " + this.warChest);
        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fname))) {
            out.writeObject(this);
            System.out.println("Game object written to output stream");
            out.flush();
            System.out.println("Output stream flushed");
            System.out.println("Game saved to " + fname);
            
            // Verify the file was created
            File savedFile = new File(fname);
            if (savedFile.exists()) {
                System.out.println("Verified: File exists with size: " + savedFile.length() + " bytes");
            } else {
                System.err.println("Warning: File does not appear to exist after saving");
            }
        } catch (IOException e) {
            System.err.println("Error saving game: " + e);
            e.printStackTrace();
        }
    }
    
    /** reads all information about the game from the specified file 
     * and returns 
     * @param fname name of file storing the game
     * @return the game (as an SeaBattles object)
     */
    public SeaBattles loadGame(String fname)
    {   // uses object serialisation 
        System.out.println("Attempting to load game from: " + fname);
        File file = new File(fname);
        if (!file.exists()) {
            System.err.println("Error: Save file does not exist at " + file.getAbsolutePath());
            return null;
        }
        
        if (!file.canRead()) {
            System.err.println("Error: Cannot read save file (permission denied)");
            return null;
        }
        
        System.out.println("File exists and is readable. File size: " + file.length() + " bytes");
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fname))) {
            System.out.println("Created ObjectInputStream successfully");
            Object obj = in.readObject();
            System.out.println("Read object of type: " + obj.getClass().getName());
            
            if (!(obj instanceof SeaBattles)) {
                System.err.println("Error: File does not contain a valid SeaBattles object");
                return null;
            }
            
            SeaBattles game = (SeaBattles) obj;
            System.out.println("Game loaded from " + fname);
            System.out.println("Loaded game has admiral: " + game.admiral);
            System.out.println("Loaded game has war chest: " + game.warChest);
            return game;
        } catch (IOException e) {
            System.err.println("Error loading game (IO Exception): " + e);
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading game (Class Not Found): " + e);
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Unexpected error loading game: " + e);
            e.printStackTrace();
            return null;
        }
    } 
}
