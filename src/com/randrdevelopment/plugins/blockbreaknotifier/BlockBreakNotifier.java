package com.randrdevelopment.plugins.blockbreaknotifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.lang.System;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.randrdevelopment.plugins.blockbreaknotifier.config.MainConfig;
import com.randrdevelopment.plugins.blockbreaknotifier.config.PlayerBreakData;

public class BlockBreakNotifier extends JavaPlugin implements Listener {
	private MainConfig mainConfig;
	private PlayerBreakData playerBreakData;
	
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
    		playerBreakData = new PlayerBreakData(this);
    		playerBreakData.init();
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
    	
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void blockBreak(BlockBreakEvent e) {
    	String blockName = e.getBlock().getType().toString();
    	String blockID = Integer.toString(e.getBlock().getTypeId());
    	Block brokenBlock = e.getBlock();
    	World world = e.getBlock().getWorld();
    	
    	blockName = blockName.replace("_", " ");
    	
    	if (mainConfig.Blocks.contains(blockID)) {
    		// Construct the message to send
    		String playerName = e.getPlayer().getDisplayName();
    		String playerLocation = "[" + e.getPlayer().getLocation().getWorld().getName() + "] " + e.getPlayer().getLocation().getBlockX() + ", " + e.getPlayer().getLocation().getBlockY() + ", " + e.getPlayer().getLocation().getBlockZ();
    		
    		String message = "Player " + ChatColor.DARK_AQUA + playerName + ChatColor.GOLD + " Broke Block " + ChatColor.DARK_AQUA + blockName + " (" + blockID + ")" + ChatColor.GOLD + " at location " + ChatColor.DARK_AQUA + playerLocation;
    		
    		if (mainConfig.Block_CheckRadius > 1)
    		{
    			List<Block> blocks = new ArrayList<Block>();
    			int radius = mainConfig.Block_CheckRadius -1;
    			for (int x = brokenBlock.getX() - radius; x < brokenBlock.getX() + radius; x++) {
    				for (int y = brokenBlock.getY() - radius; y < brokenBlock.getY() + radius; y++) {
    					for (int z = brokenBlock.getZ() - radius; z < brokenBlock.getZ() + radius; z++) {
    						Block radiusBlock = world.getBlockAt(x,y,z);
    						String radiusBlockID = Integer.toString(radiusBlock.getTypeId());
    						if (blockID.equals(radiusBlockID))
    						{
    							blocks.add(radiusBlock);
    						}
    					}
    				}
    			}
    			
    			if (blocks.size() > 0)
    			{
    				message += ChatColor.GOLD + " Found " + ChatColor.DARK_AQUA + blocks.size() + ChatColor.GOLD + " in area";
    			}
    		}
    		
    		// Log Break in Config
    		Boolean ShowMessage = true;
    		
    		HashMap<String, Long> blockBreak = playerBreakData.PlayerBlockLastBreak.get(e.getPlayer().getName());
    		
    		if (blockBreak == null) {
    			blockBreak = new HashMap<String, Long>();
    		}
    		else
    		{
    			if (blockBreak.containsKey(blockID))
    			{
    				if (System.currentTimeMillis() - mainConfig.Notification_Cooldown <= blockBreak.get(blockID)) {
    					ShowMessage = false;
    				}
    			}
    		}
    		
    		blockBreak.put(blockID, System.currentTimeMillis());
    		playerBreakData.PlayerBlockLastBreak.put(e.getPlayer().getName(), blockBreak);
    		
    		int count = 0;
    		HashMap<String, Integer> blockCount = playerBreakData.PlayerBlockBreakCount.get(e.getPlayer().getName());
    		
    		if (blockCount == null)
    		{
    			blockCount = new HashMap<String, Integer>();
    			blockCount.put(blockID, 1);
    		}
    		else
    		{
    			if (blockCount.containsKey(blockID))
    			{
    				count = blockCount.get(blockID);
    			}
    			count ++;
    			blockCount.put(blockID, count);
    		}
    		
    		playerBreakData.PlayerBlockBreakCount.put(e.getPlayer().getName(), blockCount);
    		
    		try {
				playerBreakData.save();
			} catch (InvalidConfigurationException e1) {
				broadcastMessage(e1.toString());
			}
    		
    		if (ShowMessage) {
    			broadcastMessage(message);
    		}
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

