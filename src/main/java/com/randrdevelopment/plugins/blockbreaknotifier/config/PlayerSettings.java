package com.randrdevelopment.plugins.blockbreaknotifier.config;

import java.io.File;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

public class PlayerSettings extends Config{
	public PlayerSettings (Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "playerconfig.yml");
		CONFIG_HEADER = "** DO NOT EDIT THIS FILE **";
	}
	
	public HashMap<String, Boolean> PlayerConfig_MuteOption = new HashMap<String, Boolean>();
}
