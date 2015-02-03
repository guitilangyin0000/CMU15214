package edu.cmu.cs.cs214.rec09;

import javax.swing.SwingUtilities;

import edu.cmu.cs.cs214.rec09.framework.core.GameFrameworkImpl;
import edu.cmu.cs.cs214.rec09.framework.gui.GameFrameworkGui;
import edu.cmu.cs.cs214.rec09.plugin.MemoryPlugin;
import edu.cmu.cs.cs214.rec09.plugin.RpsPlugin;
import edu.cmu.cs.cs214.rec09.plugin.TicTacToePlugin;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndStartFramework();
			}
		});
	}

	private static void createAndStartFramework() {
		GameFrameworkImpl core = new GameFrameworkImpl();
		GameFrameworkGui gui = new GameFrameworkGui(core);
		core.setGameChangeListener(gui);

		core.addPlayer("X");
		core.addPlayer("O");

		core.registerPlugin(new MemoryPlugin());
		core.registerPlugin(new RpsPlugin());
		core.registerPlugin(new TicTacToePlugin());
		core.startNewGame(core.);
	}

}
