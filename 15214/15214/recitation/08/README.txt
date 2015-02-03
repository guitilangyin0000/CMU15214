ChatServer

Inside the samples/ source directory is a Simple GUI program which will give you
a basic idea of how you can implement the GUIs for Tic-Tac-Toe and Scrabble.
Note that like Tic-Tac-Toe and Scrabble, it has separate core and GUI components.

Pay attention to how the GUI and core interact with each other. In particular,
notice the way that each ChatPanel is registered as a subscriber to the ChatServer.
What design pattern is used here?

Run the main() method in SimpleChatClient.java to run the ChatServer application.
It will present a screen where users can be registered, and the "Start Chat"
button will open a new chat window for each user.
