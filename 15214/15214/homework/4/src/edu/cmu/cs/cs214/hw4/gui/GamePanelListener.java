package edu.cmu.cs.cs214.hw4.gui;

public interface GamePanelListener {
	
	public void updateBoardPanel();
	
	public void updatePlayerInfoPanel();
	
	public void updatePlayerCommandPanel();
	
	public void updateAll();
	
	public void setTrue();
	
	public void setGameMsgLabel(String string);
	
	public void removeSettingPanel();
	
	public void setPlacementInstruction(int startX, int startY, int dir, int length);

}
