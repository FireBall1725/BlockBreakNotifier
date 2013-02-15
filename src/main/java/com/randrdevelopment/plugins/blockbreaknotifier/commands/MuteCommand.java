package com.randrdevelopment.plugins.blockbreaknotifier.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.randrdevelopment.plugins.blockbreaknotifier.BlockBreakNotifier;
import com.randrdevelopment.plugins.blockbreaknotifier.command.BaseCommand;

public class MuteCommand extends BaseCommand{
	public MuteCommand(BlockBreakNotifier plugin) {
		super(plugin);
		name = "Mute";
		description = "Mute Notifications";
		usage = "/bbn mute";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("bbn mute");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String PlayerName = sender.getName();
		
		if (!sender.hasPermission("blockbreaknotifier.getnotification")){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
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
		
		if (MutedNotifications) {
			plugin.playerConfig.PlayerConfig_MuteOption.put(PlayerName, false);
			sender.sendMessage(plugin.getTag() + "Notifications unmuted");
		}
		else 
		{
			plugin.playerConfig.PlayerConfig_MuteOption.put(PlayerName, true);
			sender.sendMessage(plugin.getTag() + "Notifications muted");
		}
		
		try {
			plugin.playerConfig.save();
		}
		catch (Exception ex) {
			
		}
	}
}
