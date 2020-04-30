package fr.uiytt.timereward;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.leonhard.storage.Json;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	private static Economy econ;
	public static final ConfigManager CONFIG = new ConfigManager();
	public static final Json DATASTORAGE = new Json("data","plugins/TimeReward");
	private static HashMap<Player, Integer> time_playersonline = new HashMap<Player, Integer>();
	
	@Override
	public void onEnable() {
		//Setup the Economy using Vault
		if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault and Economy dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
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
		
		//Register BukkitRunnable (that will run every minute)
		new EveryMinute().runTaskTimerAsynchronously(this, 20, 20 * 60);
	}
	
	

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
	
	

	public static HashMap<Player, Integer> getTimePlayersonline() {
		return time_playersonline;
	}

	public static Economy getEcon() {
		return econ;
	}
}
