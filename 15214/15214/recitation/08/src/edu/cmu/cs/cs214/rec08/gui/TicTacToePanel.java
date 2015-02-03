package edu.cmu.cs.cs214.rec08.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.cmu.cs.cs214.rec08.core.Player;
import edu.cmu.cs.cs214.rec08.core.TicTacToe;
import edu.cmu.cs.cs214.rec08.core.TicTacToeImpl;

public class TicTacToePanel extends TicTacToeImpl {

	private TicTacToe game;
	
	public TicTacToePanel(TicTacToe game){
		this.game = game;
	}
	
}
