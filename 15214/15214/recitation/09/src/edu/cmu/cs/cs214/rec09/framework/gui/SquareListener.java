package edu.cmu.cs.cs214.rec09.framework.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.cmu.cs.cs214.rec09.framework.core.GameFrameworkImpl;

/**
 * {@link ActionListener} used by the {@link GameFrameworkGui} to detect button
 * clicks. Click events are immediately forwarded to the {@link GameFrameworkImpl}.
 */
public class SquareListener implements ActionListener {

    private final int x;
    private final int y;
    private final GameFrameworkImpl core;

    public SquareListener(int x, int y, GameFrameworkImpl core) {
        this.x = x;
        this.y = y;
        this.core = core;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        core.playMove(x, y);
    }

}
