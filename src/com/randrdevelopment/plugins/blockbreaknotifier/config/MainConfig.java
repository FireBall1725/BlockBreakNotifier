package com.randrdevelopment.plugins.blockbreaknotifier.config;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.plugin.Plugin;

import com.randrdevelopment.plugins.blockbreaknotifier.config.Config;

public class MainConfig extends Config {
	public MainConfig(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "Block Notification Configuration File";
	}
	
	public ArrayList<BlockListenerConfig> BlockConfig = new ArrayList<BlockListenerConfig>(){{add(new BlockListenerConfig(1));}};
}