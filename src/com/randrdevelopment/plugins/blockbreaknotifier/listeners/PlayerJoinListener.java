package com.randrdevelopment.plugins.blockbreaknotifier.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.randrdevelopment.plugins.blockbreaknotifier.BlockBreakNotifier;

public class PlayerJoinListener implements Listener {
	public BlockBreakNotifier plugin;
	
	public PlayerJoinListener (BlockBreakNotifier instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		
	}
}
