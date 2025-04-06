package wars;

/**
 * A Sloop ship in the BATHS game
 * 
 * @author Student
 * @version 22/03/2025
 */
public class Sloop extends Ship 
{
    private double commissionFee;
    private boolean hasDoctor;
    
    /**
     * Constructor for Sloop ships
     * @param name The name of the ship
     * @param captain The captain of the ship
     * @param commissionFee The commission fee for the ship
     * @param hasDoctor Whether the ship has a doctor
     */
    public Sloop(String name, String captain, double commissionFee, boolean hasDoctor) 
    {
        super(name, captain, 5); // All sloops have a battle skill of 5
        this.commissionFee = commissionFee;
        this.hasDoctor = hasDoctor;
    }
    
    /**
     * Returns whether the ship has a doctor
     * @return true if the ship has a doctor, false otherwise
     */
    public boolean hasDoctor() 
    {
        return hasDoctor;
    }
    
    /**
     * Returns the commission fee for this Sloop
     * @return the commission fee
     */
    @Override
    public double getCommissionFee() 
    {
        return commissionFee;
    }
    
    /**
     * Checks if the ship can fight a particular type of encounter
     * Sloop can fight Battle and Skirmish, but not Blockade
     * @param type the type of encounter
     * @return true if the ship can fight the encounter, false otherwise
     */
    @Override
    public boolean canFight(EncounterType type) 
    {
        return type == EncounterType.BATTLE || type == EncounterType.SKIRMISH;
    }
    
    /**
     * Returns a string representation of the Sloop
     * @return a string representation of the Sloop
     */
    @Override
    public String toString() 
    {
        return super.toString() + " - Sloop" + 
               (hasDoctor ? " with a doctor" : " without a doctor");
    }
}
