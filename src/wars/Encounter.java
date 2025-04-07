package wars;

import java.io.Serializable;

/**
 * This class represents an encounter in the BATHS system
 * 
 * @version 12/02/25
 */
public class Encounter implements Serializable 
{
    private int encounterNumber;
    private EncounterType type;
    private String location;
    private int requiredSkill;
    private double prizeMoney;
    
    /**
     * Constructor for objects of class Encounter
     * @param encounterNumber The number of the encounter
     * @param type The type of the encounter
     * @param location The location of the encounter
     * @param requiredSkill The required skill level to succeed
     * @param prizeMoney The prize money for the encounter
     */
    public Encounter(int encounterNumber, EncounterType type, String location, int requiredSkill, double prizeMoney) 
    {
        this.encounterNumber = encounterNumber;
        this.type = type;
        this.location = location;
        this.requiredSkill = requiredSkill;
        this.prizeMoney = prizeMoney;
    }
    
    /**
     * Returns the encounter number
     * @return the encounter number
     */
    public int getEncounterNumber() 
    {
        return encounterNumber;
    }
    
    /**
     * Returns the type of encounter
     * @return the type of encounter
     */
    public EncounterType getType() 
    {
        return type;
    }
    
    /**
     * Returns the location of the encounter
     * @return the location of the encounter
     */
    public String getLocation() 
    {
        return location;
    }
    
    /**
     * Returns the required skill level for the encounter
     * @return the required skill level
     */
    public int getRequiredSkill() 
    {
        return requiredSkill;
    }
    
    /**
     * Returns the prize money for the encounter
     * @return the prize money
     */
    public double getPrizeMoney() 
    {
        return prizeMoney;
    }
    
    /**
     * Returns a string representation of the encounter
     * @return a string representation of the encounter
     */
    @Override
    public String toString() 
    {
        return "Encounter " + encounterNumber + ": " + type + 
               " at " + location + ", requires skill " + requiredSkill +
               ", prize money: " + prizeMoney + " pounds";
    }
}
