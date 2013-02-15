package com.randrdevelopment.plugins.blockbreaknotifier.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.randrdevelopment.plugins.blockbreaknotifier.BlockBreakNotifier;

public class PlayerJoinListener implements Listener {
	private final BlockBreakNotifier plugin;
	
	public PlayerJoinListener (BlockBreakNotifier instance) {
		plugin = instance;
	}
	
	@EventHandler (priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e) {
		String PlayerName = e.getPlayer().getName();
		
		if (!e.getPlayer().hasPermission("blockbreaknotifier.getnotification")){
			return;
		}
		
		Boolean MutedNotifications;
		
		if (plugin.playerConfig.PlayerConfig_MuteOption.containsKey(PlayerName)) {
			MutedNotifications = plugin.playerConfig.PlayerConfig_MuteOption.get(PlayerName);
		}
		else
		{
			MutedNotifications = false;
		}
		
		if (MutedNotifications)
		{
			e.getPlayer().sendMessage(plugin.getTag() + "Your notifications are muted");
		}
	}
}
