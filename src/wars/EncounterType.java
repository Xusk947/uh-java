package wars;

import java.io.*;
/**
 * Enumeration class EncounterType
 * Lists the types of encounter available
 * 
 * @version 12/02/2025
 */
public enum EncounterType implements Serializable
{
    BLOCKADE(" Blockade"), BATTLE(" Battle"), SKIRMISH (" Skirmish"), INVALID (" Invalid");
    private String type;
    
    private EncounterType(String ty)
    {
        type = ty;
    }
    
    public String toString()
    {
        return type;
    }
}
