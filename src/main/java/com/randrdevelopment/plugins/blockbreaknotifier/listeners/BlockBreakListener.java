package com.randrdevelopment.plugins.blockbreaknotifier.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
			
			int radiuscount = 0;
			
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
				radiuscount = blocks.size();
			}
			
			String partPlayerName = playerName;
			String partBlockName = blockName;
			String partBlockID = blockID;
			String partWorldName = e.getPlayer().getLocation().getWorld().getName();
			String partPlayerLocation = e.getPlayer().getLocation().getBlockX() + ", " + e.getPlayer().getLocation().getBlockY() + ", " + e.getPlayer().getLocation().getBlockZ();
			String partBlockLocation = e.getBlock().getLocation().getBlockX() + ", " + e.getBlock().getLocation().getBlockY() + ", " + e.getBlock().getLocation().getBlockZ();
			String partRadiusCount = Integer.toString(radiuscount);
			
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
			
			// Generate Message
			String Message = "";
			
			if (plugin.mainConfig.Notification_Message.equals("") || plugin.mainConfig.Notification_Message == null)
				Message = "&6Player &3%playername% &6Broke Block &3%blockname% (%blockid%) &6at location &3[%worldname%] %playerlocation% &6Found &3%radiuscount% &6in area";
			else
				Message = plugin.mainConfig.Notification_Message;
			
			/* Message Parts
			 * %playername% - Player Display Name
			 * %blockname% - Block Name (i.e. DIAMOND BLOCK)
			 * %blockid% - Block ID Number
			 * %worldname% - World Name
			 * %playerlocation% - Players Location (X, Y, Z)
			 * %blocklocation% - Blocks Location (X, Y, Z)
			 * %radiuscount% - Number of blocks around the block of the same type
			 */

			Message = Message.replace("%playername%", partPlayerName);
			Message = Message.replace("%blockname%", partBlockName);
			Message = Message.replace("%blockid%", partBlockID);
			Message = Message.replace("%worldname%", partWorldName);
			Message = Message.replace("%playerlocation%", partPlayerLocation);
			Message = Message.replace("%blocklocation%", partBlockLocation);
			Message = Message.replace("%radiuscount%", partRadiusCount);
			
			Message = plugin.colouriseText(Message);
			
			if (ShowMessage) {
				plugin.broadcastMessage(Message);
			}
		}
	}
}
