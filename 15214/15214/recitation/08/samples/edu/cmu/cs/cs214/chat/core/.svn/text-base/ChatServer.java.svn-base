package edu.cmu.cs.cs214.chat.core;

import java.util.ArrayList;
import java.util.List;

public class ChatServer {
	/**
	 * The list of current subscribers.
	 */
	private final List<ChatSubscriber> subscribers;

	public ChatServer() {
		subscribers = new ArrayList<ChatSubscriber>();
	}

	/**
	 * Add a subscriber to the chat server. The subscriber
	 * will receive messages from and can publish messages 
	 * to all subscribers
	 * 
	 * @param subscriber : the new subscriber
	 */
	public void subscribe(ChatSubscriber subscriber) {
		subscribers.add(subscriber);
	}

	/**
	 * Unsubscribe a user from the list of subscribers.
	 * The user will no longer receive messages from other
	 * subscribers
	 * 
	 * @param subscriber : the user that wants to unsubscribe
	 */
	public void unsubscribe(ChatSubscriber subscriber) {
		subscribers.remove(subscriber);
	}

	/**
	 * Post a message to all subscribers of the chat server
	 * 
	 * @param name : publisher's name
	 * @param message : message to be published
	 */
	public void publish(String name, String message) {
		for (ChatSubscriber s : subscribers) {
			s.handleMessage(name, message);
		}
	}
}
