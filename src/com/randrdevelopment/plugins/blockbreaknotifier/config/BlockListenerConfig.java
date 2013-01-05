package com.randrdevelopment.plugins.blockbreaknotifier.config;

public class BlockListenerConfig extends ConfigObject {
	public BlockListenerConfig(int BlockID) {
		this.BlockID = BlockID;
	}
	
	public int BlockID = 0;
	public String NotificationMessage = "{username} Broke {blockid} at {userlocation}";
}
