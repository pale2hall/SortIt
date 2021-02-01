package com.palecraft.sortit;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;

import org.bukkit.Bukkit;
//import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
//import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.jeff_media.ChestSortAPI.Sortable;



//import org.bukkit.block.*;

public class SortItCmd implements CommandExecutor {
	
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


    	  if (sender instanceof Player) {
              Player player = (Player) sender;
              String sender_name = player.getDisplayName();
             Bukkit.broadcastMessage(sender_name +" is just gonna sort it");
             SortIt.var_map.put("is_sorting", "true");
             
             World world = player.getWorld();

             ArrayList<Block> chests = new ArrayList<Block>();
             ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
             
             //get player location
            

             int event_x =  player.getLocation().getBlockX();
             int event_y =  player.getLocation().getBlockY();
             int event_z =  player.getLocation().getBlockZ();
             

             Bukkit.getLogger().info("Player x: "+event_x);
             Bukkit.getLogger().info("Player y: "+event_y);
             Bukkit.getLogger().info("Player z: "+event_z);
             

             int radius = 10;
             
             Bukkit.getLogger().info("radius: "+radius);
             
             int chests_found = 0;
             
             
             int min_x = event_x - radius;
             int min_y = Math.max(0, event_y - radius);
             int min_z = event_z - radius;
             

             Bukkit.getLogger().info("min x: "+min_x);
             Bukkit.getLogger().info("min y: "+min_y);
             Bukkit.getLogger().info("min z: "+min_z);
             
             
             int max_x = event_x + radius;
             int max_y = event_y + radius;
             int max_z = event_z + radius; 
             

             Bukkit.getLogger().info("max x: "+max_x);
             Bukkit.getLogger().info("max y: "+max_y);
             Bukkit.getLogger().info("max z: "+max_z);
             
             
             for(int x = min_x; x <= max_x; x++) {

                 for(int y = min_y; y <= max_y; y++) {

                     for(int z = min_z; z <= max_z; z++) {

	                    		//First Loop = Count Chests; 
	                    		Block block = world.getBlockAt(x, y, z);
//	                    		Material mat = block.getType();

//	                            Bukkit.getLogger().info("Block: ("+x+","+y+","+z+"): " + mat.name());
	                            
	                            if((block.getState() instanceof Container))
                     {		                            
	                            	Bukkit.getLogger().info("Block: ("+x+","+y+","+z+"): is a container");

	                            	String name = block.getType().name();	

		                            Bukkit.getLogger().info("Block: ("+x+","+y+","+z+"): is kinda chesty " + name);
	                            	switch(name) {
	                				case "BLAST_FURNACE":
	                				case "BREWING_STAND":
	                				case "FURNACE":
	                				case "HOPPER":
	                				case "SMOKER":
	                				case "DROPPER":
	                				case "DISPENSER":
	                				default:
	                					boolean alreadyFound = false;
	                					for(Block prev_block : chests) {
	                						if(prev_block.getLocation() == block.getLocation())
	                							alreadyFound = true;
	                					}
	                					if(!alreadyFound) {

			                    			chests_found++;
			                    			chests.add(block);
				                           	 Inventory inv = ((Container) block.getState()).getInventory();
				                        	 for(ItemStack i_stack : inv.getContents()) {
				                     			stacks.add(i_stack);
				                        	 }
				                        	 inv.clear();
				                        	 block.getState().update();
	                					}
	                			}
	                			
                     }
	                           
	                			
	                			
	                			
	                    		
                      
                     }
                  
                 }
                 
	              
             }

             Bukkit.getLogger().info("chests_found: "+chests_found);
             
             
             
             // empty the chests
             
             int total_item_stacks = 0;
             
             Bukkit.getLogger().info("LINE 109: ");
             for(ItemStack i_stack : stacks) {
            	 if(i_stack != null) {
                     Bukkit.broadcastMessage("Found:"+i_stack.getType());
                     total_item_stacks++;
            	 }
             }
             

             Bukkit.getLogger().info("Found item stacks: "+total_item_stacks);
             
             int min_inv_size = (int) (Math.ceil(total_item_stacks / 9.0)  * 9);

             Bukkit.getLogger().info("Creating inventory size: "+min_inv_size);


             

             Inventory sortable_inv = Bukkit.createInventory(new Sortable(), min_inv_size, "Example");

             Bukkit.getLogger().info("Created inventory size: "+min_inv_size);
             
             for(ItemStack i_stack : stacks) {
            	 if(i_stack != null) {
                     sortable_inv.addItem(i_stack);
            	 }
             }

             Bukkit.getLogger().info("Added stacks to inventory: ");
             
             SortIt.chestSortAPI.sortInventory(sortable_inv);
             
             ItemStack[] sorted_stacks = sortable_inv.getContents();
             stacks.clear();
             
             for(ItemStack a_stack : sorted_stacks) {
            	 stacks.add(a_stack);
             }
             
             
             for(Block block : chests) {

               	 Inventory inv = ((Container) block.getState()).getInventory();
               	 
               	 while(inv.firstEmpty() > -1 &&  stacks.size() > 0) {
                    ItemStack current = stacks.remove(0);

                    if (current != null) {
	                    HashMap<Integer, ItemStack> leftovers = inv.addItem(current);
	                    for (ItemStack i_stack : leftovers.values()) {
	                        stacks.add(i_stack);
	                    }
                    }
               	 }
            	 block.getState().update();
             }
           
             
             Bukkit.getLogger().info("LINE 113: ");
             
    	  }
          return true;
             

          
          
    }
//    	  // Borrowed from JEFF-Media-GbR / Spigot-InvUnloadPlus
//    		public static boolean isChestLikeBlock(Block block) {
//    			
//    		}
    		
}
