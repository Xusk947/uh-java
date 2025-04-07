package wars;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * Provide a GUI interface for the game
 * 
 * @version 20/02/12
 */
public class GameGUI extends JFrame implements GameUI {
    // Colors for modern design based on shadcn (light theme)
    private static final Color BG_COLOR = new Color(255, 255, 255); // --background: 0 0% 100%
    private static final Color FG_COLOR = new Color(10, 10, 13); // --foreground: 240 10% 3.9%
    private static final Color CARD_COLOR = new Color(250, 250, 250); // --card: 0 0% 100%
    private static final Color CARD_FG_COLOR = new Color(10, 10, 13); // --card-foreground: 240 10% 3.9%

    // Primary colors of the application (primary: blue)
    private static final Color PRIMARY_COLOR = new Color(59, 130, 246); // --chart-2: Pleasant blue color
    private static final Color PRIMARY_FG_COLOR = new Color(250, 250, 250); // --primary-foreground: 0 0% 98%

    // Secondary and accent colors
    private static final Color SECONDARY_COLOR = new Color(243, 243, 246); // --secondary: 240 4.8% 95.9%
    private static final Color SECONDARY_FG_COLOR = new Color(25, 25, 31); // --secondary-foreground: 240 5.9% 10%
    private static final Color ACCENT_COLOR = new Color(243, 243, 246); // --accent: 240 4.8% 95.9%
    private static final Color ACCENT_FG_COLOR = new Color(25, 25, 31); // --accent-foreground: 240 5.9% 10%

    // Colors for muted text and borders
    private static final Color MUTED_COLOR = new Color(243, 243, 246); // --muted: 240 4.8% 95.9%
    private static final Color MUTED_FG_COLOR = new Color(115, 115, 128); // --muted-foreground: 240 3.8% 46.1%
    private static final Color BORDER_COLOR = new Color(228, 228, 233); // --border: 240 5.9% 90%
    private static final Color INPUT_COLOR = Color.WHITE; // White background for text fields
    private static final Color INPUT_BORDER_COLOR = new Color(59, 130, 246); // Blue border for focused input fields

    // Destructive actions (errors, deletion)
    private static final Color DESTRUCTIVE_COLOR = new Color(241, 63, 63); // --destructive: 0 84.2% 60.2%
    private static final Color DESTRUCTIVE_FG_COLOR = new Color(250, 250, 250); // --destructive-foreground: 0 0% 98%

    // Additional colors for charts and visualizations
    private static final Color CHART_COLOR_1 = new Color(233, 93, 67); // --chart-1: 12 76% 61%
    private static final Color CHART_COLOR_2 = new Color(41, 126, 121); // --chart-2: 173 58% 39%
    private static final Color CHART_COLOR_3 = new Color(39, 74, 87); // --chart-3: 197 37% 24%
    private static final Color CHART_COLOR_4 = new Color(217, 202, 60); // --chart-4: 43 74% 66%
    private static final Color CHART_COLOR_5 = new Color(242, 122, 45); // --chart-5: 27 87% 67%

    // Colors for menus, popovers, and tooltips
    private static final Color POPOVER_COLOR = new Color(255, 255, 255); // --popover: 0 0% 100%
    private static final Color POPOVER_FG_COLOR = new Color(10, 10, 13); // --popover-foreground: 240 10% 3.9%
    private static final Color TOOLTIP_COLOR = new Color(243, 243, 246); // Based on --secondary

    private BATHS gp;
    private JFrame myFrame = new JFrame("Battles and the High Seas");
    private Container contentPane = myFrame.getContentPane();
    private JTextArea listing = new JTextArea(20, 40);
    private JLabel titleLabel = new JLabel("Battles and the High Seas");
    private JPanel buttonPanel = new JPanel();
    private JPanel contentPanel = new JPanel();

    // Modern buttons with rounded corners
    private JButton fightBtn;
    private JButton viewBtn;
    private JButton commissionBtn;
    private JButton restoreBtn;
    private JButton clearBtn;
    private JButton quitBtn;

    public GameGUI() {
        // Setting Look and Feel and global UI settings
        try {
            // Set system Look and Feel as the base
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Global UI component settings
            setGlobalUISettings();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Stylized admiral name input dialog
        String admiralName = showStyledInputDialog("Enter your name:", "Battles and the High Seas");

        if (admiralName == null || admiralName.trim().isEmpty()) {
            admiralName = "Admiral Nelson";
        }
        gp = new SeaBattles(admiralName);
        makeFrame();
        makeMenuBar(myFrame);
    }

    /**
     * Implements the doMain method from the GameUI interface
     */
    @Override
    public void doMain() {
        // The GUI implementation starts automatically in the constructor
        // This method exists to satisfy the GameUI interface
    }

    /**
     * Setting global UI settings to improve the appearance of all components
     */
    private void setGlobalUISettings() {
        // Settings for all components
        UIManager.put("Panel.background", CARD_COLOR);
        UIManager.put("OptionPane.background", CARD_COLOR);
        UIManager.put("OptionPane.messageForeground", FG_COLOR);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.BOLD, 12));

