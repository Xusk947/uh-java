package wars;

/**
 * This class represents a Man O' War ship in the BATHS system
 * 
 * @version 12/02/25
 */
public class ManOWar extends Ship 
{
    private int decks;
    private int marines;
    
    /**
     * Constructor for Man-O-War ships
     * @param name The name of the ship
     * @param captain The captain of the ship
     * @param battleSkill The battle skill of the ship
     * @param decks The number of decks of cannons
     * @param marines The number of marines
     */
    public ManOWar(String name, String captain, int battleSkill, int decks, int marines) 
    {
        super(name, captain, battleSkill);
        this.decks = decks;
        this.marines = marines;
    }
    
    /**
     * Returns the number of decks
     * @return the number of decks
     */
    public int getDecks() 
    {
        return decks;
    }
    
    /**
     * Returns the number of marines
     * @return the number of marines
     */
    public int getMarines() 
    {
        return marines;
    }
    
    /**
     * Returns the commission fee for this Man-O-War
     * Fee is 500 pounds, or 300 pounds if only 2 decks
     * @return the commission fee
     */
    @Override
    public double getCommissionFee() 
    {
        if (decks == 2) {
            return 300.0;
        } else {
            return 500.0;
        }
    }
    
    /**
     * Checks if the ship can fight a particular type of encounter
     * Man-O-War can fight Blockade and Battle, but not Skirmish
     * @param type the type of encounter
     * @return true if the ship can fight the encounter, false otherwise
     */
    @Override
    public boolean canFight(EncounterType type) 
    {
        return type == EncounterType.BLOCKADE || type == EncounterType.BATTLE;
    }
    
    /**
     * Returns a string representation of the Man-O-War
     * @return a string representation of the Man-O-War
     */
    @Override
    public String toString() 
    {
        return super.toString() + " - Man-O-War with " + decks + " decks and " + marines + " marines";
    }
}
