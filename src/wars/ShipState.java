package wars;

import java.io.*;
/**
 * Enumeration class UnitState - write a description of the enum class here
 * 
 * @version 12/02/2025
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
