package wars;

import java.io.Serializable;

/**
 * Abstract class representing a ship in the BATHS game
 * 
 * @version 22/03/2025
 */
public abstract class Ship implements Serializable 
{
    private String name;
    private String captain;
    private int battleSkill;
    private ShipState state;
    
    /**
     * Constructor for objects of class Ship
     * @param name The name of the ship
     * @param captain The captain of the ship
     * @param battleSkill The battle skill of the ship (0-10)
     */
    public Ship(String name, String captain, int battleSkill) 
    {
        this.name = name;
        this.captain = captain;
        this.battleSkill = battleSkill;
        this.state = ShipState.RESERVE; // All ships start in reserve
    }
    
    /**
     * Returns the name of the ship
     * @return the name of the ship
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * Returns the captain of the ship
     * @return the captain of the ship
     */
    public String getCaptain() 
    {
        return captain;
    }
    
    /**
     * Returns the battle skill of the ship
     * @return the battle skill of the ship
     */
    public int getBattleSkill() 
    {
        return battleSkill;
    }
    
    /**
     * Returns the state of the ship
     * @return the state of the ship
     */
    public ShipState getState() 
    {
        return state;
    }
    
    /**
     * Sets the state of the ship
     * @param state the new state of the ship
     */
    public void setState(ShipState state) 
    {
        this.state = state;
    }
    
    /**
     * Returns the commission fee of the ship
     * @return the commission fee of the ship
     */
    public abstract double getCommissionFee();
    
    /**
     * Checks if the ship can fight a particular type of encounter
     * @param type the type of encounter
     * @return true if the ship can fight the encounter, false otherwise
     */
    public abstract boolean canFight(EncounterType type);
    
    /**
     * Returns a string representation of the ship
     * @return a string representation of the ship
     */
    @Override
    public String toString() 
    {
        return name + " (Captain: " + captain + ", Skill: " + battleSkill + ", State: " + state + ")";
    }
}
