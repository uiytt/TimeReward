package fr.uiytt.timereward;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.leonhard.storage.Json;
import net.milkbowl.vault.economy.Economy;


public class Main extends JavaPlugin {

	private static Economy econ;
	public static final ConfigManager CONFIG = new ConfigManager();
	public static final Json DATASTORAGE = new Json("data","plugins/TimeReward");
	private static HashMap<String, Integer> time_playersonline = new HashMap<String, Integer>();
	private static JavaPlugin instance;
	
	
	@Override
	public void onEnable() {
		instance = this;

		
		
		//Setup the Economy using Vault
		if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault and Economy dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
		
		//Download the config.yml for github
		File configFile = new File(this.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
		configFile.getParentFile().mkdirs();
		if(!configFile.exists()) {
			getLogger().fine("Config.yml not found, Downloading...");
			try {
				FileUtils.copyURLToFile(new URL(ConfigManager.ConfigLink), configFile, 10000, 10000);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		//Load config
		CONFIG.load();
		
		//Register Listener
		this.getServer().getPluginManager().registerEvents(new EventsListener(), this);
		
		//Basic protection for /reload, because if a player is connected during /reload, it will never fire the onJoin event
		//This auto register players connected during /reload
		Bukkit.getOnlinePlayers().forEach(player -> {
			Integer time = Main.DATASTORAGE.getOrSetDefault(player.getUniqueId().toString(), 0);
			Main.getTimePlayersonline().put(player.getName(), time);
		});
		
		
		//Register BukkitRunnable (that will run every minute)
		new EveryMinute().runTaskTimerAsynchronously(this, 20, 20 * 60);
		
	}
	
	
	//see vault wiki 
	private boolean setupEconomy() {
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
		
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
	}
	
	

	public static HashMap<String, Integer> getTimePlayersonline() {
		return time_playersonline;
	}

	public static Economy getEcon() {
		return econ;
	}

	public static JavaPlugin getInstance() {
		return instance;
	}
}
