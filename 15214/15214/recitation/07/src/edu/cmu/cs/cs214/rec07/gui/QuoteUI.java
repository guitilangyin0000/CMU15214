package edu.cmu.cs.cs214.rec07.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.rec07.core.QuoteGenerator;

public class QuoteUI {
    private static final String TITLE = "rec07";
    private static final String ADD_QUOTE = "+ Quote";
    private static final String SUBMIT = "submit";
    private static final String CLEAR = "Clear";
    private static final String NAME = "Name";

    private final JFrame frame;
    private final QuoteGenerator model;

    private final StatusPanel statusPanel;
    private final NamePanel namePanel;
    private final JPanel quotePanel = new JPanel();
    private final JButton addQuoteButton = new JButton(ADD_QUOTE);
    private final JButton clearButton = new JButton(CLEAR);
    private final JButton submitButton = new JButton(SUBMIT);
    private final JTextField inputField = new JTextField(NAME);

    public QuoteUI(QuoteGenerator gen) {
        model = gen;

        statusPanel = new StatusPanel(model);
        namePanel = new NamePanel(model, statusPanel);

        quotePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        frame = new JFrame(TITLE);
        frame.setResizable(true);

        addElements(frame.getContentPane()); // Adds all of the buttons, etc.
        frame.pack();

        // When this window's "exit" button is clicked, the program ends
        // BoxLayout
        quotePanel.setLayout(new BoxLayout(quotePanel, BoxLayout.PAGE_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addElements(Container container) {
        container.setLayout(new BorderLayout()); // set up a layout manager

        container.add(namePanel, BorderLayout.NORTH);
        container.add(quotePanel, BorderLayout.CENTER);
        container.add(statusPanel, BorderLayout.SOUTH);

        // Creates a panel with two buttons on it and adds it to the top
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(inputField);
        buttonPanel.add(submitButton);
        buttonPanel.add(addQuoteButton);
        buttonPanel.add(clearButton);
        namePanel.add(buttonPanel, BorderLayout.SOUTH);

        /*
         * generate a quote from the core and append it to the
         * quotePanel on click
         */
        addQuoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String quote = model.generateQuote();
                quotePanel.add(new JLabel(quote));
                frame.pack(); // Resizes the window so the new quote will fit.
            }
        });        
        
        /*
         * remove all quotes from the quotePanel and resize the frame on click
         */
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quotePanel.removeAll();
                frame.pack(); // Resizes the window
            }
        });

        // TODO: Make quotePanel display quote lines vertically by setting its layout
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = inputField.getText();
                //System.out.println(name);
                model.setName(name);
                statusPanel.refresh();
                frame.pack(); // Resizes the window
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    // prepare function
    public static void createAndShowGUI() {
        QuoteGenerator generator = new QuoteGenerator();
        QuoteUI main = new QuoteUI(generator);
        main.show();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}