package wars;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Graphical User Interface for the BATHS game.
 * Added improved UI and save/load functionality.
 * 
 * @author Farrukh Kasimkhodja
 * @version 01/04/2025 (evening)
 */
public class GameGUI extends JFrame {
    private BATHS game;
    private JPanel mainPanel;
    private JTextArea battleLog;
    private JComboBox<String> attackerSelection;
    private JComboBox<String> defenderSelection;
    private JComboBox<EncounterType> encounterTypeSelection;
    private JButton battleButton;
    private JButton addShipButton;
    private JButton repairButton;
    private JButton saveButton;
    private JButton loadButton;
    private JLabel statusLabel;
    
    /**
     * Constructor for the GameGUI
     * 
     * @param game The BATHS game instance
     */
    public GameGUI(BATHS game) {
        this.game = game;
        initializeUI();
    }
    
    /**
     * Initialize the UI components
     */
    private void initializeUI() {
        setTitle("Battles And The High Seas");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Battle log area
        battleLog = new JTextArea();
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(battleLog);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        
        // Add a title for the battle log
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.add(new JLabel("Battle Log"), BorderLayout.NORTH);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(logPanel, BorderLayout.CENTER);
        
        // Ship selection panel
        JPanel selectionPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Battle Setup"));
        
        attackerSelection = new JComboBox<>();
        defenderSelection = new JComboBox<>();
        encounterTypeSelection = new JComboBox<>(EncounterType.values());
        
        selectionPanel.add(new JLabel("Attacker:"));
        selectionPanel.add(attackerSelection);
        selectionPanel.add(new JLabel("Defender:"));
        selectionPanel.add(defenderSelection);
        selectionPanel.add(new JLabel("Encounter Type:"));
        selectionPanel.add(encounterTypeSelection);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        battleButton = new JButton("Battle!");
        addShipButton = new JButton("Add Ship");
        repairButton = new JButton("Repair Ship");
        saveButton = new JButton("Save Game");
        loadButton = new JButton("Load Game");
        
        buttonPanel.add(battleButton);
        buttonPanel.add(addShipButton);
        buttonPanel.add(repairButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        
        // Status label
        statusLabel = new JLabel("Ready for battle");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        
        // Control panel combining selection and buttons
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(selectionPanel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);
        controlPanel.add(statusLabel, BorderLayout.SOUTH);
        
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        battleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conductBattle();
            }
        });
        
        addShipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddShipDialog();
            }
        });
        
        repairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRepairDialog();
            }
        });
        
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });
        
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGame();
            }
        });
        
        add(mainPanel);
        
        // Initialize ship selections
        updateShipSelections();
        
        // Welcome message
        battleLog.append("Welcome to Battles And The High Seas!\n");
        battleLog.append("Add ships and conduct battles to begin.\n\n");
    }
    
    /**
     * Updates the ship selection dropdown menus
     */
    private void updateShipSelections() {
        attackerSelection.removeAllItems();
        defenderSelection.removeAllItems();
        
        for (Ship ship : game.getActiveShips()) {
            String shipInfo = ship.getName() + " (" + ship.getHitPoints() + "/" + 
                             ship.getMaxHitPoints() + " HP)";
            attackerSelection.addItem(shipInfo);
            defenderSelection.addItem(shipInfo);
        }
    }
    
    /**
     * Extract the ship name from the combo box selection
     * 
     * @param selection The string from the combo box
     * @return The ship name
     */
    private String extractShipName(String selection) {
        if (selection == null) {
            return null;
        }
        int index = selection.indexOf(" (");
        if (index > 0) {
            return selection.substring(0, index);
        }
        return selection;
    }
    
    /**
     * Conduct a battle between selected ships
     */
    private void conductBattle() {
        String attackerInfo = (String) attackerSelection.getSelectedItem();
        String defenderInfo = (String) defenderSelection.getSelectedItem();
        EncounterType encounterType = (EncounterType) encounterTypeSelection.getSelectedItem();
        
        if (attackerInfo == null || defenderInfo == null) {
            statusLabel.setText("Please select ships for battle");
            return;
        }
        
        String attackerName = extractShipName(attackerInfo);
        String defenderName = extractShipName(defenderInfo);
        
        if (attackerName.equals(defenderName)) {
            statusLabel.setText("A ship cannot battle itself");
            return;
        }
        
        Ship attacker = game.getShipByName(attackerName);
        Ship defender = game.getShipByName(defenderName);
        
        if (attacker == null || defender == null) {
            statusLabel.setText("Ship not found");
            return;
        }
        
        battleLog.append("=== " + encounterType.getDisplayName() + " ===\n");
        battleLog.append("Attacker: " + attacker.toString() + "\n");
        battleLog.append("Defender: " + defender.toString() + "\n");
        
        BATHS.BattleResult result = ((SeaBattles)game).battleWithType(attacker, defender, encounterType);
        
        battleLog.append("Damage dealt: " + result.getDamageDealt() + "\n");
        battleLog.append("After battle: " + defender.toString() + "\n");
        
        if (result.isDefenderSunk()) {
            battleLog.append(defender.getName() + " has been sunk!\n");
        } else {
            battleLog.append(defender.getName() + " is still afloat.\n");
        }
        
        battleLog.append("\n");
        
        updateShipSelections();
        statusLabel.setText("Battle completed");
    }
    
    /**
     * Show dialog for adding a new ship
     */
    private void showAddShipDialog() {
        JDialog dialog = new JDialog(this, "Add New Ship", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JTextField nameField = new JTextField();
        String[] shipTypes = {"Frigate", "Man-of-War", "Sloop"};
        JComboBox<String> typeComboBox = new JComboBox<>(shipTypes);
        JSpinner hitPointsSpinner = new JSpinner(new SpinnerNumberModel(100, 50, 200, 10));
        JSpinner maneuverabilitySpinner = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        JSpinner crewSpinner = new JSpinner(new SpinnerNumberModel(100, 50, 500, 10));
        
        panel.add(new JLabel("Ship Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Ship Type:"));
        panel.add(typeComboBox);
        panel.add(new JLabel("Hit Points:"));
        panel.add(hitPointsSpinner);
        panel.add(new JLabel("Maneuverability (1-10):"));
        panel.add(maneuverabilitySpinner);
        panel.add(new JLabel("Crew:"));
        panel.add(crewSpinner);
        
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Ship");
        JButton cancelButton = new JButton("Cancel");
        
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String type = (String) typeComboBox.getSelectedItem();
                int hitPoints = (Integer) hitPointsSpinner.getValue();
                int maneuverability = (Integer) maneuverabilitySpinner.getValue();
                int crew = (Integer) crewSpinner.getValue();
                
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please enter a ship name");
                    return;
                }
                
                Ship newShip = null;
                switch (type) {
                    case "Frigate":
                        newShip = new Frigate(name, hitPoints, maneuverability, crew, 24);
                        break;
                    case "Man-of-War":
                        newShip = new ManOWar(name, hitPoints, maneuverability, crew, 80);
                        break;
                    case "Sloop":
                        newShip = new Sloop(name, hitPoints, maneuverability, crew, 16);
                        break;
                }
                
                if (newShip != null) {
                    boolean added = game.addShip(newShip);
                    if (added) {
                        battleLog.append("Added new ship: " + newShip.toString() + "\n\n");
                        updateShipSelections();
                        statusLabel.setText("Ship added: " + name);
                    } else {
                        battleLog.append("Failed to add ship: " + name + " (name already exists)\n\n");
                        statusLabel.setText("Failed to add ship");
                    }
                }
                
                dialog.dispose();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    /**
     * Show dialog for repairing a ship
     */
    private void showRepairDialog() {
        List<Ship> activeShips = game.getActiveShips();
        if (activeShips.isEmpty()) {
            statusLabel.setText("No ships to repair");
            return;
        }
        
        JDialog dialog = new JDialog(this, "Repair Ship", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JComboBox<String> shipComboBox = new JComboBox<>();
        for (Ship ship : activeShips) {
            if (ship.getState() != ShipState.SUNK) {
                shipComboBox.addItem(ship.getName() + " (" + ship.getHitPoints() + 
                                     "/" + ship.getMaxHitPoints() + " HP)");
            }
        }
        
        JSpinner repairSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 100, 5));
        
        panel.add(new JLabel("Select Ship:"));
        panel.add(shipComboBox);
        panel.add(new JLabel("Repair Amount:"));
        panel.add(repairSpinner);
        
        JPanel buttonPanel = new JPanel();
        JButton repairButton = new JButton("Repair");
        JButton cancelButton = new JButton("Cancel");
        
        buttonPanel.add(repairButton);
        buttonPanel.add(cancelButton);
        
        repairButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String shipInfo = (String) shipComboBox.getSelectedItem();
                int repairAmount = (Integer) repairSpinner.getValue();
                
                if (shipInfo != null) {
                    String shipName = extractShipName(shipInfo);
                    Ship ship = game.getShipByName(shipName);
                    
                    if (ship != null) {
                        int beforeHP = ship.getHitPoints();
                        boolean repaired = game.repairShip(ship, repairAmount);
                        
                        if (repaired) {
                            int actualRepair = ship.getHitPoints() - beforeHP;
                            battleLog.append("Repaired " + ship.getName() + " for " + actualRepair + " HP\n");
                            battleLog.append("New status: " + ship.toString() + "\n\n");
                            updateShipSelections();
                            statusLabel.setText("Ship repaired: " + shipName);
                        } else {
                            battleLog.append("Failed to repair " + shipName + "\n\n");
                            statusLabel.setText("Failed to repair ship");
                        }
                    }
                }
                
                dialog.dispose();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.setLayout(new BorderLayout());
        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    /**
     * Save the current game state
     */
    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Game");
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filename.endsWith(".dat")) {
                filename += ".dat";
            }
            
            boolean saved = game.saveGame(filename);
            
            if (saved) {
                battleLog.append("Game saved to " + filename + "\n\n");
                statusLabel.setText("Game saved successfully");
            } else {
                battleLog.append("Failed to save game to " + filename + "\n\n");
                statusLabel.setText("Save failed");
            }
        }
    }
    
    /**
     * Load a game state
     */
    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Game");
        
        int userSelection = fileChooser.showOpenDialog(this);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getAbsolutePath();
            
            boolean loaded = game.loadGame(filename);
            
            if (loaded) {
                battleLog.append("Game loaded from " + filename + "\n");
                battleLog.append("Current ships:\n");
                
                for (Ship ship : game.getAllShips()) {
                    battleLog.append("- " + ship.toString() + "\n");
                }
                
                battleLog.append("\n");
                updateShipSelections();
                statusLabel.setText("Game loaded successfully");
            } else {
                battleLog.append("Failed to load game from " + filename + "\n\n");
                statusLabel.setText("Load failed");
            }
        }
    }
    
    /**
     * Main method to start the application
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Set system look and feel
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                BATHS game = new SeaBattles();
                
                // Add some initial ships
                game.addShip(new Frigate("HMS Victory", 120, 7, 220, 32));
                game.addShip(new ManOWar("HMS Sovereign", 180, 5, 450, 100));
                game.addShip(new Sloop("HMS Scout", 80, 9, 120, 18));
                
                GameGUI gui = new GameGUI(game);
                gui.setVisible(true);
            }
        });
    }
} 