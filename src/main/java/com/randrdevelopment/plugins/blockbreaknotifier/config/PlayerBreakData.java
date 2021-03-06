package com.randrdevelopment.plugins.blockbreaknotifier.config;

import java.io.File;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

public class PlayerBreakData extends Config {
	public PlayerBreakData(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "playerdata.yml");
		CONFIG_HEADER = "** DO NOT EDIT THIS FILE **";
	}

	public HashMap<String, HashMap<String, Long>> PlayerBlockLastBreak = new HashMap<String, HashMap<String, Long>>();
	public HashMap<String, HashMap<String, Integer>> PlayerBlockBreakCount = new HashMap<String, HashMap<String, Integer>>();
}
