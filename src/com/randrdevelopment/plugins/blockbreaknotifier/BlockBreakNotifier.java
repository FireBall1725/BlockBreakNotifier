package com.randrdevelopment.plugins.blockbreaknotifier;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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

    private String getTag() {
    	return ChatColor.AQUA + "[BBN]" + ChatColor.GOLD;
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
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void blockBreak(BlockBreakEvent e) {
    	String blockName = e.getBlock().getType().toString();
    	String blockID = Integer.toString(e.getBlock().getTypeId());
    	
    	blockName = blockName.replace("_", " ");
    	
    	if (mainConfig.Blocks.contains(blockID)) {
    		// Construct the message to send
    		String playerName = e.getPlayer().getDisplayName();
    		String playerLocation = "[" + e.getPlayer().getLocation().getWorld().getName() + "] " + e.getPlayer().getLocation().getBlockX() + ", " + e.getPlayer().getLocation().getBlockY() + ", " + e.getPlayer().getLocation().getBlockZ();
    		
    		String message = "Player " + ChatColor.DARK_AQUA + playerName + ChatColor.GOLD + " Broke Block " + ChatColor.DARK_AQUA + blockName + " (" + blockID + ")" + ChatColor.GOLD + " at location " + ChatColor.DARK_AQUA + playerLocation;
    		broadcastMessage(message);
    	}
    }

	private void broadcastMessage(String message) {
		Player[] playersonline = Bukkit.getServer().getOnlinePlayers();
		for(Player p:playersonline){
			if (p.isOp()||p.hasPermission("blockbreaknotifier.getnotification")){
				p.sendMessage(getTag() + message);
			}
		} 
	}
}

