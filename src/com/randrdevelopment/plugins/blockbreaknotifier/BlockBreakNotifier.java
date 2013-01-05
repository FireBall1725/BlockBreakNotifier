package com.randrdevelopment.plugins.blockbreaknotifier;

import java.util.logging.Level;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.randrdevelopment.plugins.blockbreaknotifier.config.MainConfig;

public class BlockBreakNotifier extends JavaPlugin implements Listener {
	private MainConfig mainConfig;
	
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
    }

    private Boolean loadConfig() {
    	try {
    		mainConfig = new MainConfig(this);
    		mainConfig.init();
    	} catch(Exception ex) {
    		getLogger().log(Level.SEVERE, "FAILED TO LOAD CONFIG!!!", ex);
    		getServer().getPluginManager().disablePlugin(this);
    		return false;
    	}
    	return true;
    }
    
    private void registerCommands() {
    	
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Welcome, " + event.getPlayer().getDisplayName() + "!");
    }
}

