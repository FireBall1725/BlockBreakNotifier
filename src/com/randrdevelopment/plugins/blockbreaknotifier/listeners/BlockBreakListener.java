package com.randrdevelopment.plugins.blockbreaknotifier.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.randrdevelopment.plugins.blockbreaknotifier.BlockBreakNotifier;

public class BlockBreakListener implements Listener {
	public BlockBreakNotifier plugin;
	
	public BlockBreakListener(BlockBreakNotifier instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void blockBreak(BlockBreakEvent e) {
		String blockName = e.getBlock().getType().toString();
		String blockID = Integer.toString(e.getBlock().getTypeId());
		Block brokenBlock = e.getBlock();
		World world = e.getBlock().getWorld();
		
		blockName = blockName.replace("_", " ");
		
		if (plugin.mainConfig.Blocks.contains(blockID)) {
			// Construct the message to send
			String playerName = e.getPlayer().getDisplayName();
			String playerLocation = "[" + e.getPlayer().getLocation().getWorld().getName() + "] " + e.getPlayer().getLocation().getBlockX() + ", " + e.getPlayer().getLocation().getBlockY() + ", " + e.getPlayer().getLocation().getBlockZ();
			
			String message = "Player " + ChatColor.DARK_AQUA + playerName + ChatColor.GOLD + " Broke Block " + ChatColor.DARK_AQUA + blockName + " (" + blockID + ")" + ChatColor.GOLD + " at location " + ChatColor.DARK_AQUA + playerLocation;
			
			if (plugin.mainConfig.Block_CheckRadius > 1)
			{
				List<Block> blocks = new ArrayList<Block>();
				int radius = plugin.mainConfig.Block_CheckRadius -1;
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
			
			HashMap<String, Long> blockBreak = plugin.playerBreakData.PlayerBlockLastBreak.get(e.getPlayer().getName());
			
			if (blockBreak == null) {
				blockBreak = new HashMap<String, Long>();
			}
			else
			{
				if (blockBreak.containsKey(blockID))
				{
					if (System.currentTimeMillis() - plugin.mainConfig.Notification_Cooldown <= blockBreak.get(blockID)) {
						ShowMessage = false;
					}
				}
			}
			
			blockBreak.put(blockID, System.currentTimeMillis());
			plugin.playerBreakData.PlayerBlockLastBreak.put(e.getPlayer().getName(), blockBreak);
			
			int count = 0;
			HashMap<String, Integer> blockCount = plugin.playerBreakData.PlayerBlockBreakCount.get(e.getPlayer().getName());
			
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
			
			plugin.playerBreakData.PlayerBlockBreakCount.put(e.getPlayer().getName(), blockCount);
			
			try {
				plugin.playerBreakData.save();
			} catch (InvalidConfigurationException e1) {
				plugin.broadcastMessage(e1.toString());
			}
			
			if (ShowMessage) {
				plugin.broadcastMessage(message);
			}
		}
	}
}
