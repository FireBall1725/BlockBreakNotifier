package com.randrdevelopment.plugins.blockbreaknotifier.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.randrdevelopment.plugins.blockbreaknotifier.BlockBreakNotifier;
import com.randrdevelopment.plugins.blockbreaknotifier.command.BaseCommand;

public class HelpCommand extends BaseCommand {
	public HelpCommand(BlockBreakNotifier plugin) {
		super(plugin);
		name = "Help";
		description = "Plugin Help";
		usage = "/bbn help <page>";
		minArgs = 0;
		maxArgs = 1;
		identifiers.add("bbn help");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("blockbreaknotifier.getnotification")){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return;
		}
		
		int page = 1;
		if (args.length > 0)
		{
			try {
				Integer.parseInt(args[0]);
			}
			catch (Exception ex) {
				sender.sendMessage(plugin.getTag() + "Page number must be a number");
				return;
			}
		}
		
		switch (page) {
		case 1:
			sender.sendMessage(ChatColor.GOLD + "Block Break Notification Help - Page 1");
			sender.sendMessage(ChatColor.AQUA + "/bbn" + ChatColor.GOLD + " Plugin information");
			sender.sendMessage(ChatColor.AQUA + "/bbn reload" + ChatColor.GOLD + " Reload configuration");
			sender.sendMessage(ChatColor.AQUA + "/bbn help" + ChatColor.GOLD + " Plugin help");
			sender.sendMessage(ChatColor.AQUA + "/bbn mute" + ChatColor.GOLD + " Mute notifications");
			break;
		}
	}
}
