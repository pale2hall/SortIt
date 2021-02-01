package com.palecraft.sortit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import de.jeff_media.ChestSortAPI.ChestSort;
import de.jeff_media.ChestSortAPI.ChestSortAPI;
//import de.jeff_media.ChestSortAPI.ChestSortEvent;
//import de.jeff_media.ChestSortAPI.Sortable;

public class SortIt extends JavaPlugin {
	
	public static Map<String, String> var_map = new HashMap<String, String>();
	public static ChestSortAPI chestSortAPI;

			
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {

    	//Setup Config
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        config.addDefault("max_chests", 20);
        config.options().copyDefaults(true);
        saveConfig();
        
        
        ChestSort chestSort = (ChestSort) Bukkit.getServer().getPluginManager().getPlugin("ChestSort");

		// ChestSort is not installed
		if(chestSort==null) {
			getLogger().severe("Error: ChestSort is not installed.");
			return;
		}

		// Get the ChestSortAPI
		 chestSortAPI = chestSort.getAPI();
        
        
        
        //Sentinel values to check if player is sorting
    	var_map.put("is_sorting", "false");
    	
    	//TODO: log to sever don't broadcast
        Bukkit.broadcastMessage("Loaded SortIt v1.0");

        //Load listeners
        this.getCommand("sortit").setExecutor(new SortItCmd());
//        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        
        
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	

    }
    
  
}

