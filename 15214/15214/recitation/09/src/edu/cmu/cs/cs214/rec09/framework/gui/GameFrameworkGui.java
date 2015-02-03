package edu.cmu.cs.cs214.rec09.framework.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import edu.cmu.cs.cs214.rec09.framework.core.GameChangeListener;
import edu.cmu.cs.cs214.rec09.framework.core.GameFrameworkImpl;
import edu.cmu.cs.cs214.rec09.framework.core.GamePlugin;
import edu.cmu.cs.cs214.rec09.framework.core.Player;

/**
 * The framework GUI implementation. This class is responsible for displaying
 * the framework GUI to the screen, and for forwarding events to
 * {@link GameFrameworkImpl} when GUI-related events are detected (such as button
 * clicks, menu-item clicks, etc.).
 */
public class GameFrameworkGui implements GameChangeListener {

    // Default JFrame title.
    private static final String DEFAULT_TITLE = "Game Framework";

    // Menu titles.
    private static final String MENU_TITLE = "File";
    private static final String MENU_NEW_GAME = "New Game";
    private static final String MENU_ADD_PLAYER = "Add Player...";
    private static final String MENU_REMOVE_PLAYER = "Remove Player...";
    private static final String MENU_EXIT = "Exit";

    // Dialog titles and messages.
    private static final String ADD_PLAYER_TITLE = "Add New Player";
    private static final String ADD_PLAYER_MSG = "Enter player name:";
    private static final String REMOVE_PLAYER_TITLE = "Remove Player";
    private static final String REMOVE_PLAYER_MSG = "Select a player to remove.";
    private static final String ERROR_ADD_PLAYER_TITLE = "Error!";
    private static final String ERROR_ADD_PLAYER_MSG = "Cannot add player with game in progress.";
    private static final String ERROR_REMOVE_PLAYER_TITLE = "Error!";
    private static final String ERROR_REMOVE_PLAYER_MSG = "Cannot remove player with game in progress.";
    private static final String ERROR_NO_PLAYERS_TITLE = "Error!";
    private static final String ERROR_NO_PLAYERS_MSG = "No players are added to the game!";
    private static final String GAME_OVER_TITLE = "Game Over!";

    // Default footer text.
    private static final String DEFAULT_FOOTER_TEXT = "Please select a new game.";

    // The parent JFrame window.
    private final JFrame frame;

    // The outer panel contains the player/footer labels and inner panel.
    private final JPanel outerPanel;

    // The inner panel contains the a grid of JButtons, each representing a
    // square in the grid.
    private final JPanel innerPanel;

    // The label displaying the current player at the top of the frame.
    private final JLabel currentPlayerLabel;

    // The label displayed at the bottom of the frame.
    private final JLabel footerLabel;

    // The buttons that make up the game grid.
    private JButton[][] squares;

    // Menu-related stuff.
    private final JMenu newGameMenu;
    private final JMenuItem addPlayerMenuItem;
    private final JMenuItem removePlayerMenuItem;
    private final ButtonGroup gameGroup = new ButtonGroup();

    private GameFrameworkImpl core;
    private GamePlugin currentPlugin;

    public GameFrameworkGui(GameFrameworkImpl fc) {
        // Set the framework core instance that the GUI will talk to in response
        // to GUI-related events.
        core = fc;

        frame = new JFrame(DEFAULT_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));

        // Add the frame's panels to the view.
        outerPanel = new JPanel(new BorderLayout());

        currentPlayerLabel = new JLabel();
        currentPlayerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outerPanel.add(currentPlayerLabel, BorderLayout.NORTH);

        footerLabel = new JLabel(DEFAULT_FOOTER_TEXT);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outerPanel.add(footerLabel, BorderLayout.SOUTH);

        innerPanel = new JPanel();
        innerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        outerPanel.add(innerPanel, BorderLayout.CENTER);

        frame.add(outerPanel);

        // Set-up the menu bar.
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(MENU_TITLE);
        fileMenu.setMnemonic(KeyEvent.VK_F);

        // Add an 'Add Player' menu item.
        addPlayerMenuItem = new JMenuItem(MENU_ADD_PLAYER);
        addPlayerMenuItem.setMnemonic(KeyEvent.VK_N);
        addPlayerMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (currentPlugin != null) {
                    // Can't add a player while the game is in progress.
                    showErrorDialog(frame, ERROR_ADD_PLAYER_TITLE, ERROR_ADD_PLAYER_MSG);
                    return;
                }

                // Show an input dialog. This method blocks until the dialog is
                // dismissed. The returned object is the text entered by the
                // user (or null if the dialog was cancelled).
                String name = (String) JOptionPane.showInputDialog(frame, ADD_PLAYER_MSG,
                        ADD_PLAYER_TITLE, JOptionPane.PLAIN_MESSAGE, null, null, "");

