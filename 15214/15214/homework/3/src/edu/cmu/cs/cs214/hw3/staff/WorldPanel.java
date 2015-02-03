package edu.cmu.cs.cs214.hw3.staff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import edu.cmu.cs.cs214.hw3.Location;
import edu.cmu.cs.cs214.hw3.World;
import edu.cmu.cs.cs214.hw3.items.Item;

/**
 * The main panel of the {@link World} responsible for drawing its
 * items and managing its steps.
 *
 * Do not modify this file.
 */
public class WorldPanel extends JPanel {
    private static final long serialVersionUID = 4927764458527709056L;

    private static final int IMAGE_DEFAULT_SIZE = 16; // Pixels
    private static final int RUN_SPEED = 100; // Milliseconds

    private final World world;
    private final Timer timer;

    /**
     * Creates a panel drawn with the contents of the specified World.
     *
     * @param w the World drawn by this panel
     */
    public WorldPanel(World w) {
        world = w;

        // Calculate the preferred width and height of the panel.
        int panelWidth = w.getWidth() * IMAGE_DEFAULT_SIZE;
        int panelHeight = w.getHeight() * IMAGE_DEFAULT_SIZE;
        Dimension preferredSize = new Dimension(panelWidth, panelHeight);
        setPreferredSize(preferredSize);
        setBackground(Color.WHITE);

        timer = new Timer(RUN_SPEED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                step();
            }
        });
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        int imgWidth = this.getWidth() / world.getWidth();
        int imgHeight = this.getHeight() / world.getHeight();

        // Iterates through all of the actors and redraws them.
        for (Item item : world.getItems()) {
            Location loc = item.getLocation();
            int x = loc.getX() * imgWidth;
            int y = loc.getY() * imgHeight;

            // Determine which icon to draw.
            graphics.drawImage(item.getImage().getImage(), x, y, imgWidth, imgHeight, this);
        }
    }

    /**
     * Runs the simulation. Continuously advances the world step-by-step until
     * {@link WorldPanel#stop()} is called.
     */
    public void start() {
        timer.start();
    }

    /**
     * Stops the simulation. If the simulation is running (because
     * {@link WorldPanel#start()} was called) then this will stop the continuous
     * stepping.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Advances the world one step and repaints the UI.
     *
     * @see World#step()
     */
    public void step() {
        world.step();
        repaint();
    }

}
