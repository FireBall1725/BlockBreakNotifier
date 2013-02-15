package com.randrdevelopment.plugins.blockbreaknotifier.config;

public class ConfigBlocks extends ConfigObject{
	public ConfigBlocks(int BlockID) {
		this.BlockID = BlockID;
	}

	public int BlockID = 0;
	public String BreakMessage = "User %username% broke block %blockid% located at %userlocation%";
	public Boolean LogBreakInDatabase = false;
	public Boolean LogBreakInChat = true;
	public int NumberOfBreaksBeforeNotify = 3;
}