                if (name != null) {
                    core.addPlayer(name);
                }
            }
        });
        fileMenu.add(addPlayerMenuItem);

        // Add a 'Remove Player' menu item.
        removePlayerMenuItem = new JMenuItem(MENU_REMOVE_PLAYER);
        removePlayerMenuItem.setMnemonic(KeyEvent.VK_R);
        removePlayerMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (currentPlugin != null) {
                    // Can't remove a player while the game is in progress.
                    showErrorDialog(frame, ERROR_REMOVE_PLAYER_TITLE, ERROR_REMOVE_PLAYER_MSG);
                    return;
                }

                List<Player> players = core.getPlayers();
                if (players.isEmpty()) {
                    // No players to remove.
                    showErrorDialog(frame, ERROR_NO_PLAYERS_TITLE, ERROR_NO_PLAYERS_MSG);
                    return;
                }

                String[] selectionValues = new String[players.size()];
                for (int i = 0; i < players.size(); i++) {
                    selectionValues[i] = players.get(i).getName();
                }

                // Show an input dialog. This method blocks until the dialog is
                // dismissed. The returned object is the name of the selected
                // player (or null if the dialog was cancelled).
                String name = (String) JOptionPane.showInputDialog(frame, REMOVE_PLAYER_MSG,
                        REMOVE_PLAYER_TITLE, JOptionPane.QUESTION_MESSAGE, null, selectionValues,
                        selectionValues[0]);

                if (name != null) {
                    core.removePlayer(name);
                }
            }
        });
        fileMenu.add(removePlayerMenuItem);

        // Add a 'New Game' menu item.
        newGameMenu = new JMenu(MENU_NEW_GAME);
        newGameMenu.setMnemonic(KeyEvent.VK_N);
        fileMenu.add(newGameMenu);

        // Add a separator between 'New Game' and 'Exit' menu items.
        fileMenu.addSeparator();

        // Add an 'Exit' menu item.
        JMenuItem exitMenuItem = new JMenuItem(MENU_EXIT);
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);


        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void onPluginRegistered(final GamePlugin plugin) {
        JRadioButtonMenuItem gameMenuItem = new JRadioButtonMenuItem(plugin.getGameName());
        gameMenuItem.setSelected(false);
        gameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (core.getPlayers().isEmpty()) {
                    // Can't start a game with no players.
                    showErrorDialog(frame, ERROR_NO_PLAYERS_TITLE, ERROR_NO_PLAYERS_MSG);
                    gameGroup.clearSelection();
                } else {
                    core.startNewGame(plugin);
                }
            }
        });
        gameGroup.add(gameMenuItem);
        newGameMenu.add(gameMenuItem);
    }

    @Override
    public void onNewGame(GamePlugin plugin) {
        final int width = plugin.getGridWidth();
        final int height = plugin.getGridHeight();

        if (currentPlugin != plugin) {
            // If currentPlugin != plugin, then we are switching to a new
            // plug-in's game. This requires a bit more work to setup since
            // different games can have different grid widths and grid heights.
            currentPlugin = plugin;
            squares = new JButton[height][width];
            innerPanel.removeAll();
            innerPanel.setLayout(new GridLayout(height, width));

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    JButton button = new JButton();
                    button.addActionListener(new SquareListener(x, y, core));
                    innerPanel.add(button);
                    squares[y][x] = button;
                }
            }
        }

        // Disable the 'Add Player' and 'Remove Player' menu items while a
        // game is in progress.
        addPlayerMenuItem.setEnabled(false);
        removePlayerMenuItem.setEnabled(false);

        setCurrentPlayerLabel(core.getCurrentPlayer());
        footerLabel.setText(" ");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Reset the state of the previous game.
                squares[y][x].setText("");
            }
        }

        frame.setTitle(currentPlugin.getGameName());
        frame.pack();
    }

    @Override
    public void onCurrentPlayerChanged(Player player) {
        setCurrentPlayerLabel(player);
    }

    private void setCurrentPlayerLabel(Player player) {
        currentPlayerLabel.setText("Current player: " + player.getName());
    }

    @Override
    public void onFooterTextChanged(String text) {
        footerLabel.setText(text);
    }

    @Override
    public void onSquareChanged(int x, int y, String text) {
        squares[y][x].setText(text);
    }

    @Override
    public void onGameEnded(String gameOverMessage) {
        showInfoDialog(frame, GAME_OVER_TITLE, gameOverMessage);
    }

    private static void showInfoDialog(Component c, String title, String msg) {
        JOptionPane.showMessageDialog(c, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showErrorDialog(Component c, String title, String msg) {
        JOptionPane.showMessageDialog(c, msg, title, JOptionPane.ERROR_MESSAGE);
    }
}
