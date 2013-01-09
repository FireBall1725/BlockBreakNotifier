package com.randrdevelopment.plugins.blockbreaknotifier;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.randrdevelopment.plugins.blockbreaknotifier.command.CommandManager;
import com.randrdevelopment.plugins.blockbreaknotifier.commands.DefaultCommand;
import com.randrdevelopment.plugins.blockbreaknotifier.commands.HelpCommand;
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
	private final BlockBreakListener blockBreakListener = new BlockBreakListener(this);
	private final PlayerJoinListener playerJoinListener = new PlayerJoinListener(this);
	
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
    }
    
    private void registerListeners() {
    	PluginManager pm = getServer().getPluginManager();
    	getCommand("bbn").setExecutor(new CommandListener(this));
    	pm.registerEvents(blockBreakListener, this);
    	pm.registerEvents(playerJoinListener, this);
    }

	public void broadcastMessage(String message) {
		Player[] playersonline = Bukkit.getServer().getOnlinePlayers();
		for(Player p:playersonline){
			if (p.isOp()||p.hasPermission("blockbreaknotifier.getnotification")){
				p.sendMessage(getTag() + message);
			}
		} 
	}
}

