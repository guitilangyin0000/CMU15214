package edu.cmu.cs.cs214.rec07.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import edu.cmu.cs.cs214.rec07.core.QuoteGenerator;

public class StatusPanel extends JPanel {
    private static final long serialVersionUID = 4052155298523697692L;

    private static final Border PADDING = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private static final Color GRAY = new Color(220, 220, 220);

    private final JLabel greeting = new JLabel("Enter a name above.");
    private final QuoteGenerator model;

    public StatusPanel(QuoteGenerator gen) {
        model = gen;

        setBorder(PADDING); // Adds padding
        setBackground(GRAY); // Background set to gray

        add(greeting);
    }

    public void refresh() {
        greeting.setText("Hello, " + model.getName() + "!");
    }

}
