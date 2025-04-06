BATHS Project - README
===================

This project implements the BATHS (Battles and the High Seas) game as specified in the assignment.

Project Structure
----------------

1. Core Game Classes:
   - BATHS.java - Interface defining the game functionality
   - SeaBattles.java - Main implementation of the BATHS interface
   - Ship.java - Abstract class representing ships
   - ManOWar.java, Frigate.java, Sloop.java - Ship subclasses
   - Encounter.java - Class representing battle encounters
   - ShipState.java, EncounterType.java - Enum classes for state management

2. User Interfaces:
   - GameUI.java - Command-line interface
   - GameGUI.java - Graphical user interface

3. Project Documentation:
   - README.txt - This file
   - TechnicalReport.txt - Technical design decisions analysis
   - RolesAndContributions.txt - Team roles and contributions
   - test/testplan.txt - Test plan with test cases
   - ProjectManagement.txt - Project management documentation placeholder

4. Test Files:
   - test/testingwars/SeaBattlesTest.java - JUnit test for SeaBattles class
   - test/testingwars/SimpleTest.java - Basic functionality test
   - test/testingwars/SaveLoadTest.java - Save/load functionality test
   - encountersAM.txt - Sample encounter data file for testing

Implemented Features
-------------------

✓ Task 3.0 - Completed Teamwork class with team details
✓ Task 3.1 - Implemented backend supplier classes (Ship, subclasses, Encounter)
✓ Task 3.2 - Implemented all BATHS interface methods in SeaBattles
✓ Task 3.3 - Design using inheritance, abstraction, and good OO principles
✓ Task 3.4 - Testing implementation with JUnit tests
✓ Task 3.5 - Persistence implementation with serialization
✓ Task 3.6 - Enhanced GameGUI with required menu items and buttons

Running the Application
----------------------

1. Command-Line Interface:
   - Run GameUI.java to start the text-based interface
   - Follow menu prompts to interact with the game

2. Graphical Interface:
   - Run GameGUI.java to start the graphical interface
   - Use menu items and buttons to play the game

3. Testing:
   - The JUnit tests can be run to verify functionality

Command Reference
----------------

### Compilation Commands

1. Compile all Java source files:
   ```
   javac -d build src/wars/*.java
   ```

2. Compile test files:
   ```
   javac -d build -cp build:src test/testingwars/SimpleTest.java
   javac -d build -cp build:src test/testingwars/SaveLoadTest.java
   ```

3. Compile JUnit tests (requires JUnit library):
   ```
   javac -d build -cp "build:src:lib/junit.jar:lib/hamcrest.jar" test/testingwars/SeaBattlesTest.java
   ```

### Running Commands

1. Run the GUI version:
   ```
   java -cp build wars.GameGUI
   ```

2. Run the text-based interface:
   ```
   java -cp build wars.GameUI
   ```

### Testing Commands

1. Run SimpleTest (basic functionality tests):
   ```
   java -cp build testingwars.SimpleTest
   ```

2. Run SaveLoadTest (save/load functionality tests):
   ```
   java -cp build testingwars.SaveLoadTest
   ```

3. Run SeaBattlesTest (JUnit tests, has 2 failures):
   ```
   java -cp "build:lib/junit.jar:lib/hamcrest.jar" org.junit.runner.JUnitCore testingwars.SeaBattlesTest
   ```

4. Run the GUI version of the game:
   ```
   java -cp build wars.GameGUI
   ```

5. Run the text-based UI version of the game:
   ```
   java -cp build wars.GameUI
   ```

Design Highlights
----------------

- Inheritance hierarchy for ships with an abstract base class
- Use of enums for type-safe state management
- HashMap collections for efficient object lookup
- Serialization for game state persistence
- Comprehensive error handling and input validation
- Separation of concerns between UI and game logic

Team Information
---------------

Team CS90:
- Oleksandr Arzamastsev (23022417)
- Farrukh Kasimkhodja (23000664)
- Aziz Nabiev (23010223)

Created for 5COM2007 Cwk1B BATHS - March 2025

