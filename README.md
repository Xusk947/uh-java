# BATHS Naval Battle Simulation

A Java implementation of the BATHS (Battle At The High Seas) simulation system.

## Project Structure

The project is organized as follows:

- **src/**: Contains the source code of the application
  - **wars/**: The main package for all game-related classes
    - **Main.java**: Entry point that can launch either GUI or CLI
    - **BATHS.java**: Main interface defining the game functionality
    - **SeaBattles.java**: Core implementation of the BATHS interface
    - **GameUI.java**: Interface for user interfaces
    - **GameGUI.java**: Graphical user interface implementation
    - **CommandLineUI.java**: Command line interface implementation
    - **Ship.java**, **Frigate.java**, **ManOWar.java**, **Sloop.java**: Ship classes
    - **Encounter.java**, **EncounterType.java**: Encounter classes
    - **ShipState.java**: Enumeration for ship states
  
- **test/**: Contains all test classes
  - **warTesting/**: Package containing JUnit tests for the game
  
- **lib/**: Contains library dependencies
  - **junit.jar**: JUnit 4.13.2 library
  - **hamcrest.jar**: Hamcrest 1.3 library
  
- **data/**: Contains data files used by the application
  - **encountersAM.txt**: Text file with encounter data
  - **baths_save.dat**: Save file for the game
  - **test_save.dat**: Test save file
  
- **docs/**: Contains documentation files
  - **README.txt**: Original documentation
  - **TechnicalReport.txt**: Technical details about the implementation
  - **RolesAndContributions.txt**: Team roles and contributions
  - **ProjectManagement.txt**: Project management information
  
- **scripts/**: Contains utility scripts
  - **run_tests.sh**: Script to compile and run all tests

## Running the Application

There are two ways to run the application:

### Graphical User Interface (GUI)
```
java -cp build:lib/junit-4.13.2.jar:lib/hamcrest-1.3.jar wars.Main
```

### Command Line Interface (CLI)
```
java -cp build:lib/junit-4.13.2.jar:lib/hamcrest-1.3.jar wars.Main --cli
```

## Running Tests

To run all tests, use the provided script:

```
./run_tests.sh
```

This script will:
1. Clean up old class files
2. Compile source code
3. Compile test code
4. Run all JUnit tests

## NetBeans Integration

This project is configured to work with NetBeans IDE. To open it in NetBeans:

1. Open NetBeans
2. Select File > Open Project
3. Navigate to the project directory
4. Select the project and click Open

### NetBeans Configuration Steps (If Needed)

If NetBeans doesn't recognize the project automatically, follow these steps:

1. In NetBeans, go to File > New Project
2. Select Java with Ant > Java Project with Existing Sources
3. Name the project "BATHS" and set the Project Folder to the root directory of this project
4. Click Next
5. In Source Package Folders, add the "src" directory
6. In Test Package Folders, add the "test" directory
7. Click Next, then Finish

### Libraries Configuration

Ensure the libraries are properly configured:

1. Right-click on the project > Properties
2. Go to Libraries
3. Under Compile tab, make sure junit-4.13.2.jar and hamcrest-1.3.jar are added
4. If not, click "Add JAR/Folder" and navigate to the lib directory to add them

## Game Overview

The BATHS game simulates naval battles where the player, as an admiral, commissions ships, fights encounters, and manages resources. The goal is to win battles, earn prize money, and avoid defeat by maintaining a positive war chest balance and keeping ships available. 