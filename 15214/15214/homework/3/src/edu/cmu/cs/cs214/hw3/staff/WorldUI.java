package edu.cmu.cs.cs214.hw3.staff;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import edu.cmu.cs.cs214.hw3.World;

/**
 * The setup of the {@link World}'s UI, including the main
 * panel, the start/stop button, and the step button.
 *
 * Do not modify this file.
 */
public class WorldUI {

    private static final String TITLE = "World 15-214";
    private static final String START_BUTTON = "Start";
    private static final String STOP_BUTTON = "Stop";
    private static final String STEP_BUTTON = "Step";

    private final World world;
    private final JFrame frame;

    private final WorldPanel worldPanel;
    private final JButton stepButton;
    private final JButton runButton;

    /**
     * Creates a GUI with the specified World.
     *
     * @param w the world drawn by the GUI
     */
    public WorldUI(World w) {
        world = w;

        worldPanel = new WorldPanel(world);
        runButton = new JButton(START_BUTTON);
        stepButton = new JButton(STEP_BUTTON);

        // Create the bottom panel and add buttons to it.
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(runButton, BorderLayout.WEST);
        bottom.add(stepButton, BorderLayout.EAST);

        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worldPanel.step();
            }
        });

        runButton.addActionListener(new ActionListener() {
            boolean toggle = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggle) {
                    worldPanel.start();
                    stepButton.setEnabled(false);
                    runButton.setText(STOP_BUTTON);
                } else {
                    worldPanel.stop();
                    stepButton.setEnabled(true);
                    runButton.setText(START_BUTTON);
                }
                toggle = !toggle;
            }
        });

        // Add the elements.
        frame = new JFrame(TITLE);
        Container pane = frame.getContentPane();
        pane.add(worldPanel, BorderLayout.CENTER);
        pane.add(bottom, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
    }

    /**
     * Sets the window to be visible if it wasn't before.
     */
    public void show() {
        frame.setVisible(true);
    }

}
