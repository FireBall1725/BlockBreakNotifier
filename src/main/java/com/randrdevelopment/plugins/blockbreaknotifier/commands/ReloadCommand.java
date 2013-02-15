package com.randrdevelopment.plugins.blockbreaknotifier.commands;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.randrdevelopment.plugins.blockbreaknotifier.BlockBreakNotifier;
import com.randrdevelopment.plugins.blockbreaknotifier.command.BaseCommand;

public class ReloadCommand extends BaseCommand {
	public ReloadCommand(BlockBreakNotifier plugin) {
		super(plugin);
		name = "Reload";
		description = "Reload Command";
		usage = "/bbn reload";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("bbn reload");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("blockbreaknotifier.reload")){
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return;
		}
		
		try {
			plugin.mainConfig.reload();
			plugin.playerBreakData.reload();
			plugin.playerConfig.reload();
			
			sender.sendMessage(plugin.getTag() + "BlockBreakNotifier Reloaded");
		}
		catch (Exception ex) {
			sender.sendMessage(plugin.getTag() + "BlockBreakNotifier Falied to reload, check the console");
			plugin.getLogger().log(Level.SEVERE, "FAILED TO RELOAD CONFIG!!!", ex);
		}
	}
}
