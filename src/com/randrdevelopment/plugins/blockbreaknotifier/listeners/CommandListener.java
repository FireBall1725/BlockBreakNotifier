package com.randrdevelopment.plugins.blockbreaknotifier.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.randrdevelopment.plugins.blockbreaknotifier.BlockBreakNotifier;

public class CommandListener implements CommandExecutor {
	public static BlockBreakNotifier plugin;
	
	public CommandListener(BlockBreakNotifier instance) {
		plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return plugin.commandManager.dispatch(sender, command, label, args, plugin);
	}
}
