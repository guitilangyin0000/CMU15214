package edu.cmu.cs.cs214.chat.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.chat.core.ChatServer;

public class SimpleChatClient extends JPanel {
	private static final long serialVersionUID = 6647535125556634481L;

	private final JFrame parentFrame;
	private final List<String> names;

	public SimpleChatClient(JFrame frame) {
		this.parentFrame = frame;
		this.names = new ArrayList<String>();

		// Create the components to add to the panel.
		JLabel participantLabel = new JLabel("Name: ");

		// Must be final to be accessible to the anonymous class.
		final JTextField participantText = new JTextField(20);

		JButton participantButton = new JButton("Add participant");
		JPanel participantPanel = new JPanel();
		participantPanel.setLayout(new BorderLayout());
		participantPanel.add(participantLabel, BorderLayout.WEST);
		participantPanel.add(participantText, BorderLayout.CENTER);
		participantPanel.add(participantButton, BorderLayout.EAST);

		// Defines an anonymous class to handle the addition of new participants
		ActionListener newParticipantListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = participantText.getText();
				if (!name.isEmpty() && !names.contains(name)) {
					names.add(name);
				}
				participantText.setText("");
				participantText.requestFocus();
			}
		};

		// notify the action listener when participantButton is pressed
		participantButton.addActionListener(newParticipantListener);
		// notify the action listener when "Enter" key is hit
		participantText.addActionListener(newParticipantListener);

		JButton createButton = new JButton("Start chat");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!names.isEmpty()) {
					// Starts a new chat when the createButton is clicked.
					startChatSession();
				}
			}
		});

		// Adds the components we've created to the panel (and to the window).
		setLayout(new BorderLayout());
		add(participantPanel, BorderLayout.NORTH);
		add(createButton, BorderLayout.SOUTH);
		setVisible(true);
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame("Start a SimpleChat session");
				frame.add(new SimpleChatClient(frame));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setResizable(false);
				frame.setVisible(true);
			}
		});

	}

	/**
	 * Starts a new chat, opening one window for each participant.
	 */
	private void startChatSession() {
		final ChatServer server = new ChatServer();
		final String title = "Simple Chat Client -- ";

		// Removes the initial dialog panel and starts the first participant's
		// chat in the existing window. This is simply for resource reuse, as
		// it produces the same result as the JFrame setup below in the loop.
		String firstName = names.get(0);
		parentFrame.remove(this);
		parentFrame.add(new ChatPanel(server, firstName));
		parentFrame.setTitle(title + firstName);
		parentFrame.setResizable(true);
		parentFrame.pack();

		// Creates a new window for each other participant.
		for (int i = 1; i < names.size(); ++i) {
			final String name = names.get(i);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					JFrame frame = new JFrame(title + name);
					frame.add(new ChatPanel(server, name));
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
					frame.setResizable(true);
					frame.setVisible(true);
				}
			});
		}
	}
}
