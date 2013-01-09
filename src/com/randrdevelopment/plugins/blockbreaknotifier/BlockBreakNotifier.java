package com.randrdevelopment.plugins.blockbreaknotifier;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.randrdevelopment.plugins.blockbreaknotifier.command.CommandManager;
import com.randrdevelopment.plugins.blockbreaknotifier.commands.DefaultCommand;
import com.randrdevelopment.plugins.blockbreaknotifier.commands.HelpCommand;
import com.randrdevelopment.plugins.blockbreaknotifier.commands.MuteCommand;
import com.randrdevelopment.plugins.blockbreaknotifier.commands.ReloadCommand;
import com.randrdevelopment.plugins.blockbreaknotifier.config.MainConfig;
import com.randrdevelopment.plugins.blockbreaknotifier.config.PlayerBreakData;
import com.randrdevelopment.plugins.blockbreaknotifier.config.PlayerSettings;
import com.randrdevelopment.plugins.blockbreaknotifier.listeners.BlockBreakListener;
import com.randrdevelopment.plugins.blockbreaknotifier.listeners.CommandListener;
import com.randrdevelopment.plugins.blockbreaknotifier.listeners.PlayerJoinListener;

public class BlockBreakNotifier extends JavaPlugin implements Listener {
	public MainConfig mainConfig;
	public PlayerBreakData playerBreakData;
	public PlayerSettings playerConfig;
	public CommandManager commandManager;
	
    public void onDisable() {
        // TODO: Place any custom disable code here.
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        // Load Configuration File
    	if (!loadConfig())
    		return;
    	
    	// Register Plugin Commands
        registerCommands();
        
        // Register Listeners
        registerListeners();
    }

    public String getTag() {
    	return ChatColor.AQUA + "[BBN]" + ChatColor.GOLD + " ";
    }
    
    private Boolean loadConfig() {
    	try {
    		mainConfig = new MainConfig(this);
    		mainConfig.init();
    		playerBreakData = new PlayerBreakData(this);
    		playerBreakData.init();
    		playerConfig = new PlayerSettings(this);
    		playerConfig.init();
    	} catch(Exception ex) {
    		getLogger().log(Level.SEVERE, "FAILED TO LOAD CONFIG!!!", ex);
    		getServer().getPluginManager().disablePlugin(this);
    		return false;
    	}
    	
    	if (mainConfig.Block_CheckRadius <= 0)
    	{
    		getLogger().log(Level.SEVERE, "Block Check Radius must be higher than 0");
    		getServer().getPluginManager().disablePlugin(this);
    		return false;
    	}
    	return true;
    }
    
    private void registerCommands() {
    	commandManager = new CommandManager();
    	commandManager.addCommand(new DefaultCommand(this));
    	commandManager.addCommand(new ReloadCommand(this));
    	commandManager.addCommand(new HelpCommand(this));
    	commandManager.addCommand(new MuteCommand(this));
    }
    
    private void registerListeners() {
    	getCommand("bbn").setExecutor(new CommandListener(this));
    	getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
    	getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    public String colouriseText(String string){
    	return string.replaceAll("(&([a-fk-or0-9]))", "\u00A7$2");
    }
    
    public void sendMessage(String message, Player player) {
    	player.sendMessage(getTag() + message);
    }
    
	public void broadcastMessage(String message) {
		Player[] playersonline = Bukkit.getServer().getOnlinePlayers();
		
		for(Player p:playersonline){
			Boolean MutedNotifications;
			
			if (playerConfig.PlayerConfig_MuteOption.containsKey(p.getName())) {
				MutedNotifications = playerConfig.PlayerConfig_MuteOption.get(p.getName());
			}
			else
			{
				MutedNotifications = false;
			}
			
			if (p.isOp()||p.hasPermission("blockbreaknotifier.getnotification")){
				if (!MutedNotifications) {
					p.sendMessage(getTag() + message);
				}
			}
		} 
	}
}

