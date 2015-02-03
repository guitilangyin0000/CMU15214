package edu.cmu.cs.cs214.chat.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.cmu.cs.cs214.chat.core.ChatServer;
import edu.cmu.cs.cs214.chat.core.ChatSubscriber;

/**
 * The Chat Panel UI for each participant.
 *
 */
public class ChatPanel extends JPanel implements ChatSubscriber {
	private static final long serialVersionUID = 4819162036004676580L;

	private static final int FIELD_WIDTH = 60;
	private static final int AREA_WIDTH = FIELD_WIDTH + 10;
	private static final int AREA_HEIGHT = 20;

	private final String mName; // participant's name
	private final ChatServer mServer; // the chat server that manages chat messages
	private final JTextArea mChatArea; // chat text area for input and message display

	/**
	 * Initialize a new ChatPanel with a chat server and 
	 * participant's name
	 * @param server
	 * @param name
	 */
	public ChatPanel(ChatServer server, String name) {
		if (name == null || server == null) {
			throw new NullPointerException("Server and name must not be null.");
		}
		mName = name;
		mServer = server;

		// Sets up a scrollable text area that line-wraps its messages, for the
		// chat messages.
		mChatArea = new JTextArea(AREA_HEIGHT, AREA_WIDTH);
		mChatArea.setEditable(false);
		mChatArea.setLineWrap(true);
		mChatArea.setWrapStyleWord(true);

		// Fun fact: the JScrollPane is a good example of the decorator pattern.
		JScrollPane scrollPane = new JScrollPane(mChatArea);

		// Sets up a text field for typing chat messages, and a button to send
		// them.
		final JTextField chatField = new JTextField(FIELD_WIDTH);
		JButton sendButton = new JButton("Send");
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add(chatField, BorderLayout.WEST);
		messagePanel.add(sendButton, BorderLayout.EAST);

		// Observer to send a message when the user presses the send button or
		// hits enter in the message field.
		ActionListener sendChatListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// get user input from text area
				String message = chatField.getText();
				if (!message.isEmpty()) {
					// publish user's chat message through server
					mServer.publish(mName, message);
				}
				// clear the sent message
				chatField.setText("");
				// regain keyboard focus
				chatField.requestFocus();
			}
		};

		// when send button is clicked
		sendButton.addActionListener(sendChatListener);
		// when "Enter" key is pressed
		chatField.addActionListener(sendChatListener);

		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.NORTH);
		add(messagePanel, BorderLayout.SOUTH);
		setVisible(true);

		// Let server know that this participant is 
		// subscribing to chat messages.
		mServer.subscribe(this);
	}

	@Override
	public void handleMessage(String name, String message) {
		String newText = String.format(" %s: %s\n", name, message);
		// add the new message to the bottom of message panel
		mChatArea.append(newText);
	}
}
