package com.randrdevelopment.plugins.blockbreaknotifier.commands;

import org.bukkit.command.CommandSender;

import com.randrdevelopment.plugins.blockbreaknotifier.BlockBreakNotifier;
import com.randrdevelopment.plugins.blockbreaknotifier.command.BaseCommand;

public class DefaultCommand extends BaseCommand {
	public DefaultCommand(BlockBreakNotifier plugin) {
		super(plugin);
		name = "Default";
		description = "Default Command";
		usage = "/bbn";
		minArgs = 0;
		maxArgs = 0;
		identifiers.add("bbn");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(plugin.getTag() + "BlockBreakNotifier Version 0.01 - /bbn help for help");
		sender.sendMessage(plugin.getTag() + "Created by: FireBall1725");
		sender.sendMessage(plugin.getTag() + "http://www.github.com/FireBall1725");
	}
}
