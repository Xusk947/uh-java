package wars;

import java.io.*;
/**
 * Enumeration class ShipState
 * Lists the states of a ship. Ships in the Admirals fleet
 * will be ACTIVE or RESTING, and if SUNK are no longer in the fleet
 * 
 * @version 05/01/23
 */
public enum ShipState implements Serializable
{
    RESERVE(" In reserve fleet"), ACTIVE(" Active in squadron"), RESTING(" Resting"), SUNK (" Sunk");
    private String state;
    
    private ShipState(String st)
    {
        state = st;
    }
    
    public String toString()
    {
        return state;
    }
}
