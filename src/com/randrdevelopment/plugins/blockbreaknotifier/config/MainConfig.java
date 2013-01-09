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
	
	public Boolean Database_UseDatabase = false;
	public String Database_DatabaseServer = "localhost";
	public String Database_DatabaseUsername = "";
	public String Database_DatabasePassword = "";
	public String Database_DatabaseName = "minecraft";
	
	@SuppressWarnings("serial")
	public ArrayList<String> Blocks = new ArrayList<String>() {
		{
			add("56");
			add("14");
			add("15");
		}
	};
	
	public int Block_CheckRadius = 1;
	public int Notification_Cooldown = 15000;
	public String Notification_Message = "";
}