        // Button settings for improved visibility
        UIManager.put("Button.background", PRIMARY_COLOR);
        UIManager.put("Button.foreground", PRIMARY_FG_COLOR);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 13)); // Bolder and larger font
        UIManager.put("Button.margin", new Insets(8, 12, 8, 12)); // Increased margins for better visibility
        UIManager.put("Button.focusPainted", false);

        // Text field settings - updated to use white background
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("TextField.caretForeground", Color.BLACK);
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextField.margin", new Insets(5, 5, 5, 5));
        UIManager.put("TextField.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Menu settings
        UIManager.put("Menu.background", POPOVER_COLOR);
        UIManager.put("Menu.foreground", FG_COLOR);
        UIManager.put("Menu.selectionBackground", PRIMARY_COLOR);
        UIManager.put("Menu.selectionForeground", PRIMARY_FG_COLOR);
        UIManager.put("Menu.border", BorderFactory.createEmptyBorder(5, 5, 5, 5));
        UIManager.put("Menu.font", new Font("Segoe UI", Font.PLAIN, 14));

        UIManager.put("MenuBar.background", BG_COLOR);
        UIManager.put("MenuBar.foreground", FG_COLOR);
        UIManager.put("MenuBar.border", BorderFactory.createEmptyBorder(5, 5, 5, 5));

        UIManager.put("MenuItem.background", POPOVER_COLOR);
        UIManager.put("MenuItem.foreground", FG_COLOR);
        UIManager.put("MenuItem.selectionBackground", PRIMARY_COLOR);
        UIManager.put("MenuItem.selectionForeground", PRIMARY_FG_COLOR);
        UIManager.put("MenuItem.border", BorderFactory.createEmptyBorder(5, 10, 5, 10));
        UIManager.put("MenuItem.font", new Font("Segoe UI", Font.PLAIN, 14));

        UIManager.put("PopupMenu.background", POPOVER_COLOR);
        UIManager.put("PopupMenu.foreground", POPOVER_FG_COLOR);
        UIManager.put("PopupMenu.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Dialog settings
        UIManager.put("OptionPane.background", CARD_COLOR);
        UIManager.put("OptionPane.foreground", FG_COLOR);
        UIManager.put("OptionPane.messageForeground", FG_COLOR);
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 14));

        // Tooltip settings
        UIManager.put("ToolTip.background", TOOLTIP_COLOR);
        UIManager.put("ToolTip.foreground", FG_COLOR);
        UIManager.put("ToolTip.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("ToolTip.border", BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    /**
     * Create a custom input dialog, matching the application's style
     */
    private String showStyledInputDialog(String message, String title) {
        try {
            System.out.println("Showing styled input dialog: " + title);
            
            // Fall back to standard JOptionPane if error occurs with custom dialog
            final String[] result = new String[1];
            
            // Use a simpler dialog for the first input to avoid initialization issues
            if (title.equals("Battles and the High Seas")) {
                // Create a simple, clean dialog without any icon
                JTextField textField = new JTextField(15); // Set column count to limit width
                textField.setMaximumSize(new Dimension(200, textField.getPreferredSize().height));
                
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel label = new JLabel(message);
                label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                label.setForeground(FG_COLOR);
                
                textField.setAlignmentX(Component.LEFT_ALIGNMENT);
                textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                textField.setBackground(Color.WHITE);
                textField.setForeground(Color.BLACK);
                textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                textField.setMaximumSize(new Dimension(200, textField.getPreferredSize().height));
                
                // Add focus listener for blue highlight when focused
                textField.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        textField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
                    }
                    
                    @Override
                    public void focusLost(FocusEvent e) {
                        textField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(BORDER_COLOR),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                    }
                });
                
                panel.add(label);
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
                panel.add(textField);
                
                // Create a custom button with the same style as other buttons
        JButton okButton = new JButton("OK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded button background
                g2.setColor(getModel().isPressed() ? new Color(29, 78, 216) : // Dark blue when pressed
                        (getModel().isRollover() ? new Color(37, 99, 235) : // Slightly lighter on hover
                                PRIMARY_COLOR)); // Regular blue in normal state

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));

                FontMetrics metrics = g2.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 64, 175)); // Dark blue border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };

                // Style the button
                okButton.setOpaque(false);
                okButton.setContentAreaFilled(false);
                okButton.setBorderPainted(true);
                okButton.setFocusPainted(false);
                okButton.setPreferredSize(new Dimension(140, 40));
                
                // Add button to panel
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttonPanel.setBackground(CARD_COLOR);
                buttonPanel.add(okButton);
                
                // Create and configure the dialog
                JDialog dialog = new JDialog((Frame)null, title, true);
                panel.add(Box.createRigidArea(new Dimension(0, 15)));
                panel.add(buttonPanel);
                dialog.setContentPane(panel);
                
                // Add action listener to button
                okButton.addActionListener(e -> {
                    result[0] = textField.getText();
                    dialog.dispose();
                });
                
                // Add action listener to text field for Enter key
                textField.addActionListener(e -> {
                    result[0] = textField.getText();
                    dialog.dispose();
                });
                
                dialog.pack();
                dialog.setSize(350, 200);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                
                return (result[0] != null && !result[0].trim().isEmpty()) ? result[0] : "Admiral Nelson";
            }
            
            // For other dialogs, use the custom styled version
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBackground(CARD_COLOR);
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JLabel label = new JLabel(message);
            label.setForeground(FG_COLOR);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            JTextField field = new JTextField(15); // Set column count to limit width
            field.setMaximumSize(new Dimension(200, field.getPreferredSize().height));
            field.setPreferredSize(new Dimension(200, field.getPreferredSize().height));
            
            field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            field.setBackground(INPUT_COLOR);
            field.setForeground(FG_COLOR);
            field.setCaretColor(FG_COLOR);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
            // Add focus listener for blue highlight when focused
            field.addFocusListener(new FocusAdapter() {
            @Override
                public void focusGained(FocusEvent e) {
                    field.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                            BorderFactory.createEmptyBorder(4, 4, 4, 4)));
                }
                
                @Override
                public void focusLost(FocusEvent e) {
                    field.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER_COLOR),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                }
            });

            JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inputWrapper.setBackground(CARD_COLOR);
            inputWrapper.add(field);
            
            panel.add(label, BorderLayout.NORTH);
            panel.add(inputWrapper, BorderLayout.CENTER);

            // Create custom buttons with rounded corners
            JButton okButton = new JButton("OK");
            JButton cancelButton = new JButton("Cancel");

        // Button properties setup
        okButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.setPreferredSize(new Dimension(100, 40));

        // Add buttons to panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Create custom dialog
        JDialog dialog = new JDialog(myFrame, title, true);
        dialog.setContentPane(panel);

        // Add logic for buttons
        okButton.addActionListener(e -> {
            result[0] = field.getText();
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });

        // Handle Enter key press in text field
        field.addActionListener(e -> {
            result[0] = field.getText();
            dialog.dispose();
        });

        // Handle window close
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                result[0] = null;
                dialog.dispose();
            }
        });

        // Set dialog size and position
        dialog.pack();
            dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(myFrame);
        dialog.setVisible(true);

        return result[0];
        } catch (Exception e) {
            System.out.println("ERROR: Exception in showStyledInputDialog: " + e.getMessage());
            e.printStackTrace();
            
            // Simple fallback without icon
            String input = JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
            return (input != null && !input.trim().isEmpty()) ? input : "Admiral Nelson";
        }
    }

    /**
     * Create a custom confirmation dialog, matching the application's style
     */
    private boolean showStyledConfirmDialog(String message, String title) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel(message);
        label.setForeground(FG_COLOR);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label, BorderLayout.CENTER);

        // Create custom buttons with rounded corners
        JButton yesButton = new JButton("Yes") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded button background
                g2.setColor(getModel().isPressed() ? new Color(29, 78, 216) : // Dark blue when pressed
                        (getModel().isRollover() ? new Color(37, 99, 235) : // Slightly lighter on hover
                                PRIMARY_COLOR)); // Regular blue in normal state

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));

                FontMetrics metrics = g2.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 64, 175)); // Dark blue border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };

        JButton noButton = new JButton("No") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded button background in gray
                g2.setColor(getModel().isPressed() ? new Color(100, 100, 100) : // Dark gray when pressed
                        (getModel().isRollover() ? new Color(150, 150, 150) : // Slightly lighter on hover
                                new Color(200, 200, 200))); // Regular gray in normal state

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));

                FontMetrics metrics = g2.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(100, 100, 100)); // Dark gray border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };

        // Button properties setup
        yesButton.setOpaque(false);
        yesButton.setContentAreaFilled(false);
        yesButton.setBorderPainted(true);
        yesButton.setFocusPainted(false);
        yesButton.setPreferredSize(new Dimension(100, 40));

        noButton.setOpaque(false);
        noButton.setContentAreaFilled(false);
        noButton.setBorderPainted(true);
        noButton.setFocusPainted(false);
        noButton.setPreferredSize(new Dimension(100, 40));

        // Add buttons to panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Create custom dialog
        JDialog dialog = new JDialog(myFrame, title, true);
        dialog.setContentPane(panel);

        // Add logic for buttons
        final boolean[] result = new boolean[1];

        yesButton.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });

        noButton.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });

        // Handle window close
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                result[0] = false;
                dialog.dispose();
            }
        });

        // Set dialog size and position
        dialog.pack();
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(myFrame);
        dialog.setVisible(true);

        return result[0];
    }

    /**
     * Create a styled button with improved visibility
     */
    private JButton createStyledButton(String text) {
        // Create a regular button instead of custom drawing
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded button background
                g2.setColor(getModel().isPressed() ? new Color(29, 78, 216) : // Dark blue when pressed
                        (getModel().isRollover() ? new Color(37, 99, 235) : // Slightly lighter on hover
                                PRIMARY_COLOR)); // Regular blue in normal state

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));

                FontMetrics metrics = g2.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 64, 175)); // Dark blue border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };

        // Button properties setup
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setFocusPainted(false);

        // Fixed size for uniformity
        button.setPreferredSize(new Dimension(200, 40));
        button.setMinimumSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));

        return button;
    }

    /**
     * Create the Swing frame and its content
     */
    private void makeFrame() {
        // Main window setup
        contentPane.setLayout(new BorderLayout(20, 20));
        contentPane.setBackground(BG_COLOR);

        // Stylized header - full-width blue bar with centered white text
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(PRIMARY_COLOR);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        titleLabel = new JLabel("Battles and the High Seas");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        contentPane.add(titlePanel, BorderLayout.NORTH);

        // Content panel with text area
        contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(CARD_COLOR);
        contentPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Stylized text area
        listing.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listing.setLineWrap(true);
        listing.setWrapStyleWord(true);
        listing.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)));

        JScrollPane listingScroll = new JScrollPane(listing);
        listingScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        listingScroll.setBorder(null);
        listingScroll.setBackground(CARD_COLOR);
        listingScroll.setPreferredSize(new Dimension(350, 150)); // Set preferred size

        contentPanel.add(listingScroll, BorderLayout.CENTER);
        contentPane.add(contentPanel, BorderLayout.CENTER);

        // Button panel in side menu
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Create buttons with improved styling
        fightBtn = createStyledButton("Engage in Battle");
        viewBtn = createStyledButton("View Admiral Status");
        commissionBtn = createStyledButton("Commission Ship");
        restoreBtn = createStyledButton("Restore Ship");
        clearBtn = createStyledButton("Clear");
        quitBtn = createStyledButton("Quit");

        // Add event handlers
        viewBtn.addActionListener(new ViewStateHandler());
        fightBtn.addActionListener(new FightHandler());
        commissionBtn.addActionListener(new CommissionHandler());
        restoreBtn.addActionListener(new RestoreHandler());
        clearBtn.addActionListener(new ClearHandler());
        quitBtn.addActionListener(new QuitHandler());

        // Add buttons to panel with margins
        addButtonWithMargin(buttonPanel, fightBtn);
        addButtonWithMargin(buttonPanel, viewBtn);
        addButtonWithMargin(buttonPanel, commissionBtn);
        addButtonWithMargin(buttonPanel, restoreBtn);
        addButtonWithMargin(buttonPanel, clearBtn);
        addButtonWithMargin(buttonPanel, quitBtn);

        contentPane.add(buttonPanel, BorderLayout.EAST);

        // Additional frame settings
        myFrame.pack();
        myFrame.setMinimumSize(new Dimension(800, 600));
        myFrame.setLocationRelativeTo(null); // Center the window
        myFrame.setVisible(true);
        listing.setText("Welcome!\n\n" + gp.toString());
    }

    /**
     * Add a button to the panel with a vertical margin for better visual separation
     */
    private void addButtonWithMargin(JPanel panel, JButton button) {
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Margin between buttons
    }

    /**
     * Create the Swing frame and its content.
     */
    private void makeMenuBar(JFrame frame) {
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(BG_COLOR);
        menubar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        frame.setJMenuBar(menubar);

        // Style for menu
        UIManager.put("Menu.selectionBackground", PRIMARY_COLOR);
        UIManager.put("Menu.selectionForeground", PRIMARY_FG_COLOR);
        UIManager.put("Menu.foreground", FG_COLOR);
        UIManager.put("Menu.background", POPOVER_COLOR);
        UIManager.put("MenuItem.selectionBackground", PRIMARY_COLOR);
        UIManager.put("MenuItem.selectionForeground", PRIMARY_FG_COLOR);
        UIManager.put("MenuItem.foreground", FG_COLOR);
        UIManager.put("MenuItem.background", POPOVER_COLOR);

        // Ships Menu
        JMenu shipMenu = new JMenu("Ships");
        shipMenu.setForeground(FG_COLOR);
        shipMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        menubar.add(shipMenu);

        addMenuItem(shipMenu, "View Ship", new ViewShipHandler());
        addMenuItem(shipMenu, "View Fleet", new ListFleetHandler());
        addMenuItem(shipMenu, "View Squadron", new ListSquadronHandler());
        shipMenu.addSeparator();
        addMenuItem(shipMenu, "Commission Ship", new CommissionHandler());
        addMenuItem(shipMenu, "Decommission Ship", new DecommissionHandler());
        addMenuItem(shipMenu, "Restore Ship", new RestoreHandler());

        // Encounters Menu
        JMenu encounterMenu = new JMenu("Encounters");
        encounterMenu.setForeground(FG_COLOR);
        encounterMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        menubar.add(encounterMenu);

        addMenuItem(encounterMenu, "List Encounters", new ListEncountersHandler());
        addMenuItem(encounterMenu, "Engage in Battle", new FightHandler());

        // Game Menu
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setForeground(FG_COLOR);
        gameMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        menubar.add(gameMenu);

        addMenuItem(gameMenu, "Save", new SaveGameHandler());
        addMenuItem(gameMenu, "Load", new LoadGameHandler());
        gameMenu.addSeparator();
        addMenuItem(gameMenu, "Quit", new QuitHandler());
    }

    // Helper method for creating menu items with unified style
    private void addMenuItem(JMenu menu, String text, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        item.setForeground(FG_COLOR);
        item.addActionListener(listener);
        menu.add(item);
    }

    /**
     * Method for displaying a stylized message
     */
    private void showStyledMessage(String message, String title) {
        JPanel panel = new JPanel(new BorderLayout(20, 20)); // Increased margin between elements
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25)); // Increased margins

        JTextArea area = new JTextArea(message);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Increased font size
        area.setForeground(FG_COLOR);
        area.setBackground(CARD_COLOR);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Increased margins

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(null);
        scrollPane.setBackground(CARD_COLOR);
        scrollPane.setPreferredSize(new Dimension(350, 150)); // Set preferred size

        panel.add(scrollPane, BorderLayout.CENTER);

        // Create custom OK button with rounded corners
        JButton okButton = new JButton("OK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded button background
                g2.setColor(getModel().isPressed() ? new Color(29, 78, 216) : // Dark blue when pressed
                        (getModel().isRollover() ? new Color(37, 99, 235) : // Slightly lighter on hover
                                PRIMARY_COLOR)); // Regular blue in normal state

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Increased font size of button

                FontMetrics metrics = g2.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 64, 175)); // Dark blue border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };

        // Button properties setup
        okButton.setOpaque(false);
        okButton.setContentAreaFilled(false);
        okButton.setBorderPainted(true);
        okButton.setFocusPainted(false);
        okButton.setPreferredSize(new Dimension(140, 50)); // Increased button size

        // Add button to panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Create custom dialog
        JDialog dialog = new JDialog(myFrame, title, true);
        dialog.setContentPane(panel);

        // Add logic for button
        okButton.addActionListener(e -> dialog.dispose());

        // Handle window close
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
            }
        });

        // Set dialog size and position
        dialog.pack();
        dialog.setMinimumSize(new Dimension(400, 300)); // Set minimum dialog size
        dialog.setLocationRelativeTo(myFrame);
        dialog.setVisible(true);
    }

    /**
     * Method for displaying a stylized message with an additional button to delete the save
     */
    private void showSaveMessage(String message, String title) {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JTextArea area = new JTextArea(message);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        area.setForeground(FG_COLOR);
        area.setBackground(CARD_COLOR);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(null);
        scrollPane.setBackground(CARD_COLOR);
        scrollPane.setPreferredSize(new Dimension(350, 150));

        panel.add(scrollPane, BorderLayout.CENTER);

        // Create custom OK button with rounded corners
        JButton okButton = new JButton("OK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded button background
                g2.setColor(getModel().isPressed() ? new Color(29, 78, 216) : // Dark blue when pressed
                        (getModel().isRollover() ? new Color(37, 99, 235) : // Slightly lighter on hover
                                PRIMARY_COLOR)); // Regular blue in normal state

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));

                FontMetrics metrics = g2.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 64, 175)); // Dark blue border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };

        // Create "Delete Save" button with red color
        JButton deleteButton = new JButton("Delete Save") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded button background in red
                g2.setColor(getModel().isPressed() ? new Color(185, 28, 28) : // Dark red when pressed
                        (getModel().isRollover() ? new Color(220, 38, 38) : // Slightly lighter on hover
                                new Color(239, 68, 68))); // Regular red in normal state

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));

                FontMetrics metrics = g2.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(153, 27, 27)); // Dark red border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };

        // Button properties setup
        okButton.setOpaque(false);
        okButton.setContentAreaFilled(false);
        okButton.setBorderPainted(true);
        okButton.setFocusPainted(false);
        okButton.setPreferredSize(new Dimension(140, 50));

        deleteButton.setOpaque(false);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorderPainted(true);
        deleteButton.setFocusPainted(false);
        deleteButton.setPreferredSize(new Dimension(200, 50));

        // Add buttons to panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(okButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Create custom dialog
        JDialog dialog = new JDialog(myFrame, title, true);
        dialog.setContentPane(panel);

        // Add logic for buttons
        okButton.addActionListener(e -> dialog.dispose());

        deleteButton.addActionListener(e -> {
            if (deleteSaveFile()) {
                dialog.dispose();
                showStyledMessage("Save file deleted successfully", "Save Deleted");
            } else {
                dialog.dispose();
                showStyledMessage("Error deleting save file", "Error");
            }
        });

        // Handle window close
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
            }
        });

        // Set dialog size and position
        dialog.pack();
        dialog.setMinimumSize(new Dimension(500, 300));
        dialog.setLocationRelativeTo(myFrame);
        dialog.setVisible(true);
    }

    /**
     * Method for deleting the save file
     * 
     * @return true if deletion was successful, false otherwise
     */
    private boolean deleteSaveFile() {
        try {
            java.io.File saveFile = new java.io.File("baths_save.dat");
            if (saveFile.exists()) {
                return saveFile.delete();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method for displaying a stylized message with an additional button to delete the save
     */
    private void showBattleResultMessage(String message, String title) {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JTextArea area = new JTextArea(message);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        area.setForeground(FG_COLOR);
        area.setBackground(CARD_COLOR);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(null);
        scrollPane.setBackground(CARD_COLOR);
        scrollPane.setPreferredSize(new Dimension(350, 150));

        panel.add(scrollPane, BorderLayout.CENTER);

        // Create custom OK button with rounded corners
        JButton okButton = new JButton("OK") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw rounded button background
                g2.setColor(getModel().isPressed() ? new Color(29, 78, 216) : // Dark blue when pressed
                        (getModel().isRollover() ? new Color(37, 99, 235) : // Slightly lighter on hover
                                PRIMARY_COLOR)); // Regular blue in normal state

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw text
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));

                FontMetrics metrics = g2.getFontMetrics();
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 64, 175)); // Dark blue border
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };

        // Button properties setup
        okButton.setOpaque(false);
        okButton.setContentAreaFilled(false);
        okButton.setBorderPainted(true);
        okButton.setFocusPainted(false);
        okButton.setPreferredSize(new Dimension(140, 50));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBackground(CARD_COLOR);
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Create custom dialog
        JDialog dialog = new JDialog(myFrame, title, true);
        dialog.setContentPane(panel);

        // Check defeat status before showing the dialog
        final boolean isDefeated = ((BATHS) gp).isDefeated();

        // Add action for OK button
        okButton.addActionListener(e -> {
            dialog.dispose();
            // If the player is defeated, show the defeat dialog immediately after closing the window
            if (isDefeated) {
                showStyledMessage("You're out of money and have no ships to decommission!\n\nYou have lost!", "Defeat");
            }
        });

        // Handle window close
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.dispose();
                // If the player is defeated, show the defeat dialog immediately after closing the window
                if (isDefeated) {
                    showStyledMessage("You're out of money and have no ships to decommission!\n\nYou have lost!", "Defeat");
                }
            }
        });

        // Set dialog size and position
        dialog.pack();
        dialog.setMinimumSize(new Dimension(350, 250));
        dialog.setLocationRelativeTo(myFrame);
        dialog.setVisible(true);
    }

    private class FightHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText("Available battles:\n" + gp.getAllEncounters());
            
            // Create a custom battle dialog with properly styled buttons
            JTextField textField = new JTextField(15); // Set column count to limit width
            textField.setMaximumSize(new Dimension(200, textField.getPreferredSize().height));
            textField.setPreferredSize(new Dimension(200, textField.getPreferredSize().height));
            
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBackground(CARD_COLOR);
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel label = new JLabel("Enter battle number:");
            label.setForeground(FG_COLOR);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textField.setBackground(INPUT_COLOR);
            textField.setForeground(FG_COLOR);
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
            // Add focus listener for blue highlight when focused
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                            BorderFactory.createEmptyBorder(4, 4, 4, 4)));
                }
                
                @Override
                public void focusLost(FocusEvent e) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER_COLOR),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                }
            });
            
            JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inputWrapper.setBackground(CARD_COLOR);
            inputWrapper.add(textField);
            
            JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
            inputPanel.setBackground(CARD_COLOR);
            inputPanel.add(label, BorderLayout.NORTH);
            inputPanel.add(inputWrapper, BorderLayout.CENTER);
            
            panel.add(inputPanel, BorderLayout.CENTER);
            
            // Create styled buttons
            JButton okButton = createStyledButton("OK");
            JButton cancelButton = createStyledButton("Cancel");
            
            // Make buttons smaller for dialog
            okButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setPreferredSize(new Dimension(100, 40));
            
            // Add buttons to panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(CARD_COLOR);
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);
            
            // Create custom dialog
            JDialog dialog = new JDialog(myFrame, "Battle", true);
            dialog.setContentPane(panel);
            
            // Set the result actions
            final String[] result = {null};
            
            okButton.addActionListener(event -> {
                result[0] = textField.getText().trim();
                dialog.dispose();
            });
            
            cancelButton.addActionListener(event -> {
                dialog.dispose();
            });
            
            // Handle Enter key in text field
            textField.addActionListener(event -> {
                result[0] = textField.getText().trim();
                dialog.dispose();
            });
            
            // Set dialog size and position
            dialog.pack();
            dialog.setSize(350, 200);
            dialog.setLocationRelativeTo(myFrame);
            dialog.setVisible(true);
            
            // Process the result after dialog closes
            String encNumberStr = result[0];
            if (encNumberStr != null && !encNumberStr.isEmpty()) {
                try {
                    int encNumber = Integer.parseInt(encNumberStr);
                    String resultText = gp.fightEncounter(encNumber);
                    showBattleResultMessage(resultText, "Battle Result");
                    listing.setText(resultText + "\n\nCurrent status:\n" + gp.toString());
                    if (((BATHS) gp).isDefeated()) {
                        showStyledMessage("You're out of money and have no ships to decommission!\n\nYou have lost!", "Defeat");
                    }
                } catch (NumberFormatException ex) {
                    showStyledMessage("Please enter a valid battle number", "Error");
                }
            }
        }
    }

    private class ListFleetHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText(gp.getReserveFleet());
        }
    }

    private class ListSquadronHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText(gp.getSquadron());
        }
    }

    private class ViewShipHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display all ships in all states
            StringBuilder allShipsInfo = new StringBuilder("Available Ships:\n\n");
            allShipsInfo.append("SQUADRON SHIPS:\n").append(gp.getSquadron()).append("\n");
            allShipsInfo.append("RESERVE FLEET SHIPS:\n").append(gp.getReserveFleet()).append("\n");
            allShipsInfo.append("SUNK SHIPS:\n").append(gp.getSunkShips());
            
            // Display the information in the text area
            listing.setText(allShipsInfo.toString());
            
            // Make sure the text is scrolled to the top to see all ships
            listing.setCaretPosition(0);
            
            // Slight delay to ensure the text area is updated before showing dialog
            SwingUtilities.invokeLater(() -> {
                // Create a custom dialog positioned in the center
                JDialog dialog = new JDialog(myFrame, "View Ship", true);
                
                JPanel panel = new JPanel(new BorderLayout(15, 15));
                panel.setBackground(CARD_COLOR);
                panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                
                JLabel label = new JLabel("Enter ship name to view details:");
                label.setForeground(FG_COLOR);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                
                JTextField field = new JTextField(15); // Set column count to limit width
                field.setMaximumSize(new Dimension(200, field.getPreferredSize().height));
                field.setPreferredSize(new Dimension(200, field.getPreferredSize().height));
                
                field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                field.setBackground(INPUT_COLOR);
                field.setForeground(FG_COLOR);
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                
                // Add focus listener for blue highlight when focused
                field.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        field.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                                BorderFactory.createEmptyBorder(4, 4, 4, 4)));
                    }
                    
                    @Override
                    public void focusLost(FocusEvent e) {
                        field.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(BORDER_COLOR),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                    }
                });
                
                JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
                inputWrapper.setBackground(CARD_COLOR);
                inputWrapper.add(field);
                
                JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
                inputPanel.setBackground(CARD_COLOR);
                inputPanel.add(label, BorderLayout.NORTH);
                inputPanel.add(inputWrapper, BorderLayout.CENTER);
                
                panel.add(inputPanel, BorderLayout.CENTER);
                
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.setBackground(CARD_COLOR);
                
                JButton okButton = createStyledButton("OK");
                JButton cancelButton = createStyledButton("Cancel");
                
                buttonPanel.add(okButton);
                buttonPanel.add(cancelButton);
                panel.add(buttonPanel, BorderLayout.SOUTH);
                
                dialog.setContentPane(panel);
                dialog.pack();
                dialog.setSize(350, 200);
                dialog.setLocationRelativeTo(myFrame); // Center dialog relative to the main frame
                
                // Set the result actions
                final String[] result = {null};
                
                okButton.addActionListener(event -> {
                    result[0] = field.getText().trim();
                    dialog.dispose();
                });
                
                cancelButton.addActionListener(event -> {
                    dialog.dispose();
                });
                
                // Handle Enter key in text field
                field.addActionListener(event -> {
                    result[0] = field.getText().trim();
                    dialog.dispose();
                });
                
                dialog.setVisible(true);
                
                // Process the result after dialog closes
                String shipName = result[0];
                if (shipName != null && !shipName.isEmpty()) {
                    String shipDetails = gp.getShipDetails(shipName);
                    
                    // Show ship details in a dialog and also update the text area
                    showStyledMessage(shipDetails, "Ship Details: " + shipName);
                    listing.setText("Details for ship " + shipName + ":\n\n" + shipDetails);
                }
            });
        }
    }

    private class CommissionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText(gp.getReserveFleet());
            
            // Create a custom ship commission dialog with properly styled buttons
            JTextField textField = new JTextField(15); // Set column count to limit width
            textField.setMaximumSize(new Dimension(200, textField.getPreferredSize().height));
            textField.setPreferredSize(new Dimension(200, textField.getPreferredSize().height));
            
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBackground(CARD_COLOR);
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel label = new JLabel("Enter ship name to commission:");
            label.setForeground(FG_COLOR);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textField.setBackground(INPUT_COLOR);
            textField.setForeground(FG_COLOR);
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
            // Add focus listener for blue highlight when focused
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                            BorderFactory.createEmptyBorder(4, 4, 4, 4)));
                }
                
                @Override
                public void focusLost(FocusEvent e) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER_COLOR),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                }
            });
            
            JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inputWrapper.setBackground(CARD_COLOR);
            inputWrapper.add(textField);
            
            JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
            inputPanel.setBackground(CARD_COLOR);
            inputPanel.add(label, BorderLayout.NORTH);
            inputPanel.add(inputWrapper, BorderLayout.CENTER);
            
            panel.add(inputPanel, BorderLayout.CENTER);
            
            // Create styled buttons
            JButton okButton = createStyledButton("OK");
            JButton cancelButton = createStyledButton("Cancel");
            
            // Make buttons smaller for dialog
            okButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setPreferredSize(new Dimension(100, 40));
            
            // Add buttons to panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            buttonPanel.setBackground(CARD_COLOR);
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);
            
            // Create custom dialog
            JDialog dialog = new JDialog(myFrame, "Commission Ship", true);
            dialog.setContentPane(panel);
            
            // Set the result actions
            final String[] result = {null};
            
            okButton.addActionListener(event -> {
                result[0] = textField.getText().trim();
                dialog.dispose();
            });
            
            cancelButton.addActionListener(event -> {
                dialog.dispose();
            });
            
            // Handle Enter key in text field
            textField.addActionListener(event -> {
                result[0] = textField.getText().trim();
                dialog.dispose();
            });
            
            // Set dialog size and position
            dialog.pack();
            dialog.setSize(350, 200);
            dialog.setLocationRelativeTo(myFrame);
            dialog.setVisible(true);
            
            // Process the result after dialog closes
            String shipName = result[0];
            if (shipName != null && !shipName.isEmpty()) {
                String resultText = gp.commissionShip(shipName);
                showStyledMessage(resultText, "Commission Result");
                listing.setText(resultText + "\n\nAdmiral's squadron:\n" + gp.getSquadron());
            }
        }
    }

    private class DecommissionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText(gp.getSquadron());
            
            // Create a custom ship decommission dialog with properly styled buttons
            JTextField textField = new JTextField(15); // Set column count to limit width
            textField.setMaximumSize(new Dimension(200, textField.getPreferredSize().height));
            textField.setPreferredSize(new Dimension(200, textField.getPreferredSize().height));
            
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBackground(CARD_COLOR);
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel label = new JLabel("Enter ship name to decommission:");
            label.setForeground(FG_COLOR);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textField.setBackground(INPUT_COLOR);
            textField.setForeground(FG_COLOR);
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
            // Add focus listener for blue highlight when focused
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                            BorderFactory.createEmptyBorder(4, 4, 4, 4)));
                }
                
                @Override
                public void focusLost(FocusEvent e) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER_COLOR),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                }
            });
            
            JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inputWrapper.setBackground(CARD_COLOR);
            inputWrapper.add(textField);
            
            JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
            inputPanel.setBackground(CARD_COLOR);
            inputPanel.add(label, BorderLayout.NORTH);
            inputPanel.add(inputWrapper, BorderLayout.CENTER);
            
            panel.add(inputPanel, BorderLayout.CENTER);
            
            // Create styled buttons
            JButton okButton = createStyledButton("OK");
            JButton cancelButton = createStyledButton("Cancel");
            
            // Make buttons smaller for dialog
            okButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setPreferredSize(new Dimension(100, 40));
            
            // Add buttons to panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            buttonPanel.setBackground(CARD_COLOR);
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);
            
            // Create custom dialog
            JDialog dialog = new JDialog(myFrame, "Decommission Ship", true);
            dialog.setContentPane(panel);
            
            // Set the result actions
            final String[] result = {null};
            
            okButton.addActionListener(event -> {
                result[0] = textField.getText().trim();
                dialog.dispose();
            });
            
            cancelButton.addActionListener(event -> {
                dialog.dispose();
            });
            
            // Handle Enter key in text field
            textField.addActionListener(event -> {
                result[0] = textField.getText().trim();
                dialog.dispose();
            });
            
            // Set dialog size and position
            dialog.pack();
            dialog.setSize(350, 200);
            dialog.setLocationRelativeTo(myFrame);
            dialog.setVisible(true);
            
            // Process the result after dialog closes
            String shipName = result[0];
            if (shipName != null && !shipName.isEmpty()) {
                boolean success = gp.decommissionShip(shipName);
                if (success) {
                    String resultText = "Ship " + shipName + " was decommissioned from the squadron and returned to reserve";
                    showStyledMessage(resultText, "Decommission Result");
                    listing.setText(resultText + "\n\nReserve fleet:\n" + gp.getReserveFleet());
                } else {
                    showStyledMessage("Failed to decommission ship " + shipName, "Error");
                }
            }
        }
    }

    private class RestoreHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText(gp.getSquadron());
            
            // Create a custom ship restore dialog with properly styled buttons
            JTextField textField = new JTextField(15); // Set column count to limit width
            textField.setMaximumSize(new Dimension(200, textField.getPreferredSize().height));
            textField.setPreferredSize(new Dimension(200, textField.getPreferredSize().height));
            
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBackground(CARD_COLOR);
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JLabel label = new JLabel("Enter ship name to restore:");
            label.setForeground(FG_COLOR);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textField.setBackground(INPUT_COLOR);
            textField.setForeground(FG_COLOR);
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDER_COLOR),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            
            // Add focus listener for blue highlight when focused
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                            BorderFactory.createEmptyBorder(4, 4, 4, 4)));
                }
                
                @Override
                public void focusLost(FocusEvent e) {
                    textField.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(BORDER_COLOR),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
                }
            });
            
            JPanel inputWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inputWrapper.setBackground(CARD_COLOR);
            inputWrapper.add(textField);
            
            JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
            inputPanel.setBackground(CARD_COLOR);
            inputPanel.add(label, BorderLayout.NORTH);
            inputPanel.add(inputWrapper, BorderLayout.CENTER);
            
            panel.add(inputPanel, BorderLayout.CENTER);
            
            // Create styled buttons
            JButton okButton = createStyledButton("OK");
            JButton cancelButton = createStyledButton("Cancel");
            
            // Make buttons smaller for dialog
            okButton.setPreferredSize(new Dimension(100, 40));
            cancelButton.setPreferredSize(new Dimension(100, 40));
            
            // Add buttons to panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            buttonPanel.setBackground(CARD_COLOR);
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);
            
            // Create custom dialog
            JDialog dialog = new JDialog(myFrame, "Restore Ship", true);
            dialog.setContentPane(panel);
            
            // Set the result actions
            final String[] result = {null};
            
            okButton.addActionListener(event -> {
                result[0] = textField.getText().trim();
                dialog.dispose();
            });
            
            cancelButton.addActionListener(event -> {
                dialog.dispose();
            });
            
            // Handle Enter key in text field
            textField.addActionListener(event -> {
                result[0] = textField.getText().trim();
                dialog.dispose();
            });
            
            // Set dialog size and position
            dialog.pack();
            dialog.setSize(350, 200);
            dialog.setLocationRelativeTo(myFrame);
            dialog.setVisible(true);
            
            // Process the result after dialog closes
            String shipName = result[0];
            if (shipName != null && !shipName.isEmpty()) {
                try {
                    gp.restoreShip(shipName);
                    showStyledMessage("Ship " + shipName + " restored", "Restore Result");
                    listing.setText("Admiral's squadron after restoration:\n" + gp.getSquadron());
                } catch (Exception ex) {
                    showStyledMessage("Error restoring ship: " + ex.getMessage(), "Error");
                }
            }
        }
    }

    private class ListEncountersHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText(gp.getAllEncounters());
        }
    }

    private class ViewStateHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText(gp.toString());
        }
    }

    private class ClearHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            listing.setText("");
        }
    }

    private class SaveGameHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                ((SeaBattles) gp).saveGame("baths_save.dat");
                showSaveMessage("Game saved to baths_save.dat", "Save");
            } catch (Exception ex) {
                showStyledMessage("Error saving: " + ex.getMessage(), "Error");
            }
        }
    }

    private class LoadGameHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                SeaBattles loadedGame = ((SeaBattles) gp).loadGame("baths_save.dat");
                if (loadedGame != null) {
                    gp = loadedGame;
                    showStyledMessage("Game loaded successfully", "Load");
                    listing.setText(gp.toString());
                } else {
                    showStyledMessage("Error loading: save file not found or corrupted", "Error");
                }
            } catch (Exception ex) {
                showStyledMessage("Error loading: " + ex.getMessage(), "Error");
            }
        }
    }

    private class QuitHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean confirmed = showStyledConfirmDialog("Are you sure you want to quit?", "Quit Confirmation");

            if (confirmed) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Starting BATHS GUI application...");
            
            // Check if we're in a headless environment (no GUI capabilities)
            if (GraphicsEnvironment.isHeadless()) {
                System.out.println("ERROR: This application requires a graphical environment, but the system is running in headless mode.");
                System.out.println("Please try running the text-based interface with: java -cp build wars.GameUI");
                return;
            }
            
            System.out.println("Initializing Swing components...");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                    try {
                        System.out.println("Creating GameGUI instance...");
                new GameGUI();
                        System.out.println("GameGUI initialized successfully.");
                    } catch (Exception e) {
                        System.out.println("ERROR: Failed to initialize the GUI.");
                        e.printStackTrace();
                    }
            }
        });
        } catch (Exception e) {
            System.out.println("ERROR: Unexpected exception in main method.");
            e.printStackTrace();
        }
    }
}
