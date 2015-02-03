package edu.cmu.cs.cs214.rec07.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cmu.cs.cs214.rec07.core.QuoteGenerator;

public class NamePanel extends JPanel {
    private static final long serialVersionUID = -7258071600712637251L;

    private static final int NUM_COLUMNS = 15;

    private final QuoteGenerator model;
    private final StatusPanel statusPanel;

    public NamePanel(QuoteGenerator gen, StatusPanel sp) {
        model = gen;
        statusPanel = sp;

        /*
         * TODO: Add the following to this JPanel:
         *   - text label saying "Name :"
         *   - text field so the user can enter his/her name
         *   - submit button
         */

        /*
         * TODO: Add an action listener to the submit button so that it changes
         * the text in statusPanel.
         *
         * Hint: You should look at QuoteGenerator#setName(String) and
         * StatusPanel#refresh().
         */
    }
}