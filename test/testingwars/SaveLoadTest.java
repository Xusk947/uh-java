package testingwars;

import wars.*;

/**
 * A simple test for specifically testing save and load functionality
 */
public class SaveLoadTest {

    public static void main(String[] args) {
        System.out.println("*** SAVE/LOAD TEST ***");
        
        // Create a new game
        System.out.println("\nCreating new game...");
        SeaBattles game = new SeaBattles("Admiral Test");
        
        // Show initial state
        System.out.println("\nInitial state:");
        System.out.println("Admiral: " + game.toString().split("\n")[0]);
        System.out.println("War Chest: " + game.getWarChest());
        
        // Make some changes to the game
        System.out.println("\nMaking changes to game...");
        game.commissionShip("Victory");
        game.fightEncounter(10);
        
        // Show state after changes
        System.out.println("\nState after changes:");
        System.out.println("Admiral: " + game.toString().split("\n")[0]);
        System.out.println("War Chest: " + game.getWarChest());
        
        // Save the game
        System.out.println("\nSaving game...");
        game.saveGame("test_save.dat");
        
        // Create a new game object
        System.out.println("\nCreating different game...");
        SeaBattles game2 = new SeaBattles("Different Admiral");
        
        // Show state of new game
        System.out.println("\nNew game state:");
        System.out.println("Admiral: " + game2.toString().split("\n")[0]);
        System.out.println("War Chest: " + game2.getWarChest());
        
        // Load the saved game
        System.out.println("\nLoading saved game...");
        SeaBattles loadedGame = game2.loadGame("test_save.dat");
        
        if (loadedGame == null) {
            System.out.println("ERROR: Failed to load game!");
            return;
        }
        
        // Show state of loaded game
        System.out.println("\nLoaded game state:");
        System.out.println("Admiral: " + loadedGame.toString().split("\n")[0]);
        System.out.println("War Chest: " + loadedGame.getWarChest());
        
        // Verify that the loaded game data matches the original
        boolean admiralCorrect = loadedGame.toString().split("\n")[0].equals(game.toString().split("\n")[0]);
        boolean warChestCorrect = Math.abs(loadedGame.getWarChest() - game.getWarChest()) < 0.01;
        
        System.out.println("\nVerification:");
        System.out.println("Admiral name matches: " + admiralCorrect);
        System.out.println("War chest matches: " + warChestCorrect);
        
        if (admiralCorrect && warChestCorrect) {
            System.out.println("\nSUCCESS: Save/Load functionality works!");
        } else {
            System.out.println("\nFAILURE: Save/Load did not preserve game state correctly!");
        }
    }
} 