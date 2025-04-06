package wars;

import java.io.*;
import java.util.*;
/**
 * Task 2 - provide command line interface
 * 
 * @version 16/02/25
 */
public class GameUI
{
    private BATHS myBattles;
    private Scanner myIn = new Scanner(System.in);

    public void doMain()
    {
        int choice;
        System.out.println("Enter admiral's name:");
        String name = myIn.nextLine();
        myBattles = new SeaBattles(name); // create
        
        choice = 100;
        while (choice != 0 )
        {
            choice = getMenuItem();
            if (choice == 1)
            {
                System.out.println(myBattles.getReserveFleet());
            }
            else if (choice == 2)
            {
                System.out.println(myBattles.getSquadron());
            }
            else if (choice == 3)
            {
                System.out.println("Available ships in reserve fleet:");
                System.out.println(myBattles.getReserveFleet());
                System.out.println("Available ships in admiral's squadron:");
                System.out.println(myBattles.getSquadron());
                System.out.println("Enter Ship name:");
                myIn.nextLine();
                String ref = (myIn.nextLine()).trim();
                System.out.println(myBattles.getShipDetails(ref));
            } 
            else if (choice == 4)
            {
                // Commission a ship
                System.out.println("Ships available in reserve fleet:");
                System.out.println(myBattles.getReserveFleet());
                System.out.println("Enter Ship name to commission:");
                myIn.nextLine();
                String shipName = (myIn.nextLine()).trim();
                String result = myBattles.commissionShip(shipName);
                System.out.println(result);
            }
            else if (choice == 5)
            {
                // Fight an encounter
                System.out.println("Available encounters:");
                System.out.println(myBattles.getAllEncounters());
                System.out.println("Ships in your squadron that can fight:");
                System.out.println(myBattles.getSquadron());
                System.out.println("Enter encounter number:");
                int encNo = myIn.nextInt();
                String result = myBattles.fightEncounter(encNo);
                System.out.println(result);
            }
            else if (choice ==6)
            {
                // Restore a ship
                System.out.println("Ships in your squadron:");
                System.out.println(myBattles.getSquadron());
                System.out.println("Note: You can only restore ships that are in RESTING state");
                System.out.println("Enter Ship name to restore:");
                myIn.nextLine();
                String shipName = (myIn.nextLine()).trim();
                
                // Get ship details before restoration
                String beforeDetails = myBattles.getShipDetails(shipName);
                boolean isRestingBefore = beforeDetails.contains("RESTING");
                
                // Attempt restoration
                myBattles.restoreShip(shipName);
                
                // Get ship details after restoration
                String afterDetails = myBattles.getShipDetails(shipName);
                boolean isActivated = afterDetails.contains("ACTIVE");
                
                if (isRestingBefore && isActivated) {
                    System.out.println("Success! Ship " + shipName + " has been restored to ACTIVE state.");
                } else if (!isRestingBefore && isActivated) {
                    System.out.println("Ship " + shipName + " was already in ACTIVE state.");
                } else if (!beforeDetails.equals(afterDetails)) {
                    System.out.println("Ship " + shipName + " has been restored.");
                } else {
                    System.out.println("Ship restoration failed. Ship may not exist or is not in your squadron.");
                }
            }
            else if (choice == 7)
            {
                // Decommission a ship
                System.out.println("Ships in admiral's squadron:");
                System.out.println(myBattles.getSquadron());
                System.out.println("Enter Ship name to decommission:");
                myIn.nextLine();
                String shipName = (myIn.nextLine()).trim();
                boolean result = myBattles.decommissionShip(shipName);
                if (result) {
                    System.out.println("Ship successfully decommissioned");
                } else {
                    System.out.println("Failed to decommission ship");
                }
            }
            else if (choice==8)
            {
                System.out.println(myBattles.toString());
            }
            else if (choice == 9) // Task 7 only
            {
                System.out.println("Write to file");
                myBattles.saveGame("baths_save.dat");
                System.out.println("Game saved to baths_save.dat");
            }
            else if (choice == 10) // Task 7 only
            {
                System.out.println("Loading game from file");
                try {
                    File saveFile = new File("baths_save.dat");
                    if (!saveFile.exists()) {
                        System.out.println("Error: Save file doesn't exist. You need to save a game first (option 9).");
                        continue;
                    }
                    
                    SeaBattles loadedGame = ((SeaBattles)myBattles).loadGame("baths_save.dat");
                    if (loadedGame != null) {
                        System.out.println("Game loaded successfully!");
                        System.out.println(loadedGame.toString());
                        
                        System.out.println("\nDo you want to continue with the loaded game? (y/n):");
                        myIn.nextLine(); // consume newline
                        String answer = myIn.nextLine().trim().toLowerCase();
                        if (answer.equals("y") || answer.equals("yes")) {
                            myBattles = loadedGame;
                            System.out.println("Now using the loaded game state.");
                        } else {
                            System.out.println("Keeping the current game state.");
                        }
                    } else {
                        System.out.println("Error: Failed to load game from file.");
                    }
                } catch (Exception e) {
                    System.out.println("Error loading game: " + e.getMessage());
                    e.printStackTrace();
                }
            }  
        }
        System.out.println("Thank-you");
    }
    
    private int getMenuItem()
    {   int choice = 100;  
        System.out.println("Main Menu");
        System.out.println("0. Quit");
        System.out.println("1. List ships in the reserve fleet");
        System.out.println("2. List ships in admirals squadron"); 
        System.out.println("3. View a ship");
        System.out.println("4. Commission a ship into admiral's squadron");
        System.out.println("5. Fight an encounter");
        System.out.println("6. Restore a ship");
        System.out.println("7. Decommission a ship");
        System.out.println("8. View admiral's state");
        System.out.println("9. Save this game");
        System.out.println("10. Restore a game");
       
        
        while (choice < 0 || choice  > 10)
        {
            System.out.println("Enter the number of your choice:");
            choice =  myIn.nextInt();
        }
        return choice;        
    } 
    
    public static void main(String[] args)
    {
        GameUI xxx = new GameUI();
        xxx.doMain();
    }
}
