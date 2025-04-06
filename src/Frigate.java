package wars;

/**
 * A Frigate ship in the BATHS game
 * First implementation of a concrete ship type
 * 
 * @author Aziz Nabiev
 * @version 01/04/2025 (evening)
 */
public class Frigate extends Ship 
{
    private int cannons;
    private boolean hasPinnace;
    
    /**
     * Constructor for Frigate ships
     * @param name The name of the ship
     * @param captain The captain of the ship
     * @param battleSkill The battle skill of the ship
     * @param cannons The number of cannons
     * @param hasPinnace Whether the ship has a pinnace
     */
    public Frigate(String name, String captain, int battleSkill, int cannons, boolean hasPinnace) 
    {
        super(name, captain, battleSkill);
        this.cannons = cannons;
        this.hasPinnace = hasPinnace;
    }
    
    /**
     * Returns the number of cannons
     * @return the number of cannons
     */
    public int getCannons() 
    {
        return cannons;
    }
    
    /**
     * Returns whether the ship has a pinnace
     * @return true if the ship has a pinnace, false otherwise
     */
    public boolean hasPinnace() 
    {
        return hasPinnace;
    }
    
    /**
     * Returns the commission fee for this Frigate
     * Fee is 10 pounds for each cannon
     * @return the commission fee
     */
    @Override
    public double getCommissionFee() 
    {
        // Basic implementation - will be refined later
        return cannons * 10.0;
    }
    
    /**
     * Checks if the ship can fight a particular type of encounter
     * Frigate can fight Battle and Skirmish
     * @param type the type of encounter
     * @return true if the ship can fight the encounter, false otherwise
     */
    @Override
    public boolean canFight(EncounterType type) 
    {
        // Simple implementation - will add more logic later
        return type == EncounterType.BATTLE || type == EncounterType.SKIRMISH;
    }
    
    /**
     * Returns a string representation of the Frigate
     * @return a string representation of the Frigate
     */
    @Override
    public String toString() 
    {
        return super.toString() + " - Frigate with " + cannons + " cannons";
    }
    
    // TODO: Add more specific Frigate methods and capabilities
}
